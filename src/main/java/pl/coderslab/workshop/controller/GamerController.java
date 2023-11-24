package pl.coderslab.workshop.controller;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.workshop.dtoForForms.GamersDTO;
import pl.coderslab.workshop.dtoForForms.GamersMatchStatsDTO;
import pl.coderslab.workshop.model.*;
import pl.coderslab.workshop.repository.*;

import java.io.*;
import java.util.Optional;

import static java.lang.Math.abs;

@Controller
public class GamerController {
    private final Gamer[] team1gamers;
    private final Gamer[] team2gamers;
    private final GamerRepository gamerRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final KillsAndCapsRepository killsAndCapsRepository;
    private final MatchGamerRepository matchGamerRepository;


    public GamerController(GamerRepository gamerRepository, MatchRepository matchRepository, TeamRepository teamRepository, KillsAndCapsRepository killsAndCapsRepository, MatchGamerRepository matchGamerRepository) {
        team1gamers = new Gamer[5];
        team2gamers = new Gamer[5];
        for (int a = 0; a < 5; a++) {
            team1gamers[a] = new Gamer();
            team2gamers[a] = new Gamer();
        }
        this.matchGamerRepository = matchGamerRepository;
        this.teamRepository = teamRepository;
        this.killsAndCapsRepository = killsAndCapsRepository;
        this.gamerRepository = gamerRepository;
        this.matchRepository = matchRepository;
    }


    //add players from data file
    @RequestMapping("/")
    public String readTextFile() throws IOException {
        long gamerCount = gamerRepository.count();
        if (gamerCount == 0) {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:all_data.txt");

            try (InputStream inputStream = resource.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] actualValue = line.split(" ");
                    gamerRepository.save(new Gamer(actualValue[0], Double.parseDouble(actualValue[1]), actualValue[7], actualValue[6]));
                }
            }
        }
        return "redirect:/pickTeams";
    }

    //start, get data and send it to view
    @GetMapping("/pickTeams")
    public String pickTeams(Model model) {
        Gamer[] gamers = gamerRepository.findAll().toArray(new Gamer[0]);
        model.addAttribute("gamers", gamers);
        model.addAttribute("servers", gamers[0].getAllServers());
        model.addAttribute("gamersDTO", new GamersDTO());
        return "gamer/pickTeams";
    }

    //find most balanced teams
    @PostMapping("/pickTeams")
    public String processForm(GamersDTO gamersDTO, Model model) {
        String server = gamersDTO.getServer();
        boolean teamsReady = gamersDTO.isTeamsReady();
        Gamer[] gamers = new Gamer[10];
        //apply handicap based on server and therefore players ping to that server
        for (int i = 0; i < 10; i++) {
            Optional<Gamer> optionalGamer = gamerRepository.findById(gamersDTO.getGamersList()[i]);

            if (optionalGamer.isPresent()) {
                gamers[i] = optionalGamer.get();
            } else {
                return "redirect:/pickTeams";
            }
            gamers[i].setMmr(gamers[i].getMmr() - gamers[i].serverHandicap(server));
        }
        //If the checkmark teamsReady was selected, teams are selected in accordance with the order of players entered in the pickTeams function.
        if (teamsReady) {
            for (int i = 0; i < 5; i++) {
                team1gamers[i] = gamers[i];
                team2gamers[i] = gamers[i + 5];
            }
            checkTeam1(team1gamers, server);
            checkTeam2(team2gamers, server);
            //algorithm for finding possibly most balanced teams - the algorithm checks all combinations without repetition
        } else {
            //find perfectBalance score
            double perfectBalance = checkPerfectBalance(gamers);
            int testIf5 = 0, d = 0;
            double mmrCounter = 0, currentDiffFromPerfectBalance, bestScoreSoFar = 0, smallestDiff = 1000;
            //find a team whose difference in mmr points with perfectBalance is as small as possible
            for (int i = 1023; i > 0; i--) {

                for (int a = 0; a < 10; ++a) {
                    if (((i >> a) & 1) == 1) {
                        mmrCounter += gamers[a].getMmr();
                        testIf5++;
                    }
                }
                currentDiffFromPerfectBalance = abs(mmrCounter - perfectBalance);
                if (currentDiffFromPerfectBalance < smallestDiff && testIf5 == 5) {
                    bestScoreSoFar = mmrCounter;
                    smallestDiff = currentDiffFromPerfectBalance;
                    for (int a = 0; a < 10; ++a) {
                        if (((i >> a) & 1) == 1) {
                            team1gamers[d].cloneValues(gamers[a]);
                            d++;
                        }
                    }
                }
                mmrCounter = 0;
                testIf5 = 0;
                d = 0;
            }
            System.out.println("team nr1 points - " + Math.round(bestScoreSoFar * 10) / 10f + "\n");
            checkTeam1(team1gamers, server);

            int wasInTeam1 = 0, counter = 0;
            double bestScoreSoFar2 = 0;
            //get remaining players into team2
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 5; b++) {
                    if (gamers[a].getName().equals(team1gamers[b].getName())) {
                        wasInTeam1++;
                    }
                }
                if (wasInTeam1 == 0) {
                    team2gamers[counter].cloneValues(gamers[a]);
                    bestScoreSoFar2 += team2gamers[counter].getMmr();
                    counter++;
                }
                wasInTeam1 = 0;
            }
            System.out.println("team nr2 points - " + Math.round(bestScoreSoFar2 * 10) / 10f + "\n");
            checkTeam2(team2gamers, server);
        }
        model.addAttribute("team1", team1gamers);
        model.addAttribute("team2", team2gamers);
        model.addAttribute("server", server);
        model.addAttribute("gamersMatchStatsDTO", new GamersMatchStatsDTO());
        return "gamer/teamsScores";
    }

    //update scores of players
    @PostMapping("/updateScores")
    public String updateScores(GamersMatchStatsDTO gamersMatchStatsDTO, Model model) {

        String server = gamersMatchStatsDTO.getServer();
        boolean suddenDeath = gamersMatchStatsDTO.isSuddenDeath();
        String suddenDeathWhoWon = gamersMatchStatsDTO.getSuddenDeathWhoWon();
        String[] sTeam1titans = gamersMatchStatsDTO.getTeam1titans();
        String[] sTeam2titans = gamersMatchStatsDTO.getTeam2titans();
        int[] team1gamersId = gamersMatchStatsDTO.getTeam1gamersId();
        int[] team1elims = gamersMatchStatsDTO.getTeam1elims();
        int[] team1flags = gamersMatchStatsDTO.getTeam1flags();
        int[] team2gamersId = gamersMatchStatsDTO.getTeam2gamersId();
        int[] team2elims = gamersMatchStatsDTO.getTeam2elims();
        int[] team2flags = gamersMatchStatsDTO.getTeam2flags();
        String mapPlayed = gamersMatchStatsDTO.getMapPlayed();
        System.out.println(gamersMatchStatsDTO);
        int team1flagsTotal = 0, team2flagsTotal = 0, whoWon = 0, streak = 0, streak2 = 0;
     /*
      team that scored more flags wins the game; whoWon = 1 -> team 1 won; whoWon = 2 -> team 2 won, suddenDeath is special case when the time
      ends and both teams have equal flags amount -> last one standing is the winner
      */
        for (int i = 0; i < 5; i++) {
            team1flagsTotal += team1flags[i];
            team2flagsTotal += team2flags[i];
        }
        if (team1flagsTotal > team2flagsTotal) {
            whoWon = 1;
        } else if (team1flagsTotal < team2flagsTotal) {
            whoWon = 2;
        } else {
            if (suddenDeathWhoWon==null) {
                model.addAttribute("team1", team1gamers);
                model.addAttribute("team2", team2gamers);
                model.addAttribute("server", server);
                return "gamer/teamsScores";
            }
            else if (suddenDeathWhoWon.equals("team1")) {
                whoWon = 1;
            }
            else if (suddenDeathWhoWon.equals("team2")) {
                whoWon = 2;
            }

        }
        //add match to database
        Match match = new Match();
        match.setMap(Match.Map_Name.valueOf(mapPlayed));
        match.setServer(server);
        matchRepository.save(match);
        //add teams to database
        Team team1 = new Team();
        Team team2 = new Team();
        team1.setFlagAdvantage(team1flagsTotal - team2flagsTotal);
        team2.setFlagAdvantage(team2flagsTotal - team1flagsTotal);

        if (whoWon == 1) {
            team1.setWinOrLoose(1);
            team2.setWinOrLoose(0);
        }
        if (whoWon == 2) {
            team1.setWinOrLoose(0);
            team2.setWinOrLoose(1);
        }
        teamRepository.save(team1);
        teamRepository.save(team2);
        //change players mmr
        for (int i = 0; i < 5; i++) {
            Optional<Gamer> optionalGamer = gamerRepository.findById(team1gamersId[i]);

            if (optionalGamer.isPresent()) {
                team1gamers[i] = optionalGamer.get();
            } else {
                return "redirect:/pickTeams";
            }
            Optional<Gamer> optionalGamer2 = gamerRepository.findById(team2gamersId[i]);

            if (optionalGamer2.isPresent()) {
                team2gamers[i] = optionalGamer.get();
            } else {
                return "redirect:/pickTeams";
            }
            //lastTen is the binary representation of last 10 games where 0 represents a loss and 1 represents a win - so e.g 1011 is: win loss win win
            int countDown = Integer.parseInt(team1gamers[i].getLastTen(), 2);
            int countDown2 = Integer.parseInt(team2gamers[i].getLastTen(), 2);

            for (int a = 0; a < 10; a++) {
                if ((countDown & 1) == 1) streak++;
                countDown = countDown >> 1;
                if ((countDown2 & 1) == 1) streak2++;
                countDown2 = countDown2 >> 1;
            }
            //apply bonus from last 10 winrate
            double points = 0, points2 = 0;
            if ((streak == 7 || streak == 8) && whoWon == 1) points = 1.2d;
            else if ((streak == 2 || streak == 3) && whoWon == 2) points = -1.2d;
            else if (streak > 1 && whoWon == 2) points = -1;
            else if (streak >= 9 && whoWon == 1) points = 1.5d;
            else if (streak <= 1 && whoWon == 2) points = -1.5d;
            else if (streak < 9 && whoWon == 1) points = 1;

            if ((streak2 == 7 || streak2 == 8) && whoWon == 2) points2 = 1.2d;
            else if ((streak2 == 2 || streak2 == 3) && whoWon == 1) points2 = -1.2d;
            else if (streak2 > 1 && whoWon == 1) points2 = -1;
            else if (streak2 >= 9 && whoWon == 2) points2 = 1.5d;
            else if (streak2 <= 1 && whoWon == 1) points2 = -1.5d;
            else if (streak2 < 9 && whoWon == 2) points2 = 1;

            //apply bonus from flag advantage
            if (whoWon == 1) {
                points = points + (team1.getFlagAdvantage() / 5.0d) - 0.2d;
                points2 = points2 + (team2.getFlagAdvantage() / 5.0d) + 0.2d;
                //change score if suddenDeath
                if (suddenDeath) {
                    points = 0.5d;
                    points2 = -0.5d;
                    //update last ten; suddenDeath result is not counted neither as win or loss for last 10
                } else {
                    team1gamers[i].setLastTen(Integer.toBinaryString((Integer.parseInt(team1gamers[i].getLastTen(), 2) >> 1) | 512));
                    team2gamers[i].setLastTen(Integer.toBinaryString(Integer.parseInt(team2gamers[i].getLastTen(), 2) >> 1));
                }
            }
            if (whoWon == 2) {
                points2 = points2 + team2.getFlagAdvantage() / 5.0d - 0.2d;
                points = points + team1.getFlagAdvantage() / 5.0d + 0.2d;
                //change score if suddenDeath
                if (suddenDeath) {
                    points = -0.5d;
                    points2 = 0.5d;
                    //update last ten; suddenDeath result is not counted neither as win or loss for last 10
                } else {
                    team1gamers[i].setLastTen(Integer.toBinaryString(Integer.parseInt(team1gamers[i].getLastTen(), 2) >> 1));
                    team2gamers[i].setLastTen(Integer.toBinaryString((Integer.parseInt(team2gamers[i].getLastTen(), 2) >> 1) | 512));
                }
            }
            team1gamers[i].setMmr(Math.round((team1gamers[i].getMmr() + points) * 10) / 10d);
            team2gamers[i].setMmr(Math.round((team2gamers[i].getMmr() + points2) * 10) / 10d);
            //update players in database
            gamerRepository.save(team1gamers[i]);
            gamerRepository.save(team2gamers[i]);
            streak = 0;
            streak2 = 0;
        }
        //save matchGamers and killsAndCapsStats in database
        for (int i = 0; i < 5; i++) {
            MatchGamer matchGamer1 = new MatchGamer();
            MatchGamer matchGamer2 = new MatchGamer();

            matchGamer1.setGamer(team1gamers[i]);
            matchGamer1.setMatch(match);
            matchGamer1.setTeam(team1);
            matchGamer2.setGamer(team2gamers[i]);
            matchGamer2.setMatch(match);
            matchGamer2.setTeam(team2);

            matchGamerRepository.save(matchGamer1);
            matchGamerRepository.save(matchGamer2);

            KillsAndCaps killsAndCaps1 = new KillsAndCaps();
            KillsAndCaps killsAndCaps2 = new KillsAndCaps();

            killsAndCaps1.setKills(team1elims[i]);
            killsAndCaps1.setCaps(team1flags[i]);
            killsAndCaps1.setTitan(KillsAndCaps.Titan_Name.valueOf(sTeam1titans[i]));
            killsAndCaps1.setMatchGamer(matchGamer1);

            killsAndCaps2.setKills(team2elims[i]);
            killsAndCaps2.setCaps(team2flags[i]);
            killsAndCaps2.setTitan(KillsAndCaps.Titan_Name.valueOf(sTeam2titans[i]));
            killsAndCaps2.setMatchGamer(matchGamer2);

            killsAndCapsRepository.save(killsAndCaps1);
            killsAndCapsRepository.save(killsAndCaps2);
        }
        model.addAttribute("team1", team1gamers);
        model.addAttribute("team2", team2gamers);
        model.addAttribute("server", server);
        return "gamer/teamsScores";
    }

    public void checkTeam1(Gamer[] team1, String server) {
        for (Gamer a : team1) {
            System.out.println(a.getName() + " - " + a.getMmr() + " - " + " - handicap -" + a.serverHandicap(server));
        }
        System.out.println("team1 checked \n");
    }

    public void checkTeam2(Gamer[] team2, String server) {
        for (Gamer a : team2) {
            System.out.println(a.getName() + " - " + a.getMmr() + " - " + " - handicap -" + a.serverHandicap(server));
        }
        System.out.println("team2 checked \n");
    }

    public double checkPerfectBalance(Gamer[] lobby) {
        double sum = 0;
        for (Gamer a : lobby) {
            sum = sum + a.getMmr();
        }
        double perfectBalance = sum / 2;
        System.out.println("perfect balance is - " + Math.round(perfectBalance * 10) / 10f + "\n");
        return perfectBalance;
    }
}

package mrblablak.ranking.workshop.service;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.repository.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatsService {
    private static final int TEAM_SIZE = 5;
    private static final int LOBBY_SIZE = 10;
    private String server;
    private final GamerRepository gamerRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final KillsAndCapsRepository killsAndCapsRepository;
    private final MatchGamerRepository matchGamerRepository;
    private final Gamer[] team1gamers = new Gamer[TEAM_SIZE];
    private final Gamer[] team2gamers = new Gamer[TEAM_SIZE];

    public void addDataIfEmpty() throws IOException {
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
    }
    public boolean calculateMmr(GamersMatchStatsDTO gamersMatchStatsDTO) {
        /*
      team that scored more flags wins the game; whoWon = 1 -> team 1 won; whoWon = 2 -> team 2 won, suddenDeath is special case when the time
      ends and both teams have equal flags amount -> last one standing is the winner
      */
        server = gamersMatchStatsDTO.getServer();
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

        Match match = new Match();
        Team team1 = new Team();
        Team team2 = new Team();
        MatchGamer[] matchGamers = new MatchGamer[LOBBY_SIZE];
        KillsAndCaps[] killsAndCaps = new KillsAndCaps[LOBBY_SIZE];

        setMatch(match, mapPlayed);
        int whoWon = setTeams(team1, team2, team1flags, team2flags, suddenDeathWhoWon);
        boolean isValidated = updateGamers(whoWon, team1gamersId, team2gamersId, team1, team2, suddenDeath);

        setMatchGamers(matchGamers, match, team1, team2);
        setKillsAndCaps(killsAndCaps, team1elims, team2elims, team1flags, team2flags, sTeam1titans, sTeam2titans, matchGamers);
        if (whoWon != 0 && isValidated) {
            saveData(matchGamers, killsAndCaps, match, team1, team2);
        }
        if (isValidated) {
            return true;
        } else {
            return false;
        }
    }

    public void setMatch(Match match, String mapPlayed) {
        match.setMap(Match.Map_Name.valueOf(mapPlayed));
        match.setServer(server);
    }

    public int setTeams(Team team1, Team team2,  int[] team1flags, int[] team2flags, String suddenDeathWhoWon) {
        int team1flagsTotal = 0, team2flagsTotal = 0, whoWon=0;
        for (int i = 0; i < TEAM_SIZE; i++) {
            team1flagsTotal += team1flags[i];
            team2flagsTotal += team2flags[i];
        }
        if (team1flagsTotal > team2flagsTotal) {
            whoWon = 1;
        } else if (team1flagsTotal < team2flagsTotal) {
            whoWon = 2;
        } else {
            if (suddenDeathWhoWon == null) {
                whoWon = 0;
            } else if (suddenDeathWhoWon.equals("team1")) {
                whoWon = 1;
            } else if (suddenDeathWhoWon.equals("team2")) {
                whoWon = 2;
            }

        }
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
        return whoWon;
    }

    public boolean updateGamers(int whoWon, int[] team1gamersId, int[] team2gamersId, Team team1, Team team2, boolean suddenDeath) {
        int streak = 0, streak2 = 0;
        for (int i = 0; i < TEAM_SIZE; i++) {
            Optional<Gamer> optionalGamer = gamerRepository.findById(team1gamersId[i]);

            if (optionalGamer.isPresent()) {
                team1gamers[i] = optionalGamer.get();
            } else {
                return false;
            }
            Optional<Gamer> optionalGamer2 = gamerRepository.findById(team2gamersId[i]);

            if (optionalGamer2.isPresent()) {
                team2gamers[i] = optionalGamer2.get();
            } else {
                return false;
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
            streak = 0;
            streak2 = 0;

        }
        return true;
    }

    public void setMatchGamers(MatchGamer[] matchGamers, Match match, Team team1, Team team2) {
        int counter=0;
        for (int i = 0; i < LOBBY_SIZE; i++) {
            matchGamers[i] = new MatchGamer();
            matchGamers[i].setGamer(team1gamers[counter]);
            matchGamers[i].setMatch(match);
            matchGamers[i].setTeam(team1);
            i++;
            matchGamers[i] = new MatchGamer();
            matchGamers[i].setGamer(team2gamers[counter]);
            matchGamers[i].setMatch(match);
            matchGamers[i].setTeam(team2);
            counter++;
        }
    }

    public void setKillsAndCaps(KillsAndCaps[] killsAndCaps, int[] team1elims, int[] team2elims, int[] team1flags, int[] team2flags, String[] sTeam1titans, String[] sTeam2titans, MatchGamer[] matchGamers) {
        int counter=0;
        for (int i = 0; i < LOBBY_SIZE; i++) {
            killsAndCaps[i] = new KillsAndCaps();
            killsAndCaps[i].setKills(team1elims[counter]);
            killsAndCaps[i].setCaps(team1flags[counter]);
            killsAndCaps[i].setTitan(KillsAndCaps.Titan_Name.valueOf(sTeam1titans[counter]));
            killsAndCaps[i].setMatchGamer(matchGamers[i]);
            System.out.println(killsAndCaps[i]);
            i++;
            killsAndCaps[i] = new KillsAndCaps();
            killsAndCaps[i].setKills(team2elims[counter]);
            killsAndCaps[i].setCaps(team2flags[counter]);
            killsAndCaps[i].setTitan(KillsAndCaps.Titan_Name.valueOf(sTeam2titans[counter]));
            killsAndCaps[i].setMatchGamer(matchGamers[i]);
            System.out.println(killsAndCaps[i]);
            counter++;
        }
    }

    public void saveData(MatchGamer[] matchGamers, KillsAndCaps[] killsAndCaps, Match match, Team team1, Team team2) {
        matchRepository.save(match);
        teamRepository.save(team1);
        teamRepository.save(team2);
        for (int i = 0; i < TEAM_SIZE; i++) {
            gamerRepository.save(team1gamers[i]);
            gamerRepository.save(team2gamers[i]);
        }
        for (int i = 0; i < LOBBY_SIZE; i++) {
            matchGamerRepository.save(matchGamers[i]);
            killsAndCapsRepository.save(killsAndCaps[i]);
        }
    }

    public Gamer[] getTeam1() {
        return team1gamers;
    }

    public Gamer[] getTeam2() {
        return team2gamers;
    }

    public String getServer() {
        return server;
    }
}

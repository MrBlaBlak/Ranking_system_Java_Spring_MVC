package mrblablak.ranking.workshop.service;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersDTO;
import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.repository.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static java.lang.Math.abs;
@Service
@RequiredArgsConstructor
public class LobbyService {
    private final GamerRepository gamerRepository;
    private static final int TEAM_SIZE = 5;
    private static final int LOBBY_SIZE = 10;

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final KillsAndCapsRepository killsAndCapsRepository;
    private final MatchGamerRepository matchGamerRepository;
    private static final Gamer[] team1gamers = new Gamer[TEAM_SIZE];
    private static final Gamer[] team2gamers = new Gamer[TEAM_SIZE];
    private String server;
    public List<Gamer> getAllGamers() {
        return gamerRepository.findAll();
    }

    public boolean processTeams(GamersDTO gamersDTO) {
        server = gamersDTO.getServer();
        boolean teamsReady = gamersDTO.isTeamsReady();
        Gamer[] gamers = getGamersWithHandicap(gamersDTO);

        if (teamsReady) {
            prepareTeamsFromGamers(gamers);
            checkAndPrintTeams(server);
        } else {
            calculateAndAssignTeams(gamers);
            checkAndPrintTeams(server);
        }
        return true;
    }
    private Gamer[] getGamersWithHandicap(GamersDTO gamersDTO) {
        Gamer[] gamers = new Gamer[LOBBY_SIZE];

        for (int i = 0; i < LOBBY_SIZE; i++) {
            Optional<Gamer> optionalGamer = gamerRepository.findById(gamersDTO.getGamersList()[i]);

            if (optionalGamer.isPresent()) {
                gamers[i] = optionalGamer.get();
                gamers[i].setMmr(gamers[i].getMmr() - gamers[i].serverHandicap(server));
            } else {
                return new Gamer[0];
            }
        }

        return gamers;
    }
    public void setHandicap(){
        for (int i = 0; i < TEAM_SIZE; i++) {
            team1gamers[i].setMmr(team1gamers[i].getMmr() - team1gamers[i].serverHandicap(server));
            team2gamers[i].setMmr(team2gamers[i].getMmr() - team2gamers[i].serverHandicap(server));
        }
    }
    private void prepareTeamsFromGamers(Gamer[] gamers) {
        for (int i = 0; i < TEAM_SIZE; i++) {
            team1gamers[i] = new Gamer();
            team1gamers[i].cloneValues(gamers[i]);
            team2gamers[i] = new Gamer();
            team2gamers[i].cloneValues(gamers[i + TEAM_SIZE]);
        }
    }

    private void calculateAndAssignTeams(Gamer[] gamers) {
        double perfectBalance = checkPerfectBalance(gamers);
        int gamersCount = 0;
        double mmrCounter = 0, currentDiffFromPerfectBalance, bestScoreSoFar = 0, smallestDiff = 1000;

        for (int i = 1023; i > 0; i--) {
            for (int a = 0; a < LOBBY_SIZE; ++a) {
                if (((i >> a) & 1) == 1) {
                    mmrCounter += gamers[a].getMmr();
                    gamersCount++;
                }
            }
            currentDiffFromPerfectBalance = abs(mmrCounter - perfectBalance);
            if (currentDiffFromPerfectBalance < smallestDiff && gamersCount == 5) {
                bestScoreSoFar = mmrCounter;
                smallestDiff = currentDiffFromPerfectBalance;
                assignTeam1Gamers(gamers, i);
            }
            mmrCounter = 0;
            gamersCount = 0;

        }

        double bestScoreSoFar2 = assignTeam2Gamers(gamers);

        System.out.println("team nr1 points - " + Math.round(bestScoreSoFar * 10) / 10f + "\n");
        System.out.println("team nr2 points - " + Math.round(bestScoreSoFar2 * 10) / 10f + "\n");
    }

    private void assignTeam1Gamers(Gamer[] gamers, int bitmask) {
        int counter=0;
        for (int a = 0; a < LOBBY_SIZE; ++a) {
            if (((bitmask >> a) & 1) == 1) {
                team1gamers[counter] = new Gamer();
                team1gamers[counter].cloneValues(gamers[a]);
                counter++;
            }
        }
    }

    private double assignTeam2Gamers(Gamer[] gamers) {
        int counter = 0;
        int wasInTeam1 = 0;
        double bestScoreSoFar2 = 0;

        for (int a = 0; a < LOBBY_SIZE; a++) {
            for (int b = 0; b < TEAM_SIZE; b++) {
                if (gamers[a].getName().equals(team1gamers[b].getName())) {
                    wasInTeam1++;
                }
            }
            if (wasInTeam1 == 0) {
                team2gamers[counter] = new Gamer();
                team2gamers[counter].cloneValues(gamers[a]);
                bestScoreSoFar2 += team2gamers[counter].getMmr();
                counter++;
            }
            wasInTeam1 = 0;
        }
        return bestScoreSoFar2;
    }

    private void checkAndPrintTeams(String server) {
        checkTeam(team1gamers, server, 1);
        checkTeam(team2gamers, server, 2);
    }
    public void checkTeam(Gamer[] team1, String server, int number) {
        for (Gamer a : team1) {
            if (a != null) {
                System.out.println(a.getName() + " - " + a.getMmr() + " - " + " - handicap -" + a.serverHandicap(server));
            } else {
                System.out.println("Encountered a null Gamer object in the team.");
            }
        }
        System.out.format("team%d checked \n",number);
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
        setHandicap();
        return isValidated;
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
            //lastTen is the binary representation of last 10 games where 0 represents a loss and 1 represents a win - so e.g. 1011 is: win loss win win
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
                    //update last ten; suddenDeath result is not counted neither as win nor loss for last 10
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
                    //update last ten; suddenDeath result is not counted neither as win nor loss for last 10
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
            i++;
            killsAndCaps[i] = new KillsAndCaps();
            killsAndCaps[i].setKills(team2elims[counter]);
            killsAndCaps[i].setCaps(team2flags[counter]);
            killsAndCaps[i].setTitan(KillsAndCaps.Titan_Name.valueOf(sTeam2titans[counter]));
            killsAndCaps[i].setMatchGamer(matchGamers[i]);
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

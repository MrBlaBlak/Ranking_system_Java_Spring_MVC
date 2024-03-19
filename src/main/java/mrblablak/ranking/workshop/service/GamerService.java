package mrblablak.ranking.workshop.service;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersDTO;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.repository.GamerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.abs;
@Service
@RequiredArgsConstructor
public class GamerService {
    private final GamerRepository gamerRepository;
    private static final int TEAM_SIZE = 5;
    private static final int LOBBY_SIZE = 10;
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
                return new Gamer[0]; // lub rzuć wyjątek, zależnie od potrzeb
            }
        }

        return gamers;
    }

    private void prepareTeamsFromGamers(Gamer[] gamers) {
        for (int i = 0; i < TEAM_SIZE; i++) {
            team1gamers[i].cloneValues(gamers[i]);
            team2gamers[i].cloneValues(gamers[i + TEAM_SIZE]);
        }
    }

    private void calculateAndAssignTeams(Gamer[] gamers) {
        double perfectBalance = checkPerfectBalance(gamers);
        int gamersCount = 0, counter = 0;
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
                assignTeam1Gamers(gamers, i, counter);
            }
            mmrCounter = 0;
            gamersCount = 0;
            counter = 0;
        }

        double bestScoreSoFar2 = assignTeam2Gamers(gamers);

        System.out.println("team nr1 points - " + Math.round(bestScoreSoFar * 10) / 10f + "\n");
        System.out.println("team nr2 points - " + Math.round(bestScoreSoFar2 * 10) / 10f + "\n");
    }

    private void assignTeam1Gamers(Gamer[] gamers, int bitmask, int counter) {
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
        checkTeam1(team1gamers, server);
        checkTeam2(team2gamers, server);
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

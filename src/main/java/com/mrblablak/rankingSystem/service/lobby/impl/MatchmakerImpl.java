package com.mrblablak.rankingSystem.service.lobby.impl;

import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.model.Gamer;
import com.mrblablak.rankingSystem.service.lobby.Matchmaker;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchmakerImpl implements Matchmaker {
    private static final int TEAM_SIZE = 5;
    private static final int LOBBY_SIZE = 10;

    @Override
    public void calculateAndAssignTeams(Gamer[] gamers, Gamer[] team1gamers, Gamer[] team2gamers) {
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
            currentDiffFromPerfectBalance = Math.abs(mmrCounter - perfectBalance);
            if (currentDiffFromPerfectBalance < smallestDiff && gamersCount == 5) {
                bestScoreSoFar = mmrCounter;
                smallestDiff = currentDiffFromPerfectBalance;
                assignTeam1Gamers(gamers, i, team1gamers, team2gamers);
            }
            mmrCounter = 0;
            gamersCount = 0;
        }

        double bestScoreSoFar2 = assignTeam2Gamers(gamers, team1gamers, team2gamers);

        System.out.println("team nr1 points - " + Math.round(bestScoreSoFar * 10) / 10f + "\n");
        System.out.println("team nr2 points - " + Math.round(bestScoreSoFar2 * 10) / 10f + "\n");
    }

    private double checkPerfectBalance(Gamer[] lobby) {
        double sum = 0;
        for (Gamer a : lobby) {
            sum = sum + a.getMmr();
        }
        double perfectBalance = sum / 2;
        System.out.println("perfect balance is - " + Math.round(perfectBalance * 10) / 10f + "\n");
        return perfectBalance;
    }

    private void assignTeam1Gamers(Gamer[] gamers, int bitmask, Gamer[] team1gamers, Gamer[] team2gamers) {
        int counter = 0;
        for (int a = 0; a < LOBBY_SIZE; ++a) {
            if (((bitmask >> a) & 1) == 1) {
                team1gamers[counter] = new Gamer();
                team1gamers[counter].cloneValues(gamers[a]);
                counter++;
            }
        }
    }

    private double assignTeam2Gamers(Gamer[] gamers, Gamer[] team1gamers, Gamer[] team2gamers) {
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
}

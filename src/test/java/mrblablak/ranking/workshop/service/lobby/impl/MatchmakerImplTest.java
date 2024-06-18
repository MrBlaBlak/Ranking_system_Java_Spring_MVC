package mrblablak.ranking.workshop.service.lobby.impl;

import mrblablak.ranking.workshop.model.Gamer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class MatchmakerImplTest {

    private MatchmakerImpl matchmaker;
    private Gamer[] lobby;
    private Gamer[] team1;
    private Gamer[] team2;

    @BeforeEach
    void setUp() {
        matchmaker = new MatchmakerImpl();
        lobby = new Gamer[10];
        team1 = new Gamer[5];
        team2 = new Gamer[5];

        // Initialize the gamers in the lobby with dummy data
        for (int i = 0; i < 10; i++) {
            double randomMMR = 500 + ThreadLocalRandom.current().nextDouble() * 150;
            randomMMR = Math.round(randomMMR * 10) / 10.0;
            lobby[i] = new Gamer("Gamer" + i, randomMMR, "EU", "1010101010");
        }
    }

    @RepeatedTest(10)
    void testCalculateAndAssignTeams_balancedTeams() {
        matchmaker.calculateAndAssignTeams(lobby, team1, team2);
//        printTeam(team1);
//        printTeam(team2);

        double team1MMR = calculateTotalMMR(team1);
        double team2MMR = calculateTotalMMR(team2);

        // Check that the MMRs are balanced
        double perfectBalance = (calculateTotalMMR(lobby) / 2.0);
        assertEquals(perfectBalance, team1MMR, 20); // Allow some tolerance
        assertEquals(perfectBalance, team2MMR, 20); // Allow some tolerance
    }

    @RepeatedTest(10)
    void testCalculateAndAssignTeams_teamsHaveFivePlayers() {
        matchmaker.calculateAndAssignTeams(lobby, team1, team2);

        // Verify both teams have exactly 5 players
        assertEquals(5, team1.length);
        assertEquals(5, team2.length);

        // Verify none of the team slots are null
        for (Gamer g : team1) {
            assertNotNull(g);
        }
        for (Gamer g : team2) {
            assertNotNull(g);
        }
    }

    private double calculateTotalMMR(Gamer[] team) {
        return Arrays.stream(team)
                .mapToDouble(Gamer::getMmr)
                .sum();
    }

    private void printTeam(Gamer[] team) {
        Arrays.stream(team)
                .forEach(System.out::println);
    }
}
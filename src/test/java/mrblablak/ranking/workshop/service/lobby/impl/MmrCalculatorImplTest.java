package mrblablak.ranking.workshop.service.lobby.impl;

import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.model.Team;
import mrblablak.ranking.workshop.service.lobby.MmrCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MmrCalculatorImplTest {

    private MmrCalculator mmrCalculator;

    private Gamer[] team1Gamers;
    private Gamer[] team2Gamers;
    private Team team1;
    private Team team2;

    @BeforeEach
    void setUp() {

        mmrCalculator = new MmrCalculatorImpl();

        team1Gamers = new Gamer[5];
        team2Gamers = new Gamer[5];

        for (int i = 0; i < 5; i++) {
            team1Gamers[i] = new Gamer("Gamer" + i, 500.0, "EU", "1111100000");
            team2Gamers[i] = new Gamer("Gamer" + (i + 5), 500.0, "EU", "1111100000");
        }

    }

    @Test
    void testCalculateMmr_team1Wins() {
        team1 = new Team(0,0, 1);
        team2 = new Team(0,0, -1);
        boolean result = mmrCalculator.calculateMmr(1, team1Gamers, team2Gamers, team1, team2, false);

        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(501.0, gamer.getMmr());
            assertEquals("1111110000", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(499.0, gamer.getMmr());
            assertEquals("111110000", gamer.getLastTen());
        }

        assertEquals(true, result);
    }

    @Test
    void testCalculateMmr_team2Wins() {
        team1 = new Team(0,0, -1);
        team2 = new Team(0,0, 1);
        boolean result = mmrCalculator.calculateMmr(2, team1Gamers, team2Gamers, team1, team2, false);

        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(499.0, gamer.getMmr());
            assertEquals("111110000", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(501.0, gamer.getMmr());
            assertEquals("1111110000", gamer.getLastTen());
        }

        assertEquals(true, result);
    }
    @Test
    void testCalculateMmr_team1Wins_2flagAdvantage() {
        team1 = new Team(0,1, 2);
        team2 = new Team(0,0, -2);
        boolean result = mmrCalculator.calculateMmr(1, team1Gamers, team2Gamers, team1, team2, false);
        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(501.2, gamer.getMmr());
            assertEquals("1111110000", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(498.8, gamer.getMmr());
            assertEquals("111110000", gamer.getLastTen());
        }

        assertEquals(true, result);
    }

    @Test
    void testCalculateMmr_team2Wins_2flagAdvantage() {
        team1 = new Team(0,0, -2);
        team2 = new Team(0,1, 2);
        boolean result = mmrCalculator.calculateMmr(2, team1Gamers, team2Gamers, team1, team2, false);
        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(498.8, gamer.getMmr());
            assertEquals("111110000", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(501.2, gamer.getMmr());
            assertEquals("1111110000", gamer.getLastTen());
        }

        assertEquals(true, result);
    }
    @Test
    void testCalculateMmr_suddenDeath_team1Wins() {
        team1 = new Team(0,1, 0);
        team2 = new Team(0,0, 0);
        boolean result = mmrCalculator.calculateMmr(1, team1Gamers, team2Gamers, team1, team2, true);

        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(500.5, gamer.getMmr());
            assertEquals("1111100000", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(499.5, gamer.getMmr());
            assertEquals("1111100000", gamer.getLastTen());
        }

        assertEquals(true, result);
    }
    @Test
    void testCalculateMmr_suddenDeath_team2Wins() {
        team1 = new Team(0,0, 0);
        team2 = new Team(0,1, 0);
        boolean result = mmrCalculator.calculateMmr(2, team1Gamers, team2Gamers, team1, team2, true);

        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(499.5, gamer.getMmr());
            assertEquals("1111100000", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(500.5, gamer.getMmr());
            assertEquals("1111100000", gamer.getLastTen());
        }

        assertEquals(true, result);
    }
    @Test
    void testCalculateMmr_team1WinsPositiveStreak() {
        team1 = new Team(0,1, 1);
        team2 = new Team(0,0, -1);
        for (int i = 0; i < 5; i++) {
            team1Gamers[i].setLastTen("1111111000");
            team2Gamers[i].setLastTen("1111111000");
        }
        boolean result = mmrCalculator.calculateMmr(1, team1Gamers, team2Gamers, team1, team2, false);

        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(501.2, gamer.getMmr());
            assertEquals("1111111100", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(499.0, gamer.getMmr());
            assertEquals("111111100", gamer.getLastTen());
        }

        assertEquals(true, result);
    }
    @Test
    void testCalculateMmr_team1WinsNegativeStreak() {
        team1 = new Team(0,1, 1);
        team2 = new Team(0,0, -1);
        for (int i = 0; i < 5; i++) {
            team1Gamers[i].setLastTen("1110000000");
            team2Gamers[i].setLastTen("1110000000");
        }
        boolean result = mmrCalculator.calculateMmr(1, team1Gamers, team2Gamers, team1, team2, false);

        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(501.0, gamer.getMmr());
            assertEquals("1111000000", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(498.8, gamer.getMmr());
            assertEquals("111000000", gamer.getLastTen());
        }

        assertEquals(true, result);
    }
    @Test
    void testCalculateMmr_team2WinsPositiveStreak() {
        team1 = new Team(0,0, -1);
        team2 = new Team(0,1, 1);
        for (int i = 0; i < 5; i++) {
            team1Gamers[i].setLastTen("1111111000");
            team2Gamers[i].setLastTen("1111111000");
        }
        boolean result = mmrCalculator.calculateMmr(2, team1Gamers, team2Gamers, team1, team2, false);

        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(499.0, gamer.getMmr());
            assertEquals("111111100", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(501.2, gamer.getMmr());
            assertEquals("1111111100", gamer.getLastTen());
        }

        assertEquals(true, result);
    }
    @Test
    void testCalculateMmr_team2WinsNegativeStreak() {
        team1 = new Team(0,0, -1);
        team2 = new Team(0,1, 1);
        for (int i = 0; i < 5; i++) {
            team1Gamers[i].setLastTen("1110000000");
            team2Gamers[i].setLastTen("1110000000");
        }
        boolean result = mmrCalculator.calculateMmr(2, team1Gamers, team2Gamers, team1, team2, false);

        // Verify the MMR changes for each gamer in team1 and team2
        for (Gamer gamer : team1Gamers) {
            assertEquals(498.8, gamer.getMmr());
            assertEquals("111000000", gamer.getLastTen());
        }
        for (Gamer gamer : team2Gamers) {
            assertEquals(501.0, gamer.getMmr());
            assertEquals("1111000000", gamer.getLastTen());
        }

        assertEquals(true, result);
    }

}
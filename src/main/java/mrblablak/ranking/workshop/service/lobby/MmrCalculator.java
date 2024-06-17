package mrblablak.ranking.workshop.service.lobby;

import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.model.Team;

public interface MmrCalculator {
    boolean calculateMmr(int whoWon, Gamer[] team1gamers, Gamer[] team2gamers, Team team1, Team team2, boolean suddenDeath);
}

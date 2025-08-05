package com.mrblablak.rankingSystem.service.lobby;

import com.mrblablak.rankingSystem.dtoForForms.GamersMatchStatsDTO;
import com.mrblablak.rankingSystem.model.Gamer;
import com.mrblablak.rankingSystem.model.Team;

public interface MmrCalculator {
    boolean calculateMmr(int whoWon, Gamer[] team1gamers, Gamer[] team2gamers, Team team1, Team team2, boolean suddenDeath);
}

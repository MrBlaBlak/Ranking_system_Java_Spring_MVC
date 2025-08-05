package com.mrblablak.rankingSystem.service.lobby;

import com.mrblablak.rankingSystem.model.Gamer;

public interface Matchmaker {
    void calculateAndAssignTeams(Gamer[] gamers, Gamer[] team1gamers, Gamer[] team2gamers);
}

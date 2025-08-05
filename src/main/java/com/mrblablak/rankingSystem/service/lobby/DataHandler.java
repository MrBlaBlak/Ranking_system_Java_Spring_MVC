package com.mrblablak.rankingSystem.service.lobby;

import com.mrblablak.rankingSystem.dtoForForms.GamersMatchStatsDTO;
import com.mrblablak.rankingSystem.model.Gamer;

public interface DataHandler {
   boolean setData(GamersMatchStatsDTO gamersMatchStatsDTO, Gamer[] team1gamers, Gamer[] team2gamers);
}

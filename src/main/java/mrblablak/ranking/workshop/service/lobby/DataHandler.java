package mrblablak.ranking.workshop.service.lobby;

import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.Gamer;

public interface DataHandler {
   boolean setData(GamersMatchStatsDTO gamersMatchStatsDTO, Gamer[] team1gamers, Gamer[] team2gamers);
}

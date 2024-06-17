package mrblablak.ranking.workshop.service.lobby;

import mrblablak.ranking.workshop.model.Gamer;

public interface Matchmaker {
    void calculateAndAssignTeams(Gamer[] gamers, Gamer[] team1gamers, Gamer[] team2gamers);
}

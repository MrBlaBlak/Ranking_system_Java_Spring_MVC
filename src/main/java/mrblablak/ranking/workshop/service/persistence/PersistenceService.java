package mrblablak.ranking.workshop.service.persistence;

import mrblablak.ranking.workshop.model.*;

public interface PersistenceService {
    void saveData(
            MatchGamer[] matchGamers,
            KillsAndCaps[] killsAndCaps,
            Match match,
            Team team1,
            Team team2,
            Gamer[] team1gamers,
            Gamer[] team2gamers
    );
}

package mrblablak.ranking.workshop.dtoForForms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class GamersMatchStatsDTO {
    String server;
    boolean suddenDeath;
    String suddenDeathWhoWon;
    String[] team1titans;
    String[] team2titans;
    int[] team1gamersId;
    int[] team1elims;
    int[] team1flags;
    int[] team2gamersId;
    int[] team2elims;
    int[] team2flags;
    String mapPlayed;
}

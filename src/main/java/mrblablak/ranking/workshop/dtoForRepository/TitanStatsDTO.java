package mrblablak.ranking.workshop.dtoForRepository;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class TitanStatsDTO {
    private String gamerName;
    private Map<String, Integer> titanWins; // map for number of wins with each titan played
    private Map<String, Integer> titanLosses;// map for number of losses with each titan played
    private Map<String, Integer> titanWinPercent; // map for winPercent for each titan played

    public TitanStatsDTO(String gamerName) {
        this.gamerName = gamerName;
        this.titanWins = new HashMap<>();
        this.titanLosses = new HashMap<>();
        this.titanWinPercent = new HashMap<>();
    }
}

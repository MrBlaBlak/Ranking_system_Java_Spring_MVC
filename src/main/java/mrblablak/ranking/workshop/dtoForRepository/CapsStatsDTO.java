package mrblablak.ranking.workshop.dtoForRepository;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class CapsStatsDTO {
    private String gamerName;
    private Map<String, Integer> mapCaps; // map for number of caps on each map played
    private Map<String, Integer> mapTotalGames; // map for number of games on each map played
    private Map<String, Integer> mapBestCaps; // map for highest cap score on each map played
    private Map<String, Double> mapAverageCaps; // map for average cap score on each map played

    public CapsStatsDTO(String gamerName) {
        this.gamerName = gamerName;
        this.mapCaps = new HashMap<>();
        this.mapTotalGames = new HashMap<>();
        this.mapBestCaps = new HashMap<>();
        this.mapAverageCaps = new HashMap<>();
    }

}

package mrblablak.ranking.workshop.dtoForRepository;

import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class MapStatsDTO {
    private String gamerName;
    private Map<String, Integer> mapWins; // map for number of wins on each map played
    private Map<String, Integer> mapLosses; // map for number of losses on each map played
    private Map<String, Integer> mapWinPercent; // map for winPercent on each map played

    public MapStatsDTO(String gamerName) {
        this.gamerName = gamerName;
        this.mapWins = new HashMap<>();
        this.mapLosses = new HashMap<>();
        this.mapWinPercent = new HashMap<>();
    }
}

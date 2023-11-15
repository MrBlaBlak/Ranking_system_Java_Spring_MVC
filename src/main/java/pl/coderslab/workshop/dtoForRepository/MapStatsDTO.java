package pl.coderslab.workshop.dtoForRepository;

import java.util.HashMap;
import java.util.Map;

public class MapStatsDTO {
    private String gamerName;
    private Map<String, Integer> mapWins; // Mapa do liczby zwycięstw
    private Map<String, Integer> mapLosses; // Mapa do liczby porażek
    private Map<String, Integer> mapWinPercent;

    public MapStatsDTO(String gamerName) {
        this.gamerName = gamerName;
        this.mapWins = new HashMap<>();
        this.mapLosses = new HashMap<>();
        this.mapWinPercent = new HashMap<>();
    }

    public Map<String, Integer> getMapWins() {
        return mapWins;
    }

    public void setMapWins(Map<String, Integer> mapWins) {
        this.mapWins = mapWins;
    }

    public Map<String, Integer> getMapLosses() {
        return mapLosses;
    }

    public void setMapLosses(Map<String, Integer> mapLosses) {
        this.mapLosses = mapLosses;
    }

    public String getGamerName() {
        return gamerName;
    }

    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public Map<String, Integer> getMapWinPercent() {
        return mapWinPercent;
    }
    public void setMapWinPercent(Map<String, Integer> mapWinPercent) {
        this.mapWinPercent = mapWinPercent;
    }
}

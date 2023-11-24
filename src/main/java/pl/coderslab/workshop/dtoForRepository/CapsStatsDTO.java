package pl.coderslab.workshop.dtoForRepository;

import java.util.HashMap;
import java.util.Map;

public class CapsStatsDTO {
    private String gamerName;
    private Map<String, Integer> mapCaps; // map for number of caps on each map played
    private Map<String, Integer> mapTotalGames; // map for number of games on each map played
    private Map<String, Integer> mapBestCaps; // map for highest cap score on each map played
    private Map<String, Double> mapAverageCaps; // map for average cap score on each map played

    public CapsStatsDTO() {
    }

    public CapsStatsDTO(String gamerName) {
        this.gamerName=gamerName;
        this.mapCaps= new HashMap<>();
        this.mapTotalGames = new HashMap<>();
        this.mapBestCaps = new HashMap<>();
        this.mapAverageCaps = new HashMap<>();
    }

    public String getGamerName() {
        return gamerName;
    }

    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public Map<String, Integer> getMapCaps() {
        return mapCaps;
    }

    public void setMapCaps(Map<String, Integer> mapCaps) {
        this.mapCaps = mapCaps;
    }

    public Map<String, Integer> getMapTotalGames() {
        return mapTotalGames;
    }

    public void setMapTotalGames(Map<String, Integer> mapTotalGames) {
        this.mapTotalGames = mapTotalGames;
    }

    public Map<String, Integer> getMapBestCaps() {
        return mapBestCaps;
    }

    public void setMapBestCaps(Map<String, Integer> mapBestCaps) {
        this.mapBestCaps = mapBestCaps;
    }

    public Map<String, Double> getMapAverageCaps() {
        return mapAverageCaps;
    }

    public void setMapAverageCaps(Map<String, Double> mapAverageCaps) {
        this.mapAverageCaps = mapAverageCaps;
    }
}

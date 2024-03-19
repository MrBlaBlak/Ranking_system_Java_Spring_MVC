package mrblablak.ranking.workshop.dtoForRepository;

import java.util.HashMap;
import java.util.Map;

public class KillsStatsDTO {
    private String gamerName;
    private Map<String, Integer> mapKills; // map for number of kills on each map played
    private Map<String, Integer> mapTotalGames; // map for number of games on each map played
    private Map<String, Integer> mapBestKills; // map for highest kill score on each map played
    private Map<String, Double> mapAverageKills; // map for average kill score on each map played
    public KillsStatsDTO(String gamerName){
        this.gamerName=gamerName;
        this.mapKills= new HashMap<>();
        this.mapTotalGames = new HashMap<>();
        this.mapBestKills = new HashMap<>();
        this.mapAverageKills = new HashMap<>();
    }

    public Map<String, Double> getMapAverageKills() {
        return mapAverageKills;
    }

    public void setMapAverageKills(Map<String, Double> mapAverageKills) {
        this.mapAverageKills = mapAverageKills;
    }

    public String getGamerName() {
        return gamerName;
    }

    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public Map<String, Integer> getMapKills() {
        return mapKills;
    }

    public void setMapKills(Map<String, Integer> mapKills) {
        this.mapKills = mapKills;
    }

    public Map<String, Integer> getMapTotalGames() {
        return mapTotalGames;
    }

    public void setMapTotalGames(Map<String, Integer> mapTotalGames) {
        this.mapTotalGames = mapTotalGames;
    }

    public Map<String, Integer> getMapBestKills() {
        return mapBestKills;
    }

    public void setMapBestKills(Map<String, Integer> mapBestKills) {
        this.mapBestKills = mapBestKills;
    }
}

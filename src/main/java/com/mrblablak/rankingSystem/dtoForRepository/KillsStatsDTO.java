package com.mrblablak.rankingSystem.dtoForRepository;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
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
}

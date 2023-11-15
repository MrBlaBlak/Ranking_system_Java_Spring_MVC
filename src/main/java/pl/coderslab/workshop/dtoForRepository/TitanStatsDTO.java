package pl.coderslab.workshop.dtoForRepository;

import java.util.HashMap;
import java.util.Map;

public class TitanStatsDTO {
    private String gamerName;
    private Map<String, Integer> titanWins;
    private Map<String, Integer> titanLosses;
    private Map<String, Integer> titanWinPercent;

    public TitanStatsDTO(String gamerName) {
        this.gamerName = gamerName;
        this.titanWins = new HashMap<>();
        this.titanLosses = new HashMap<>();
        this.titanWinPercent = new HashMap<>();
    }

    public Map<String, Integer> getTitanLosses() {
        return titanLosses;
    }

    public void setTitanLosses(Map<String, Integer> titanLosses) {
        this.titanLosses = titanLosses;
    }

    public String getGamerName() {
        return gamerName;
    }

    public void setGamerName(String gamerName) {
        this.gamerName = gamerName;
    }

    public Map<String, Integer> getTitanWins() {
        return titanWins;
    }

    public void setTitanWins(Map<String, Integer> titanWins) {
        this.titanWins = titanWins;
    }


    public Map<String, Integer> getTitanWinPercent() {
        return titanWinPercent;
    }

    public void setTitanWinPercent(Map<String, Integer> titanWinPercent) {
        this.titanWinPercent = titanWinPercent;
    }
}

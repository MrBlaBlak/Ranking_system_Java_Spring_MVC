package mrblablak.ranking.workshop.service;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForRepository.*;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.repository.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final GamerRepository gamerRepository;

    public Map<Integer, String> getMostFrequentTitanForGamer() {
        List<Object[]> gamerTitans = gamerRepository.findMostFrequentTitanForGamers();
        Map<Integer, String> gamerTitanMap = new HashMap<>();

        for (Object[] result : gamerTitans) {
            int gamerId = (int) result[0];
            String titan = getTitanName((int) result[1]);
            gamerTitanMap.put(gamerId, titan);
            System.out.println(gamerTitanMap.get(gamerId));
        }
        return gamerTitanMap;
    }

    public List<MapStatsDTO> getMapStats() {
        List<Object[]> results = gamerRepository.getMapStats();
        List<MapStatsDTO> gamerStatsList = new ArrayList<>();
        for (Object[] result : results) {
            String gamerName = (String) result[0];
            Integer mapWins = ((BigDecimal) result[1]).intValue();
            Integer mapLosses = ((BigDecimal) result[2]).intValue();
            String map = "";
            if (result[3] != null) {
                map = getMapName((int) result[3]);
            }
            Integer mapWinPercent = Long.valueOf(Math.round(mapWins * 1.0 / (mapWins + mapLosses) * 100)).intValue();


            // czy gracz na liście
            MapStatsDTO existingGamerStats = gamerStatsList.stream()
                    .filter(gamerStats -> gamerStats.getGamerName().equals(gamerName))
                    .findFirst()
                    .orElse(null);

            if (existingGamerStats != null) {
                // jeżeli tak to dodaj do mapy
                existingGamerStats.getMapWins().put(map, mapWins);
                existingGamerStats.getMapLosses().put(map, mapLosses);
                existingGamerStats.getMapWinPercent().put(map, mapWinPercent);
            } else {
                // jeżeli nie to nowy gracz
                MapStatsDTO newGamerStats = new MapStatsDTO(gamerName);
                newGamerStats.getMapWins().put(map, mapWins);
                newGamerStats.getMapLosses().put(map, mapLosses);
                newGamerStats.getMapWinPercent().put(map, mapWinPercent);
                gamerStatsList.add(newGamerStats);
            }
        }
//        gamerStatsList.sort(customComparator);
        return gamerStatsList;
    }

    public List<TitanStatsDTO> getTitanStats() {
        List<Object[]> results = gamerRepository.getTitansStats();
        List<TitanStatsDTO> gamerStatsList = new ArrayList<>();

        for (Object[] result : results) {
            String gamerName = (String) result[0];
            Integer titanWins = ((BigDecimal) result[1]).intValue();
            Integer titanLosses = ((BigDecimal) result[2]).intValue();
            String titan = "";
            if (result[3] != null) {
                titan = getTitanName((int) result[3]);
            }
            Integer titanWinPercent = Long.valueOf(Math.round(titanWins * 1.0 / (titanWins + titanLosses) * 100)).intValue();

            // czy gracz na liście
            TitanStatsDTO existingGamerStats = gamerStatsList.stream()
                    .filter(gamerStats -> gamerStats.getGamerName().equals(gamerName))
                    .findFirst()
                    .orElse(null);

            if (existingGamerStats != null) {
                // jeżeli tak to dodaj do mapy
                existingGamerStats.getTitanWins().put(titan, titanWins);
                existingGamerStats.getTitanLosses().put(titan, titanLosses);
                existingGamerStats.getTitanWinPercent().put(titan, titanWinPercent);
            } else {
                // jeżeli nie to nowy gracz
                TitanStatsDTO newGamerStats = new TitanStatsDTO(gamerName);
                newGamerStats.getTitanWins().put(titan, titanWins);
                newGamerStats.getTitanLosses().put(titan, titanLosses);
                newGamerStats.getTitanWinPercent().put(titan, titanWinPercent);
                gamerStatsList.add(newGamerStats);
            }
        }
        return gamerStatsList;
    }

    public List<KillsStatsDTO> getKillsStats() {
        List<Object[]> resultsKills = gamerRepository.getKillsStats();
        List<KillsStatsDTO> gamerStatsList = new ArrayList<>();

        for (Object[] result : resultsKills) {
            String gamerName = (String) result[0];
            Integer mapKills = 0;
            Integer mapTotalGames = 0;
            Integer mapBestKills = 0;
            String map = "";
            if (result[1] != null) {
                mapKills = ((BigDecimal) result[1]).intValue();
            }
            if (result[2] != null) {
                mapTotalGames = ((BigInteger) result[2]).intValue();
            }
            if (result[3] != null) {
                mapBestKills = (Integer) result[3];
            }
            if (result[4] != null) {
                map = getMapName((int) result[4]);
            }

            Double mapAverageKills = (Math.round(mapKills * 1.0 / (mapTotalGames) * 100)) / 100.0;

            // czy gracz na liście
            KillsStatsDTO existingGamerStats = gamerStatsList.stream()
                    .filter(gamerStats -> gamerStats.getGamerName().equals(gamerName))
                    .findFirst()
                    .orElse(null);

            if (existingGamerStats != null) {
                // jeżeli tak to dodaj do mapy
                existingGamerStats.getMapKills().put(map, mapKills);
                existingGamerStats.getMapTotalGames().put(map, mapTotalGames);
                existingGamerStats.getMapBestKills().put(map, mapBestKills);
                existingGamerStats.getMapAverageKills().put(map, mapAverageKills);
            } else {
                // jeżeli nie to nowy gracz
                KillsStatsDTO newGamerStats = new KillsStatsDTO(gamerName);
                newGamerStats.getMapKills().put(map, mapKills);
                newGamerStats.getMapTotalGames().put(map, mapTotalGames);
                newGamerStats.getMapBestKills().put(map, mapBestKills);
                newGamerStats.getMapAverageKills().put(map, mapAverageKills);
                gamerStatsList.add(newGamerStats);
            }
        }
        return gamerStatsList;
    }

    public List<CapsStatsDTO> getCapsStats() {
        List<Object[]> resultsCaps = gamerRepository.getCapsStats();
        List<CapsStatsDTO> gamerStatsList = new ArrayList<>();
        for (Object[] result : resultsCaps) {
            String gamerName = (String) result[0];
            Integer mapCaps = 0;
            Integer mapTotalGames = 0;
            Integer mapBestCap = 0;
            String map = "";
            if (result[1] != null) {
                mapCaps = ((BigDecimal) result[1]).intValue();
            }
            if (result[2] != null) {
                mapTotalGames = ((BigInteger) result[2]).intValue();
            }
            if (result[3] != null) {
                mapBestCap = (Integer) result[3];
            }
            if (result[4] != null) {
                map = getMapName((int) result[4]);
            }

            Double mapAverageCaps = (Math.round(mapCaps * 1.0 / (mapTotalGames) * 100)) / 100.0;

            // czy gracz na liście
            CapsStatsDTO existingGamerStats = gamerStatsList.stream()
                    .filter(gamerStats -> gamerStats.getGamerName().equals(gamerName))
                    .findFirst()
                    .orElse(null);

            if (existingGamerStats != null) {
                // jeżeli tak to dodaj do mapy
                existingGamerStats.getMapCaps().put(map, mapCaps);
                existingGamerStats.getMapTotalGames().put(map, mapTotalGames);
                existingGamerStats.getMapBestCaps().put(map, mapBestCap);
                existingGamerStats.getMapAverageCaps().put(map, mapAverageCaps);
            } else {
                // jeżeli nie to nowy gracz
                CapsStatsDTO newGamerStats = new CapsStatsDTO(gamerName);
                newGamerStats.getMapCaps().put(map, mapCaps);
                newGamerStats.getMapTotalGames().put(map, mapTotalGames);
                newGamerStats.getMapBestCaps().put(map, mapBestCap);
                newGamerStats.getMapAverageCaps().put(map, mapAverageCaps);
                gamerStatsList.add(newGamerStats);
            }
        }
        return gamerStatsList;
    }
    public List<WingmanNemesisStatsDTO> getWingmanAndNemesisStats(int playerId, String statType) {
        List<Object[]> statsList = statType.equals("nemesis") ?
                gamerRepository.findMostFrequentLoserOpponent(playerId) :
                gamerRepository.findMostFrequentWinnerTeammate(playerId);

        List<WingmanNemesisStatsDTO> statsDTOList = new ArrayList<>();
        for (Object[] stats : statsList) {
            WingmanNemesisStatsDTO statsDTO = new WingmanNemesisStatsDTO();
            statsDTO.setName((String) stats[0]);
            statsDTO.setWins(((BigInteger) stats[1]).intValue());
            statsDTO.setLosses(((BigInteger) stats[2]).intValue());
            statsDTO.setWinPercentage(calculateWinPercentage(statsDTO.getWins(), statsDTO.getLosses()));
            statsDTOList.add(statsDTO);
        }
        return statsDTOList;
    }

    private int calculateWinPercentage(int wins, int losses) {
        return (int) Math.round(wins * 100.0 / (wins + losses));
    }

    public Gamer getGamer(int id) {
        return gamerRepository.findById(id).get();
    }
    public void addDataIfEmpty() throws IOException {
        long gamerCount = gamerRepository.count();
        if (gamerCount == 0) {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource("classpath:all_data.txt");

            try (InputStream inputStream = resource.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] actualValue = line.split(" ");
                    gamerRepository.save(new Gamer(actualValue[0], Double.parseDouble(actualValue[1]), actualValue[7], actualValue[6]));
                }
            }
        }
    }
    private String getTitanName(int titanId) {
        switch (titanId) {
            case 0:
                return "ion";
            case 1:
                return "tone";
            case 2:
                return "ronin";
            case 3:
                return "northstar";
            case 4:
                return "monarch";
            case 5:
                return "legion";
            case 6:
                return "scorch";
            default:
                return "none";
        }
    }
    private String getMapName(int mapId) {
        switch (mapId) {
            case 0:
                return "boomtown";
            case 1:
                return "exo";
            case 2:
                return "eden";
            case 3:
                return "drydock";
            case 4:
                return "angel";
            case 5:
                return "colony";
            case 6:
                return "glitch";
            default:
                return "none";
        }
    }
    public String[] getMapOrder() {
        return new String[]{"boomtown", "exo", "eden", "drydock", "angel", "colony", "glitch"};
    }

    public String[] getTitanOrder() {
        return new String[]{"ion", "tone", "monarch", "northstar", "ronin", "legion", "scorch"};
    }
    public List<Gamer> getAllGamers() {
        return gamerRepository.findAll();
    }

//    Comparator<MapStatsDTO> customComparator = Comparator.comparing(MapStatsDTO::getGamerName);
}

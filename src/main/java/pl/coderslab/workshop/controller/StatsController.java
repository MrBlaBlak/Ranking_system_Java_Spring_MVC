package pl.coderslab.workshop.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.workshop.dtoForRepository.*;
import pl.coderslab.workshop.model.Gamer;
import pl.coderslab.workshop.repository.GamerRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
@Controller
public class StatsController {

    private final GamerRepository gamerRepository;

    public StatsController( GamerRepository gamerRepository) {

        this.gamerRepository = gamerRepository;
    }

    @RequestMapping("/stats/general")
    public String statsGeneral(Model model) {
        List<Gamer> gamers = gamerRepository.findAll();
        List<Object[]> gamerTitans = gamerRepository.findMostFrequentTitanForGamers();
        Map<Integer, String> gamerTitanMap = new HashMap<>();

        for (Object[] result : gamerTitans) {
            int gamerId = (int) result[0];
            String titan = getTitanName((int) result[1]);
            gamerTitanMap.put(gamerId, titan);
            System.out.println(gamerTitanMap.get(gamerId));
        }
        model.addAttribute("gamers", gamers);
        model.addAttribute("gamerTitans", gamerTitanMap);

        return "gamer/statsGeneral";
    }

    @RequestMapping("/stats/maps")
    public String statsMaps(Model model) {
        List<Object[]> results = gamerRepository.getMapStats();
        List<MapStatsDTO> gamerStatsList = new ArrayList<>();
        String[] mapOrder = {"boomtown", "exo", "eden", "drydock", "angel", "colony", "glitch"};
        for (Object[] result : results) {
            String gamerName = (String) result[0];
            Integer mapWins = ((BigDecimal) result[1]).intValue();
            Integer mapLosses = ((BigDecimal) result[2]).intValue();
            String map="";
            if(result[3]!=null){
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
        gamerStatsList.sort(customComparator);
        model.addAttribute("gamerStatsList", gamerStatsList);
        model.addAttribute("mapOrder", mapOrder);
        return "gamer/statsMaps";
    }
    @RequestMapping("/stats/titans")
    public String statsTitans(Model model) {
        List<Object[]> results = gamerRepository.getTitansStats();
        List<TitanStatsDTO> gamerStatsList = new ArrayList<>();
        String[] titanOrder = {"ion", "tone", "monarch", "northstar", "ronin", "legion", "scorch"};
        for (Object[] result : results) {
            String gamerName = (String) result[0];
            Integer titanWins = ((BigDecimal) result[1]).intValue();
            Integer titanLosses = ((BigDecimal) result[2]).intValue();
            String titan="";
            if(result[3]!=null){
                titan = getTitanName((int)result[3]);
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
        model.addAttribute("gamerStatsList", gamerStatsList);
        model.addAttribute("titanOrder", titanOrder);
        return "gamer/statsTitans";
    }

    @RequestMapping("/stats/kills")
    public String statsKills(Model model) {
        List<Object[]> resultsKills = gamerRepository.getKillsStats();
        List<KillsStatsDTO> gamerStatsList = new ArrayList<>();

        String[] mapOrder = {"boomtown", "exo", "eden", "drydock", "angel", "colony", "glitch"};
        for (Object[] result : resultsKills) {
            String gamerName = (String) result[0];
            Integer mapKills=0;
            Integer mapTotalGames=0;
            Integer mapBestKills=0;
            String map="";
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
                map = getMapName((int)result[4]);
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
        model.addAttribute("gamerStatsList", gamerStatsList);
        model.addAttribute("mapOrder", mapOrder);
        return "gamer/statsKills";
    }
    @RequestMapping("/stats/caps")
    public String statsCaps(Model model) {
        List<Object[]> resultsCaps = gamerRepository.getCapsStats();
        List<CapsStatsDTO> gamerStatsList = new ArrayList<>();
        String[] mapOrder = {"boomtown", "exo", "eden", "drydock", "angel", "colony", "glitch"};

        for (Object[] result : resultsCaps) {
            String gamerName = (String) result[0];
            Integer mapCaps=0;
            Integer mapTotalGames=0;
            Integer mapBestCap=0;
            String map="";
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
                map = getMapName((int)result[4]);
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

        model.addAttribute("gamerStatsList", gamerStatsList);
        model.addAttribute("mapOrder", mapOrder);
        return "gamer/statsCaps";
    }
    @GetMapping("/nemesis/{playerId}")
    public String getNemesisStats(@PathVariable int playerId, Model model) {
        List<Object[]> nemesisStatsList = gamerRepository.findMostFrequentLoserOpponent(playerId);
        List<Object[]> wingmanStatsList = gamerRepository.findMostFrequentWinnerTeammate(playerId);

        if (!nemesisStatsList.isEmpty() || !wingmanStatsList.isEmpty()) {
            List<WingmanNemesisStatsDTO> nemesisStatsDTOList = new ArrayList<>();
            List<WingmanNemesisStatsDTO> wingmanStatsDTOList = new ArrayList<>();
            findWingmanAndNemesis(nemesisStatsList, nemesisStatsDTOList);
            findWingmanAndNemesis(wingmanStatsList, wingmanStatsDTOList);

            model.addAttribute("nemesisStatsList", nemesisStatsDTOList);
            model.addAttribute("wingmanStatsList", wingmanStatsDTOList);
            model.addAttribute("name", getGamer(playerId).getName());
        }

        return "gamer/statsNemesis";
    }

    private void findWingmanAndNemesis(List<Object[]> wingmanStatsList, List<WingmanNemesisStatsDTO> nemesisStatsDTOList) {
        for (Object[] wingmanStats : wingmanStatsList) {
            WingmanNemesisStatsDTO wingmanStatsDTO = new WingmanNemesisStatsDTO();
            wingmanStatsDTO.setName((String) wingmanStats[0]);
            wingmanStatsDTO.setWins(((BigInteger) wingmanStats[1]).intValue());
            wingmanStatsDTO.setLosses(((BigInteger) wingmanStats[2]).intValue());
            wingmanStatsDTO.setWinPercentage((int)Math.round(wingmanStatsDTO.getWins()*1.0/ (wingmanStatsDTO.getWins() + wingmanStatsDTO.getLosses())* 100));
            System.out.println(wingmanStatsDTO.getWinPercentage());
            nemesisStatsDTOList.add(wingmanStatsDTO);
        }
    }

    public Gamer getGamer(int id) {
        return gamerRepository.findById(id).get();

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

    Comparator<MapStatsDTO> customComparator = (a, b) -> {
        // sortujemy sobie gamerName
        int nameComparison = a.getGamerName().compareTo(b.getGamerName());

        if (nameComparison != 0) {
            return nameComparison;
        }
        return 0;
    };

}





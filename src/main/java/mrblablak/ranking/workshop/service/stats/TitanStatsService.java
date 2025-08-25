package mrblablak.ranking.workshop.service.stats;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForRepository.TitanStatsDTO;
import mrblablak.ranking.workshop.repository.GamerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TitanStatsService {

    private final GamerRepository gamerRepository;

    public List<TitanStatsDTO> getTitanStats() {
        List<Object[]> results = gamerRepository.getTitansStats();
        List<TitanStatsDTO> gamerStatsList = new ArrayList<>();

        for (Object[] result : results) {
            String gamerName = (String) result[0];
            Integer titanWins = ((BigDecimal) result[1]).intValue();
            Integer titanLosses = ((BigDecimal) result[2]).intValue();
            String titan = "";
            if (result[3] != null) {
                titan = (String) result[3];
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
    public Map<Integer, String> getMostFrequentTitanForGamer() {
        List<Object[]> gamerTitans = gamerRepository.findMostFrequentTitanForGamers();
        Map<Integer, String> gamerTitanMap = new HashMap<>();

        for (Object[] result : gamerTitans) {
            int gamerId = (int) result[0];
            String titan = (String) result[1];
            gamerTitanMap.put(gamerId, titan);
            System.out.println(gamerTitanMap.get(gamerId));
        }
        return gamerTitanMap;
    }
}

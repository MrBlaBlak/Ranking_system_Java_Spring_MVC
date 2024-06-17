package mrblablak.ranking.workshop.service.stats;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForRepository.CapsStatsDTO;
import mrblablak.ranking.workshop.repository.GamerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CapsStatsService {

    private final GamerRepository gamerRepository;

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
                map = TitanAndMapNameUtility.getMapName((int) result[4]);
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
}

package mrblablak.ranking.workshop.service.stats;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForRepository.KillsStatsDTO;
import mrblablak.ranking.workshop.repository.GamerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KillsStatsService {

    private final GamerRepository gamerRepository;

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
                map = (String) result[4];
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
}

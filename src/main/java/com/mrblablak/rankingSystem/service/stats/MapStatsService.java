package com.mrblablak.rankingSystem.service.stats;

import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.dtoForRepository.MapStatsDTO;
import com.mrblablak.rankingSystem.repository.GamerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapStatsService  {

    private final GamerRepository gamerRepository;

    public List<MapStatsDTO> getMapStats() {
        List<Object[]> results = gamerRepository.getMapStats();
        List<MapStatsDTO> gamerStatsList = new ArrayList<>();
        for (Object[] result : results) {
            String gamerName = (String) result[0];
            Integer mapWins = ((BigDecimal) result[1]).intValue();
            Integer mapLosses = ((BigDecimal) result[2]).intValue();
            String map = "";
            if (result[3] != null) {
                map = (String) result[3];
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
        return gamerStatsList;
    }
}

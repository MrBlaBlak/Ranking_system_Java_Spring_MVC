package com.mrblablak.rankingSystem.service.stats;

import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.dtoForRepository.MapStatsDTO;
import com.mrblablak.rankingSystem.repository.GamerRepository;
import com.mrblablak.rankingSystem.service.stats.base.BaseWinLossStatsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapStatsService extends BaseWinLossStatsService<MapStatsDTO> {

    private final GamerRepository gamerRepository;

    public List<MapStatsDTO> getMapStats() {
        return buildStats(gamerRepository.getMapStats(),
                MapStatsDTO::new,
                (dto,map,values) -> {
                    dto.getMapWins().put(map, values[0]);
                    dto.getMapLosses().put(map, values[1]);
                    dto.getMapWinPercent().put(map, values[2]);
                }
                );
    }
}

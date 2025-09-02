package com.mrblablak.rankingSystem.service.stats;

import com.mrblablak.rankingSystem.service.stats.base.BaseGameStatsService;
import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.dtoForRepository.CapsStatsDTO;
import com.mrblablak.rankingSystem.repository.GamerRepository;
import org.springframework.stereotype.Service;
import com.mrblablak.rankingSystem.utils.StatsUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CapsStatsService extends BaseGameStatsService<CapsStatsDTO> {

    private final GamerRepository gamerRepository;

    public List<CapsStatsDTO> getCapsStats() {
        return buildStats(gamerRepository.getCapsStats(),
                CapsStatsDTO::new,
                (dto, map, values) -> {
                    dto.getMapCaps().put(map, values[0]);
                    dto.getMapTotalGames().put(map, values[1]);
                    dto.getMapBestCaps().put(map, values[2]);
                    dto.getMapAverageCaps().put(map, StatsUtils.calculateAverageScore(values[0], values[1]));
                }
        );
    }
}

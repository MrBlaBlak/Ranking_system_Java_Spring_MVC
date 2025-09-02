package com.mrblablak.rankingSystem.service.stats;

import com.mrblablak.rankingSystem.service.stats.base.BaseGameStatsService;
import com.mrblablak.rankingSystem.utils.StatsUtils;
import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.dtoForRepository.KillsStatsDTO;
import com.mrblablak.rankingSystem.repository.GamerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KillsStatsService extends BaseGameStatsService<KillsStatsDTO> {

    private final GamerRepository gamerRepository;

    public List<KillsStatsDTO> getKillsStats() {
        return buildStats(gamerRepository.getKillsStats(),
                KillsStatsDTO::new,
                (dto, map, values) -> {
                    dto.getMapKills().put(map, values[0]);
                    dto.getMapTotalGames().put(map, values[1]);
                    dto.getMapBestKills().put(map, values[2]);
                    dto.getMapAverageKills().put(map, StatsUtils.calculateAverageScore(values[0], values[1]));
                }
        );
    }
}

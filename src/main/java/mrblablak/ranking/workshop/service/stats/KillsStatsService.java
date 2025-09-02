package mrblablak.ranking.workshop.service.stats;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForRepository.KillsStatsDTO;
import mrblablak.ranking.workshop.repository.GamerRepository;
import mrblablak.ranking.workshop.service.stats.base.BaseGameStatsService;
import mrblablak.ranking.workshop.utils.StatsUtils;
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

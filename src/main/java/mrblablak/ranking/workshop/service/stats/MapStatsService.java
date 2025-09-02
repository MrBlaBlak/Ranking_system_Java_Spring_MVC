package mrblablak.ranking.workshop.service.stats;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForRepository.MapStatsDTO;
import mrblablak.ranking.workshop.repository.GamerRepository;
import mrblablak.ranking.workshop.service.stats.base.BaseWinLossStatsService;
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

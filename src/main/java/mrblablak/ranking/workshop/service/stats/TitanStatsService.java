package mrblablak.ranking.workshop.service.stats;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForRepository.TitanStatsDTO;
import mrblablak.ranking.workshop.repository.GamerRepository;
import mrblablak.ranking.workshop.service.stats.base.BaseWinLossStatsService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TitanStatsService extends BaseWinLossStatsService<TitanStatsDTO> {

    private final GamerRepository gamerRepository;

    public List<TitanStatsDTO> getTitanStats() {
        return buildStats(gamerRepository.getTitansStats(),
                TitanStatsDTO::new,
                (dto,titan,values) -> {
                    dto.getTitanWins().put(titan, values[0]);
                    dto.getTitanLosses().put(titan, values[1]);
                    dto.getTitanWinPercent().put(titan, values[2]);
                }
        );
    }
    public Map<Integer, String> getMostFrequentTitanForGamer() {
        List<Object[]> gamerTitans = gamerRepository.findMostFrequentTitanForGamers();
        Map<Integer, String> gamerTitanMap = new HashMap<>();

        for (Object[] result : gamerTitans) {
            gamerTitanMap.put((Integer) result[0], (String) result[1]);
        }
        return gamerTitanMap;
    }
}

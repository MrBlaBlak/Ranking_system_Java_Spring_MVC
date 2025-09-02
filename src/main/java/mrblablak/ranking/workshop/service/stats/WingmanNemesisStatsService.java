package mrblablak.ranking.workshop.service.stats;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForRepository.WingmanNemesisStatsDTO;
import mrblablak.ranking.workshop.repository.GamerRepository;
import org.springframework.stereotype.Service;
import mrblablak.ranking.workshop.utils.StatsUtils;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WingmanNemesisStatsService {

    private final GamerRepository gamerRepository;

    public List<WingmanNemesisStatsDTO> getWingmanAndNemesisStats(int playerId, String statType) {
        List<Object[]> statsList = statType.equals("nemesis") ?
                gamerRepository.findMostFrequentLoserOpponent(playerId) :
                gamerRepository.findMostFrequentWinnerTeammate(playerId);

        List<WingmanNemesisStatsDTO> statsDTOList = new ArrayList<>();
        for (Object[] stats : statsList) {
            WingmanNemesisStatsDTO statsDTO = new WingmanNemesisStatsDTO();
            statsDTO.setName((String) stats[0]);
            statsDTO.setWins(StatsUtils.toInt(stats[1]));
            statsDTO.setLosses(StatsUtils.toInt(stats[2]));
            statsDTO.setWinPercentage(StatsUtils.calcWinPercent(statsDTO.getWins(), statsDTO.getLosses()));
            statsDTOList.add(statsDTO);
        }
        return statsDTOList;
    }
}

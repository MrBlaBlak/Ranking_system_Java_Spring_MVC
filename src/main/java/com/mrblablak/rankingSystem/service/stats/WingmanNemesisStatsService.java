package com.mrblablak.rankingSystem.service.stats;

import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.dtoForRepository.WingmanNemesisStatsDTO;
import com.mrblablak.rankingSystem.repository.GamerRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
            statsDTO.setWins(((Long) stats[1]).intValue());
            statsDTO.setLosses(((Long) stats[2]).intValue());
            statsDTO.setWinPercentage(calculateWinPercentage(statsDTO.getWins(), statsDTO.getLosses()));
            statsDTOList.add(statsDTO);
        }
        return statsDTOList;
    }

    private int calculateWinPercentage(int wins, int losses) {
        return (int) Math.round(wins * 100.0 / (wins + losses));
    }
}

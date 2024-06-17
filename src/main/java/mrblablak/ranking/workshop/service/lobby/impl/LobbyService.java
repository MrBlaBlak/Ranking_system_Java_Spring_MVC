package mrblablak.ranking.workshop.service.lobby.impl;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersDTO;
import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.repository.GamerRepository;
import mrblablak.ranking.workshop.service.lobby.DataHandler;
import mrblablak.ranking.workshop.service.lobby.TeamProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LobbyService {
    private final GamerRepository gamerRepository;
    private final TeamProcessor teamProcessor;
    private final DataHandler dataHandler;

    public boolean processTeams(GamersDTO gamersDTO) {
        return teamProcessor.processTeams(gamersDTO);
    }

    public boolean calculateMmrAndUpdatePlayers(GamersMatchStatsDTO gamersMatchStatsDTO) {
        return dataHandler.setData(gamersMatchStatsDTO, getTeam1(), getTeam2());
    }
    public List<Gamer> getAllGamers() {
        return gamerRepository.findAll();
    }

    public Gamer[] getTeam1() {
        return ((TeamProcessorImpl) teamProcessor).getTeam1();
    }

    public Gamer[] getTeam2() {
        return ((TeamProcessorImpl) teamProcessor).getTeam2();
    }
    public String getServer() {
        return ((TeamProcessorImpl) teamProcessor).getServer();
    }

}

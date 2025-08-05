package com.mrblablak.rankingSystem.service.lobby.impl;

import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.dtoForForms.GamersDTO;
import com.mrblablak.rankingSystem.dtoForForms.GamersMatchStatsDTO;
import com.mrblablak.rankingSystem.model.Gamer;
import com.mrblablak.rankingSystem.repository.GamerRepository;
import com.mrblablak.rankingSystem.service.lobby.DataHandler;
import com.mrblablak.rankingSystem.service.lobby.TeamProcessor;
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

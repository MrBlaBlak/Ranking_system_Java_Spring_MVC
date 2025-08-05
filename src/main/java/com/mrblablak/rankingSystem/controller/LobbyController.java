package com.mrblablak.rankingSystem.controller;
import com.mrblablak.rankingSystem.utils.ServerUtils;
import lombok.RequiredArgsConstructor;
import com.mrblablak.rankingSystem.dtoForForms.GamersDTO;
import com.mrblablak.rankingSystem.dtoForForms.GamersMatchStatsDTO;
import com.mrblablak.rankingSystem.model.Gamer;
import com.mrblablak.rankingSystem.service.lobby.impl.LobbyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LobbyController {

    private final LobbyService lobbyService;

    //start, get data and send it to view
    @GetMapping("/pickTeams")
    public String pickTeams(Model model) {
        List <Gamer> gamers = lobbyService.getAllGamers();
        model.addAttribute("gamers", gamers);
        model.addAttribute("servers", ServerUtils.getAllServers());
        model.addAttribute("gamersDTO", new GamersDTO());
        return "gamer/pickTeams";
    }
    //find most balanced teams
    @PostMapping("/pickTeams")
    public String processForm(GamersDTO gamersDTO, Model model) {
        boolean isValidated = lobbyService.processTeams(gamersDTO);
        if(isValidated){
            model.addAttribute("team1", lobbyService.getTeam1());
            model.addAttribute("team2", lobbyService.getTeam2());
            model.addAttribute("server", lobbyService.getServer());
            model.addAttribute("gamersMatchStatsDTO", new GamersMatchStatsDTO());
            return "gamer/teamsScores";
        }
        else
        {
            return "redirect:/pickTeams";
        }
    }
    //update scores of players
    @PostMapping("/updateScores")
    public String updateScores(GamersMatchStatsDTO gamersMatchStatsDTO, Model model) {
        boolean isValidated = lobbyService.calculateMmrAndUpdatePlayers(gamersMatchStatsDTO);
        if(isValidated) {
            model.addAttribute("team1", lobbyService.getTeam1());
            model.addAttribute("team2", lobbyService.getTeam2());
            model.addAttribute("server", gamersMatchStatsDTO.getServer());

            return "gamer/teamsScores";
        }
        else
        {
            return "redirect:/pickTeams";
        }
    }
}

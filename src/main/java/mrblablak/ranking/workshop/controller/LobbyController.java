package mrblablak.ranking.workshop.controller;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersDTO;
import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.service.lobby.impl.LobbyService;
import mrblablak.ranking.workshop.utils.ServerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String processForm(GamersDTO gamersDTO, RedirectAttributes redirectAttrs) {
        if (lobbyService.processTeams(gamersDTO)) {
            redirectAttrs.addFlashAttribute("team1", lobbyService.getTeam1());
            redirectAttrs.addFlashAttribute("team2", lobbyService.getTeam2());
            redirectAttrs.addFlashAttribute("server", lobbyService.getServer());
            redirectAttrs.addFlashAttribute("gamersMatchStatsDTO", new GamersMatchStatsDTO());
            return "redirect:/gameResults";  // 🔥 PRG
        }
        else
        {
            return "redirect:/pickTeams";
        }
    }
    //prg
    @GetMapping("/gameResults")
    public String teamsScores() {
        return "gamer/gameResults";
    }

    //update scores of players
    @PostMapping("/gameResults")
    public String updateScores(GamersMatchStatsDTO gamersMatchStatsDTO, RedirectAttributes redirectAttrs) {
        if(lobbyService.calculateMmrAndUpdatePlayers(gamersMatchStatsDTO)) {
            redirectAttrs.addFlashAttribute("team1", lobbyService.getTeam1());
            redirectAttrs.addFlashAttribute("team2", lobbyService.getTeam2());
            redirectAttrs.addFlashAttribute("server", gamersMatchStatsDTO.getServer());
            redirectAttrs.addFlashAttribute("gamersMatchStatsDTO", new GamersMatchStatsDTO());
            return "redirect:/gameResults";
        }
        else
        {
            return "redirect:/pickTeams";
        }
    }
}

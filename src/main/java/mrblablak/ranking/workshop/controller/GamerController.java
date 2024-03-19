package mrblablak.ranking.workshop.controller;
import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersDTO;
import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.service.StatsService;
import mrblablak.ranking.workshop.service.GamerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class GamerController {
    private final StatsService statsService;
    private final GamerService gamerService;

    //add players from data file
    @RequestMapping("/")
    public String readTextFile() throws IOException {
        statsService.addDataIfEmpty();
        return "redirect:/pickTeams";
    }

    //start, get data and send it to view
    @GetMapping("/pickTeams")
    public String pickTeams(Model model) {
        List<Gamer> gamers = gamerService.getAllGamers();
        model.addAttribute("gamers", gamers);
        model.addAttribute("servers", gamers.get(0).getAllServers());
        model.addAttribute("gamersDTO", new GamersDTO());
        return "gamer/pickTeams";
    }

    //find most balanced teams
    @PostMapping("/pickTeams")
    public String processForm(GamersDTO gamersDTO, Model model) {
        boolean isValidated = gamerService.processTeams(gamersDTO);
        if(isValidated){
            model.addAttribute("team1", gamerService.getTeam1());
            model.addAttribute("team2", gamerService.getTeam2());
            model.addAttribute("server", gamerService.getServer());
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
        boolean isValidated = statsService.calculateMmr(gamersMatchStatsDTO);
        if(isValidated) {
            model.addAttribute("team1", statsService.getTeam1());
            model.addAttribute("team2", statsService.getTeam2());
            model.addAttribute("server", gamersMatchStatsDTO.getServer());

            return "gamer/teamsScores";
        }
        else
        {
            return "redirect:/pickTeams";
        }
    }




}

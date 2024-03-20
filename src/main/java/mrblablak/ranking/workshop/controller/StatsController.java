package mrblablak.ranking.workshop.controller;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.service.StatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class StatsController {


    private final StatsService statsService;

    //add players from data file
    @RequestMapping("/")
    public String readTextFile() throws IOException {
        statsService.addDataIfEmpty();
        return "redirect:/pickTeams";
    }

    @RequestMapping("/stats/general")
    public String statsGeneral(Model model) {
        model.addAttribute("gamers", statsService.getAllGamers());
        model.addAttribute("gamerTitans", statsService.getMostFrequentTitanForGamer());
        return "gamer/statsGeneral";
    }

    @RequestMapping("/stats/maps")
    public String statsMaps(Model model) {
        model.addAttribute("gamerStatsList", statsService.getMapStats());
        model.addAttribute("mapOrder", statsService.getMapOrder());
        return "gamer/statsMaps";
    }

    @RequestMapping("/stats/titans")
    public String statsTitans(Model model) {
        model.addAttribute("gamerStatsList", statsService.getTitanStats());
        model.addAttribute("titanOrder", statsService.getTitanOrder());
        return "gamer/statsTitans";
    }

    @RequestMapping("/stats/kills")
    public String statsKills(Model model) {
        model.addAttribute("gamerStatsList", statsService.getKillsStats());
        model.addAttribute("mapOrder", statsService.getMapOrder());
        return "gamer/statsKills";
    }

    @RequestMapping("/stats/caps")
    public String statsCaps(Model model) {
        model.addAttribute("gamerStatsList", statsService.getCapsStats());
        model.addAttribute("mapOrder", statsService.getMapOrder());
        return "gamer/statsCaps";
    }

    @GetMapping("/nemesis/{playerId}")
    public String getWingmanAndNemesisStats(@PathVariable int playerId, Model model) {
        model.addAttribute("nemesisStatsList", statsService.getNemesisStats(playerId));
        model.addAttribute("wingmanStatsList", statsService.getWingmanStats(playerId));
        model.addAttribute("name", statsService.getGamer(playerId).getName());
        return "gamer/statsNemesis";
    }


}





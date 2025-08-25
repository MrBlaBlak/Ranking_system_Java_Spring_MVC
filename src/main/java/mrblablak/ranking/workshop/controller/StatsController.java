package mrblablak.ranking.workshop.controller;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.service.stats.*;
import mrblablak.ranking.workshop.utils.TitanAndMapNameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;


@Controller
@RequiredArgsConstructor
public class StatsController {

    private final GamerStatsService gamerStatsService;
    private final MapStatsService mapStatsService;
    private final TitanStatsService titanStatsService;
    private final KillsStatsService killsStatsService;
    private final CapsStatsService capsStatsService;
    private final WingmanNemesisStatsService wingmanNemesisStatsService;

    //add players from data file
    @RequestMapping("/")
    public String readTextFile() throws IOException {
        gamerStatsService.addDataIfEmpty();
        return "redirect:/pickTeams";
    }

    @RequestMapping("/stats/general")
    public String statsGeneral(Model model) {
        model.addAttribute("gamers", gamerStatsService.getAllGamers());
        model.addAttribute("gamerTitans", titanStatsService.getMostFrequentTitanForGamer());
        return "gamer/statsGeneral";
    }

    @RequestMapping("/stats/maps")
    public String statsMaps(Model model) {
        model.addAttribute("gamerStatsList", mapStatsService.getMapStats());
        model.addAttribute("mapOrder", TitanAndMapNameUtils.getMapOrder());
        return "gamer/statsMaps";
    }

    @RequestMapping("/stats/titans")
    public String statsTitans(Model model) {
        model.addAttribute("gamerStatsList", titanStatsService.getTitanStats());
        model.addAttribute("titanOrder", TitanAndMapNameUtils.getTitanOrder());
        return "gamer/statsTitans";
    }

    @RequestMapping("/stats/kills")
    public String statsKills(Model model) {
        model.addAttribute("gamerStatsList", killsStatsService.getKillsStats());
        model.addAttribute("mapOrder", TitanAndMapNameUtils.getMapOrder());
        return "gamer/statsKills";
    }

    @RequestMapping("/stats/caps")
    public String statsCaps(Model model) {
        model.addAttribute("gamerStatsList", capsStatsService.getCapsStats());
        model.addAttribute("mapOrder", TitanAndMapNameUtils.getMapOrder());
        return "gamer/statsCaps";
    }

    @GetMapping("/nemesis/{playerId}")
    public String getWingmanAndNemesisStats(@PathVariable int playerId, Model model) {
        model.addAttribute("nemesisStatsList", wingmanNemesisStatsService.getWingmanAndNemesisStats(playerId, "nemesis"));
        model.addAttribute("wingmanStatsList", wingmanNemesisStatsService.getWingmanAndNemesisStats(playerId, "wingman"));
        model.addAttribute("name", gamerStatsService.getGamer(playerId).getName());
        return "gamer/statsNemesis";
    }
}





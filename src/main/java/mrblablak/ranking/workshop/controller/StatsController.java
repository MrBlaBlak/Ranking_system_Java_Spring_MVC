package mrblablak.ranking.workshop.controller;
import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.service.StatsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import mrblablak.ranking.workshop.repository.GamerRepository;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.dtoForRepository.*;
import java.math.BigInteger;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class StatsController {

    private final GamerRepository gamerRepository;
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

    // code below has to go to StatsService, nemesis not working properly yet
    /*
    @GetMapping("/nemesis/{playerId}")
    public String getNemesisStats(@PathVariable int playerId, Model model) {
        List<Object[]> nemesisStatsList = gamerRepository.findMostFrequentLoserOpponent(playerId);
        List<Object[]> wingmanStatsList = gamerRepository.findMostFrequentWinnerTeammate(playerId);

        if (!nemesisStatsList.isEmpty() || !wingmanStatsList.isEmpty()) {
            List<WingmanNemesisStatsDTO> nemesisStatsDTOList = new ArrayList<>();
            List<WingmanNemesisStatsDTO> wingmanStatsDTOList = new ArrayList<>();
            findWingmanAndNemesis(nemesisStatsList, nemesisStatsDTOList);
            findWingmanAndNemesis(wingmanStatsList, wingmanStatsDTOList);

            model.addAttribute("nemesisStatsList", nemesisStatsDTOList);
            model.addAttribute("wingmanStatsList", wingmanStatsDTOList);
            model.addAttribute("name", getGamer(playerId).getName());
        }

        return "gamer/statsNemesis";
    }

    private void findWingmanAndNemesis(List<Object[]> wingmanStatsList, List<WingmanNemesisStatsDTO> nemesisStatsDTOList) {
        for (Object[] wingmanStats : wingmanStatsList) {
            WingmanNemesisStatsDTO wingmanStatsDTO = new WingmanNemesisStatsDTO();
            wingmanStatsDTO.setName((String) wingmanStats[0]);
            wingmanStatsDTO.setWins(((BigInteger) wingmanStats[1]).intValue());
            wingmanStatsDTO.setLosses(((BigInteger) wingmanStats[2]).intValue());
            wingmanStatsDTO.setWinPercentage((int)Math.round(wingmanStatsDTO.getWins()*1.0/ (wingmanStatsDTO.getWins() + wingmanStatsDTO.getLosses())* 100));
            System.out.println(wingmanStatsDTO.getWinPercentage());
            nemesisStatsDTOList.add(wingmanStatsDTO);
        }
    }

    public Gamer getGamer(int id) {
        return gamerRepository.findById(id).get();
    }
*/
}





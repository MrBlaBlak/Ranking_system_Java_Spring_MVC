package mrblablak.ranking.workshop.service.lobby.impl;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.repository.*;
import mrblablak.ranking.workshop.service.lobby.DataHandler;
import mrblablak.ranking.workshop.service.lobby.MmrCalculator;
import mrblablak.ranking.workshop.service.persistence.PersistenceService;
import mrblablak.ranking.workshop.utils.ServerUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@RequiredArgsConstructor
public class DataHandlerImpl implements DataHandler {

    private final GamerRepository gamerRepository;
    private final PersistenceService persistenceService;
    private final MmrCalculator mmrCalculator;
    public static final int TEAM_SIZE = 5;
    public static final int LOBBY_SIZE = 10;
    private String server;

    @Override
    public boolean setData(GamersMatchStatsDTO dto, Gamer[] team1gamers, Gamer[] team2gamers) {

        this.server = dto.getServer();
        Match match = createMatch(dto.getMapPlayed(), server);
        Team team1 = new Team();
        Team team2 = new Team();
        MatchGamer[] matchGamers = new MatchGamer[LOBBY_SIZE];
        KillsAndCaps[] killsAndCaps = new KillsAndCaps[LOBBY_SIZE];

        fetchGamers(dto, team1gamers, team2gamers);

        int whoWon = setTeams(team1, team2, dto.getTeam1flags(), dto.getTeam2flags(), dto.getSuddenDeathWhoWon());
        boolean isValidated = mmrCalculator.calculateMmr(whoWon, team1gamers, team2gamers, team1, team2, dto.isSuddenDeath());

        populateStats(
                matchGamers, killsAndCaps, team1gamers, team2gamers, dto.getTeam1elims(), dto.getTeam2elims(),
                dto.getTeam1flags(), dto.getTeam2flags(), dto.getTeam1titans(), dto.getTeam2titans(),
                match, team1, team2
        );

        if (whoWon != 0 && isValidated) {
            persistenceService.saveData(matchGamers, killsAndCaps, match, team1, team2, team1gamers, team2gamers);
        }

        applyHandicap(team1gamers, team2gamers);
        return isValidated;
    }

    private void fetchGamers(GamersMatchStatsDTO dto, Gamer[] team1gamers, Gamer[] team2gamers) {
        List<Integer> allGamersId =
                Stream.concat(Arrays.stream(dto.getTeam1gamersId()).boxed(),
                                Arrays.stream(dto.getTeam2gamersId()).boxed())
                        .collect(Collectors.toList());
        if (allGamersId.size() != LOBBY_SIZE) {
            throw new IllegalStateException("Invalid number of Gamers");
        }

        List<Gamer> allGamers = gamerRepository.findAllById(allGamersId);
        Map<Integer, Gamer> gamerMap = allGamers.stream()
                .collect(Collectors.toMap(Gamer::getId, g -> g));

        for (int i = 0; i < TEAM_SIZE; i++) {
            int g1Id = dto.getTeam1gamersId()[i];
            int g2Id = dto.getTeam2gamersId()[i];
            team1gamers[i] = gamerMap.get(g1Id);
            team2gamers[i] = gamerMap.get(g2Id);
        }
    }

    private Match createMatch(String mapPlayed, String server) {
        Match match = new Match();
        match.setMap(Match.Map_Name.valueOf(mapPlayed));
        match.setServer(server);
        return match;
    }

    private int setTeams(Team team1, Team team2,  int[] team1flags, int[] team2flags, String suddenDeathWhoWon) {
        int team1flagsTotal = 0, team2flagsTotal = 0, whoWon=0;
        for (int i = 0; i < TEAM_SIZE; i++) {
            team1flagsTotal += team1flags[i];
            team2flagsTotal += team2flags[i];
        }
        if (team1flagsTotal > team2flagsTotal) {
            whoWon = 1;
        } else if (team1flagsTotal < team2flagsTotal) {
            whoWon = 2;
        } else {
            if (suddenDeathWhoWon == null) {
                whoWon = 0;
            } else if (suddenDeathWhoWon.equals("team1")) {
                whoWon = 1;
            } else if (suddenDeathWhoWon.equals("team2")) {
                whoWon = 2;
            }

        }
        team1.setFlagAdvantage(team1flagsTotal - team2flagsTotal);
        team2.setFlagAdvantage(team2flagsTotal - team1flagsTotal);

        if (whoWon == 1) {
            team1.setWinOrLoose(1);
            team2.setWinOrLoose(0);
        }
        if (whoWon == 2) {
            team1.setWinOrLoose(0);
            team2.setWinOrLoose(1);
        }
        return whoWon;
    }

    private void applyHandicap(Gamer[] team1gamers, Gamer[] team2gamers){
        for (int i = 0; i < TEAM_SIZE; i++) {
            team1gamers[i].setMmr(team1gamers[i].getMmr() - ServerUtils.serverHandicap(team1gamers[i].getServer(),server));
            team2gamers[i].setMmr(team2gamers[i].getMmr() - ServerUtils.serverHandicap(team2gamers[i].getServer(),server));
        }
    }

    private void populateStats(
            MatchGamer[] matchGamers, KillsAndCaps[] killsAndCaps,
            Gamer[] team1gamers, Gamer[] team2gamers,
            int[] team1elims, int[] team2elims,
            int[] team1flags, int[] team2flags,
            String[] team1titans, String[] team2titans,
            Match match, Team team1, Team team2
    ) {
        for (int i = 0; i < TEAM_SIZE; i++) {
            int idx1 = i * 2;
            int idx2 = i * 2 + 1;

            matchGamers[idx1] = new MatchGamer(team1gamers[i], match, team1);
            matchGamers[idx2] = new MatchGamer(team2gamers[i], match, team2);

            killsAndCaps[idx1] = new KillsAndCaps(
                    team1elims[i],
                    team1flags[i],
                    KillsAndCaps.Titan_Name.valueOf(team1titans[i]),
                    matchGamers[idx1]
            );
            killsAndCaps[idx2] = new KillsAndCaps(
                    team2elims[i],
                    team2flags[i],
                    KillsAndCaps.Titan_Name.valueOf(team2titans[i]),
                    matchGamers[idx2]
            );
        }
    }
}

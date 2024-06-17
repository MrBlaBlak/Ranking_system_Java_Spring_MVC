package mrblablak.ranking.workshop.service.lobby.impl;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersMatchStatsDTO;
import mrblablak.ranking.workshop.model.*;
import mrblablak.ranking.workshop.repository.*;
import mrblablak.ranking.workshop.service.lobby.DataHandler;
import mrblablak.ranking.workshop.service.lobby.MmrCalculator;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataHandlerImpl implements DataHandler {
    private static final Logger logger = LoggerFactory.getLogger(DataHandlerImpl.class);
    private final GamerRepository gamerRepository;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final KillsAndCapsRepository killsAndCapsRepository;
    private final MatchGamerRepository matchGamerRepository;
    private final MmrCalculator mmrCalculator;
    private static final int TEAM_SIZE = 5;
    private static final int LOBBY_SIZE = 10;
    private String server;

    @Override
    public boolean setData(GamersMatchStatsDTO gamersMatchStatsDTO, Gamer[] team1gamers, Gamer[] team2gamers) {
        server = gamersMatchStatsDTO.getServer();
        boolean suddenDeath = gamersMatchStatsDTO.isSuddenDeath();
        String suddenDeathWhoWon = gamersMatchStatsDTO.getSuddenDeathWhoWon();
        String[] sTeam1titans = gamersMatchStatsDTO.getTeam1titans();
        String[] sTeam2titans = gamersMatchStatsDTO.getTeam2titans();
        int[] team1gamersId = gamersMatchStatsDTO.getTeam1gamersId();
        int[] team1elims = gamersMatchStatsDTO.getTeam1elims();
        int[] team1flags = gamersMatchStatsDTO.getTeam1flags();
        int[] team2gamersId = gamersMatchStatsDTO.getTeam2gamersId();
        int[] team2elims = gamersMatchStatsDTO.getTeam2elims();
        int[] team2flags = gamersMatchStatsDTO.getTeam2flags();
        String mapPlayed = gamersMatchStatsDTO.getMapPlayed();

        Match match = new Match();
        Team team1 = new Team();
        Team team2 = new Team();
        MatchGamer[] matchGamers = new MatchGamer[LOBBY_SIZE];
        KillsAndCaps[] killsAndCaps = new KillsAndCaps[LOBBY_SIZE];

        setMatch(match, mapPlayed);

        for (int i = 0; i < TEAM_SIZE; i++) {
            Optional<Gamer> optionalGamer = gamerRepository.findById(team1gamersId[i]);

            if (optionalGamer.isPresent()) {
                team1gamers[i] = optionalGamer.get();
            } else {
                return false;
            }
            Optional<Gamer> optionalGamer2 = gamerRepository.findById(team2gamersId[i]);

            if (optionalGamer2.isPresent()) {
                team2gamers[i] = optionalGamer2.get();
            } else {
                return false;
            }
        }

        int whoWon = setTeams(team1, team2, team1flags, team2flags, suddenDeathWhoWon);
        boolean isValidated = mmrCalculator.calculateMmr(whoWon, team1gamers, team2gamers, team1, team2, suddenDeath);
        setMatchGamers(matchGamers, match, team1, team2, team1gamers, team2gamers);
        setKillsAndCaps(killsAndCaps, team1elims, team2elims, team1flags, team2flags, sTeam1titans, sTeam2titans, matchGamers);
        if (whoWon != 0 && isValidated) {
            saveData(matchGamers, killsAndCaps, match, team1, team2, team1gamers, team2gamers);
        }
        setHandicap(team1gamers, team2gamers);
        return isValidated;

    }
    private void setMatch(Match match, String mapPlayed) {
        match.setMap(Match.Map_Name.valueOf(mapPlayed));
        match.setServer(server);
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
    private void saveData(MatchGamer[] matchGamers, KillsAndCaps[] killsAndCaps, Match match, Team team1, Team team2, Gamer[] team1gamers, Gamer[] team2gamers) {
        try {
            matchRepository.save(match);
            teamRepository.save(team1);
            teamRepository.save(team2);

            // Log the state of gamers before saving
            logGamersState(team1gamers, team2gamers);

            // Save team1 gamers
            for (Gamer gamer : team1gamers) {
                if (isValidGamer(gamer)) {
                    gamerRepository.save(gamer);
                } else {
                    throw new IllegalStateException("Invalid Gamer entity: " + gamer);
                }
            }

            // Save team2 gamers
            for (Gamer gamer : team2gamers) {
                if (isValidGamer(gamer)) {
                    gamerRepository.save(gamer);
                } else {
                    throw new IllegalStateException("Invalid Gamer entity: " + gamer);
                }
            }

            // Save match gamers and kills and caps
            for (int i = 0; i < LOBBY_SIZE; i++) {
                matchGamerRepository.save(matchGamers[i]);
                killsAndCapsRepository.save(killsAndCaps[i]);
            }
        } catch (Exception e) {
            // Log the exception with details
            logger.error("Error while saving data: " + e.getMessage(), e);
            throw e; // Re-throw the exception to ensure transaction rollback
        }
    }
    private void setHandicap(Gamer[] team1gamers, Gamer[] team2gamers){
        for (int i = 0; i < TEAM_SIZE; i++) {
            team1gamers[i].setMmr(team1gamers[i].getMmr() - team1gamers[i].serverHandicap(server));
            team2gamers[i].setMmr(team2gamers[i].getMmr() - team2gamers[i].serverHandicap(server));
        }
    }
    private void setMatchGamers(MatchGamer[] matchGamers, Match match, Team team1, Team team2, Gamer[] team1gamers, Gamer[] team2gamers) {
        int counter=0;
        for (int i = 0; i < LOBBY_SIZE; i++) {
            matchGamers[i] = new MatchGamer();
            matchGamers[i].setGamer(team1gamers[counter]);
            matchGamers[i].setMatch(match);
            matchGamers[i].setTeam(team1);
            i++;
            matchGamers[i] = new MatchGamer();
            matchGamers[i].setGamer(team2gamers[counter]);
            matchGamers[i].setMatch(match);
            matchGamers[i].setTeam(team2);
            counter++;
        }
    }
    private void setKillsAndCaps(KillsAndCaps[] killsAndCaps, int[] team1elims, int[] team2elims, int[] team1flags, int[] team2flags, String[] sTeam1titans, String[] sTeam2titans, MatchGamer[] matchGamers) {
        int counter=0;
        for (int i = 0; i < LOBBY_SIZE; i++) {
            killsAndCaps[i] = new KillsAndCaps();
            killsAndCaps[i].setKills(team1elims[counter]);
            killsAndCaps[i].setCaps(team1flags[counter]);
            killsAndCaps[i].setTitan(KillsAndCaps.Titan_Name.valueOf(sTeam1titans[counter]));
            killsAndCaps[i].setMatchGamer(matchGamers[i]);
            i++;
            killsAndCaps[i] = new KillsAndCaps();
            killsAndCaps[i].setKills(team2elims[counter]);
            killsAndCaps[i].setCaps(team2flags[counter]);
            killsAndCaps[i].setTitan(KillsAndCaps.Titan_Name.valueOf(sTeam2titans[counter]));
            killsAndCaps[i].setMatchGamer(matchGamers[i]);
            counter++;
        }
    }
    private void logGamersState(Gamer[] team1gamers, Gamer[] team2gamers) {
        logger.info("Team 1 Gamers:");
        Arrays.stream(team1gamers).forEach(gamer -> logger.info(gamer.toString()));

        logger.info("Team 2 Gamers:");
        Arrays.stream(team2gamers).forEach(gamer -> logger.info(gamer.toString()));
    }

    private boolean isValidGamer(Gamer gamer) {
        // Implement validation logic for the gamer entity
        // Example: Check for non-null fields, valid references, etc.
        return gamer != null && gamer.getId() > 0;
    }
}

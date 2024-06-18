package mrblablak.ranking.workshop.service.lobby.impl;

import lombok.RequiredArgsConstructor;
import mrblablak.ranking.workshop.dtoForForms.GamersDTO;
import mrblablak.ranking.workshop.model.Gamer;
import mrblablak.ranking.workshop.repository.GamerRepository;
import mrblablak.ranking.workshop.service.lobby.Matchmaker;
import mrblablak.ranking.workshop.service.lobby.TeamProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class TeamProcessorImpl implements TeamProcessor {
    private final GamerRepository gamerRepository;
    private final Matchmaker matchmaker;

    private static final int TEAM_SIZE = 5;
    private static final int LOBBY_SIZE = 10;
    private final Gamer[] team1gamers = new Gamer[TEAM_SIZE];
    private final Gamer[] team2gamers = new Gamer[TEAM_SIZE];
    private static String server;

    @Override
    public boolean processTeams(GamersDTO gamersDTO) {
        server = gamersDTO.getServer();
        boolean teamsReady = gamersDTO.isTeamsReady();
        Gamer[] gamers = getGamersWithHandicap(gamersDTO);
        if (teamsReady) {
            prepareTeamsFromGamers(gamers);
            checkAndPrintTeams(server);
        } else {
            matchmaker.calculateAndAssignTeams(gamers, team1gamers, team2gamers);
            checkAndPrintTeams(server);
        }
        return true;
    }

    private Gamer[] getGamersWithHandicap(GamersDTO gamersDTO) {
        Gamer[] gamers = new Gamer[LOBBY_SIZE];
        for (int i = 0; i < LOBBY_SIZE; i++) {
            Optional<Gamer> optionalGamer = gamerRepository.findById(gamersDTO.getGamersList()[i]);
            if (optionalGamer.isPresent()) {
                gamers[i] = optionalGamer.get();
                gamers[i].setMmr(gamers[i].getMmr() - gamers[i].serverHandicap(server));
            } else {
                return new Gamer[0];
            }
        }
        return gamers;
    }

    private void prepareTeamsFromGamers(Gamer[] gamers) {
        for (int i = 0; i < TEAM_SIZE; i++) {
            team1gamers[i] = new Gamer();
            team1gamers[i].cloneValues(gamers[i]);
            team2gamers[i] = new Gamer();
            team2gamers[i].cloneValues(gamers[i + TEAM_SIZE]);
        }
    }

    private void checkAndPrintTeams(String server) {
        checkTeam(team1gamers, server, 1);
        checkTeam(team2gamers, server, 2);
    }

    private void checkTeam(Gamer[] team, String server, int number) {
        for (Gamer a : team) {
            if (a != null) {
                System.out.println(a.getName() + " - " + a.getMmr() + " - " + " - handicap -" + a.serverHandicap(server));
            } else {
                System.out.println("Encountered a null Gamer object in the team.");
            }
        }
        System.out.format("team%d checked \n", number);
    }

    public Gamer[] getTeam1() {
        return team1gamers;
    }

    public Gamer[] getTeam2() {
        return team2gamers;
    }


    public static String getServer() {
        return server;
    }
}

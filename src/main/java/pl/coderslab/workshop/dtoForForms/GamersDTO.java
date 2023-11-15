package pl.coderslab.workshop.dtoForForms;

import pl.coderslab.workshop.model.Gamer;

import java.util.List;

public class GamersDTO {
    private List<Gamer> gamersList;
    private String server;
    private boolean teamsReady;

    public GamersDTO() {
    }

    public GamersDTO(List<Gamer> gamersList, String server, boolean teamsReady) {
        this.gamersList = gamersList;
        this.server = server;
        this.teamsReady = teamsReady;
    }

    public List<Gamer> getGamersList() {
        return gamersList;
    }

    public void setGamersList(List<Gamer> gamersList) {
        this.gamersList = gamersList;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isTeamsReady() {
        return teamsReady;
    }

    public void setTeamsReady(boolean teamsReady) {
        this.teamsReady = teamsReady;
    }
}

package com.mrblablak.rankingSystem.dtoForForms;

public class GamersDTO {
    private int[] gamersList;
    private String server;
    private boolean teamsReady;

    public GamersDTO() {
    }

    public GamersDTO(String server, boolean teamsReady) {
        this.gamersList = new int[10];
        this.server = server;
        this.teamsReady = teamsReady;
    }

    public int[] getGamersList() {
        return gamersList;
    }

    public void setGamersList(int[] gamersList) {
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

    @Override
    public String toString() {
        return "GamersDTO{" +
                "gamersList=" + gamersList +
                ", server='" + server + '\'' +
                ", teamsReady=" + teamsReady +
                '}';
    }
}

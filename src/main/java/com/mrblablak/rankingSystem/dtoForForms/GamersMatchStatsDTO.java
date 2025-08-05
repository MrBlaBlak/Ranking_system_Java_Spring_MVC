package com.mrblablak.rankingSystem.dtoForForms;

import java.util.Arrays;

public class GamersMatchStatsDTO {
    String server;
    boolean suddenDeath;
    String suddenDeathWhoWon;
    String[] team1titans;
    String[] team2titans;
    int[] team1gamersId;
    int[] team1elims;
    int[] team1flags;
    int[] team2gamersId;
    int[] team2elims;
    int[] team2flags;
    String mapPlayed;

    public GamersMatchStatsDTO( ) {

    }

    public GamersMatchStatsDTO(String server, boolean suddenDeath, String suddenDeathWhoWon, String[] team1titans, String[] team2titans, int[] team1gamersId, int[] team1elims, int[] team1flags, int[] team2gamersId, int[] team2elims, int[] team2flags, String mapPlayed) {
        this.server = server;
        this.suddenDeath = suddenDeath;
        this.suddenDeathWhoWon = suddenDeathWhoWon;
        this.team1titans = team1titans;
        this.team2titans = team2titans;
        this.team1gamersId = team1gamersId;
        this.team1elims = team1elims;
        this.team1flags = team1flags;
        this.team2gamersId = team2gamersId;
        this.team2elims = team2elims;
        this.team2flags = team2flags;
        this.mapPlayed = mapPlayed;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public boolean isSuddenDeath() {
        return suddenDeath;
    }

    public void setSuddenDeath(boolean suddenDeath) {
        this.suddenDeath = suddenDeath;
    }

    public String getSuddenDeathWhoWon() {
        return suddenDeathWhoWon;
    }

    public void setSuddenDeathWhoWon(String suddenDeathWhoWon) {
        this.suddenDeathWhoWon = suddenDeathWhoWon;
    }

    public String[] getTeam1titans() {
        return team1titans;
    }

    public void setTeam1titans(String[] team1titans) {
        this.team1titans = team1titans;
    }

    public String[] getTeam2titans() {
        return team2titans;
    }

    public void setTeam2titans(String[] team2titans) {
        this.team2titans = team2titans;
    }

    public int[] getTeam1gamersId() {
        return team1gamersId;
    }

    public void setTeam1gamersId(int[] team1gamersId) {
        this.team1gamersId = team1gamersId;
    }

    public int[] getTeam1elims() {
        return team1elims;
    }

    public void setTeam1elims(int[] team1elims) {
        this.team1elims = team1elims;
    }

    public int[] getTeam1flags() {
        return team1flags;
    }

    public void setTeam1flags(int[] team1flags) {
        this.team1flags = team1flags;
    }

    public int[] getTeam2gamersId() {
        return team2gamersId;
    }

    public void setTeam2gamersId(int[] team2gamersId) {
        this.team2gamersId = team2gamersId;
    }

    public int[] getTeam2elims() {
        return team2elims;
    }

    public void setTeam2elims(int[] team2elims) {
        this.team2elims = team2elims;
    }

    public int[] getTeam2flags() {
        return team2flags;
    }

    public void setTeam2flags(int[] team2flags) {
        this.team2flags = team2flags;
    }

    public String getMapPlayed() {
        return mapPlayed;
    }

    public void setMapPlayed(String mapPlayed) {
        this.mapPlayed = mapPlayed;
    }

    @Override
    public String toString() {
        return "GamersMatchStatsDTO{" +
                ", team1gamersId=" + Arrays.toString(team1gamersId) +
                ", team2gamersId=" + Arrays.toString(team2gamersId) +
                '}';
    }
}

package com.mrblablak.rankingSystem.dtoForForms;

public record GamersMatchStatsDTO(String server, boolean suddenDeath, String suddenDeathWhoWon, String[] team1titans,
                                  String[] team2titans, int[] team1gamersId, int[] team1elims, int[] team1flags,
                                  int[] team2gamersId, int[] team2elims, int[] team2flags, String mapPlayed) {

    public GamersMatchStatsDTO() {
        this(
                "",
                false,
                "",
                new String[5],
                new String[5],
                new int[5],
                new int[5],
                new int[5],
                new int[5],
                new int[5],
                new int[5],
                ""
        );
    }
}

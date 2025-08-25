package com.mrblablak.rankingSystem.dtoForForms;

public record GamersDTO(int[] gamersList, String server, boolean teamsReady) {
    public GamersDTO() {
        this(new int[10], "", false);
    }
}

package com.mrblablak.rankingSystem.utils;

public class ServerUtils {
    public static String[] getAllServers() {
        return new String[]{"EU", "NY"};
    }

    public static int calculateHandicap(String playerServer, String gameServer) {
        return switch (playerServer) {
            case "EU1" -> gameServer.equals("NY") ? 5 : 0;
            case "EU2" -> gameServer.equals("NY") ? 6 : 0;
            case "EU3" -> gameServer.equals("NY") ? 7 : 0;
            case "EU4" -> gameServer.equals("NY") ? 8 : 0;
            case "EU5" -> gameServer.equals("NY") ? 10 : 5;
            case "EU6" -> gameServer.equals("NY") ? 12 : 6;
            case "NY1" -> gameServer.equals("EU") ? 5 : 0;
            case "NY2" -> gameServer.equals("EU") ? 6 : 0;
            case "NY3" -> gameServer.equals("EU") ? 7 : 0;
            case "NY4" -> gameServer.equals("EU") ? 8 : 0;
            case "NY5" -> gameServer.equals("EU") ? 10 : 5;
            case "NY6" -> gameServer.equals("EU") ? 12 : 6;
            default -> 0;
        };
    }
}


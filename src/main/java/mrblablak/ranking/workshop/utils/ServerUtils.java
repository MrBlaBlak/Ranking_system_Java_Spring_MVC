package mrblablak.ranking.workshop.utils;

public class ServerUtils {
    public static String[] getAllServers() {
        return new String[]{"EU", "NY"};
    }
    public static int serverHandicap(String playerServer, String game_server) {
        int handicap = 0;
        switch (playerServer) {
            case "EU1": {
                if (game_server.equals("NY")) handicap = 5;
                else if (game_server.equals("EU")) handicap = 0;
                break;
            }
            case "EU2": {
                if (game_server.equals("NY")) handicap = 6;
                else if (game_server.equals("EU")) handicap = 0;
                break;
            }
            case "EU3": {
                if (game_server.equals("NY")) handicap = 7;
                else if (game_server.equals("EU")) handicap = 0;
                break;
            }
            case "EU4": {
                if (game_server.equals("NY")) handicap = 8;
                else if (game_server.equals("EU")) handicap = 0;
                break;
            }
            case "EU5": {
                if (game_server.equals("NY")) handicap = 10;
                else if (game_server.equals("EU")) handicap = 5;
                break;
            }
            case "EU6": {
                if (game_server.equals("NY")) handicap = 12;
                else if (game_server.equals("EU")) handicap = 6;
                break;
            }
            case "NY1": {
                if (game_server.equals("EU")) handicap = 5;
                else if (game_server.equals("NY")) handicap = 0;
                break;
            }
            case "NY2": {
                if (game_server.equals("EU")) handicap = 6;
                else if (game_server.equals("NY")) handicap = 0;
                break;
            }
            case "NY3": {
                if (game_server.equals("EU")) handicap = 7;
                else if (game_server.equals("NY")) handicap = 0;
                break;
            }
            case "NY4": {
                if (game_server.equals("EU")) handicap = 8;
                else if (game_server.equals("NY")) handicap = 0;
                break;
            }
            case "NY5": {
                if (game_server.equals("EU")) handicap = 10;
                else if (game_server.equals("NY")) handicap = 5;
                break;
            }
            case "NY6": {
                if (game_server.equals("EU")) handicap = 12;
                else if (game_server.equals("NY")) handicap = 6;
                break;
            }
            default: {
                handicap = 0;
            }
        }
        return handicap;
    }

}


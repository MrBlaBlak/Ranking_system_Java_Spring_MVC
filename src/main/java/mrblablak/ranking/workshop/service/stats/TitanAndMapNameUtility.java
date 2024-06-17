package mrblablak.ranking.workshop.service.stats;

public class TitanAndMapNameUtility {

        public static String getTitanName(int titanId) {
            switch (titanId) {
                case 0:
                    return "ion";
                case 1:
                    return "tone";
                case 2:
                    return "ronin";
                case 3:
                    return "northstar";
                case 4:
                    return "monarch";
                case 5:
                    return "legion";
                case 6:
                    return "scorch";
                default:
                    return "none";
            }
        }

        public static String getMapName(int mapId) {
            switch (mapId) {
                case 0:
                    return "boomtown";
                case 1:
                    return "exo";
                case 2:
                    return "eden";
                case 3:
                    return "drydock";
                case 4:
                    return "angel";
                case 5:
                    return "colony";
                case 6:
                    return "glitch";
                default:
                    return "none";
            }
        }

        public static String[] getMapOrder() {
            return new String[]{"boomtown", "exo", "eden", "drydock", "angel", "colony", "glitch"};
        }

        public static String[] getTitanOrder() {
            return new String[]{"ion", "tone", "monarch", "northstar", "ronin", "legion", "scorch"};
        }
    }


package com.mrblablak.rankingSystem.utils;

public class TitanAndMapNameUtility {

        public static String getTitanName(int titanId) {
            return switch (titanId) {
                case 0 -> "ion";
                case 1 -> "tone";
                case 2 -> "ronin";
                case 3 -> "northstar";
                case 4 -> "monarch";
                case 5 -> "legion";
                case 6 -> "scorch";
                default -> "none";
            };
        }

        public static String getMapName(int mapId) {
            return switch (mapId) {
                case 0 -> "boomtown";
                case 1 -> "exo";
                case 2 -> "eden";
                case 3 -> "drydock";
                case 4 -> "angel";
                case 5 -> "colony";
                case 6 -> "glitch";
                default -> "none";
            };
        }

        public static String[] getMapOrder() {
            return new String[]{"boomtown", "exo", "eden", "drydock", "angel", "colony", "glitch"};
        }

        public static String[] getTitanOrder() {
            return new String[]{"ion", "tone", "monarch", "northstar", "ronin", "legion", "scorch"};
        }
    }


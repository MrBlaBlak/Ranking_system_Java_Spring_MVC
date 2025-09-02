package mrblablak.ranking.workshop.utils;

public class StatsUtils {
    public static int toInt(Object obj) {
        return obj == null ? 0 : ((Number) obj).intValue();
    }

    public static int calcWinPercent(int wins, int losses) {
        int total = wins + losses;
        return total == 0 ? 0 : (int) Math.round(wins * 100.0 / total);
    }
    public static double calculateAverageScore(int score, int totalGames) {
        return totalGames == 0 ? 0 : (Math.round((double) score  / (totalGames) * 100)) / 100.0;
    }
}

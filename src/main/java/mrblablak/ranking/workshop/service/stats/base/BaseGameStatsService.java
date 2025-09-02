package mrblablak.ranking.workshop.service.stats.base;

import mrblablak.ranking.workshop.utils.StatsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseGameStatsService<T> {
    protected List<T> buildStats(List<Object[]> results,
                                 Function<String, T> dtoFactory,
                                 TriConsumer<T, String, Integer[]> updater) {

        Map<String, T> statsMap = new HashMap<>();

        for (Object[] result : results) {
            String gamerName = (String) result[0];
            int score = StatsUtils.toInt(result[1]);
            int totalGames = StatsUtils.toInt(result[2]);
            int bestScore = StatsUtils.toInt(result[3]);
            String key = result[4] != null ? (String) result[4] : "";

            T dto = statsMap.computeIfAbsent(gamerName, dtoFactory);
            updater.accept(dto, key, new Integer[]{score, totalGames, bestScore});
        }

        return new ArrayList<>(statsMap.values());
    }
}

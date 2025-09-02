package mrblablak.ranking.workshop.service.stats.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import mrblablak.ranking.workshop.utils.StatsUtils;

public abstract class BaseWinLossStatsService<T> {

    protected List<T> buildStats(List<Object[]> results,
                                 Function<String, T> dtoFactory,
                                 TriConsumer<T, String, Integer[]> updater) {

        Map<String, T> statsMap = new HashMap<>();

        for (Object[] result : results) {
            String gamerName = (String) result[0];
            int wins = StatsUtils.toInt(result[1]);
            int losses = StatsUtils.toInt(result[2]);
            String key = result[3] != null ? (String) result[3] : "";
            int winPercent = StatsUtils.calcWinPercent(wins, losses);

            T dto = statsMap.computeIfAbsent(gamerName, dtoFactory);
            updater.accept(dto, key, new Integer[]{wins, losses, winPercent});
        }

        return new ArrayList<>(statsMap.values());
    }
}

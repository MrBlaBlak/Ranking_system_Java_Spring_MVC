package com.mrblablak.rankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mrblablak.rankingSystem.model.Gamer;
import java.util.List;

@Repository
public interface GamerRepository extends JpaRepository<Gamer, Integer> {


    List<Gamer> findAllByOrderByMmrDesc();
    //find most frequently used titan for every gamer
    @Query(value = "SELECT t.gamer_id, MAX(t.max_titan) AS max_titan " +
            "FROM (SELECT mg.gamer_id, k.titan AS max_titan, " +
            "             ROW_NUMBER() OVER (PARTITION BY mg.gamer_id ORDER BY COUNT(k.titan) DESC) AS row_num " +
            "      FROM match_gamer mg " +
            "      JOIN kills_and_caps k ON mg.id = k.match_gamer_id " +
            "      WHERE k.titan != 8 " +
            "      GROUP BY mg.gamer_id, k.titan) AS t " +
            "WHERE t.row_num = 1 " +
            "GROUP BY t.gamer_id",
            nativeQuery = true)
    List<Object[]> findMostFrequentTitanForGamers();

    //get map stats such as wins and losses for each map for each gamer

    @Query(value = "SELECT g.name, " +
            "SUM(CASE WHEN t.win_or_loose = 1 THEN 1 ELSE 0 END) AS total_wins, " +
            "SUM(CASE WHEN t.win_or_loose = 0 THEN 1 ELSE 0 END) AS total_losses, " +
            "m.map " +
            "FROM gamers g " +
            "LEFT JOIN match_gamer mg ON g.id = mg.gamer_id " +
            "LEFT JOIN matches m ON mg.match_id = m.id " +
            "LEFT JOIN teams t ON mg.team_id = t.id " +
            "GROUP BY g.name, m.map ORDER BY g.name", nativeQuery = true)
    List<Object[]> getMapStats();

    // get stats such as total kills and max kills for each map as well as number of matches played on each map for a gamer
    @Query(value = "SELECT g.name, SUM(kc.kills), COUNT(m.map), MAX(kc.kills), m.map " +
            "FROM gamers g " +
            "LEFT JOIN match_gamer mg ON g.id = mg.gamer_id " +
            "LEFT JOIN matches m ON mg.match_id = m.id " +
            "LEFT JOIN kills_and_caps kc ON kc.match_gamer_id = mg.id " +
            "GROUP BY g.name, m.map ORDER BY g.name", nativeQuery = true)
    List<Object[]> getKillsStats();
    // get stats such as total caps and maxcaps for each map as well as number of matches played on each map for a gamer
    @Query(value = "SELECT g.name, SUM(kc.caps), COUNT(m.map), MAX(kc.caps), m.map " +
            "FROM gamers g " +
            "LEFT JOIN match_gamer mg ON g.id = mg.gamer_id " +
            "LEFT JOIN matches m ON mg.match_id = m.id " +
            "LEFT JOIN kills_and_caps kc ON kc.match_gamer_id = mg.id " +
            "GROUP BY g.name, m.map ORDER BY g.name", nativeQuery = true)
    List<Object[]> getCapsStats();
    //get titan stats such as wins and losses for each titan for each gamer
    @Query(value= "SELECT g.name, " +
            "SUM(CASE WHEN t.win_or_loose = 1 THEN 1 ELSE 0 END) AS total_wins, " +
            "SUM(CASE WHEN t.win_or_loose = 0 THEN 1 ELSE 0 END) AS total_losses, " +
            "kc.titan " +
            "FROM gamers g " +
            "LEFT JOIN match_gamer mg ON g.id = mg.gamer_id " +
            "LEFT JOIN matches m ON mg.match_id = m.id " +
            "LEFT JOIN kills_and_caps kc ON kc.match_gamer_id = mg.id " +
            "LEFT JOIN teams t ON mg.team_id = t.id " +
            "GROUP BY g.name, kc.titan " +
            "ORDER BY g.name", nativeQuery = true)
    List<Object[]> getTitansStats();

    //find "nemesis" that is count wins and losses of a given player when facing each other player on the opposite team
    @Query(value = "SELECT g2.name AS opponent_name, " +
            "       COUNT(CASE WHEN t1.win_or_loose = 1 and t2.win_or_loose = 0 THEN 1 END) AS wins, " +
            "       COUNT(CASE WHEN t1.win_or_loose = 0 AND t2.win_or_loose = 1 THEN 1 END) AS losses " +
            "FROM match_gamer m1 " +
            "         JOIN match_gamer m2 ON m1.match_id = m2.match_id AND m1.gamer_id != m2.gamer_id " +
            "         JOIN teams t1 ON m1.team_id = t1.id " +
            "         JOIN teams t2 ON m2.team_id = t2.id " +
            "         JOIN gamers g2 ON m2.gamer_id = g2.id " +
            "WHERE m1.gamer_id = :playerId " +
            "GROUP BY g2.name " +
            "ORDER BY COUNT(*) DESC ", nativeQuery = true)
    List<Object[]> findMostFrequentLoserOpponent(@Param("playerId") int playerId);
    //find "bestWingman" that is count wins and losses of a given player when playing with each other player on the same team
    @Query(value = "SELECT g2.name AS opponent_name, " +
            "       COUNT(CASE WHEN t1.win_or_loose = 1 and t2.win_or_loose = 1 THEN 1 END) AS wins, " +
            "       COUNT(CASE WHEN t1.win_or_loose = 0 AND t2.win_or_loose = 0 THEN 1 END) AS losses " +
            "FROM match_gamer m1 " +
            "         JOIN match_gamer m2 ON m1.match_id = m2.match_id AND m1.gamer_id != m2.gamer_id " +
            "         JOIN teams t1 ON m1.team_id = t1.id " +
            "         JOIN teams t2 ON m2.team_id = t2.id " +
            "         JOIN gamers g2 ON m2.gamer_id = g2.id " +
            "WHERE m1.gamer_id = :playerId " +
            "GROUP BY g2.name " +
            "ORDER BY COUNT(*) DESC ", nativeQuery = true)
    List<Object[]> findMostFrequentWinnerTeammate(@Param("playerId") int playerId);



}


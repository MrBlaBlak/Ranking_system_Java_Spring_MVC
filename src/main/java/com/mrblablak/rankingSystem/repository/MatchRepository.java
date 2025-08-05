package com.mrblablak.rankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mrblablak.rankingSystem.model.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {

}

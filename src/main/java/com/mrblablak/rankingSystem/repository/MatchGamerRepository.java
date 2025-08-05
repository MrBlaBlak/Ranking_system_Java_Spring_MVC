package com.mrblablak.rankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mrblablak.rankingSystem.model.MatchGamer;

public interface MatchGamerRepository extends JpaRepository<MatchGamer, Integer> {
}

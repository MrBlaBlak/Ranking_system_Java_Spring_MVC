package com.mrblablak.rankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mrblablak.rankingSystem.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}

package com.mrblablak.rankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mrblablak.rankingSystem.model.KillsAndCaps;

public interface KillsAndCapsRepository extends JpaRepository<KillsAndCaps, Integer> {
}

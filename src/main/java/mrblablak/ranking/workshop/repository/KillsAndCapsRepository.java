package mrblablak.ranking.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mrblablak.ranking.workshop.model.KillsAndCaps;

public interface KillsAndCapsRepository extends JpaRepository<KillsAndCaps, Integer> {
}

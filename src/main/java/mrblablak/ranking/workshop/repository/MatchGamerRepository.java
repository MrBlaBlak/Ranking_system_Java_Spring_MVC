package mrblablak.ranking.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mrblablak.ranking.workshop.model.MatchGamer;

public interface MatchGamerRepository extends JpaRepository<MatchGamer, Integer> {
}

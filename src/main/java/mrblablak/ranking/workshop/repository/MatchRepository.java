package mrblablak.ranking.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mrblablak.ranking.workshop.model.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {

}

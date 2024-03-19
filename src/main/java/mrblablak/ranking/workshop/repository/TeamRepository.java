package mrblablak.ranking.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import mrblablak.ranking.workshop.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}

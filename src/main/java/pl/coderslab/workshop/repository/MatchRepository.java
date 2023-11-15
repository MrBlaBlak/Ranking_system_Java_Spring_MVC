package pl.coderslab.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.workshop.model.Match;

public interface MatchRepository extends JpaRepository<Match, Integer> {

}

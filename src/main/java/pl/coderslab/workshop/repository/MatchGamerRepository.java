package pl.coderslab.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.workshop.model.MatchGamer;

public interface MatchGamerRepository extends JpaRepository<MatchGamer, Integer> {
}

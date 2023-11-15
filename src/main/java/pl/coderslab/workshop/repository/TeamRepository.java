package pl.coderslab.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.workshop.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}

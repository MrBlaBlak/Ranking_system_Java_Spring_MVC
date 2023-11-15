package pl.coderslab.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.workshop.model.KillsAndCaps;

public interface KillsAndCapsRepository extends JpaRepository<KillsAndCaps, Integer> {
}

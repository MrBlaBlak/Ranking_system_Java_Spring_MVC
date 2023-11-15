package pl.coderslab.workshop.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.workshop.model.Gamer;
import pl.coderslab.workshop.model.KillsAndCaps;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class KillsAndCapsDao {
    @PersistenceContext
    private EntityManager entityManager;
    public void saveKillsAndCaps(KillsAndCaps killsAndCaps){
        entityManager.persist(killsAndCaps);
    }
}

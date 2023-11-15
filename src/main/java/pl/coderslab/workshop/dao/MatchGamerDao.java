package pl.coderslab.workshop.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.workshop.model.Gamer;
import pl.coderslab.workshop.model.MatchGamer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class MatchGamerDao {
    @PersistenceContext
    private EntityManager entityManager;
    public void saveMatchGamer(MatchGamer matchGamer){
        entityManager.persist(matchGamer);
    }
}

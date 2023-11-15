package pl.coderslab.workshop.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.workshop.model.Gamer;
import pl.coderslab.workshop.model.Match;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class MatchDao {
    @PersistenceContext
    private EntityManager entityManager;
    public void saveMatch(Match match){
        entityManager.persist(match);
    }
    public int getLastMatchId(){
        return (int) entityManager.createQuery("SELECT MAX(m.id) FROM Match m").getSingleResult();
    }

}

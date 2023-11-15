package pl.coderslab.workshop.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.workshop.model.Gamer;
import pl.coderslab.workshop.model.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class TeamDao {
    @PersistenceContext
    private EntityManager entityManager;
    public void saveTeam(Team team){
        entityManager.persist(team);
    }
    public int getLastTeamId(){
        return (int) entityManager.createQuery("SELECT MAX(t.id) FROM Team t").getSingleResult();
    }

}

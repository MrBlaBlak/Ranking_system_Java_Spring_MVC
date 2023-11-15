package pl.coderslab.workshop.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.workshop.model.Gamer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.awt.print.Book;
import java.util.List;

@Repository
@Transactional
public class GamerDao {
    @PersistenceContext
    private EntityManager entityManager;
    public void saveGamer(Gamer gamer){
        entityManager.persist(gamer);
    }
    public Gamer findById(int id) {
        return entityManager.find(Gamer.class, id);
    }
    public void update(Gamer gamer) {
        entityManager.merge(gamer);
    }
    public void delete(Gamer gamer) {
        entityManager.remove(entityManager.contains(gamer) ? gamer : entityManager.merge(gamer));
    }
    public List<Gamer> findAll(){
        Query query = entityManager.createQuery("SELECT g FROM Gamer g");
        return query.getResultList();
    }


}

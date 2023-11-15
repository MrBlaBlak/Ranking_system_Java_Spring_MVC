package pl.coderslab.homework.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.homework.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class AuthorDao {
    @PersistenceContext
    EntityManager entityManager;
    public void save(Author author){
        entityManager.persist(author);
    }
    public void update(Author author){
        entityManager.merge(author);
    }
    public void delete(Author author){
        entityManager.remove(entityManager.contains(author) ? author : entityManager.merge(author));
    }
    public List<Author> findAll(){
        return entityManager.createQuery("SELECT a FROM Author a").getResultList();
    }
}

package pl.coderslab.homework.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.homework.model.Article;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class ArticleDao {
    @PersistenceContext
    EntityManager entityManager;
    public void save(Article entity) {
        entityManager.persist(entity);
    }

    public void update(Article entity) {
        entityManager.merge(entity);
    }

    public Article getById(long id) {
        return entityManager.find(Article.class, id);
    }

    public void delete(Article article) {
        entityManager.remove(entityManager.contains(article) ? article : entityManager.merge(article));
    }
    public List<Article> findAll(){
        return entityManager.createQuery("SELECT a FROM Article a").getResultList();
    }
    public List<Article> findLast5(){
        return entityManager.createQuery("SELECT a FROM Article a").setMaxResults(5).getResultList();
    }
}

package andrey019.dao;

import andrey019.model.dao.Todo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("todoDaoImpl")
public class TodoDaoImpl implements TodoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Todo getById(long id) {
        try {
            return entityManager.find(Todo.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

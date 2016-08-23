package andrey019.dao;


import andrey019.model.dao.DoneTodo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("doneTodoDao")
public class DoneTodoDaoImpl implements DoneTodoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public DoneTodo getById(long id) {
        try {
            return entityManager.find(DoneTodo.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

package andrey019.dao;

import andrey019.model.dao.TodoList;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("todoListDao")
public class TodoListDaoImpl implements TodoListDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean save(TodoList todoList) {
        try {
            entityManager.merge(todoList);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(TodoList todoList) {
        try {
            todoList = entityManager.merge(todoList);
            entityManager.remove(todoList);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public TodoList getById(long id) {
        try {
            return entityManager.find(TodoList.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public TodoList getByIdWithTodos(long id) {
        try {
            @SuppressWarnings("unchecked")
            List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                    "left join fetch list.todos where list.id = :idParam")
                    .setParameter("idParam", id).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public TodoList getByIdWithDoneTodos(long id) {
        try {
            @SuppressWarnings("unchecked")
            List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                    "left join fetch list.doneTodos where list.id = :idParam")
                    .setParameter("idParam", id).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public TodoList getByIdWithTodosAndDoneTodos(long id) {
        try {
            @SuppressWarnings("unchecked")
            List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                    "left join fetch list.todos left join fetch list.doneTodos where list.id = :idParam")
                    .setParameter("idParam", id).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public TodoList getByIdWithUsersAndSharedLists(long id) {
        try {
            @SuppressWarnings("unchecked")
            List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                    "left join fetch list.users as user left join fetch user.sharedTodoLists where list.id = :idParam")
                    .setParameter("idParam", id).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override
    public TodoList getByIdWithUsers(long id) {
        try {
            @SuppressWarnings("unchecked")
            List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                    "left join fetch list.users where list.id = :idParam")
                    .setParameter("idParam", id).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

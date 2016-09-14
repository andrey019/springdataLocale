package andrey019.service.dao;


import andrey019.model.dao.*;
import andrey019.repository.DoneTodoRepository;
import andrey019.repository.TodoListRepository;
import andrey019.repository.TodoRepository;
import andrey019.repository.UserRepository;
import andrey019.service.HtmlGenerator;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Service("todoService")
public class TodoServiceImpl implements TodoService {

    private final static String ERROR = "error";
    private final static String OK = "ok";
    private final static String USER_NOT_FOUND = "There is no such user!";
    private final static String EMAIL_NOT_VALID = "Email is not valid!";
    private final static String ALREADY_HAVE = "You already have this list!";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoListRepository todoListRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private DoneTodoRepository doneTodoRepository;

    @Autowired
    private HtmlGenerator htmlGenerator;

    @Autowired
    private EmailValidator emailValidator;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public String addTodoList(String email, String todoListName) {
        TodoList todoList = new TodoList();
        todoList.setName(escapeHtml(todoListName));
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ERROR;
        }
        user.addTodoList(todoList);
        user.addSharedTodoList(todoList);
        if (userRepository.save(user) == null) {
            return ERROR;
        }
        return OK;
    }

    @Transactional
    @Override
    public String addTodo(String email, long todoListId, String todoText) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return ERROR;
        }
        Todo todo = new Todo();
        todo.setCreatedByEmail(user.getEmail());
        todo.setCreatedByName(user.getFullName());
        todo.setTodoText(escapeHtml(todoText));
        todo.setCreated(new Date());
        todoList.addTodo(todo);
        if (todoListRepository.save(todoList) == null) {
            return ERROR;
        }
        return OK;
    }

    private String escapeHtml(String text) {
        return StringEscapeUtils.escapeHtml4(text).replace("\"", "&quot;");
    }

    @Transactional
    @Override
    public String doneTodo(String email, long todoListId, long todoId) {
        Todo todo = todoRepository.findOne(todoId);
        if ( (todo == null) || (todo.getTodoList().getId() != todoListId) ) {
            return ERROR;
        }
        User user = userRepository.findByEmail(email);
        if ( (user == null) || (!user.getSharedTodoLists().contains(todo.getTodoList())) ) {
            return ERROR;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        DoneTodo doneTodo = new DoneTodo();
        doneTodo.setFromTodo(todo);
        doneTodo.setDoneByEmail(user.getEmail());
        doneTodo.setDoneByName(user.getFullName());
        doneTodo.setDone(new Date());
        todoList.addDoneTodo(doneTodo);
        todoList.removeTodo(todo);
        if (todoListRepository.save(todoList) == null) {
            return ERROR;
        }
        return OK;
    }

    @Transactional
    @Override
    public String unDoneTodo(String email, long todoListId, long doneTodoId) {
        DoneTodo doneTodo = doneTodoRepository.findOne(doneTodoId);
        if ( (doneTodo == null) || (doneTodo.getTodoList().getId() != todoListId) ) {
            return ERROR;
        }
        User user = userRepository.findByEmail(email);
        if ( (user == null) || (!user.getSharedTodoLists().contains(doneTodo.getTodoList())) ) {
            return ERROR;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        Todo todo = new Todo();
        todo.setFromDoneTodo(doneTodo);
        todoList.addTodo(todo);
        todoList.removeDoneTodo(doneTodo);
        if (todoListRepository.save(todoList) == null) {
            return ERROR;
        }
        return OK;
    }

    @Transactional
    @Override
    public String shareWith(String email, long todoListId, String emailToShareWith) {
        if (!emailValidator.isValid(emailToShareWith)) {
            return EMAIL_NOT_VALID;
        }
        if (email.equals(emailToShareWith)) {
            return ALREADY_HAVE;
        }
        User userToShare = userRepository.findByEmail(emailToShareWith);
        if (userToShare == null) {
            return USER_NOT_FOUND;
        }
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!todoList.getUsers().contains(user)) {
            return ERROR;
        }
        todoList.addUsers(userToShare);
        if (todoListRepository.save(todoList) == null) {
            return ERROR;
        }
        return OK;
    }

    @Transactional
    @Override
    public String unShareWith(String email, long todoListId, long idToUnShareWith) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ERROR;
        }
        User userToUnShare = userRepository.findOne(idToUnShareWith);
        if (userToUnShare == null) {
            return ERROR;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if ( (!todoList.getUsers().contains(user)) || (todoList.getOwner().equals(userToUnShare))) {
            return ERROR;
        }
        todoList.removeUsers(userToUnShare);
        if (todoListRepository.save(todoList) == null) {
            return ERROR;
        }
        return OK;
    }

    @Transactional
    @Override
    public String deleteTodoList(String email, long todoListId) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!todoList.getOwner().equals(user)) {
            user.removeSharedTodoList(todoList);
            userRepository.save(user);
            return OK;
        }
        todoList.getUsers().size();
        for (User innerUser : todoList.getUsers()) {
            innerUser.getSharedTodoLists().remove(todoList);
        }
        todoListRepository.delete(todoList);
        return OK;
    }

    @Override
    public String getDeleteInfo(String email, long todoListId) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ERROR;
        }
        List<User> users = userRepository.findBySharedTodoLists_Id(todoListId);
        if ( (users == null) || (users.isEmpty()) ) {
            return ERROR;
        }
        if (!users.contains(user)) {
            return ERROR;
        }
        users.remove(user);
        return htmlGenerator.generateTodoListsInfoHtml(users);
    }

    @Transactional
    @Override
    public String getSharedWithInfo(String email, long todoListId) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!todoList.getUsers().contains(user)) {
            return ERROR;
        }
        entityManager.detach(todoList);
        todoList.getUsers().remove(user);
        if (todoList.getOwner().equals(user)) {
            return htmlGenerator.generateSharedInfoHtml(todoList.getUsers(), null);
        } else {
            todoList.getUsers().remove(todoList.getOwner());
            return htmlGenerator.generateSharedInfoHtml(todoList.getUsers(), todoList.getOwner());
        }
    }

    @Transactional
    @Override
    public String getTodosByListId(String email, long todoListId, String timeZone) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return null;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return null;
        }
        todoList.getTodos().size();
        return htmlGenerator.generateTodosHtml(todoList.getTodos(), timeZone);
    }

    @Transactional
    @Override
    public String getDoneTodosByListId(String email, long todoListId, String timeZone) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListRepository.findOne(todoListId);
        if (todoList == null) {
            return null;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return null;
        }
        todoList.getDoneTodos().size();
        return htmlGenerator.generateDoneTodosHtml(todoList.getDoneTodos(), timeZone);
    }

    @Transactional
    @Override
    public String getAllTodoLists(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        user.getSharedTodoLists().size();
        return htmlGenerator.generateTodoListsHtml(user.getSharedTodoLists());
    }

    @Transactional
    @Override
    public String getDonationInfo(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ERROR;
        }
        user.getDonations().size();
        double amount = 0;
        for (Donation donation : user.getDonations()) {
            amount += donation.getAmount();
        }
        return Double.toString(amount);
    }
}

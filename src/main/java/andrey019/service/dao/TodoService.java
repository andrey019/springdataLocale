package andrey019.service.dao;


import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;

import java.util.Set;

public interface TodoService {

    String addTodoList(String email, String todoListName);

    String addTodo(String email, long todoListId, String todoText);

    String doneTodo(String email, long todoListId, long todoId);

    String unDoneTodo(String email, long todoListId, long doneTodoId);

    String shareWith(String email, long todoListId, String emailToShareWith);

    String unShareWith(String email, long todoListId, long idToUnShareWith);

    String deleteTodoList(String email, long todoListId);

    String getDeleteInfo(String email, long todoListId);

    String getSharedWithInfo(String email, long todoListId);

    Set<Todo> getTodosByListId(String email, long todoListId);

    String getDoneTodosByListId(String email, long todoListId, String timeZone);

    Set<TodoList> getAllTodoLists(String email);

    String getDonationInfo(String email);
}

package andrey019.service;


import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;

import java.util.HashMap;
import java.util.Set;

public interface SearchService {

    HashMap<TodoList, Set<Todo>> findTodos(String email, String request);
}

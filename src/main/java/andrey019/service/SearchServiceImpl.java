package andrey019.service;


import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;
import andrey019.model.json.JsonSearchResult;
import andrey019.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    @Override
    public List<JsonSearchResult> findTodos(String email, String request) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return prepareResult(search(user, request));
    }

    private HashMap<TodoList, Set<Todo>> search(User user, String request) {
        HashMap<TodoList, Set<Todo>> result = new HashMap<>();
        user.getSharedTodoLists().size();
        for (TodoList todoList : user.getSharedTodoLists()) {
            todoList.getTodos().size();
            for (Todo todo : todoList.getTodos()) {
                if (todo.getTodoText().contains(request)) {
                    if (result.containsKey(todoList)) {
                        result.get(todoList).add(todo);
                    } else {
                        Set<Todo> newSet = new HashSet<>();
                        newSet.add(todo);
                        result.put(todoList, newSet);
                    }
                }
            }
        }
        return result;
    }

    private List<JsonSearchResult> prepareResult(Map<TodoList, Set<Todo>> rawResult) {
        List<JsonSearchResult> result = new ArrayList<>();
        for (Map.Entry<TodoList, Set<Todo>> entry : rawResult.entrySet()) {
            JsonSearchResult jsr = new JsonSearchResult(entry.getKey());
            jsr.setTodos(entry.getValue());
            result.add(jsr);
        }
        return result;
    }
}

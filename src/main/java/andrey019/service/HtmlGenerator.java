package andrey019.service;


import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;

import java.util.List;
import java.util.Set;

public interface HtmlGenerator {

    String generateTodoListsHtml(Set<TodoList> todoLists);
    String generateTodosHtml(Set<Todo> todos);
    String generateDoneTodosHtml(Set<DoneTodo> doneTodos);
    String generateTodoListsInfoHtml(List<User> users);
    String generateSharedInfoHtml(Set<User> users, User owner);
}

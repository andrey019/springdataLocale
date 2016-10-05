package andrey019.model.json;


import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;

import java.util.Set;

public class JsonSearchResult {

    private long id;

    private String name;

    private int todoAmount;

    private Set<Todo> todos;

    public JsonSearchResult() {}

    public JsonSearchResult(TodoList todoList) {
        this.id = todoList.getId();
        this.name = todoList.getName();
        this.todoAmount = todoList.getTodoAmount();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTodoAmount() {
        return todoAmount;
    }

    public void setTodoAmount(int todoAmount) {
        this.todoAmount = todoAmount;
    }

    public Set<Todo> getTodos() {
        return todos;
    }

    public void setTodos(Set<Todo> todos) {
        this.todos = todos;
    }
}

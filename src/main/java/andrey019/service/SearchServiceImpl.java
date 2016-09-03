package andrey019.service;


import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;
import andrey019.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

    private final static String TODO_GROUP_START = "<div class=\"list-group\">";
    private final static String TODO_GROUP_END = "</div>";

    private final static String TODO_BUTTON_0 = "<button id=\"todo=";
    private final static String TODO_BUTTON_1 = "\" name=\"list=";
    private final static String TODO_BUTTON_2 = "\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"doneTodoFromSearch(event)\" style=\"word-wrap: break-word\">";
    private final static String TODO_BUTTON_3 = "<div style=\"font-size:11px; text-align: right\">Created by: ";
    private final static String TODO_BUTTON_4 = ". At: ";
    private final static String TODO_BUTTON_5 = ".</div></button>";

    private final static String LIST_BUTTON_0 = "<button id=\"list=";
    private final static String LIST_BUTTON_1 = "\" type=\"button\" class=\"btn btn-primary\" " +
            "onclick=\"loadTodos(event)\" name=\"";
    private final static String LIST_BUTTON_2 = "\" style=\"word-wrap: break-word\">";
    private final static String LIST_BUTTON_3 = "   <span class=\"badge\">";
    private final static String LIST_BUTTON_4 = "</span></button>";

    private final static String NEW_LINE = "<br>";
    private final static String EMPTY = "";
    private final static String DATE_FORMAT = "HH:mm:ss dd-MM-yyyy";
    private final static String DEFAULT_TIMEZONE = "GMT";

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public String findTodos(String email, String request, String timeZone) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return EMPTY;
        }
        return searchAndBuild(user, request, timeZone);
    }

    private String searchAndBuild(User user, String request, String timeZone) {
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat dateFormatter = getDateFormatter(timeZone);
        boolean isFound = false;
        user.getSharedTodoLists().size();
        for (TodoList todoList : user.getSharedTodoLists()) {
            todoList.getTodos().size();
            for (Todo todo : todoList.getTodos()) {
                if (todo.getTodoText().contains(request)) {
                    if (!isFound) {
                        stringBuilder.append(TODO_GROUP_START);
                        isFound = true;
                    }
                    addTodo(stringBuilder, todo, dateFormatter);
                }
            }
            if (isFound) {
                stringBuilder.append(TODO_GROUP_END);
                addListButton(stringBuilder, todoList);
                isFound = false;
            }
        }
        return stringBuilder.toString();
    }

    private void addTodo(StringBuilder stringBuilder, Todo todo, SimpleDateFormat dateFormatter) {
        stringBuilder.append(TODO_BUTTON_0);
        stringBuilder.append(todo.getId());
        stringBuilder.append(TODO_BUTTON_1);
        stringBuilder.append(todo.getTodoList().getId());
        stringBuilder.append(TODO_BUTTON_2);
        stringBuilder.append(todo.getTodoText());
        stringBuilder.append(TODO_BUTTON_3);
        stringBuilder.append(todo.getCreatedByName());
        stringBuilder.append(TODO_BUTTON_4);
        stringBuilder.append(dateFormatter.format(todo.getCreated()));
        stringBuilder.append(TODO_BUTTON_5);
    }

    private void addListButton(StringBuilder stringBuilder, TodoList todoList) {
        stringBuilder.append(LIST_BUTTON_0);
        stringBuilder.append(todoList.getId());
        stringBuilder.append(LIST_BUTTON_1);
        stringBuilder.append(todoList.getName());
        stringBuilder.append(LIST_BUTTON_2);
        stringBuilder.append(todoList.getName());
        stringBuilder.append(LIST_BUTTON_3);
        stringBuilder.append(todoList.getTodoAmount());
        stringBuilder.append(LIST_BUTTON_4);
        stringBuilder.append(NEW_LINE);
        stringBuilder.append(NEW_LINE);
        stringBuilder.append(NEW_LINE);
    }

    private SimpleDateFormat getDateFormatter(String timeZone) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        if (timeZone != null) {
            dateFormatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        } else {
            dateFormatter.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        }
        return dateFormatter;
    }
}

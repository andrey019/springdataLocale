package andrey019.controller;

import andrey019.model.api.JsonList;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.json.JsonFindTodo;
import andrey019.model.json.JsonMessage;
import andrey019.model.json.JsonProfile;
import andrey019.model.json.JsonTodoList;
import andrey019.service.SearchService;
import andrey019.service.auth.ProfileService;
import andrey019.service.dao.TodoService;
import andrey019.service.maintenance.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static String TEXT_UTF8 = "text/plain;charset=UTF-8";
    private final static String JSON_UTF8 = "application/json;charset=UTF-8";

    @Autowired
    private LogService logService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SearchService searchService;


    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String userPage(HttpServletRequest request) {
        logService.accessToPage("user " + getUserEmail() + ", ip = " + request.getRemoteAddr());
        return "user_page";
    }

    @RequestMapping(value = "/loadLists", method = RequestMethod.POST, produces = JSON_UTF8)
    @ResponseBody
    public Set<TodoList> loadLists() {
        logService.ajaxJson("loadLists " + getUserEmail());
        return todoService.getAllTodoLists(getUserEmail());
    }

//    @RequestMapping(value = "/loadTodos", method = RequestMethod.POST, produces = TEXT_UTF8)
//    @ResponseBody
//    public String loadTodos(@RequestBody JsonTodoList jsonTodoList) {
//        logService.ajaxJson("loadTodos " + getUserEmail());
//        return todoService.getTodosByListId(getUserEmail(), jsonTodoList.getTodoListId(),
//                jsonTodoList.getTimeZone());
//    }

    @RequestMapping(value = "/loadTodos", method = RequestMethod.POST, produces = JSON_UTF8)
    @ResponseBody
    public Set<Todo> loadTodos(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("loadTodos " + getUserEmail());
        return todoService.getTodosByListId(getUserEmail(), jsonTodoList.getTodoListId());
    }

    @RequestMapping(value = "/loadDoneTodos", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String loadDoneTodos(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("loadDoneTodos " + getUserEmail());
        return todoService.getDoneTodosByListId(getUserEmail(), jsonTodoList.getTodoListId(),
                jsonTodoList.getTimeZone());
    }

    @RequestMapping(value = "/addTodo", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String addTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("addTodo " + getUserEmail());
        return todoService.addTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getTodoText());
    }

    @RequestMapping(value = "/doneTodo", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String doneTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("doneTodo " + getUserEmail());
        return todoService.doneTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getTodoId());
    }

    @RequestMapping(value = "/unDoneTodo", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String unDoneTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("unDoneTodo " + getUserEmail());
        return todoService.unDoneTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getDoneTodoId());
    }

    @RequestMapping(value = "/addTodoList", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String addTodoList(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("addTodoList " + getUserEmail());
        return todoService.addTodoList(getUserEmail(), jsonMessage.getListName());
    }

    @RequestMapping(value = "/todoListDeleteInfo", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String getTodoListDeleteInfo(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("getTodoListDeleteInfo " + getUserEmail());
        return todoService.getDeleteInfo(getUserEmail(), jsonTodoList.getTodoListId());
    }

    @RequestMapping(value = "/deleteTodoList", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String deleteTodoList(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("getTodoListDeleteInfo " + getUserEmail());
        return todoService.deleteTodoList(getUserEmail(), jsonTodoList.getTodoListId());
    }

    @RequestMapping(value = "/todoListShareInfo", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String todoListShareInfo(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("todoListShareInfo " + getUserEmail());
        return todoService.getSharedWithInfo(getUserEmail(), jsonTodoList.getTodoListId());
    }

    @RequestMapping(value = "/shareUser", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String shareUser(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("shareUser " + getUserEmail());
        return todoService.shareWith(getUserEmail(), jsonMessage.getListId(), jsonMessage.getShareWith());
    }

    @RequestMapping(value = "/unShareUser", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String unShareUser(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("unShareUser " + getUserEmail());
        return todoService.unShareWith(getUserEmail(), jsonMessage.getListId(), jsonMessage.getUnShareWith());
    }

    @RequestMapping(value = "/getProfile", method = RequestMethod.POST, produces = JSON_UTF8)
    public @ResponseBody JsonProfile getProfile() {
        logService.ajaxJson("getProfile " + getUserEmail());
        return profileService.getProfile(getUserEmail());
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String updateProfile(@RequestBody JsonProfile jsonProfile) {
        logService.ajaxJson("updateProfile " + getUserEmail());
        return profileService.updateProfile(getUserEmail(), jsonProfile);
    }

    @RequestMapping(value = "/findTodo", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String findTodo(@RequestBody JsonFindTodo jsonFindTodo) {
        logService.ajaxJson("findTodo " + getUserEmail() + " / " + jsonFindTodo.getRequest());
        return searchService.findTodos(getUserEmail(), jsonFindTodo.getRequest(), jsonFindTodo.getTimeZone());
    }

    @RequestMapping(value = "/donationInfo", method = RequestMethod.POST, produces = TEXT_UTF8)
    @ResponseBody
    public String findTodo() {
        logService.ajaxJson("donationInfo " + getUserEmail());
        return todoService.getDonationInfo(getUserEmail());
    }

    private String getUserEmail(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}

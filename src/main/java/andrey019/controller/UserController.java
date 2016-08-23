package andrey019.controller;

import andrey019.model.json.JsonFindTodo;
import andrey019.model.json.JsonMessage;
import andrey019.model.json.JsonProfile;
import andrey019.model.json.JsonTodoList;
import andrey019.service.HtmlGenerator;
import andrey019.service.SearchService;
import andrey019.service.auth.ProfileService;
import andrey019.service.dao.TodoService;
import andrey019.service.maintenance.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static String RESPONSE_OK = "ok";
    private final static String RESPONSE_ERROR = "error";

    @Autowired
    private LogService logService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private HtmlGenerator htmlGenerator;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public String userPage() {
        logService.accessToPage("user (user_page)");
        return "user_page";
    }

    @RequestMapping(value = "/loadLists", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadLists() {
        logService.ajaxJson("loadLists " + getUserEmail());
        return htmlGenerator.generateTodoListsHtml(todoService.getAllTodoLists(getUserEmail()));
    }

    @RequestMapping(value = "/loadTodos", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadTodos(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("loadTodos " + getUserEmail());
        return htmlGenerator.generateTodosHtml(todoService.getTodosByListId(getUserEmail(), jsonMessage.getListId()));
    }

    @RequestMapping(value = "/loadDoneTodos", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loadDoneTodos(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("loadDoneTodos " + getUserEmail());
        return htmlGenerator.generateDoneTodosHtml
                (todoService.getDoneTodosByListId(getUserEmail(), jsonMessage.getListId()));
    }

    @RequestMapping(value = "/addTodo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("addTodo " + getUserEmail());
        if (todoService.addTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getTodoText())) {
            return RESPONSE_OK;
        }
        return RESPONSE_ERROR;
    }

    @RequestMapping(value = "/doneTodo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String doneTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("doneTodo " + getUserEmail());
        if (todoService.doneTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getTodoId())) {
            return RESPONSE_OK;
        }
        return RESPONSE_ERROR;
    }

    @RequestMapping(value = "/unDoneTodo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String unDoneTodo(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("unDoneTodo " + getUserEmail());
        if (todoService.unDoneTodo(getUserEmail(), jsonMessage.getListId(), jsonMessage.getDoneTodoId())) {
            return RESPONSE_OK;
        }
        return RESPONSE_ERROR;
    }

    @RequestMapping(value = "/addTodoList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addTodoList(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("addTodoList " + getUserEmail());
        if (todoService.addTodoList(getUserEmail(), jsonMessage.getListName())) {
            return RESPONSE_OK;
        }
        return RESPONSE_ERROR;
    }

    @RequestMapping(value = "/todoListDeleteInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getTodoListDeleteInfo(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("getTodoListDeleteInfo " + getUserEmail());
        return todoService.getTodoListInfo(getUserEmail(), jsonTodoList.getTodoListId());
    }

    @RequestMapping(value = "/deleteTodoList", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String deleteTodoList(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("getTodoListDeleteInfo " + getUserEmail());
        return todoService.deleteTodoList(getUserEmail(), jsonTodoList.getTodoListId());
    }

    @RequestMapping(value = "/todoListShareInfo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String todoListShareInfo(@RequestBody JsonTodoList jsonTodoList) {
        logService.ajaxJson("todoListShareInfo " + getUserEmail());
        return todoService.getSharedWithInfo(getUserEmail(), jsonTodoList.getTodoListId());
    }

    @RequestMapping(value = "/shareUser", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String shareUser(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("shareUser " + getUserEmail());
        return todoService.shareWith(getUserEmail(), jsonMessage.getListId(), jsonMessage.getShareWith());
    }

    @RequestMapping(value = "/unShareUser", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String unShareUser(@RequestBody JsonMessage jsonMessage) {
        logService.ajaxJson("unShareUser " + getUserEmail());
        return todoService.unShareWith(getUserEmail(), jsonMessage.getListId(), jsonMessage.getUnShareWith());
    }

    @RequestMapping(value = "/getProfile", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody JsonProfile getProfile() {
        logService.ajaxJson("getProfile " + getUserEmail());
        return profileService.getProfile(getUserEmail());
    }

    @RequestMapping(value = "/updateProfile", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateProfile(@RequestBody JsonProfile jsonProfile) {
        logService.ajaxJson("updateProfile " + getUserEmail());
        return profileService.updateProfile(getUserEmail(), jsonProfile);
    }

    @RequestMapping(value = "/findTodo", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String findTodo(@RequestBody JsonFindTodo jsonFindTodo) {
        logService.ajaxJson("findTodo " + getUserEmail() + " / " + jsonFindTodo.getRequest());
        return searchService.findTodos(getUserEmail(), jsonFindTodo.getRequest());
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

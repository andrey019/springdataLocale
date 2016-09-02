package andrey019.model.json;


public class JsonTodoList {

    private long todoListId;

    private String timeZone;

    public long getTodoListId() {
        return todoListId;
    }

    public void setTodoListId(long todoListId) {
        this.todoListId = todoListId;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}

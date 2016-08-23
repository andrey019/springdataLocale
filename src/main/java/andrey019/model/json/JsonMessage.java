package andrey019.model.json;


public class JsonMessage {

    private long listId;
    private long todoId;
    private long doneTodoId;
    private String shareWith;
    private long unShareWith;
    private String todoText;
    private String listName;

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

    public long getDoneTodoId() {
        return doneTodoId;
    }

    public void setDoneTodoId(long doneTodoId) {
        this.doneTodoId = doneTodoId;
    }

    public String getShareWith() {
        return shareWith;
    }

    public void setShareWith(String shareWith) {
        this.shareWith = shareWith;
    }

    public long getUnShareWith() {
        return unShareWith;
    }

    public void setUnShareWith(long unShareWith) {
        this.unShareWith = unShareWith;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    @Override
    public String toString() {
        return "listId: " + listId + ", todoId: " + todoId + ", doneTodoId: " + doneTodoId +
                ", shareWith: " + shareWith + ", unShareWith: " + unShareWith + ", todoText: " + todoText +
                ", listName: " + listName;
    }
}

package andrey019.model.dao;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "done_todo")
public class DoneTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoList todoList;

    @Lob
    @Column(name = "todo_text", nullable = false)
    private String todoText;

    @Column(name = "done_by_name", nullable = false)
    private String doneByName;

    @Column(name = "done_by_email", nullable = false)
    private String doneByEmail;

    @Column(name = "created_by_name", nullable = false)
    private String createdByName;

    @Column(name = "created_by_email", nullable = false)
    private String createdByEmail;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date done;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public String getDoneByName() {
        return doneByName;
    }

    public void setDoneByName(String doneByName) {
        this.doneByName = doneByName;
    }

    public String getDoneByEmail() {
        return doneByEmail;
    }

    public void setDoneByEmail(String doneByEmail) {
        this.doneByEmail = doneByEmail;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCreatedByEmail() {
        return createdByEmail;
    }

    public void setCreatedByEmail(String createdByEmail) {
        this.createdByEmail = createdByEmail;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getDone() {
        return done;
    }

    public void setDone(Date done) {
        this.done = done;
    }

    public void setFromTodo(Todo todo) {
        todoText = todo.getTodoText();
        createdByEmail = todo.getCreatedByEmail();
        createdByName = todo.getCreatedByName();
        created = todo.getCreated();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) id;
        result = prime * result + ((doneByEmail == null) ? 0 : doneByEmail.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof DoneTodo))
            return false;
        DoneTodo other = (DoneTodo) obj;
        if (id != other.getId())
            return false;
        if (hashCode() != other.hashCode()) {
            return false;
        }
        return true;
    }
}

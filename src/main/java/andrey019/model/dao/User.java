package andrey019.model.dao;

import andrey019.model.json.JsonProfile;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
    private String lName;

    @OrderBy("id DESC")
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TodoList> todoLists = new HashSet<>();

    @OrderBy("id DESC")
    @ManyToMany(mappedBy = "users", cascade = {CascadeType.MERGE})
    private Set<TodoList> sharedTodoLists = new HashSet<>();

    @Column(nullable = false)
    private String state = State.ACTIVE.getState();

    @Column(nullable = false)
    private String role = Role.USER.getRole();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Set<TodoList> getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(Set<TodoList> todoLists) {
        this.todoLists = todoLists;
    }

    public Set<TodoList> getSharedTodoLists() {
        return sharedTodoLists;
    }

    public void setSharedTodoLists(Set<TodoList> sharedTodoLists) {
        this.sharedTodoLists = sharedTodoLists;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fName + " " + lName;
    }

    public void setUserFromConfirmation(UserConfirmation userConfirmation) {
        this.email = userConfirmation.getEmail();
        this.password = userConfirmation.getPassword();
        this.fName = userConfirmation.getfName();
        this.lName = userConfirmation.getlName();
    }

    public void setFromJsonProfile(JsonProfile jsonProfile) {
        if (!jsonProfile.getfName().isEmpty()) {
            fName = jsonProfile.getfName();
        }
        if (!jsonProfile.getlName().isEmpty()) {
            lName = jsonProfile.getlName();
        }
        if (!jsonProfile.getPassword().isEmpty()) {
            password = jsonProfile.getPassword();
        }
    }

    public void addTodoList(TodoList todoList) {
        todoList.setOwner(this);
        todoLists.add(todoList);
    }

    public void removeTodoList(TodoList todoList) {
        todoLists.remove(todoList);
    }

    public void addSharedTodoList(TodoList todoList) {
        sharedTodoLists.add(todoList);
        todoList.getUsers().add(this);
    }

    public void removeSharedTodoList(TodoList todoList) {
        sharedTodoLists.remove(todoList);
        todoList.getUsers().remove(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) id;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id != other.getId())
            return false;
        if (email == null) {
            if (other.getEmail() != null)
                return false;
        } else if (!email.equals(other.getEmail()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", fname=" + fName +
                ", lname=" + lName + ", state=" + state + ", role=" + role +"]";
    }


}
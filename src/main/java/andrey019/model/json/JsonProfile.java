package andrey019.model.json;


import andrey019.model.dao.User;

public class JsonProfile {

    private String email;
    private String fName;
    private String lName;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFromUser(User user) {
        email = user.getEmail();
        fName = user.getfName();
        lName = user.getlName();
        password = null;
    }

    @Override
    public String toString() {
        return "email: " + email + ", fName: " + fName + ", lName: " + lName + ", password: " + password;
    }
}

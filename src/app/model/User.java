package app.model;

public class User {
    private String firstName;
    private String login;
    private String password;
    private String email;
    // boolean admin;

    public User(String firstName, String login, String password, String email) {
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package nl.saxion.jelmer.topics.model;

/**
 * This class represents a User.
 */
public class User {
    
    private int userId;
    private String username, password, name, surname;

    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    //Constructor used by ApiHandler to keep track of a user's id in the app.
    public User(int userId, String username, String password, String name, String surname) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

}

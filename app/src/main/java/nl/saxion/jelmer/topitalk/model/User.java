package nl.saxion.jelmer.topitalk.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by Nyds on 23/05/2016.
 */
@DatabaseTable(tableName = "users")
public class User {

    /**
     * Database fields
     */
    @DatabaseField (generatedId = true)
    private int userId;
    @DatabaseField
    private String username, password, name, surname, teamname;

    private int userPictureId;
    private ArrayList<Post> userPosts;
    private ArrayList<Comment> userComments;

    public User() {
        //No arg constructor, needed by ORMLite.
    }

    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
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

    public String getTeamname() {
        return teamname;
    }

    public int getUserPictureId() {
        return userPictureId;
    }

    public ArrayList<Post> getUserPosts() {
        return userPosts;
    }

    public ArrayList<Comment> getUserComments() {
        return userComments;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void setUserPictureId(int userPictureId) {
        this.userPictureId = userPictureId;
    }

    public void addUserPost(Post post) {
        if (userPosts == null) {
            userPosts = new ArrayList<>();
        }
        userPosts.add(post);
    }

    public void addUserComment(Comment comment) {
        if (userComments == null) {
            userComments = new ArrayList<>();
        }
        userComments.add(comment);
    }
}

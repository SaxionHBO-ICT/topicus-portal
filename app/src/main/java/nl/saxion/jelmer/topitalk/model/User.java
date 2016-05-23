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
    @DatabaseField
    private String userName;
    @DatabaseField
    private String password;
    @DatabaseField
    private String name;
    @DatabaseField
    private String surname;
    @DatabaseField
    private String team; //Team of which user is part within Topicus.

    private int userPictureId;
    private ArrayList<Post> userPosts;
    private ArrayList<Comment> userComments;

    public User() {
        //No-argument constructor, needed by ORMLite.
    }

    public User(String userName, String password, String name, String surname) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.surname = surname;
        userPosts = new ArrayList<>();
        userComments = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
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

    public String getTeam() {
        return team;
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

    public void setTeam(String team) {
        this.team = team;
    }

    public void setUserPictureId(int userPictureId) {
        this.userPictureId = userPictureId;
    }
}

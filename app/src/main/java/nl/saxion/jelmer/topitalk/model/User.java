package nl.saxion.jelmer.topitalk.model;

import java.util.ArrayList;

/**
 * Created by Nyds on 23/05/2016.
 */
public class User {

    /**
     * Database fields
     */
    private int userId;
    private String username, password, name, surname, teamname;

    private ArrayList<Integer> upvotedPostIds;

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

    public String getTeamname() {
        return teamname;
    }

    public boolean hasUserUpvotedPost(int postId) {
        if (upvotedPostIds == null) {
            return false;
        } else if (upvotedPostIds.contains(postId)) {
            return true;
        } else {
            return false;
        }
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void addUserUpvotedPostId(int postId) {
        if (upvotedPostIds == null) {
            upvotedPostIds = new ArrayList<>();
        }
        upvotedPostIds.add(postId);
    }
}

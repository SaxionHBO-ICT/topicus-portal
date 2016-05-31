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

    private int userPictureId;
    private ArrayList<Post> userPosts;
    private ArrayList<Integer> upvotedPostIds;
    private ArrayList<Comment> userComments;

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

    public void setUserPictureId(int userPictureId) {
        this.userPictureId = userPictureId;
    }

    public void addUserUpvotedPostId(int postId) {
        if (upvotedPostIds == null) {
            upvotedPostIds = new ArrayList<>();
        }
        upvotedPostIds.add(postId);
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

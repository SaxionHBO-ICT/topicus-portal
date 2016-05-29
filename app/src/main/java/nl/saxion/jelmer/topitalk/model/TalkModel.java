package nl.saxion.jelmer.topitalk.model;

import java.util.ArrayList;

import nl.saxion.jelmer.topitalk.controller.DatabaseHelper;

/**
 * Created by Nyds on 20/05/2016.
 */
public class TalkModel {

    private User currentUser;
    private static TalkModel talkModel = null;
    private ArrayList<Post> postList;
    private ArrayList<User> users;

    private TalkModel() {
        postList = new ArrayList<>();
        users = new ArrayList<>();

        //Dummy User
        User user = new User("test", "test", "test", "test");
        users.add(user);
    }

    public static TalkModel getInstance() {

        if (talkModel == null) {
            talkModel = new TalkModel();
        }
        return talkModel;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<Post> getPostList() {
        return postList;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public boolean isUsernameUnique(String name) {

        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public User findUserByUsername(String username) {
        User result = null;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                result = user;
            }
        }
        return result;
    }

    public void upvotePost(int postId) {

        if (!currentUser.hasUserUpvotedPost(postId)) {
            currentUser.addUserUpvotedPostId(postId);
            postList.get(postId).upvotePost();
        }
    }

    public void addUser(String userName, String password, String name, String surname) {
        users.add(new User(userName, password, name, surname));
    }

    public void addUsertoDb(String userName, String password, String name, String surname) {
        User userToBeAdded = new User(userName, password, name, surname);
        DatabaseHelper.getInstance().addUserToDatabase(userToBeAdded);
    }

    public User findUserFromDb(String name) {
        return DatabaseHelper.getInstance().findUserByName(name);
    }

    public void addPost(User author, String title, String text) {
        postList.add(new Post(author, title, text));
    }

    public void addPostToDb(User author, String title, String text) {
        Post postToBeAdded = new Post(author, title, text);
        DatabaseHelper.getInstance().addPostToDatabase(postToBeAdded);
    }

    public void logoutCurrentUser() {
        currentUser = null;
    }
}

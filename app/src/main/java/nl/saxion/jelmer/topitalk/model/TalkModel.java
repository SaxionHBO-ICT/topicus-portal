package nl.saxion.jelmer.topitalk.model;

import java.util.ArrayList;

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
        User user = new User("JelmerDuzijn", "1234", "Jelmer", "Jelmer");
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

    public void addUser(String userName, String password, String name, String surname) {
        users.add(new User(userName, password, name, surname));
    }

    public void addPost(User author, String title, String text) {
        postList.add(new Post(author, title, text));
    }

    public void logoutCurrentUser() {
        currentUser = null;
    }
}

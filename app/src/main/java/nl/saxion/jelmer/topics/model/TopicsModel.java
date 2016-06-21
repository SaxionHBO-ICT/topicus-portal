package nl.saxion.jelmer.topics.model;

import java.util.ArrayList;

import nl.saxion.jelmer.topics.controller.ApiHandler;

/**
 * Created by Nyds on 20/05/2016.
 */
public class TopicsModel {

    private User currentUser;
    private static TopicsModel topicsModel = null;
    private ArrayList<Post> localPostList;

    private TopicsModel() {
        localPostList = new ArrayList<>();
    }

    public static TopicsModel getInstance() {

        if (topicsModel == null) {
            topicsModel = new TopicsModel();
        }
        return topicsModel;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<Post> getPostListFromDb() {

        localPostList = ApiHandler.getInstance().getPostList();
        return localPostList;
    }

    public ArrayList<Comment> getCommentsForThreadId(int threadId) {
        return ApiHandler.getInstance().getCommentsByThreadId(threadId);
    }

    public void refreshPostList() {
        localPostList = getPostListFromDb();
    }

    public ArrayList<Post> getLocalPostList() {
        return localPostList;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void upvotePost(int postId, int userId) {

        if (!ApiHandler.getInstance().hasUserUpvotedPost(postId, userId)) {
            ApiHandler.getInstance().upvotePost(postId, userId);
        }
    }

    public void addPost(int authorId, String authorUsername, String title, String text) {
        ApiHandler.getInstance().addPostToDb(new Post(authorId, authorUsername, title, text)); //Add a new post to the database.
        //localPostList.add(new Post(authorId, authorUsername, title, text));
    }

    public void addComment(int inThreadId, int authorId, String authorUsername, String text) {
        ApiHandler.getInstance().addCommentToDb(new Comment(inThreadId, authorId, authorUsername, text));
    }

    public void logoutCurrentUser() {
        currentUser = null;
    }
}

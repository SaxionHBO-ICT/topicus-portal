package nl.saxion.jelmer.topitalk.model;

import java.util.ArrayList;

import nl.saxion.jelmer.topitalk.controller.ApiHandler;

/**
 * Created by Nyds on 20/05/2016.
 */
public class TopiCoreModel {

    private User currentUser;
    private static TopiCoreModel topiCoreModel = null;
    private ArrayList<Post> localPostList;
    private ArrayList<Comment> comments = new ArrayList<>();

    private TopiCoreModel() {
        localPostList = new ArrayList<>();
    }

    public static TopiCoreModel getInstance() {

        if (topiCoreModel == null) {
            topiCoreModel = new TopiCoreModel();
        }
        return topiCoreModel;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public ArrayList<Post> getPostListFromDb() {

        localPostList = ApiHandler.getInstance().getPostList();
        return localPostList;
    }

    public ArrayList<Comment> getCommentsForThread(int threadId) {
        return ApiHandler.getInstance().getCommentsByThreadId(threadId);
    }

    public ArrayList<Comment> getComments() {
        return comments;
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

    public void upvotePost(int postId) {

        if (!currentUser.hasUserUpvotedPost(postId)) {
            currentUser.addUserUpvotedPostId(postId);
            localPostList.get(postId).upvotePost();
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

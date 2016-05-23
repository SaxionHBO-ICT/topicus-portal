package nl.saxion.jelmer.topitalk.model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nyds on 21/05/2016.
 */
public class Post implements Datable {

    private static int lastAssignedPostId = 0;
    private int postId;
    private int imageId;
    private String postDate;
    private User author;
    private String title;
    private String text;
    private ArrayList<Comment> comments;

    public Post(User author, String title, String text) {
        this.author = author;
        this.title = title;
        this.text = text;
        lastAssignedPostId++;
        postId = lastAssignedPostId;
        postDate = generateDate();
        comments = new ArrayList<>();
    }

    public Post(User author, String title, String text, int imageId) {
        this.author = author;
        this.title = title;
        this.text = text;
        this.imageId = imageId;
        lastAssignedPostId++;
        postId = lastAssignedPostId;
        postDate = generateDate();
        comments = new ArrayList<>();
    }

    /**
     * Getters
     */

    public static int getLastAssignedPostId() {
        return lastAssignedPostId;
    }

    public int getPostId() {
        return postId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getPostDate() {
        return postDate;
    }

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * Setters
     */

    public void editPostText(String text) {
        this.text = text;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    /**
     * Method to generate a formatted String with the current system date & time.
     * @return String containing formatted date. 23/05/2016 13:59
     */
    @NonNull
    public String generateDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date());
    }
}

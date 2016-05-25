package nl.saxion.jelmer.topitalk.model;

import android.support.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nyds on 21/05/2016.
 */
@DatabaseTable
public class Post implements Datable {

    private static int lastAssignedPostId = 0;
    @DatabaseField (id = true)
    private int postId;
    private int imageId;
    @DatabaseField
    private String postDate;
    @DatabaseField
    private User author;
    @DatabaseField
    private String title;
    @DatabaseField
    private String text;
    @DatabaseField
    private boolean isHotTopic;
    @DatabaseField
    private int highFives; //Total number of votes for a post. Can be increased by up-voting, decreased by down-voting.
    private ArrayList<Comment> comments;

    public Post() {
        //Non-arg constructor needed by ORMLite.
    }

    public Post(User author, String title, String text) {
        this.author = author;
        this.title = title;
        this.text = text;
        isHotTopic = false;
        highFives = 0;
        lastAssignedPostId++;
        postId = lastAssignedPostId;
        postDate = generateDate();
        comments = new ArrayList<>();
    }

    public Post(User author, String title, String text, int imageId) {
        this.author = author;
        this.title = title;
        this.text = text;
        isHotTopic = false;
        highFives = 0;
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

    public int getHighFives() {
        return highFives;
    }

    public boolean isHotTopic() {
        return isHotTopic;
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

    public void upvotePost() {
        highFives++;

        if (highFives >= 20) {
            isHotTopic = true;
        }
    }

    public void downvotePost() {
        highFives--;

        if (highFives < 20) {
            isHotTopic = false;
        }
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

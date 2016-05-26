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

    @DatabaseField (generatedId = true)
    private int postId;
    @DatabaseField
    private int authorId;
    @DatabaseField
    private String postDate, title, text;
    @DatabaseField
    private boolean isHotTopic;
    @DatabaseField
    private int highFives; //Total number of votes for a post. Can be increased by up-voting, decreased by down-voting.

    private int imageId;
    private User author;
    private ArrayList<Comment> comments;

    public Post() {
        //No arg constructor, needed by ORMLite.
    }

    public Post(User author, String title, String text) {
        this.author = author;
        this.title = title;
        this.text = text;
        isHotTopic = false;
        highFives = 0;
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
        postDate = generateDate();
        comments = new ArrayList<>();
    }

    /**
     * Getters
     */

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

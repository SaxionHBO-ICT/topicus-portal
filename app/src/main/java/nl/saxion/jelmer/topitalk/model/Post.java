package nl.saxion.jelmer.topitalk.model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nyds on 21/05/2016.
 */
public class Post implements Datable {

    private int postId, userId;
    private String authorUsername, postDate, title, text;
    private boolean isHotTopic;
    private int postScore; //Total number of votes for a post. Can be increased by up-voting.

    private int imageId;
    private ArrayList<Comment> comments;

    public Post(int userId, String authorUsername, String title, String text) {
        this.userId = userId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.text = text;
        isHotTopic = false;
        postScore = 0;
        postDate = generateDate();
        comments = new ArrayList<>();
    }

    public Post(int userId, String authorUsername, String title, String text, int imageId) {
        this.userId = userId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.text = text;
        isHotTopic = false;
        postScore = 0;
        this.imageId = imageId;
        postDate = generateDate();
        comments = new ArrayList<>();
    }

    /**
     * Getters
     */

    public int getUserId() {
        return userId;
    }

    public String getAuthorUsername() {
        return authorUsername;
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

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int getPostScore() {
        return postScore;
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

    public void setPostScore(int postScore) { //Debug method to manually set a post's score.
        this.postScore = postScore;
    }

    public void setHotTopic(boolean isHotTopic) { //Debug method to manually flag a post as hot topic.
        this.isHotTopic = isHotTopic;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void upvotePost() {
        postScore++;

        if (postScore >= 20) {
            isHotTopic = true;
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

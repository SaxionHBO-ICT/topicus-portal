package nl.saxion.jelmer.topics.model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a Post.
 */
public class Post implements Datable {

    private int postId, userId;
    private String authorUsername, postDate, title, text;
    private boolean isHotTopic;
    private int postScore; //Total number of votes for a post. Can be increased by up-voting.

    public Post(int userId, String authorUsername, String title, String text) {
        this.userId = userId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.text = text;
        isHotTopic = false;
        postScore = 0;
        postDate = generateDate();
    }

    public Post(int postId, int userId, String authorUsername, String title, String text) {
        this.postId = postId;
        this.userId = userId;
        this.authorUsername = authorUsername;
        this.title = title;
        this.text = text;
        isHotTopic = false;
        postScore = 0;
        postDate = generateDate();
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

    public String getPostDate() {
        return postDate;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public int getPostScore() {
        return postScore;
    }

    public boolean isHotTopic() {
        if (postScore >= 20) {
            isHotTopic = true;
        }
        return isHotTopic;
    }

    public void upvote() {
        postScore++;
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

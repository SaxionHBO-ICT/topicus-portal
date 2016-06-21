package nl.saxion.jelmer.topitalk.model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a Comment.
 */

public class Comment implements Datable {

    private int commentId, inThreadId, authorId;
    private String commentDate, text, authorUsername;

    public Comment(int inThreadId, int authorId, String authorUsername, String text) {
        this.inThreadId = inThreadId;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.text = text;
        commentDate = generateDate();
    }

    public Comment(int commentId, int inThreadId, int authorId, String authorUsername, String text) {
        this.commentId = commentId;
        this.inThreadId = inThreadId;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.text = text;
        commentDate = generateDate();
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getInThreadId() {
        return inThreadId;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public String getText() {
        return text;
    }

    @NonNull
    public String generateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date());
    }
}

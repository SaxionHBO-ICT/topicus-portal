package nl.saxion.jelmer.topitalk.model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nyds on 21/05/2016.
 */

public class Comment implements Datable {

    private int commentId, inThreadId, authorId;
    private String commentDate, text, authorUsername;


    public Comment() {
        //No arg constructor, needed by ORMLite.
    }

    public Comment(int inThreadId, int authorId, String authorUsername, String text) {
        this.inThreadId = inThreadId;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.text = text;
        commentDate = generateDate();
    }

    public int getCommentId() {
        return commentId;
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

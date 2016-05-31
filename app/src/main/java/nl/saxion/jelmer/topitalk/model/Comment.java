package nl.saxion.jelmer.topitalk.model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nyds on 21/05/2016.
 */

public class Comment implements Datable {

    private int commentId, inThreadId, authorId;
    private String commentDate, title, text;

    private User author;

    public Comment() {
        //No arg constructor, needed by ORMLite.
    }

    public Comment(int inThreadId, User author, String title, String text) {
        this.inThreadId = inThreadId;
        this.author = author;
        this.title = title;
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

    public User getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
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

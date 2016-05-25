package nl.saxion.jelmer.topitalk.controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.Comment;
import nl.saxion.jelmer.topitalk.model.Post;
import nl.saxion.jelmer.topitalk.model.User;

/**
 * Created by Nyds on 23/05/2016.
 */
public class DatabaseHelper {

    private static final String DATABASE_NAME = "topiTalkdb";
    private static final int DATABASE_VERSION = 1;
    private static final String databaseUrl = "jdbc:mysql://localhost:3306/topiTalkdb";
    private static DatabaseHelper dbHelper = null;

    private ConnectionSource connectionSource;
    private Dao<User, Integer> userDao;
    private Dao<Post, Integer> postDao;
    private Dao<Comment, Integer> commentDao;

    private DatabaseHelper() {
        try {
            connectionSource = new JdbcConnectionSource(databaseUrl, "root", "root");
            userDao = DaoManager.createDao(connectionSource, User.class);
            postDao = DaoManager.createDao(connectionSource, Post.class);
            commentDao = DaoManager.createDao(connectionSource, Comment.class);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DatabaseHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper();
        }
        return dbHelper;
    }

    public void addUserToDatabase(User user) {
        try {
            userDao.create(user);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPostToDatabase(Post post) {
        try {
            postDao.create(post);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

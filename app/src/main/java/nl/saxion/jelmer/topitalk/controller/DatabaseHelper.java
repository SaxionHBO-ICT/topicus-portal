package nl.saxion.jelmer.topitalk.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import nl.saxion.jelmer.topitalk.model.Comment;
import nl.saxion.jelmer.topitalk.model.Post;
import nl.saxion.jelmer.topitalk.model.User;

/**
 * Created by Nyds on 23/05/2016.
 */
public class DatabaseHelper {

    private static final String DB_URL = "jdbc:mysql://10.0.2.2:3306/topiTalkdb";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";
    private static DatabaseHelper dbHelper = null;

    private ConnectionSource connectionSource;
    private Dao<User, String> userDao;
    private Dao<Post, Integer> postDao;
    private Dao<Comment, Integer> commentDao;

    private DatabaseHelper() {
       initializeDbConnection();
    }

    private void initializeDbConnection() {
        try {
            connectionSource = new JdbcConnectionSource(DB_URL, DB_USERNAME, DB_PASSWORD);
            userDao = DaoManager.createDao(connectionSource, User.class);
            //postDao = DaoManager.createDao(connectionSource, Post.class);
            //commentDao = DaoManager.createDao(connectionSource, Comment.class);
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

        AddUserTask addUserTask = new AddUserTask();
        addUserTask.execute(user);

    }

    public User findUserByName(String name) {

        User result = null;
        GetUserByNameTask getUserTask = new GetUserByNameTask();
        getUserTask.execute(name);

        try {
            result = getUserTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addPostToDatabase(Post post) {
        try {
            postDao.create(post);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private class AddUserTask extends AsyncTask<User, Void, String> {

        @Override
        protected String doInBackground(User... params) {
            try {
                userDao.create(params[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return "User added.";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private class GetUserByNameTask extends AsyncTask<String, Void, User> {

        private User result = null;

        @Override
        protected User doInBackground(String... params) {

            try {
                //Log.e("GetUserByNamyTask", "doInBackground: reached query.");
                result = userDao.queryForId(params[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connectionSource.closeQuietly();
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
        }
    }

    public class AddPostTask extends AsyncTask<Post, Void, String> {

        @Override
        protected String doInBackground(Post... params) {
            return null;
        }
    }
}

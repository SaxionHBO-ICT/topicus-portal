package nl.saxion.jelmer.topitalk.controller;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import nl.saxion.jelmer.topitalk.model.Post;
import nl.saxion.jelmer.topitalk.model.User;


/**
 * Helper class to be used to handle communication
 * between TopiRESTApi and the Android system.
 */
public class ApiHandler {

    /**
     * Database URL constants
     */
    private static final String API_URL = "http://10.0.2.2:4567/";
    private static final String API_SEARCH_USER_URL = API_URL + "users/";
    private static final String API_ADD_USER_URL = API_URL + "users";
    private static final String API_GET_POSTS_URL = API_URL + "posts/";
    private static final String API_NEW_POST_URL = API_URL + "posts";

    /**
     * Connection time-out constants.
     */

    private static final int CONN_TIMEOUT = 3000; //In milliseconds.

    private static ApiHandler apiHandler = null;

    private ApiHandler() {

    }

    public static ApiHandler getInstance() {
        if (apiHandler == null) {
            apiHandler = new ApiHandler();
        }
        return apiHandler;
    }

    /**
     * Authentication API operations.
     */

    public boolean isAuthenticated(String authToken) {
        return false;
    }


    public class AuthenticateTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return null;
        }
    }

    /**
     * User object API operations.
     */

    public boolean addUserToDb(User user) {

        AddUserTask addUserTask = new AddUserTask();
        addUserTask.execute(user);

        try {
            return addUserTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    public User getUserByName(String name) {

        FindUserTask findUserTask = new FindUserTask();
        findUserTask.execute(name);

        try {
            return findUserTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public class FindUserTask extends AsyncTask<String, Double, User> {

        private String jsonString = null;

        @Override
        protected User doInBackground(String... params) {
            try {
                //Open a new connection.
                URL url = new URL(API_SEARCH_USER_URL + params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(CONN_TIMEOUT);

                //Get the data if the response code == 200 (Everything ok).
                if (conn.getResponseCode() == 200) {
                    InputStream is = conn.getInputStream();
                    jsonString = IOUtils.toString(is, "UTF-8"); //Parse InputStream to JSON.

                    //Close the inputstream and the connection.
                    is.close();
                    conn.disconnect();

                    //Get the data from the jsonString using Gson and create a new User object.
                    Gson gson = new Gson();
                    return gson.fromJson(jsonString, User.class);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class AddUserTask extends AsyncTask<User, Void, Boolean> {

        @Override
        protected Boolean doInBackground(User... params) {
            //Get the data from the User object.
            String username = params[0].getUsername();
            String password = params[0].getPassword();
            String name = params[0].getName();
            String surname = params[0].getSurname();

            try {
                //Open a new connection
                URL url = new URL(API_ADD_USER_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setConnectTimeout(CONN_TIMEOUT);

                //Write the data to the output stream and flush it.
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write("username=" + username + "&password=" + password + "&name=" + name + "&surname=" + surname);
                out.flush();

                //Close the stream.
                out.close();

                //Check http response code, close the connection regardless.
                if (conn.getResponseCode() == 201) {
                    conn.disconnect();
                    return true;
                } else {
                    conn.disconnect();
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Post object API operations.
     */

    public ArrayList<Post> getPostList() {

        GetPostListTask getPostListTask = new GetPostListTask();
        getPostListTask.execute();

        try {
            return getPostListTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addPostToDb(Post post) {

        AddPostTask addPostTask = new AddPostTask();
        addPostTask.execute(post);

        try {
            return addPostTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public class GetPostListTask extends AsyncTask<Void, Void, ArrayList<Post>> {

        @Override
        protected ArrayList<Post> doInBackground(Void... params) {

            try {
                URL url = new URL(API_GET_POSTS_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(CONN_TIMEOUT);

                if (conn.getResponseCode() == 200) {
                    InputStream is = conn.getInputStream();
                    String jsonString = IOUtils.toString(is, "UTF-8");

                    is.close();
                    conn.disconnect();

                    Gson gson = new Gson();
                    ArrayList<Post> posts = gson.fromJson(jsonString, new TypeToken<ArrayList<Post>>(){}.getType());
                    return posts;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class AddPostTask extends AsyncTask<Post, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Post... params) {

            //Get the data from the Post object.
            int userId = params[0].getUserId();
            String authorUsername = params[0].getAuthorUsername();
            String title = params[0].getTitle();
            String text = params[0].getText();

            try {
                //Open a new connection
                URL url = new URL(API_NEW_POST_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setConnectTimeout(CONN_TIMEOUT);

                //Write the data to the output stream and flush it.
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write("userId=" + userId + "&authorUsername=" + authorUsername + "&title=" + title + "&text=" + text);
                out.flush();

                //Close the stream.
                out.close();

                //Check http response code, close the connection regardless.
                if (conn.getResponseCode() == 201) {
                    conn.disconnect();
                    return true;
                } else {
                    conn.disconnect();
                    return false;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Comment object API operations
     */
}

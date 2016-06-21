package nl.saxion.jelmer.topics.controller;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import nl.saxion.jelmer.topics.activity.MainActivity;
import nl.saxion.jelmer.topics.activity.PostDetailActivity;
import nl.saxion.jelmer.topics.model.Comment;
import nl.saxion.jelmer.topics.model.Post;
import nl.saxion.jelmer.topics.model.TopicsModel;
import nl.saxion.jelmer.topics.model.User;


/**
 * Helper class to be used to handle communication
 * between TopiRESTApi and the Android system.
 *
 * It contains API URLs, ASynchronous Tasks to handle various API calls
 * and methods that can be used by outside this class.
 *
 * @author Jelmer Duzij
 */
public class ApiHandler {

    private static final String APP_AUTH_TOKEN = "zxcASDrfv0/2031lM<K";

    /**
     * Database URL constants
     */
    private static final String API_URL = "http://10.0.2.2:4567/";
//    private static final String API_URL = "http://192.168.1.3:4567/";
    private static final String API_SEARCH_USER_URL = API_URL + "users/";
    private static final String API_ADD_USER_URL = API_URL + "users";
    private static final String API_GET_POSTS_URL = API_URL + "posts/";
    private static final String API_NEW_POST_URL = API_URL + "posts";
    private static final String API_UPVOTE_POST_URL = API_GET_POSTS_URL + "upvote";
    private static final String API_UPVOTE_CHECK_URL = API_URL + "posts/upvote/check";
    private static final String API_NEW_COMMENT_URL = API_URL + "comments";
    private static final String API_GET_COMMENTS_URL = API_URL + "comments/";

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
     * @TODO Implement authentication.
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

    /**
     * Method to add a User object to the database.
     * Creates an asynctask to handle the operation.
     *
     * @param user The new User.
     * @return true if the user was added, false if it failed.
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

    /**
     * Method to retrieve a User object by its username.
     * Creates an asynctask to handle the operation.
     *
     * @param name The username search parameter.
     * @return User if a user was found, null if no user was found.
     */
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

    /**
     * ASyncTask called by getUserByName() to try and retrieve a User object from the API.
     */
    private class FindUserTask extends AsyncTask<String, Double, User> {

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

    /**
     * ASyncTask called by addUserToDb() to try and write a new User to the API.
     */
    private class AddUserTask extends AsyncTask<User, Void, Boolean> {

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
                    return true; //The user was created successfully.
                } else {
                    conn.disconnect();
                    return false; //Failed to create a user. (Username already taken).
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

    /**
     * Method to return a list of all posts from the API.
     * Uses an ASyncTask to complete the operation.
     *
     * @return an ArrayList of Posts
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

    /**
     * Method to upvote a post using a postId & userId.
     * Uses an ASyncTask to complete the operation.
     *
     * @param postId The postId of the post that should be upvoted.
     * @param userId The userId of the user that tried to upvote the post.
     * @return true if the post was upvoted successfully, false if it failed (user has already upvoted this post).
     */
    public boolean upvotePost(int postId, int userId) {

        UpvotePostTask upvotePostTask = new UpvotePostTask();
        upvotePostTask.execute(postId, userId);

        try {
            return upvotePostTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to check if a certain user has upvoted a certain post.
     * Uses an ASyncTask to complete the operation.
     *
     * @param postId The postId of the post that needs to be checked.
     * @param userId The userId of the user that needs to be checked.
     * @return true if the user has upvoted this post, false if not.
     */
    public boolean hasUserUpvotedPost(int postId, int userId) {

        CheckUpvoteTask checkUpvoteTask = new CheckUpvoteTask();
        checkUpvoteTask.execute(postId, userId);

        try {
            return checkUpvoteTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to write a Post object to the API.
     * Uses an ASyncTask to complete the operation.
     *
     * @param post The post to be sent.
     * @return true if the post was added successfully, false if not.
     */
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

    /**
     * ASyncTask to retrieve a list of posts from the API.
     */
    private class GetPostListTask extends AsyncTask<Void, Void, ArrayList<Post>> {

        @Override
        protected ArrayList<Post> doInBackground(Void... params) {

            try {
                //Open the connection
                URL url = new URL(API_GET_POSTS_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(CONN_TIMEOUT);

                //If the response is okay get the data.
                if (conn.getResponseCode() == 200) {
                    InputStream is = conn.getInputStream();
                    String jsonString = IOUtils.toString(is, "UTF-8");

                    //Close the stream and connection.
                    is.close();
                    conn.disconnect();

                    //Parse the JSON string to an ArrayList of Post objects using GSON.
                    Gson gson = new Gson();
                    return gson.fromJson(jsonString, new TypeToken<ArrayList<Post>>() {
                    }.getType());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Post> posts) {
            MainActivity.finishRefreshing(); //Tell the refresh layout that the operation has been completed.
        }
    }

    /**
     * ASyncTask to write a new Post to the API.
     * A Post object is passed as a parameter.
     */
    private class AddPostTask extends AsyncTask<Post, Void, Boolean> {

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

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) { //Force refresh the list of posts on the device.
                TopicsModel.getInstance().refreshPostList();
            }
        }
    }

    /**
     * ASyncTask to handle an upvoting call to the API
     * A postId & userId are passed as parameters
     */
    private class UpvotePostTask extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {

            //Get the postId from params.
            int postId = params[0];
            int userId = params[1];

            try {
                //Open a new connection.
                URL url = new URL(API_UPVOTE_POST_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setConnectTimeout(CONN_TIMEOUT);

                //Write the data to the output stream, flush and close it.
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write("postId=" + postId + "&userId=" + userId);
                out.flush();
                out.close();

                //Return true if the response code == 200 (Post added successfully), close connection regardless.
                if (conn.getResponseCode() == 200) {
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
     * ASyncTask to handle API calls to check if a certain user has upvoted a certain post.
     * A postId & userId are passed as parameters.
     */
    private class CheckUpvoteTask extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {

            //Get the IDs from the params array.
            int postId = params[0];
            int userId = params[1];

            try {
                //Open a new connection.
                URL url = new URL(API_UPVOTE_CHECK_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setConnectTimeout(CONN_TIMEOUT);

                //Write the data to the output stream, flush and close it.
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write("postId=" + postId + "&userId=" + userId);
                out.flush();
                out.close();

                //Check the responsecode and return false if the user hasn't upvoted the topic, true if the user has upvoted.
                if (conn.getResponseCode() == 200) {
                    conn.disconnect();
                    return false;
                } else {
                    conn.disconnect();
                    return true;
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

    /**
     * Method to write a new comment to the API.
     * Uses an ASyncTask to complete the operation.
     *
     * @param comment The Comment object that should be sent to the API.
     * @return true if the comment was added successfully, false if not.
     */
    public boolean addCommentToDb(Comment comment) {

        AddCommentTask addCommentTask = new AddCommentTask();
        addCommentTask.execute(comment);

        try {
            return addCommentTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method to get an ArrayList of comments for a specific thread.
     * Uses an ASyncTask to complete the operation.
     *
     * @param threadId The postId that we want the comments from.
     * @return A list of comments if the thread was found and has comments, null if not.
     */
    public ArrayList<Comment> getCommentsByThreadId(int threadId) {

        GetCommentByThreadTask getCommentByThreadTask = new GetCommentByThreadTask();
        getCommentByThreadTask.execute(threadId);

        try {
            return getCommentByThreadTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ASyncTask to handle sending a comment to the API.
     * A Comment object is passed as a parameter.
     */
    private class AddCommentTask extends AsyncTask<Comment, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Comment... params) {

            //Get the data from the Comment object
            int inThreadId = params[0].getInThreadId();
            int authorId = params[0].getAuthorId();
            String authorUsername = params[0].getAuthorUsername();
            String text = params[0].getText();

            try {
                //Open a new connection;
                URL url = new URL(API_NEW_COMMENT_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setConnectTimeout(CONN_TIMEOUT);

                //Write the data to the output stream and flush it.
                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write("inThreadId=" + inThreadId + "&authorId=" + authorId + "&authorUsername=" + authorUsername + "&text=" + text);
                out.flush();

                //Close the stream.
                out.close();

                //If the responsecode == 201 (Comment was added successfully), return true, else return false. Close the connection regardless.
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
     * ASyncTask to handle retrieving an ArrayList of comments from a specific post from the API.
     * A postId is passed as a parameter.
     */
    private class GetCommentByThreadTask extends AsyncTask<Integer, Void, ArrayList<Comment>> {

        @Override
        protected ArrayList<Comment> doInBackground(Integer... params) {

            try {
                //Open a new connection
                URL url = new URL(API_GET_COMMENTS_URL + params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(CONN_TIMEOUT);

                //If the response is okay (A list of comments was found), get the data from the input stream.
                if (conn.getResponseCode() == 200) {
                    InputStream is = conn.getInputStream();
                    String jsonString = IOUtils.toString(is, "UTF-8");

                    //Close the stream and connection.
                    is.close();
                    conn.disconnect();

                    //Parse the JSON string to an ArrayList of comments.
                    Gson gson = new Gson();
                    return gson.fromJson(jsonString, new TypeToken<ArrayList<Comment>>(){}.getType());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Comment> comments) {
            PostDetailActivity.finishRefreshing();
        }
    }
}

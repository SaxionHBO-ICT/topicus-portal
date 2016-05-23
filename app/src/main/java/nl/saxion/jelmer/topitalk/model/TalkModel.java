package nl.saxion.jelmer.topitalk.model;

import java.util.ArrayList;

/**
 * Created by Nyds on 20/05/2016.
 */
public class TalkModel {

    private User currentUser;
    private static TalkModel talkModel = null;
    private ArrayList<Post> postList;
    private ArrayList<User> users;

    private TalkModel() {
        postList = new ArrayList<>();
        users = new ArrayList<>();
    }

    private static TalkModel getInstance() {

        if (talkModel == null) {
            talkModel = new TalkModel();
        }
        return talkModel;
    }


}

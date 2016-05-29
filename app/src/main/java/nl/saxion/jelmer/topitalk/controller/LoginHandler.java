package nl.saxion.jelmer.topitalk.controller;

import nl.saxion.jelmer.topitalk.model.TopiCoreModel;
import nl.saxion.jelmer.topitalk.model.User;

/**
 * Login helper class that validates a login attempt.
 *
 * @author Jelmer Duzijn
 */
public abstract class LoginHandler {

    /**
     * Method the validate a login attempt.
     * If true, the user is set in TopiCoreModel.
     *
     * @param username Username parameter from login attempt
     * @param password Password parameter from login attempt
     * @return true if password matches, false if it doesn't.
     */
    public static boolean login(String username, String password) {

        User user = TopiCoreModel.getInstance().findUserByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            TopiCoreModel.getInstance().setCurrentUser(user);
            return true;
        }
        return false;
    }


}

package nl.saxion.jelmer.topitalk.controller;

import org.jasypt.util.text.BasicTextEncryptor;

import nl.saxion.jelmer.topitalk.model.TopiCoreModel;
import nl.saxion.jelmer.topitalk.model.User;

/**
 * Login helper class that validates a login attempt.
 * Handles user registration.
 *
 * Uses jasypt to encrypt & decrypt passwords.
 *
 * @author Jelmer Duzijn
 */
public abstract class LoginHandler {

    private static final String AUTH_KEY = "/zddAw12#LLLo0w9;'][[#1gmA";

    /**
     * Method to validate a login attempt.
     * If true, the user is set in TopiCoreModel.
     *
     * @param username Username parameter from login attempt
     * @param password Password parameter from login attempt
     * @return true if password matches, false if it doesn't.
     */
    public static boolean login(String username, String password) {

        User user = TopiCoreModel.getInstance().findUserByUsername(username);

        if (user != null) {
            String encryptPassword = encryptPassword(password);
            if (decryptPassword(user.getPassword()).equals(decryptPassword(encryptPassword))) {
                TopiCoreModel.getInstance().setCurrentUser(user);
                return true;
            }
        }
        return false;
    }

    /**
     * Method to validate an attempt to register a user.
     * Username must be unique. If true, register the user.
     *
     * @param username The username that is sent by the activity.
     * @param password The password that is sent by the activity.
     * @param name The name that is sent by the activity.
     * @param surname The surname that is sent by the activity.
     * @return true if the username is unique, false if not.
     */
    public static boolean registerUser(String username, String password, String name, String surname) {
        if (TopiCoreModel.getInstance().isUsernameUnique(username)) {

            String encryptPassword = encryptPassword(password);
            TopiCoreModel.getInstance().addUser(username, encryptPassword, name, surname);
            return true;
        }
        return false;
    }

    public static String encryptPassword(String password) {
        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword(AUTH_KEY);
        return bte.encrypt(password);
    }

    private static String decryptPassword(String encryptedPassword) {
        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword(AUTH_KEY);
        return bte.decrypt(encryptedPassword);
    }
}

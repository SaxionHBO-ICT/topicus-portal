package nl.saxion.jelmer.topics.controller;

import org.jasypt.util.text.BasicTextEncryptor;

import nl.saxion.jelmer.topics.model.TopicsModel;
import nl.saxion.jelmer.topics.model.User;

/**
 * Login helper class that validates a login attempt.
 * Handles user registration.
 * Uses jasypt to encrypt & decrypt passwords.
 *
 * @author Jelmer Duzijn
 */
public abstract class LoginHandler {

    private static final String PASSWORD_HASH = "/zddAw12#LLLo0w9;'][[#1gmA";

    /**
     * Method to validate a login attempt.
     * If true, the user is set in TopicsModel.
     *
     * @param username Username parameter from login attempt
     * @param password Password parameter from login attempt
     * @return true if password matches, false if it doesn't.
     */
    public static boolean login(String username, String password) {

        User user = ApiHandler.getInstance().getUserByName(username); //Try to get the user from the database by its name.

        if (user != null) {
            String encryptPassword = encryptPassword(password); //Encrypt the password.
            if (decryptPassword(user.getPassword()).equals(decryptPassword(encryptPassword))) { //Decrypt the passwords & compare.
                TopicsModel.getInstance().setCurrentUser(user); //If passwords match, login the user.
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
     * @param name     The name that is sent by the activity.
     * @param surname  The surname that is sent by the activity.
     * @return true if the username is unique, false if not.
     */
    public static boolean registerUser(String username, String password, String name, String surname) {

        String encryptPassword = encryptPassword(password);
        return ApiHandler.getInstance().addUserToDb(new User(username, encryptPassword, name, surname));
    }

    public static String encryptPassword(String password) {
        BasicTextEncryptor bte = new BasicTextEncryptor();
        bte.setPassword(PASSWORD_HASH);
        return bte.encrypt(password).replaceAll(" ", "");
    }

    private static String decryptPassword(String encryptedPassword) {
        BasicTextEncryptor bte = new BasicTextEncryptor();
        encryptedPassword = encryptedPassword.replaceAll(" ", "");
        bte.setPassword(PASSWORD_HASH);
        return bte.decrypt(encryptedPassword);
    }
}

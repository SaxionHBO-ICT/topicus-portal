package nl.saxion.jelmer.topics.controller;

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
    private static final Kryptonizer crptKey = new Kryptonizer("2gG.", "VZ!C8bf9");

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
            if (checkPasswords(password, user.getPassword(), crptKey)) { //Decrypt the passwords & compare.
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

        String encryptPassword = enkryptPassword(password, crptKey);
        return ApiHandler.getInstance().addUserToDb(new User(username, encryptPassword, name, surname));
    }

    /**
     * Password encryptor based on Kryptonizer keycards handed out by Topicus.
     * Passwords begin with the prefix, a unique combination of 4 characters.
     *
     * Then, based on an 8 character long unique encryption key
     * a new password is generated from the input password,
     * using the Kryptonizer algorithm.
     *
     * Letter based passwords only.
     *
     */

    public static String enkryptPassword(String input, Kryptonizer crptKey) {

        String unenKrypted = input.toLowerCase().replaceAll(" ", "");
        String result = crptKey.getPrefix();

        for (int i = 0; i < unenKrypted.length(); i++) {
            switch (unenKrypted.charAt(i)) {
                case 'a' :case 'b':case 'c': result += crptKey.getCrptKeyArray()[0];
                    break;
                case 'd':case 'e':case 'f': result += crptKey.getCrptKeyArray()[1];
                    break;
                case 'g':case 'h':case 'i':case 'j': result += crptKey.getCrptKeyArray()[2];
                    break;
                case 'k':case 'l':case 'm': result += crptKey.getCrptKeyArray()[3];
                    break;
                case 'n':case 'o':case 'p': result += crptKey.getCrptKeyArray()[4];
                    break;
                case 'q':case 'r':case 's': result += crptKey.getCrptKeyArray()[5];
                    break;
                case 't':case 'u':case 'v': result += crptKey.getCrptKeyArray()[6];
                    break;
                case 'w':case 'x':case 'y':case 'z': result += crptKey.getCrptKeyArray()[7];
            }
        }
        return result;
    }

    public static boolean checkPasswords(String password, String encryptedPassword, Kryptonizer crptKey) {
        return enkryptPassword(password, crptKey).equals(encryptedPassword);
    }

    /**
     * Class represents a Kryptonizer key.
     * Consists of a prefix and a key.
     */
    private static class Kryptonizer {

        private char[] crptKeyArray = new char[8];
        private String prefix;

        public Kryptonizer(String prefix, String key) {
            this.prefix = prefix;
            createKeyArray(key);
        }

        public void createKeyArray(String key) {
            for (int i = 0; i < key.length(); i++) {
                crptKeyArray[i] = key.charAt(i);
            }
        }

        public char[] getCrptKeyArray() {
            return crptKeyArray;
        }

        public String getPrefix() {
            return prefix;
        }
    }
}

package nl.saxion.jelmer.topitalk.controller;

import android.widget.EditText;

/**
 * Helper class to get formatted text.
 * Used to trim whitespace from usernames and passwords.
 * @author Jelmer Duzijn
 */
public abstract class TextFormatter {

    /**
     * Method to extract text from an EditText field
     * @param field the EditText we want the text from.
     * @return the text without spaces.
     */
    public static String getFormattedTextFromField(EditText field) {
        return field.getText().toString().replaceAll(" ", "");
    }
}

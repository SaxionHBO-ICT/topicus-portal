package nl.saxion.jelmer.topitalk.controller;

import android.widget.EditText;

/**
 * Helper class to get formatted text from an EditText field
 *
 * @author Jelmer Duzijn
 */
public abstract class TextFormatter {

    public static String getFormattedTextFromField(EditText field) {
        return field.getText().toString().replaceAll(" ", "");
    }
}

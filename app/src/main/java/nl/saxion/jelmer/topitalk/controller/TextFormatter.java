package nl.saxion.jelmer.topitalk.controller;

import android.support.annotation.NonNull;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class to get formatted text.
 *
 * @author Jelmer Duzijn
 */
public abstract class TextFormatter {

    public static String getFormattedTextFromField(EditText field) {
        return field.getText().toString().replaceAll(" ", "");
    }

    @NonNull
    public static String generateDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(new Date());
    }
}

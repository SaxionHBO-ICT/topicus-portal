package nl.saxion.jelmer.topics.controller;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Helper OnFocusChange class for EditText fields
 * to be able to hide the keyboard when the focus changes.
 *
 * @author Jelmer Duzijn
 */
public class KeyboardFocusHandler implements View.OnFocusChangeListener {

    private Context context;

    public KeyboardFocusHandler(Context context) {
        this.context = context;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            hideKeyboard(v);
        }
    }

    /**
     * Helper method to hide the keyboard.
     *
     * @param view The view whose state has changed.
     */
    private void hideKeyboard(View view) {

        InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}


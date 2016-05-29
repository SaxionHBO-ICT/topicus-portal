package nl.saxion.jelmer.topitalk.activity;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.TalkModel;

public class NewPostActivity extends AppCompatActivity {

    private EditText etPostTitle, etPostText;
    private FloatingActionButton btPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        etPostTitle = (EditText) findViewById(R.id.et_post_title);
        etPostText = (EditText) findViewById(R.id.et_post_text);
        btPost = (FloatingActionButton) findViewById(R.id.bt_fab_post);

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPostFormFilled()) {
                    TalkModel.getInstance().addPost(TalkModel.getInstance().getCurrentUser(),
                            etPostTitle.getText().toString(),
                            etPostText.getText().toString());
                    finish();
                } else {
                    Toast.makeText(NewPostActivity.this, "Titel- en berichtveld mogen niet leeg zijn.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etPostTitle.setOnFocusChangeListener(new OnFocusChangeListener());
        etPostText.setOnFocusChangeListener(new OnFocusChangeListener());
    }

    private boolean isPostFormFilled() {
        return !etPostTitle.getText().toString().equals("") && !etPostText.getText().toString().equals("");
    }

    /**
     * Listener for EditText fields to call hideKeyBoard on focus change.
     *
     */
    private class OnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        }
    }

    /**
     * Helper method to hide the keyboard.
     *
     * @param view The view whose state has changed.
     */
    private void hideKeyboard(View view) {

        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

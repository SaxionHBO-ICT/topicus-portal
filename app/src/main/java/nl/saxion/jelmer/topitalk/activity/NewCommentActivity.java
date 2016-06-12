package nl.saxion.jelmer.topitalk.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.controller.KeyboardFocusHandler;
import nl.saxion.jelmer.topitalk.controller.TextFormatter;
import nl.saxion.jelmer.topitalk.model.TopiCoreModel;

public class NewCommentActivity extends AppCompatActivity {

    private EditText etText;
    private FloatingActionButton btPostComment;
    public static final String POST_ID = "post_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);

        etText = (EditText) findViewById(R.id.et_comment_text);
        btPostComment = (FloatingActionButton) findViewById(R.id.bt_fab_comment);

        Intent intent = getIntent();
        final int postId = intent.getIntExtra(POST_ID, 0);

        btPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCommentFormFilled()) {
                    int authorId = TopiCoreModel.getInstance().getCurrentUser().getUserId();
                    String authorUsername = TopiCoreModel.getInstance().getCurrentUser().getUsername();
                    String text = etText.getText().toString();

                    TopiCoreModel.getInstance().addComment(postId, authorId, authorUsername, text);
                    finish();
                }
            }
        });

        etText.setOnFocusChangeListener(new KeyboardFocusHandler(this));
    }

    private boolean isCommentFormFilled() {
        return !etText.getText().toString().equals("");
    }
}

package nl.saxion.jelmer.topitalk.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.controller.KeyboardFocusHandler;
import nl.saxion.jelmer.topitalk.model.TopiCoreModel;

public class NewCommentActivity extends AppCompatActivity {

    private EditText etText;
    private TextView tvTitlebar;
    private FloatingActionButton btPostComment;
    public static final String POST_ID = "post_id";
    public static final String POST_TITLE = "post_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_new_comment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etText = (EditText) findViewById(R.id.et_comment_text);
        btPostComment = (FloatingActionButton) findViewById(R.id.bt_fab_comment);
        tvTitlebar = (TextView) findViewById(R.id.tv_new_comment);

        Intent intent = getIntent();
        final int postId = intent.getIntExtra(POST_ID, 0);
        String postTitle = intent.getStringExtra(POST_TITLE);

        tvTitlebar.setText("Reactie op bericht met titel: " + "'" + postTitle + "'");

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

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

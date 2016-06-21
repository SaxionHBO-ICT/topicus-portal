package nl.saxion.jelmer.topics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import nl.saxion.jelmer.topics.R;
import nl.saxion.jelmer.topics.controller.KeyboardFocusHandler;
import nl.saxion.jelmer.topics.model.TopicsModel;

public class NewPostActivity extends AppCompatActivity {

    private EditText etPostTitle, etPostText;
    private FloatingActionButton btPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_new_post);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etPostTitle = (EditText) findViewById(R.id.et_post_title);
        etPostText = (EditText) findViewById(R.id.et_post_text);
        btPost = (FloatingActionButton) findViewById(R.id.bt_fab_post);

        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPostFormFilled()) {

                    String title = etPostTitle.getText().toString();
                    String text = etPostText.getText().toString();

                    TopicsModel.getInstance().addPost(TopicsModel.getInstance().getCurrentUser().getUserId(), TopicsModel.getInstance().getCurrentUser().getUsername(), title, text);
                    finish();

//                    Intent intent = new Intent(NewPostActivity.this, MainActivity.class);
//                    startActivity(intent);

                } else {
                    Toast.makeText(NewPostActivity.this, "Titel- en berichtveld mogen niet leeg zijn.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etPostTitle.setOnFocusChangeListener(new KeyboardFocusHandler(this));
        etPostText.setOnFocusChangeListener(new KeyboardFocusHandler(this));
    }

    private boolean isPostFormFilled() {
        return !etPostTitle.getText().toString().equals("") && !etPostText.getText().toString().equals("");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewPostActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(NewPostActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}

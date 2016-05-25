package nl.saxion.jelmer.topitalk.controller;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    }

    private boolean isPostFormFilled() {
        return !etPostTitle.getText().toString().equals("") && !etPostText.getText().toString().equals("");
    }
}

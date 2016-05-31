package nl.saxion.jelmer.topitalk.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.Post;
import nl.saxion.jelmer.topitalk.model.TopiCoreModel;

public class PostDetailActivity extends AppCompatActivity {

    private ImageView ivUpvote, ivDownvote;
    private TextView tvPostscore, tvUsername, tvDate, tvTitle, tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        tvUsername = (TextView) findViewById(R.id.tv_username_post);
        tvDate = (TextView) findViewById(R.id.tv_date_post);
        tvTitle = (TextView) findViewById(R.id.tv_posttitle_post);
        tvText = (TextView) findViewById(R.id.tv_posttext_post);
        tvPostscore = (TextView) findViewById(R.id.tv_postscore_post);

        Intent intent = getIntent();
        int position = intent.getIntExtra(MainActivity.POSITION_MESSAGE, 0);

        Post post = TopiCoreModel.getInstance().getPostList().get(position);

        tvUsername.setText(post.getAuthor().getUsername());
        tvDate.setText(post.getPostDate());
        tvTitle.setText(post.getTitle());
        tvText.setText(post.getText());

        if (post.getPostScore() != 0) {
            tvPostscore.setVisibility(View.VISIBLE);
            tvPostscore.setText(""+ post.getPostScore());
        } else {
            tvPostscore.setVisibility(View.INVISIBLE);
        }
    }
}
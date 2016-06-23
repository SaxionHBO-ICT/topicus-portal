package nl.saxion.jelmer.topics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import nl.saxion.jelmer.topics.R;
import nl.saxion.jelmer.topics.controller.ApiHandler;
import nl.saxion.jelmer.topics.model.Post;
import nl.saxion.jelmer.topics.model.TopicsModel;
import nl.saxion.jelmer.topics.view.PostDetailListAdapter;
import uk.co.imallan.jellyrefresh.JellyRefreshLayout;


public class PostDetailActivity extends AppCompatActivity {

    public final static String POSITION_MESSAGE = "position_message";

    private ImageView ivUpvote, ivHotIcon;
    private TextView tvPostscore, tvUsername, tvDate, tvTitle, tvText, tvAddComment;
    private ListView lvPostDetail;
    private PostDetailListAdapter adapter;
    private static JellyRefreshLayout refreshLayout;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_post_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvPostDetail = (ListView) findViewById(R.id.lv_post_detail);
        tvAddComment = (TextView) findViewById(R.id.tv_add_comment);
        refreshLayout = (JellyRefreshLayout) findViewById(R.id.jr_refresh_container_detail);

        Intent intent = getIntent();
        int position = intent.getIntExtra(POSITION_MESSAGE, 0);
        Post post = null;

        //This try/catch clause will prevent the app from crashing when a deleted post is selected.
        try {
            post = TopicsModel.getInstance().getLocalPostList().get(position);
        } catch (IndexOutOfBoundsException e) {
            finish();
            Toast.makeText(PostDetailActivity.this, "Bericht niet gevonden!", Toast.LENGTH_SHORT).show();
        } finally {

            if (post != null) {

                adapter = new PostDetailListAdapter(this, TopicsModel.getInstance().getCommentsForThreadId(post.getPostId()));

                final Post finalPost = post;
                postId = finalPost.getPostId();
                tvAddComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostDetailActivity.this, NewCommentActivity.class);
                        intent.putExtra(NewCommentActivity.POST_ID, finalPost.getPostId());
                        intent.putExtra(NewCommentActivity.POST_TITLE, finalPost.getTitle());
                        startActivity(intent);
                    }
                });

                //Enables swipe down to refresh.
                refreshLayout.setRefreshListener(new JellyRefreshLayout.JellyRefreshListener() {
                    @Override
                    public void onRefresh(JellyRefreshLayout jellyRefreshLayout) {
                        adapter.updateCommentList(finalPost.getPostId());
                    }
                });

                View headerView = LayoutInflater.from(this).inflate(R.layout.post_detail_header, lvPostDetail, false); //Inflate the detailheaderview.

                //Set the header data.
                tvUsername = (TextView) headerView.findViewById(R.id.tv_username_post);
                tvDate = (TextView) headerView.findViewById(R.id.tv_date_post);
                tvTitle = (TextView) headerView.findViewById(R.id.tv_posttitle_post);
                tvText = (TextView) headerView.findViewById(R.id.tv_posttext_post);
                tvPostscore = (TextView) headerView.findViewById(R.id.tv_postscore_post);
                ivHotIcon = (ImageView) headerView.findViewById(R.id.iv_hot_icon_post);
                ivUpvote = (ImageView) headerView.findViewById(R.id.iv_upvote_post);

                ivUpvote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Simulate instant updating of the postscore field.
                        ivUpvote.setAlpha(0.5f);
                        tvPostscore.setVisibility(View.VISIBLE);
                        tvPostscore.setText(String.valueOf(finalPost.getPostScore() + 1 + ""));
                        TopicsModel.getInstance().upvotePost(finalPost.getPostId(), TopicsModel.getInstance().getCurrentUser().getUserId()); //Upvote the post.
                        adapter.notifyDataSetChanged();
                    }
                });

                //This sets the textcolor of the username field to blue and adds an asterix (*) if the post is owned by the current user.
                if (post.getAuthorUsername().equals(TopicsModel.getInstance().getCurrentUser().getUsername())) {
                    tvUsername.setText(post.getAuthorUsername() + "*");
                    tvUsername.setTextColor(headerView.getResources().getColor(R.color.topicusBlue));
                } else {
                    tvUsername.setText(post.getAuthorUsername());
                    tvUsername.setTextColor(tvDate.getTextColors().getDefaultColor());
                }

                tvDate.setText(post.getPostDate());
                tvTitle.setText(post.getTitle());
                tvText.setText(post.getText());

                lvPostDetail.addHeaderView(headerView);
                lvPostDetail.setAdapter(adapter);

                //If the post hasn't been upvoted yet, hide the score textview. If not, fill the field.
                if (post.getPostScore() != 0) {
                    tvPostscore.setVisibility(View.VISIBLE);
                    tvPostscore.setText("" + post.getPostScore());
                } else {
                    tvPostscore.setVisibility(View.INVISIBLE);
                }

                //Greys out the upvote button if a user has already upvoted a certain post.
                if (ApiHandler.getInstance().hasUserUpvotedPost(post.getPostId(), TopicsModel.getInstance().getCurrentUser().getUserId())) {
                    ivUpvote.setAlpha(0.5f);
                    ivUpvote.setClickable(false);
                } else {
                    ivUpvote.setAlpha(1f);
                    ivUpvote.setClickable(true);
                }

                //If the post is a hot topic (20+ upvotes), make the flame icon visible.
                if (post.isHotTopic()) {
                    ivHotIcon.setVisibility(View.VISIBLE);
                } else {
                    ivHotIcon.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    /**
     * Method to tell the refreshLayout refreshing animation can be stopped.
     * Called from the onPostExecute in ApiHandler method pertaining to comments.
     */
    public static void finishRefreshing() {
        refreshLayout.finishRefreshing();
    }

    //Keep an updated list.
    @Override
    protected void onResume() {
        adapter.updateCommentList(postId);
        super.onResume();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

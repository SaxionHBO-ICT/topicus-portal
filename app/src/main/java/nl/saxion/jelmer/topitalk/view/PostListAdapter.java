package nl.saxion.jelmer.topitalk.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.Post;
import nl.saxion.jelmer.topitalk.model.TopiCoreModel;

/**
 * Created by Nyds on 21/05/2016.
 */
public class PostListAdapter extends ArrayAdapter<Post> {

    private ImageView ivUpvote, ivHotIcon;
    private TextView tvPostscore, tvTitle, tvUsername, tvDate, tvText;

    public PostListAdapter(Context context, List<Post> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, parent, false);
        }

        tvPostscore = (TextView) convertView.findViewById(R.id.tv_postscore_listitem);
        tvTitle = (TextView) convertView.findViewById(R.id.tv_posttitle_listitem);
        tvUsername = (TextView) convertView.findViewById(R.id.tv_username_listitem);
        tvDate = (TextView) convertView.findViewById(R.id.tv_date_listitem);
        ivHotIcon = (ImageView) convertView.findViewById(R.id.iv_hot_icon);
        tvText = (TextView) convertView.findViewById(R.id.tv_posttext_listitem);
        ivUpvote = (ImageView) convertView.findViewById(R.id.iv_upvote_listitem);

        Post post = (Post) getItem(position);

        tvTitle.setText(post.getTitle());
        tvUsername.setText(post.getAuthorUsername());
        tvDate.setText(post.getPostDate());
        tvText.setText(post.getText());

        if (post.getPostScore() != 0) {
            tvPostscore.setVisibility(View.VISIBLE);
            tvPostscore.setText(""+ post.getPostScore());
        } else {
            tvPostscore.setVisibility(View.INVISIBLE);
        }

        if (TopiCoreModel.getInstance().getCurrentUser().hasUserUpvotedPost(position)) {
            ivUpvote.setAlpha(0.5f);
        } else {
            ivUpvote.setAlpha(1f);
        }

        if (post.isHotTopic()) {
            ivHotIcon.setVisibility(View.VISIBLE);
        } else {
            ivHotIcon.setVisibility(View.INVISIBLE);
        }

        ivUpvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopiCoreModel.getInstance().upvotePost(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void updatePostList() {
        super.clear();
        super.addAll(TopiCoreModel.getInstance().getPostListFromDb());
        super.notifyDataSetChanged();
    }
}

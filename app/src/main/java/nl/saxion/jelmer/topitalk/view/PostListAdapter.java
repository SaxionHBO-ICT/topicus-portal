package nl.saxion.jelmer.topitalk.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.Post;
import nl.saxion.jelmer.topitalk.model.TopiCoreModel;

/**
 * Created by Nyds on 21/05/2016.
 */
public class PostListAdapter extends ArrayAdapter {

    private ImageView ivUpvote, ivDownvote;
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
        tvText = (TextView) convertView.findViewById(R.id.tv_posttext_listitem);
        ivUpvote = (ImageView) convertView.findViewById(R.id.iv_upvote_listitem);

        Post post = (Post) getItem(position);

        tvTitle.setText(post.getTitle());
        tvUsername.setText(post.getAuthor().getUsername());
        tvDate.setText(post.getPostDate());
        tvText.setText(post.getText());

        if (post.getPostScore() != 0) {
            tvPostscore.setText(""+ post.getPostScore());
        }

        if (TopiCoreModel.getInstance().getCurrentUser().hasUserUpvotedPost(post.getPostId())) {
            ivUpvote.setAlpha(0.5f);
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


}

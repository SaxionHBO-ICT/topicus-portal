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
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, parent, false);
        }

        tvPostscore = (TextView) convertView.findViewById(R.id.tv_postscore_listitem);
        tvTitle = (TextView) convertView.findViewById(R.id.tv_posttitle_listitem);
        tvUsername = (TextView) convertView.findViewById(R.id.tv_username_listitem);
        tvDate = (TextView) convertView.findViewById(R.id.tv_date_listitem);
        tvText = (TextView) convertView.findViewById(R.id.tv_posttext_listitem);

        Post post = (Post) getItem(position);

        tvTitle.setText(post.getTitle());
        tvUsername.setText(post.getAuthor().getUsername());
        tvDate.setText(post.getPostDate());
        tvText.setText(post.getText());

        return convertView;
    }
}

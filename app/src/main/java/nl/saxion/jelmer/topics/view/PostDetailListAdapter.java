package nl.saxion.jelmer.topics.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.saxion.jelmer.topics.R;
import nl.saxion.jelmer.topics.model.Comment;
import nl.saxion.jelmer.topics.model.TopicsModel;

/**
 * Adapter class used in PostDetailActivity to display a list of comments
 * below the header (post detail) of the ListView.
 */
public class PostDetailListAdapter extends ArrayAdapter<Comment> {
    
    private TextView tvUsername, tvDate, tvText;

    public PostDetailListAdapter(Context context, List<Comment> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item, parent, false);
        }

        tvUsername = (TextView) convertView.findViewById(R.id.tv_user_comment);
        tvDate = (TextView) convertView.findViewById(R.id.tv_date_comment);
        tvText = (TextView) convertView.findViewById(R.id.tv_text_comment);

        Comment comment = getItem(position);

        tvUsername.setText(comment.getAuthorUsername());
        tvDate.setText(comment.getCommentDate());
        tvText.setText(comment.getText());

        return convertView;
    }

    public void updateCommentList(int postId) {
        super.clear();
        super.addAll(TopicsModel.getInstance().getCommentsForThreadId(postId));
        super.notifyDataSetChanged();
    }
}

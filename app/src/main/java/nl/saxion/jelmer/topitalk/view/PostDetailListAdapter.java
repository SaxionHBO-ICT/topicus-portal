package nl.saxion.jelmer.topitalk.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.Comment;
import nl.saxion.jelmer.topitalk.model.TopiCoreModel;

/**
 * Created by Nyds on 23/05/2016.
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
        super.addAll(TopiCoreModel.getInstance().getCommentsForThread(postId));
        super.notifyDataSetChanged();
    }
}

package nl.saxion.jelmer.topitalk.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.Comment;

/**
 * Created by Nyds on 23/05/2016.
 */
public class CommentListAdapter extends ArrayAdapter<Comment> {

    public CommentListAdapter(Context context, List<Comment> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item, parent, false);
        }

        return convertView;
    }
}

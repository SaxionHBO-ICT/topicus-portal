package nl.saxion.jelmer.topitalk.view;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

import nl.saxion.jelmer.topitalk.model.Comment;

/**
 * Created by Nyds on 23/05/2016.
 */
public class CommentListAdapter extends ArrayAdapter<Comment> {

    public CommentListAdapter(Context context, List<Comment> objects) {
        super(context, -1, objects);
    }
}

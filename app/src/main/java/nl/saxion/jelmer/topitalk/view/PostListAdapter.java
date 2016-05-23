package nl.saxion.jelmer.topitalk.view;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Nyds on 21/05/2016.
 */
public class PostListAdapter extends ArrayAdapter {

    public PostListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }
}

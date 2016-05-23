package nl.saxion.jelmer.topitalk.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import nl.saxion.jelmer.topitalk.R;

/**
 * Created by Nyds on 21/05/2016.
 */
public class PostListAdapter extends ArrayAdapter {

    public PostListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item, parent, false);
        }

        return convertView;
    }
}

package nl.saxion.jelmer.topitalk.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.controller.ApiHandler;
import nl.saxion.jelmer.topitalk.model.TopiCoreModel;
import nl.saxion.jelmer.topitalk.view.PostListAdapter;

public class MainActivity extends AppCompatActivity {

    private PostListAdapter adapter;
    private ListView postList;
    private FloatingActionButton btNewPost;
    public final static String POSITION_MESSAGE = "position_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postList = (ListView) findViewById(R.id.lv_post_list);
        btNewPost = (FloatingActionButton) findViewById(R.id.bt_fab_main);

        initialize();

        try {
            adapter = new PostListAdapter(this, TopiCoreModel.getInstance().getPostListFromDb());
            postList.setAdapter(adapter);
        } catch (NullPointerException e) {
            Toast.makeText(MainActivity.this, "Berichtenlijst kon niet worden opgehaald.", Toast.LENGTH_SHORT).show();
        }

        btNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewPostActivity.class);
                startActivity(intent);
                finish();
            }
        });

        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PostDetailActivity.class);
                intent.putExtra(POSITION_MESSAGE, position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        initialize();

        if (adapter != null) {

            adapter.notifyDataSetChanged();
        }
        super.onResume();
    }

    private boolean isUserLoggedIn() {
        return TopiCoreModel.getInstance().getCurrentUser() != null;
    }

    private void initialize() {

        if (!isUserLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topi_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_logout:
                TopiCoreModel.getInstance().logoutCurrentUser();
                initialize();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import garin.artemiy.quickaction.library.QuickAction;
import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.TopiCoreModel;
import nl.saxion.jelmer.topitalk.view.PostListAdapter;
import uk.co.imallan.jellyrefresh.JellyRefreshLayout;

public class MainActivity extends AppCompatActivity {

    private PostListAdapter adapter;
    private ListView postList;
    private FloatingActionButton btNewPost;
    private static JellyRefreshLayout refreshLayout;
    private QuickAction quickAction;
    private RelativeLayout popUpMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postList = (ListView) findViewById(R.id.lv_post_list);
        btNewPost = (FloatingActionButton) findViewById(R.id.bt_fab_main);
        refreshLayout = (JellyRefreshLayout) findViewById(R.id.jr_refresh_container_main);
        popUpMenu = (RelativeLayout) getLayoutInflater().inflate(R.layout.popup_menu, null);
        quickAction = new QuickAction(this, R.style.PopupAnimation, popUpMenu, popUpMenu);

        initialize();

        try {
            adapter = new PostListAdapter(this, TopiCoreModel.getInstance().getPostListFromDb());
            postList.setAdapter(adapter);

            refreshLayout.setRefreshListener(new JellyRefreshLayout.JellyRefreshListener() {
                @Override
                public void onRefresh(JellyRefreshLayout jellyRefreshLayout) {
                    adapter.updatePostList();
                }
            });

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
                intent.putExtra(PostDetailActivity.POSITION_MESSAGE, position);
                startActivity(intent);
            }
        });

        postList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if (TopiCoreModel.getInstance().getLocalPostList().get(position).getAuthorUsername().equals(TopiCoreModel.getInstance().getCurrentUser().getUsername())) {

                    ImageView ivEdit = (ImageView) popUpMenu.findViewById(R.id.iv_edit_popup);
                    ImageView ivDelete = (ImageView) popUpMenu.findViewById(R.id.iv_delete_popup);

                    quickAction.show(view);
                    return true;
                } else {
                    Toast.makeText(MainActivity.this, "Je kunt alleen je eigen berichten aanpassen.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
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

    public static void finishRefreshing() {
        refreshLayout.finishRefreshing();
    }

}

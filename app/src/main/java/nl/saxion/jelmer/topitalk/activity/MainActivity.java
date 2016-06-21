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

        //Try instantiating the PostListAdapter using a list of posts from the API.
        //If no list is returned (no connection to the API), a nullpointer is thrown and caught displaying a toast message.
        try {
            adapter = new PostListAdapter(this, TopiCoreModel.getInstance().getPostListFromDb());
            postList.setAdapter(adapter);

            refreshLayout.setRefreshListener(new JellyRefreshLayout.JellyRefreshListener() { //Enables swipe down to refresh.
                @Override
                public void onRefresh(JellyRefreshLayout jellyRefreshLayout) {
                    adapter.updatePostList();
                }
            });

        } catch (NullPointerException e) {
            Toast.makeText(MainActivity.this, "Berichtenlijst kon niet worden opgehaald.", Toast.LENGTH_SHORT).show();
        }

        //Called when the FAB new post button is pressed.
        btNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewPostActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        //Called when a post is selected. Start the PostDetailActivity and send the position.
        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PostDetailActivity.class);
                intent.putExtra(PostDetailActivity.POSITION_MESSAGE, position);
                startActivity(intent);
            }
        });

        //Open a pop-up menu when a post is long-pressed. (This menu doesn't do anything currently, but should be used to edit/delete posts).
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

    @Override
    protected void onResume() {
        adapter.updatePostList();
        super.onResume();
    }

    /**
     * Method to check if a user is logged in.
     * @return true if yes, false if no.
     */
    private boolean isUserLoggedIn() {
        return TopiCoreModel.getInstance().getCurrentUser() != null;
    }

    /**
     * Method to handle starting the LoginActivity and finishing the MainActivity
     * if there is no user logged in.
     */
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

    /**
     * Method to handle finishing refresh state of the JellyRefreshLayout
     * This method is called from the onPostExecute() method in the ApiHandler.
     */
    public static void finishRefreshing() {
        refreshLayout.finishRefreshing();
    }
}

package nl.saxion.jelmer.topitalk.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.TalkModel;
import nl.saxion.jelmer.topitalk.view.PostListAdapter;

public class MainActivity extends AppCompatActivity {

    private Button btLogout;
    private PostListAdapter adapter;
    private ListView postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLogout = (Button) findViewById(R.id.bt_logout);
        postList = (ListView) findViewById(R.id.lv_post_list);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TalkModel.getInstance().logoutCurrentUser();
                initialize();
            }
        });

        initialize();

        adapter = new PostListAdapter(this, TalkModel.getInstance().getPostList());
        postList.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        initialize();
        super.onResume();
    }

    private boolean isUserLoggedIn() {
        return TalkModel.getInstance().getCurrentUser() != null;
    }

    private void initialize() {

        if (isUserLoggedIn()) {
            btLogout.setVisibility(View.VISIBLE);
            btLogout.setClickable(true);
        } else {
            btLogout.setVisibility(View.INVISIBLE);
            btLogout.setClickable(false);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

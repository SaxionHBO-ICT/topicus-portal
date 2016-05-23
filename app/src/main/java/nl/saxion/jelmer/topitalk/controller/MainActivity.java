package nl.saxion.jelmer.topitalk.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.TalkModel;

public class MainActivity extends AppCompatActivity {

    private Button btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initialize();
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
            setContentView(R.layout.activity_main);
            btLogout = (Button) findViewById(R.id.bt_logout);
            btLogout.setVisibility(View.VISIBLE);
            btLogout.setClickable(true);

            btLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TalkModel.getInstance().logoutCurrentUser();
                    initialize();
                }
            });
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}

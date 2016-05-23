package nl.saxion.jelmer.topitalk.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.TalkModel;

public class MainActivity extends AppCompatActivity {

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
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

}

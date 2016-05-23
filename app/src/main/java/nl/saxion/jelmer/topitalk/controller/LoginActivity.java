package nl.saxion.jelmer.topitalk.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nl.saxion.jelmer.topitalk.R;

/**
 * Created by Nyds on 23/05/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUser, etPassword;
    private Button btLogin;
    private TextView tvNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        tvNewUser = (TextView) findViewById(R.id.tv_new_user);

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });
    }
}

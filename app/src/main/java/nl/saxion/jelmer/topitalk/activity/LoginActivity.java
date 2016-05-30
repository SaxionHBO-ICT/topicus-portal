package nl.saxion.jelmer.topitalk.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.controller.KeyboardFocusHandler;
import nl.saxion.jelmer.topitalk.controller.LoginHandler;
import nl.saxion.jelmer.topitalk.controller.TextFormatter;

/**
 * Created by Nyds on 23/05/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUser, etPassword;
    private Button btLogin;
    private TextView tvNewUser;
    private CheckBox cbSave;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        etUser = (EditText) findViewById(R.id.et_user);
        etPassword = (EditText) findViewById(R.id.et_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        tvNewUser = (TextView) findViewById(R.id.tv_new_user);
        cbSave = (CheckBox) findViewById(R.id.cb_remember_details);

        if (preferences.contains("username")) {
            cbSave.setChecked(true);
            etUser.setText(preferences.getString("username", ""));
            etPassword.setText(preferences.getString("password", ""));
        }

        tvNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        etUser.setOnFocusChangeListener(new KeyboardFocusHandler(this));
        etPassword.setOnFocusChangeListener(new KeyboardFocusHandler(this));
    }

    private void login() {

        String username = TextFormatter.getFormattedTextFromField(etUser);
        String password = TextFormatter.getFormattedTextFromField(etPassword);

        if (LoginHandler.login(username, password)) {

            if (cbSave.isChecked()) {
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();
            } else {
                editor.remove("username");
                editor.remove("password");
                editor.apply();
            }
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(LoginActivity.this, "Gegevens onjuist. Controleer uw invoer.", Toast.LENGTH_SHORT).show();
        }
    }

}

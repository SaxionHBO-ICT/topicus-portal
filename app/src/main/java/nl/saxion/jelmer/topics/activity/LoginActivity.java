package nl.saxion.jelmer.topics.activity;

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

import nl.saxion.jelmer.topics.R;
import nl.saxion.jelmer.topics.controller.KeyboardFocusHandler;
import nl.saxion.jelmer.topics.controller.LoginHandler;
import nl.saxion.jelmer.topics.controller.TextFormatter;

/**
 * The LoginActivity handles all user input related to logging in.
 * When the app is launched, this activity will be run.
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

        //Check the shared preferences for saved user credentials, when found, auto-fill the textfields.
        if (preferences.contains("username")) {
            cbSave.setChecked(true);
            etUser.setText(preferences.getString("username", ""));
            etPassword.setText(preferences.getString("password", ""));
        }

        //RegisterUserActivity
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

        //If a user taps outside either textview, the keyboard will be closed.
        etUser.setOnFocusChangeListener(new KeyboardFocusHandler(this));
        etPassword.setOnFocusChangeListener(new KeyboardFocusHandler(this));
    }

    /**
     * Method to handle the login of a user.
     * Get the formatted text from the fields.
     * If the login attempt is successful start the MainActivity.
     */
    private void login() {

        String username = TextFormatter.getFormattedTextFromField(etUser);
        String password = TextFormatter.getFormattedTextFromField(etPassword);

        if (LoginHandler.login(username, password)) { //If the login is successful.

            if (cbSave.isChecked()) { //If the check button is checked save the username & password as keypairs.
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();
            } else { //If not, remove the data from the device.
                editor.remove("username");
                editor.remove("password");
                editor.apply();
            }
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(LoginActivity.this, "Inloggen niet mogelijk. Gegevens incorrect, of de server is offline.", Toast.LENGTH_SHORT).show();
        }
    }
}

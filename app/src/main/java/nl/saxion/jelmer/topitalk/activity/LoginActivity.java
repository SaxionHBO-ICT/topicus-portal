package nl.saxion.jelmer.topitalk.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.TalkModel;
import nl.saxion.jelmer.topitalk.model.User;

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

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        etUser.setOnFocusChangeListener(new OnFocusChangeListener());
        etPassword.setOnFocusChangeListener(new OnFocusChangeListener());


    }

    private void login() {

        User user = TalkModel.getInstance().findUserByUsername(getFormattedTextFromField(etUser));

        if (user != null && user.getPassword().equals(getFormattedTextFromField(etPassword))) {
            TalkModel.getInstance().setCurrentUser(user);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Gegevens onjuist. Controleer uw invoer.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFormattedTextFromField(EditText field) {
        return field.getText().toString().replaceAll(" ", "");
    }

    /**
     * Listener for EditText fields to call hideKeyBoard on focus change.
     */
    private class OnFocusChangeListener implements View.OnFocusChangeListener {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
            }
        }
    }

    /**
     * Helper method to hide the keyboard.
     *
     * @param view The view whose state has changed.
     */
    private void hideKeyboard(View view) {

        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

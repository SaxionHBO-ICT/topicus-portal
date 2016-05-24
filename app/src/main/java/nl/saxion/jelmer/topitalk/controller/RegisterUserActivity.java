package nl.saxion.jelmer.topitalk.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nl.saxion.jelmer.topitalk.R;
import nl.saxion.jelmer.topitalk.model.TalkModel;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etRepeatPassword, etName, etSurname;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        etUsername = (EditText) findViewById(R.id.et_user_register);
        etPassword = (EditText) findViewById(R.id.et_password_register);
        etRepeatPassword = (EditText) findViewById(R.id.et_password_register_repeat);
        etName = (EditText) findViewById(R.id.et_name_register);
        etSurname = (EditText) findViewById(R.id.et_surname_register);

        btRegister = (Button) findViewById(R.id.bt_register);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormFilledCorrectly()) {
                    TalkModel.getInstance().addUser(etUsername.getText().toString(),
                            etPassword.getText().toString(),
                            etName.getText().toString(),
                            etSurname.getText().toString());
                    Toast.makeText(RegisterUserActivity.this, "Account met naam: " + etUsername.getText().toString() + " is voor je geregistreerd.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RegisterUserActivity.this, "Er ging iets mis! Controleer of alle velden juist zijn ingevuld.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean isFormFilledCorrectly() {
        return isUsernameUnique(etUsername.getText().toString()) && doPasswordsMatch() && isNameFilled();
    }

    private boolean isUsernameUnique(String name) {
        return TalkModel.getInstance().isUsernameUnique(name);
    }

    private boolean doPasswordsMatch() {
        if (!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
            return false;
        } else if (etPassword.getText().toString().equals("") || etRepeatPassword.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    private boolean isNameFilled() {
        if (etName.getText().toString().equals("") || etSurname.getText().toString().equals("")) {
            return false;
        } else if (etName.getText().toString().equals("") && etSurname.getText().toString().equals("")) {
            return false;
        }
        return true;
    }
}

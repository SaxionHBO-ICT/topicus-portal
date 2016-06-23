package nl.saxion.jelmer.topics.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nl.saxion.jelmer.topics.R;
import nl.saxion.jelmer.topics.controller.KeyboardFocusHandler;
import nl.saxion.jelmer.topics.controller.LoginHandler;
import nl.saxion.jelmer.topics.controller.TextFormatter;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etRepeatPassword, etName, etSurname;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUsername = (EditText) findViewById(R.id.et_user_register);
        etPassword = (EditText) findViewById(R.id.et_password_register);
        etRepeatPassword = (EditText) findViewById(R.id.et_password_register_repeat);
        etName = (EditText) findViewById(R.id.et_name_register);
        etSurname = (EditText) findViewById(R.id.et_surname_register);

        btRegister = (Button) findViewById(R.id.bt_register);


        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!doesPasswordContainSpaces()) { //Check for spaces in passwords first.

                    if (isFormFilledCorrectly()) { //Then check if the form is filled correctly.

                        //Get the data from the fields.
                        String username = TextFormatter.getFormattedTextFromField(etUsername);
                        String password = TextFormatter.getFormattedTextFromField(etPassword);
                        String name = TextFormatter.getFormattedTextFromField(etName);
                        String surname = TextFormatter.getFormattedTextFromField(etSurname);

                        if (LoginHandler.registerUser(username, password, name, surname)) { //If the registration was a success, toast and finish the activity.
                            Toast.makeText(RegisterUserActivity.this, "Account met naam: " + username + " is voor je geregistreerd.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterUserActivity.this, "Er ging iets mis! Gebruikersnaam is reeds in gebruik, of geen verbinding mogelijk.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterUserActivity.this, "Er ging iets mis! Controleer of alle velden juist zijn ingevuld.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterUserActivity.this, "Er ging iets mis! Wachtwoord mag geen spaties bevatten.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Enables the user to tap outside a textview to close the keyboard.
        etUsername.setOnFocusChangeListener(new KeyboardFocusHandler(this));
        etPassword.setOnFocusChangeListener(new KeyboardFocusHandler(this));
        etRepeatPassword.setOnFocusChangeListener(new KeyboardFocusHandler(this));
        etName.setOnFocusChangeListener(new KeyboardFocusHandler(this));
        etSurname.setOnFocusChangeListener(new KeyboardFocusHandler(this));

    }

    /**
     * Method to check if all fields are filled correctly.
     * @return true if password fields match and namefields are filled.
     */
    private boolean isFormFilledCorrectly() {
        return doPasswordFieldsMatch() && isNameFilled();
    }

    /**
     * Method to check if passwords match and neither has been left empty.
     * @return true if they match and fields are filled, false if they're not.
     */
    private boolean doPasswordFieldsMatch() {
        if (!TextFormatter.getFormattedTextFromField(etPassword).equals(TextFormatter.getFormattedTextFromField(etRepeatPassword))) {
            return false;
        } else if (etPassword.getText().toString().equals("") || etRepeatPassword.getText().toString().equals("")) {
            return false;
        }
        return true;
    }

    /**
     * MEthod to check if passwords contain spaces.
     * Spaces are trimmed from the passwords anyway, but this enables specific feedback to the user.
     * @return true if either password contains a space, false if they don't.
     */
    private boolean doesPasswordContainSpaces() {
        return etPassword.getText().toString().contains(" ") || etRepeatPassword.getText().toString().contains(" ");
    }

    /**
     * Method to check if both namefieds are filled.
     * @return true if they are, false if they're not.
     */
    private boolean isNameFilled() {
        return !(etName.getText().toString().equals("") || etSurname.getText().toString().equals(""));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

package com.example.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    // UI references.
    private Button mReturnButton;
    private Button mRegisterButton;
    private EditText mEmailText;
    private EditText mPasswordText;
    private EditText mUsernameText;
    private static int account_id = 0;

    //Database
    Database db = new Database();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mReturnButton = findViewById(R.id.return_button_from_register);
        mRegisterButton = findViewById(R.id.create_user_button);
        mEmailText = findViewById(R.id.email);
        mUsernameText = findViewById(R.id.username);
        mPasswordText = findViewById(R.id.password);

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Returning to log in!", Toast.LENGTH_SHORT).show();

                Intent returnActivity = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(returnActivity);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });


    }
    private boolean isUsernameValid(String username) {
        return username.length() > 4 && username.length() < 16;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4 && password.length() < 16;
    }

    private boolean isEmailValid(String email) {
        return email.length() > 4 && email.contains("@");
    }

    public void attemptSignUp(){
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(mPasswordText.getText().toString()) && !isPasswordValid(mPasswordText.getText().toString())) {
            mPasswordText.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordText;
            cancel = true;
        }

        // Check for a valid email.
        if (TextUtils.isEmpty(mEmailText.getText())) {
            mEmailText.setError(getString(R.string.error_field_required));
            focusView = mEmailText;
            cancel = true;
        } else if (!isEmailValid(mEmailText.getText().toString())) {
        mEmailText.setError(getString(R.string.error_invalid_email));
        focusView = mEmailText;
        cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(mUsernameText.getText())) {
            mUsernameText.setError(getString(R.string.error_field_required));
            focusView = mUsernameText;
            cancel = true;
        } else if (!isUsernameValid(mUsernameText.getText().toString())) {
            mUsernameText.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Random random = new Random();
            account_id = random.nextInt(1000000);
            String user = String.valueOf(mUsernameText.getText());
            String email = String.valueOf(mEmailText.getText());
            String password = String.valueOf(mPasswordText.getText());
            db.registerUser(user,email,password,account_id);
            Toast.makeText(getBaseContext(),"User created, returning!", Toast.LENGTH_SHORT).show();
            Intent returnActivity = new Intent(RegisterActivity.this, LogInActivity.class);
            startActivity(returnActivity);
        }
    }

}

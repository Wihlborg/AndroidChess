package com.example.androidchess;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class RegisterActivity extends AppCompatActivity {
    /**
     * Keep track of the register task to ensure we can cancel it if requested.
     */
    protected UserRegisterTask mAuthTask = null;


    // UI references.
    private LinearLayout mLayout;
    protected Button mReturnButton;
    protected Button mRegisterButton;
    private TextInputEditText mEmailText;
    public TextInputEditText mPasswordText;
    public TextInputEditText mUsernameText;
    private ProgressBar mBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Set up layout
        mLayout = findViewById(R.id.LinearRegister);
        mReturnButton = findViewById(R.id.return_button_from_register);
        mRegisterButton = findViewById(R.id.create_user_button);
        mEmailText = findViewById(R.id.email);
        mUsernameText = findViewById(R.id.username);
        mPasswordText = findViewById(R.id.password);
        //Might add animation of loading (optional)
        mBar = findViewById(R.id.register_progress);

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Returning to log in!", Toast.LENGTH_SHORT).show();

                Intent returnActivity = new Intent(RegisterActivity.this, LogInActivity.class);
                startActivity(returnActivity);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mLayout.getWindowToken(), 0);
                } catch (Exception e){
                    e.printStackTrace();
                }
                attemptSignUp();
            }
        });


    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptSignUp() {
        if (mAuthTask != null) {
            return;
        }

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(mPasswordText.getText())){
            mPasswordText.setError(getString(R.string.error_field_required));
            focusView = mPasswordText;
            cancel = true;
        }else if (!isPasswordValid(mPasswordText.getText().toString())){
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
            int account_id = 0;
            String user = String.valueOf(mUsernameText.getText());
            String email = String.valueOf(mEmailText.getText());
            String password = String.valueOf(mPasswordText.getText());
            Toast.makeText(getBaseContext(), "User created, returning!", Toast.LENGTH_SHORT).show();
            Intent returnActivity = new Intent(RegisterActivity.this, LogInActivity.class);
            startActivity(returnActivity);
            mAuthTask = new UserRegisterTask(user, email, password, account_id);
            mAuthTask.execute((Void) null);

        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 3 && username.length() < 15;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3 && password.length() < 15;
    }

    private boolean isEmailValid(String email) {
        return email.length() > 4 && email.contains("@");
    }

    /**
     * Represents an asynchronous sign up task used for creation
     * of a user.
     */

    class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final String mUsername;
        private final String mEmail;
        private final String mPassword;
        private final int mAccount_Id;


        UserRegisterTask(String username, String email, String password, int account_id) {
            mUsername = username;
            mEmail = email;
            mPassword = password;
            mAccount_Id = account_id;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            return Database.getInstance().registerUser(mUsername, mEmail, mPassword, mAccount_Id);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mAuthTask = null;
            if (success) {
                Intent returnTo = new Intent(RegisterActivity.this, LogInActivity.class);
                Toast.makeText(getApplicationContext(),"Registration success", Toast.LENGTH_SHORT).show();
                startActivity(returnTo);
            } else {
                Toast.makeText(getApplicationContext(),"Registration failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}

package com.example.androidchess;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

public class LogInActivity  extends AppCompatActivity{
    private static final String TAG = "LogInActivity";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    protected UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mUsername;
    private EditText mPasswordView;
    protected Button mRegisterButton;
    protected Button mSignInButton;
    protected Button mForgotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsername = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);
        // Set up the register form
        mRegisterButton = findViewById(R.id.register_button_from_login);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptSignIn();
                    return true;
                }
                return false;
            }
        });

        mSignInButton = findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignIn();
            }
        });

        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Going to register!", Toast.LENGTH_SHORT).show();

                Intent registerActivity = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
            }
        });

        mForgotButton = findViewById(R.id.forgot_password_button);
        mForgotButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(LogInActivity.this, ForgotActivity.class);
                startActivity(forgot);
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignIn() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsername.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsername.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsername.setError(getString(R.string.error_invalid_username));
            focusView = mUsername;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);

        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 3 && username.length() < 15;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3 && password.length() < 15;
    }

    /**
     * Represents an asynchronous sign in task used to authenticate
     * the user.
     */
     class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //Database

            return Database.getInstance().authenticateUser(mUsername, mPassword);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                //Replace later with an actual online layout
                //This is just for testing if it works or not.
                //Might be a good idea to use
                //Intent r = new Intent(LogInActivity.this, NextClass.class);
                //startActivity(r);

                setContentView(R.layout.signedin_layout);
                Toast.makeText(getApplicationContext(),"Log In success", Toast.LENGTH_SHORT).show();
                Log.d(TAG,"Log In Success");
            } else {
                Toast.makeText(getApplicationContext(),"Log In failed", Toast.LENGTH_SHORT).show();
                mPasswordView.setError(getString(R.string.error_incorrect_login));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

}

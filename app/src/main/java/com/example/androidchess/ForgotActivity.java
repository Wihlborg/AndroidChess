package com.example.androidchess;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class ForgotActivity extends AppCompatActivity {
    /**
     * Keep track of the forgot task to ensure we can cancel it if requested.
     */
    protected UserForgotPassword mAuthTask = null;
    private final static String TAG = "ForgotActivity";

    //UI
    private LinearLayout mLayoutForgot;
    protected Button mSendButton;
    protected Button mReturnButton;
    private TextInputEditText mEmail;
    private TextInputEditText mUsername;
    private ProgressBar mBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        //Set up layout
        mLayoutForgot = findViewById(R.id.LinearForgot);
        mEmail = findViewById(R.id.email_from_forgot);
        mSendButton = findViewById(R.id.send_recovery_button);
        mUsername = findViewById(R.id.user_from_forgot);
        mReturnButton = findViewById(R.id.return_from_forgot);
        //Might add animation of loading (optional)
        mBar = findViewById(R.id.forgot_progress);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mLayoutForgot.getWindowToken(), 0);
                } catch (Exception e){
                    e.printStackTrace();
                }
                recoverPassword();
            }
        });

        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToLogin = new Intent(ForgotActivity.this, LogInActivity.class);
                startActivity(returnToLogin);

            }
        });
    }
    private boolean isEmailValid(String email) {
        return email.length() > 4 && email.contains("@");
    }
    private boolean isUsernameValid(String username) {
        return username.length() > 3 && username.length() < 15;
    }

    public void recoverPassword(){
        if (mAuthTask != null) {
            return;
        }
        mEmail.setError(null);
        mUsername.setError(null);
        String email = mEmail.getText().toString();
        String user = mUsername.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email.
        if (TextUtils.isEmpty(mEmail.getText())) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            cancel = true;
        } else if (!isEmailValid(mEmail.getText().toString())) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        // Check for a valid username.
        if (TextUtils.isEmpty(mUsername.getText())) {
            mUsername.setError(getString(R.string.error_field_required));
            focusView = mUsername;
            cancel = true;
        } else if (!isUsernameValid(mUsername.getText().toString())) {
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
            mAuthTask = new UserForgotPassword(email, user);
            mAuthTask.execute((Void) null);
        }

    }

    /**
     * Represents an asynchronous forgot password task used to recover a new
     * password for the user.
     */
    class UserForgotPassword extends AsyncTask<Void, Void, Boolean> {
        String email;
        String user;


        UserForgotPassword(String recipientEmail, String username){
            email = recipientEmail;
            user = username;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            return Database.getInstance().forgotPassword(email, user);
        }

        @Override
        protected void onPostExecute(Boolean success) {
          mAuthTask = null;
            if (success) {
                Intent returnTo = new Intent(ForgotActivity.this, LogInActivity.class);
                Toast.makeText(getApplicationContext(),"Recovery success", Toast.LENGTH_SHORT).show();
                startActivity(returnTo);
                Log.d(TAG,"Recovery Success");
            } else {
                Toast.makeText(getApplicationContext(),"Recovery failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
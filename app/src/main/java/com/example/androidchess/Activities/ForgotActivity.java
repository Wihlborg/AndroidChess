package com.example.androidchess.Activities;

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
import com.example.androidchess.Database.*;
import com.example.androidchess.R;

public class ForgotActivity extends AppCompatActivity {
    //Enables Log comments
    private final static String TAG = "ForgotActivity";
    /**
     * Keep track of the forgot task to ensure we can cancel it if requested.
     */

    protected UserForgotPassword mAuthTask = null;

    //UI
    private LinearLayout mLayoutForgot;
    protected Button mSendButton;
    protected TextView mReturnButton;
    private EditText mEmail;

    @Override
    public void onBackPressed() {
        this.finish();
        returnMethod();
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        //Set up layout
        getSupportActionBar().hide();
        mLayoutForgot = findViewById(R.id.LinearForgot);
        mEmail = findViewById(R.id.email_from_forgot);
        mSendButton = findViewById(R.id.send_recovery_button);
        mReturnButton = findViewById(R.id.return_from_forgot);

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
                finish();
                returnMethod();

            }
        });
    }

    //To make onBackPressed override possible for that ''triangel'' shape button.
    private void returnMethod(){
        Intent returnToLogin = new Intent(ForgotActivity.this, LogInActivity.class);
        startActivity(returnToLogin);
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
        String email = mEmail.getText().toString();

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


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserForgotPassword(email);
            mAuthTask.execute((Void) null);
        }

    }

    /**
     * Represents an asynchronous forgot password task used to recover a new
     * password for the user.
     */
    class UserForgotPassword extends AsyncTask<Void, Void, Boolean> {
        String email;


        UserForgotPassword(String recipientEmail){
            email = recipientEmail;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return Database.getInstance().forgotPassword(email);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mAuthTask = null;
            if (success) {
                finish();
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
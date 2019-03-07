package com.example.androidchess;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgotActivity extends AppCompatActivity {
    /**
     * Keep track of the forgot task to ensure we can cancel it if requested.
     */
    protected UserForgotPassword mAuthTask = null;

    //UI
    protected Button mSend;
    protected Button mReturn;
    private EditText mEmail;
    private EditText mUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mEmail = findViewById(R.id.email_from_forgot);
        mSend = findViewById(R.id.send_recovery_button);
        mUsername = findViewById(R.id.user_from_forgot);
        mReturn = findViewById(R.id.return_from_forgot);

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnToLogin = new Intent(ForgotActivity.this, LogInActivity.class);
                startActivity(returnToLogin);

            }
        });

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverPassword();
            }
        });
    }

    public void recoverPassword(){
        String email = mEmail.getText().toString();
        String user = mUsername.getText().toString();
        mAuthTask = new UserForgotPassword(email, user);

    }

    class UserForgotPassword extends AsyncTask<Void, Void, Boolean> {
        String email;
        String user;


        UserForgotPassword(String recipientEmail, String username){
            email = recipientEmail;
            user = username;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            return Database.getInstance().forgotPassword(email, user);
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
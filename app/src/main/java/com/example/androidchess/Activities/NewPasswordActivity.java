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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.androidchess.Database.Database;
import com.example.androidchess.R;

public class NewPasswordActivity extends AppCompatActivity {

    //Enables Log comments
    private final static String TAG = "NewPasswordActivity";

    protected UserUpdatePassword mAuthTask = null;

    //UI
    private LinearLayout mLayoutNew;
    protected Button mSendButton;
    private EditText mPassword;
    private EditText mPasswordConfirm;

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        //Set up layout
        getSupportActionBar().hide();
        mLayoutNew = findViewById(R.id.LinearNewPassword);
        mPassword = findViewById(R.id.new_password_text);
        mSendButton = findViewById(R.id.send_update_button);
        mPasswordConfirm = findViewById(R.id.new_password_text2);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mLayoutNew.getWindowToken(), 0);
                } catch (Exception e){
                    e.printStackTrace();
                }
                recoverPassword();
            }
        });

    }

    public void recoverPassword(){
        if (mAuthTask != null) {
            return;
        }
        mPassword.setError(null);
        mPasswordConfirm.setError(null);
        String password = mPassword.getText().toString();
        String passwordConfirm = mPasswordConfirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a password email.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password) &&
                passwordConfirm.equals(password) && password.equals(passwordConfirm)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordConfirm.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordConfirm;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserUpdatePassword(password);
            mAuthTask.execute((Void) null);
        }

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3 && password.length() < 15;
    }


    /**
     * Represents an asynchronous forgot password task used to recover a new
     * password for the user.
     */
    class UserUpdatePassword extends AsyncTask<Void, Void, Boolean> {
        String password;

        UserUpdatePassword(String password1){
            password = password1;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return Database.getInstance().updatePassword(password);
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mAuthTask = null;
            if (success) {
                finish();
                Intent returnTo = new Intent(NewPasswordActivity.this, MenuActivity.class);
                Toast.makeText(getApplicationContext(),"Update success", Toast.LENGTH_SHORT).show();
                startActivity(returnTo);
                Log.d(TAG,"Update Success");
            } else {
                Toast.makeText(getApplicationContext(),"Update failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}

package com.example.androidchess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    //TAG
    private static final String TAG = "RegisterActivity";

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
        });


    }

}

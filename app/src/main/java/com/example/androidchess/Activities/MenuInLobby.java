package com.example.androidchess.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.androidchess.Activities.MenuActivity;
import com.example.androidchess.R;

public class MenuInLobby extends AppCompatActivity implements View.OnClickListener {

    private Button start;
    private Button returntoLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__inlobby);


        returntoLobby = (Button) findViewById(R.id.ButtonReturn);
        start = (Button) findViewById(R.id.buttonStart);
        returntoLobby.setOnClickListener(this);
        start.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonStart: {
                // do something for button 1 click
                break;
            }

            case R.id.ButtonReturn: {
                Intent myIntent = new Intent(this, MenuActivity.class);
                startActivity(myIntent);
                break;
            }

            //.... etc
        }


    }
}


package com.example.androidchess.chessboard;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import com.example.androidchess.R;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        GameInfo.get().whiteTurn = true;

        ConstraintLayout boardContainer = findViewById(R.id.boardContainer);

        GameInfo.get().board = new Board(8, this, boardContainer);
    }
}

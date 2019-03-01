package com.example.androidchess;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

public class Game extends AppCompatActivity {

    GridView board;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        board = findViewById(R.id.board);
        imageAdapter = new ImageAdapter(this);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        int width = metrics.widthPixels;

        ImageView boardImg = findViewById(R.id.boardImg);
        boardImg.getLayoutParams().width = width;
        boardImg.getLayoutParams().height = width;

        board.setAdapter(imageAdapter);

        board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                swap(position);
            }
        });

    }

    int swapCounter = 0;
    int firstPos;
    //int firstID;

    public void swap(int pos) {
        if (++swapCounter == 1) {
            //firstID = imageAdapter.pieceIds[pos];
            firstPos = pos;
        }
        else if (swapCounter == 2) {
            int temp = imageAdapter.pieceIds[pos];
            imageAdapter.pieceIds[pos] = imageAdapter.pieceIds[firstPos];
            imageAdapter.pieceIds[firstPos] = temp;

            imageAdapter.notifyDataSetChanged();
            board.invalidateViews();

            swapCounter = 0;
        }

    }



}


package com.example.androidchess.chessboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.androidchess.R;

public class Game extends AppCompatActivity {

    GridView board;
    ImageAdapter imageAdapter;
    //ImageView[] pieces = new ImageView[64];

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
                possibleMoves(position);
            }
        });

    }

    public void printArray(ImageView[] pieces, String tag) {
        Log.d("gridview size", Integer.toString(board.getChildCount()));
        String toString = "";
        for (int i = 0; i < pieces.length; i++) {
            toString += "[" + pieces[i].getTag() + "], ";
        }
        //toString = pieces.toString();
        Log.d(tag, toString);
    }

    public boolean validCell(int position) {
        if (((ImageView)board.getItemAtPosition(position)).getTag().toString().charAt(0) != 't')
            return true;
        else
            return false;
    }

    public void bishopCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int i = x;
        int n = y;
        boolean obstacle = false;
        while (i < 8 && n < 8 && !obstacle) {
            int t = i + 8*n;
            if (((ImageView)board.getItemAtPosition(position)).getTag().toString().charAt(0) != 't') {
                obstacle = true;
            }
            else {
                ((ImageView)board.getItemAtPosition(position)).setAlpha(1f);
            }
            i++;
            n++;
        }

        i = x;
        n = y;
        while (i < 0 && n < 0 && !obstacle) {
            int t = i + 8*n;
            if (((ImageView)board.getItemAtPosition(position)).getTag().toString().charAt(0) != 't') {
                obstacle = true;
            }
            else {
                ((ImageView)board.getItemAtPosition(position)).setAlpha(1f);
            }
            i--;
            n--;
        }

        i = x;
        n = y;
        while (i < 0 && n < 0 && !obstacle) {
            int t = i + 8*n;
            if (((ImageView)board.getItemAtPosition(position)).getTag().toString().charAt(0) != 't') {
                obstacle = true;
            }
            else {
                ((ImageView)board.getItemAtPosition(position)).setAlpha(1f);
            }
            i++;
            n--;
        }

        i = x;
        n = y;
        while (i < 0 && n < 0 && !obstacle) {
            int t = i + 8*n;
            if (((ImageView)board.getItemAtPosition(position)).getTag().toString().charAt(0) != 't') {
                obstacle = true;
            }
            else {
                ((ImageView)board.getItemAtPosition(position)).setAlpha(1f);
            }
            i--;
            n++;
        }
    }

    public void rookCheck(int position) {

    }

    public void knightCheck(int position) {

    }

    public void pawnCheck(int position) {
        
    }

    public void kingCheck(int position) {

    }

    public void possibleMoves(int position) {
        switch (((ImageView)board.getItemAtPosition(position)).getTag().toString().charAt(0)) {
            // queen
            case 'q':
                bishopCheck(position);
                rookCheck(position);
                break;
            // king
            case 'k':
                kingCheck(position);
                break;
            // rook
            case 'r':
                rookCheck(position);
                break;
            // knight
            case 'n':
                knightCheck(position);
                break;
            // bishop
            case 'b':
                bishopCheck(position);
                break;
            // pawn
            case 'p':
                pawnCheck(position);
                break;
        }
    }

    public boolean legalMove() {

        return true;
    }

    int swapCounter = 0;
    int firstPos;
    //int firstID;

    public void swap(int pos) {
        if (++swapCounter == 1) {
            //firstID = imageAdapter.pieceIds[pos];
            firstPos = pos;
        } else if (swapCounter == 2) {
            int temp = imageAdapter.pieceIds[pos];
            imageAdapter.pieceIds[pos] = imageAdapter.pieceIds[firstPos];
            imageAdapter.pieceIds[firstPos] = temp;

            imageAdapter.notifyDataSetChanged();
            board.invalidateViews();

            swapCounter = 0;
        }

    }


}


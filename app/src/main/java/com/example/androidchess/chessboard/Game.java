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
    public static boolean[] possibleMoves = new boolean[64];
    //ImageView[] squares = new ImageView[64];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        resetPossibleMoves();
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

    public void resetPossibleMoves() {
        for (int i = 0; i<64; i++)
            possibleMoves[i] = false;
    }

    public void printArray(ImageView[] pieces, String tag) {
        Log.d("gridview size", Integer.toString(board.getChildCount()));
        String toString = "";
        for (int i = 0; i < pieces.length; i++) {
            toString += "[" + pieces[i].getTag() + "], ";
        }
        //toString = squares.toString();
        Log.d(tag, toString);
    }

    public boolean validCell(int position) {
        if (getCell(position).getTag().toString().charAt(0) != 't')
            return true;
        else
            return false;
    }

    public void bishopCheck(int position) {
        int x = position % 8;
        int y = position / 8;

        // diagonal towards bottom right
        int i = x+1;
        int n = y+1;
        boolean obstacle = false;
        while (i < 8 && n < 8 && !obstacle) {
            int currentPos = i + 8*n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i+n + ", " + getFilename(position));
                obstacle = true;
            }
            possibleMoves[currentPos] = true;
            i++;
            n++;

        }

        // diagonal towards top left
        i = x-1;
        n = y-1;
        obstacle = false;
        while (i >= 0 && n >= 0 && !obstacle) {

            // position in array currently getting looked at
            int currentPos = i + 8*n;

            if (getFilename(currentPos).charAt(0) != 't') {
                Log.d("obstacle i- n-", "true @" + i+n + ", " + getFilename(position));
                obstacle = true;
            }

            possibleMoves[currentPos] = true;

            i--;
            n--;
        }

        // diagonal towards top right
        i = x+1;
        n = y-1;
        obstacle = false;
        while (i < 8 && n >= 0 && !obstacle) {
            int currentPos = i + 8*n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n-", "true @" + i+n + ", " + getFilename(position));
                obstacle = true;
            }

            possibleMoves[currentPos] = true;

            i++;
            n--;
        }

        // diagonal toward bottom left
        i = x-1;
        n = y+1;
        obstacle = false;
        while (i >= 0 && n < 8 && !obstacle) {
            int currentPos = i + 8*n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i- n+", "true @" + i+n + ", " + getFilename(position));
                obstacle = true;
            }

            possibleMoves[currentPos] = true;
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
        switch (getFilename(position).charAt(0)) {
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
                Log.d("switch", "entered case b");
                break;
            // pawn
            case 'p':
                pawnCheck(position);
                break;
        }
        imageAdapter.currentCells = 0;
        imageAdapter.notifyDataSetChanged();
        board.invalidateViews();
    }

    public boolean legalMove(int position) {
        if (possibleMoves[position])
            return true;
        else
            return false;
    }

    int swapCounter = 0;
    int firstPos;
    //int firstID;

    //TODO not currently checking for enemy or friendly peices
    public void swap(int position) {

        if (++swapCounter == 1 && getFilename(position).charAt(0) != 't' ) {
            firstPos = position;
            possibleMoves(position);
        }

        // && legalMove(position)
        else if (swapCounter == 2 ) {
            int temp = imageAdapter.pieceIds[position];

            imageAdapter.pieceIds[position] = imageAdapter.pieceIds[firstPos];

            imageAdapter.pieceIds[firstPos] = temp;

            imageAdapter.currentCells = 0;
            imageAdapter.notifyDataSetChanged();
            board.invalidateViews();

            swapCounter = 0;
            Log.d("swap", getFilename(firstPos) + ", " + getFilename(position));
            resetPossibleMoves();
        }
        else {
            swapCounter = 0;
        }

        print2DArray();

    }

    public void print2DArray() {
        Log.d("", "----------------");
        for (int n = 0; n < 8; n++) {
            String row = "";
            for (int i = 0; i < 8; i++) {
                if (Game.possibleMoves[i+n*8])
                    row += "1";

                else
                    row += "0";
            }
            Log.d("", row+ "..." + n);
        }
    }

    public ImageView getCell(int position) {
        return ((ImageView)board.getItemAtPosition(position));
    }

    public String getFilename(int position) {
        String fileName = getCell(position).getResources().getResourceName(imageAdapter.pieceIds[position]);
        fileName = fileName.charAt(fileName.length()-2) + "" + fileName.charAt(fileName.length()-1);
        Log.d("filename", fileName);
        return fileName;
    }

}


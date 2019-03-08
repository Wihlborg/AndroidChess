package com.example.androidchess.chessboard.pieces;

import android.util.Log;
import com.example.androidchess.R;

import static com.example.androidchess.chessboard.Game.getCell;
import static com.example.androidchess.chessboard.Game.getFilename;
import static com.example.androidchess.chessboard.Game.possibleMoves;

public class Rook {

    public void rookCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        if (getFilename(currentPos).charAt(1) == 'w') {
            colorRookCheck(position, 'w');
        } else {
            colorRookCheck(position, 'b');
        }
    }


    public void colorRookCheck(int position, char color) {
        int x = position % 8;
        int y = position / 8;
        int i = x + 1;
        int n = y;
        boolean obstacle = false;

        while (i < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                possibleMoves[currentPos] = true;
            }
            i++;

        }

        i = x;
        n = y + 1;
        obstacle = false;
        while (n < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i n+", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                possibleMoves[currentPos] = true;
            }
            n++;

        }

        i = x;
        n = y - 1;
        obstacle = false;
        while (n >= 0 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i n-", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                Log.d("boolean", "true" + i+n);
                possibleMoves[currentPos] = true;
            }
            n--;

        }

        i = x - 1;
        n = y;
        obstacle = false;
        while (i >= 0 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {
                Log.d("obstacle i- n", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                possibleMoves[currentPos] = true;
            }
            i--;
        }
    }

}

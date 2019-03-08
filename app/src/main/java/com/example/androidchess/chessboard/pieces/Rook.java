package com.example.androidchess.chessboard.pieces;

import android.util.Log;

import static com.example.androidchess.chessboard.Game.getFilename;
import static com.example.androidchess.chessboard.Game.possibleMoves;

public class Rook {

    public static void rookCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int i = x + 1;
        int n = y;
        boolean obstacle = false;

        while (i < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            possibleMoves[currentPos] = true;
            i++;

        }

        i = x;
        n = y + 1;
        obstacle = false;
        while (n < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            possibleMoves[currentPos] = true;

            n++;

        }

        i = x;
        n = y - 1;
        obstacle = false;
        while (n >= 0 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            possibleMoves[currentPos] = true;

            n--;

        }

        i = x - 1;
        n = y;
        obstacle = false;
        while (i >= 0 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            possibleMoves[currentPos] = true;
            i--;
        }
    }
}

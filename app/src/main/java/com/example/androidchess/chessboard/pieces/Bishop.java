package com.example.androidchess.chessboard.pieces;

import android.util.Log;

import static com.example.androidchess.chessboard.Game.getFilename;
import static com.example.androidchess.chessboard.Game.possibleMoves;

public class Bishop {

    public static void bishopCheck(int position) {
        int x = position % 8;
        int y = position / 8;

        // diagonal towards bottom right
        int i = x + 1;
        int n = y + 1;
        boolean obstacle = false;
        while (i < 8 && n < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            possibleMoves[currentPos] = true;
            i++;
            n++;

        }

        // diagonal towards top left
        i = x - 1;
        n = y - 1;
        obstacle = false;
        while (i >= 0 && n >= 0 && !obstacle) {

            // position in array currently getting looked at
            int currentPos = i + 8 * n;

            if (getFilename(currentPos).charAt(0) != 't') {
                Log.d("obstacle i- n-", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }

            possibleMoves[currentPos] = true;

            i--;
            n--;
        }

        // diagonal towards top right
        i = x + 1;
        n = y - 1;
        obstacle = false;
        while (i < 8 && n >= 0 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n-", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }

            possibleMoves[currentPos] = true;

            i++;
            n--;
        }

        // diagonal toward bottom left
        i = x - 1;
        n = y + 1;
        obstacle = false;
        while (i >= 0 && n < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i- n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }

            possibleMoves[currentPos] = true;
            i--;
            n++;
        }
    }


}

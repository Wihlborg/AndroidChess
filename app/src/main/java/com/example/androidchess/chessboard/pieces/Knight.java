package com.example.androidchess.chessboard.pieces;

import android.util.Log;

import static com.example.androidchess.chessboard.Game.getFilename;
import static com.example.androidchess.chessboard.Game.possibleMoves;

public class Knight {

    public static void knightCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        if (getFilename(currentPos).charAt(1) == 'w') {
            colorKnightCheck(position, 'w');
        } else {
            colorKnightCheck(position, 'b');
        }
    }

    public static void colorKnightCheck(int position, char color) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        currentPos = (x + 2) + (8 * (y + 1));
        if (x + 2 < 8 && y + 1 < 8 && getFilename(currentPos).charAt(1) != color) {
            Log.d("x+2,y+1", currentPos + "");
            possibleMoves[currentPos] = true;
        }

        currentPos = (x + 1) + (8 * (y + 2));
        if (x + 1 < 8 && y + 2 < 8 && getFilename(currentPos).charAt(1) != color) {
            Log.d("x+1, y+2", currentPos + "");
            possibleMoves[currentPos] = true;
        }

        currentPos = (x - 1) + (8 * (y + 2));
        if (x - 1 >= 0 && y + 2 < 8 && getFilename(currentPos).charAt(1) != color) {
            Log.d("x-1, y+2", currentPos + "");
            possibleMoves[currentPos] = true;
        }

        currentPos = (x - 2) + (8 * (y + 1));
        if (x - 2 >= 0 && y + 1 < 8 && getFilename(currentPos).charAt(1) != color) {
            Log.d("x-2, y+1", currentPos + "");
            possibleMoves[currentPos] = true;
        }

        currentPos = (x - 2) + (8 * (y - 1));
        if (x - 2 >= 0 && y - 1 >= 0 && getFilename(currentPos).charAt(1) != color) {
            Log.d("x-2, y-1", currentPos + "");
            possibleMoves[currentPos] = true;
        }

        currentPos = (x - 1) + (8 * (y - 2));
        if (x - 1 >= 0 && y - 2 >= 0 && getFilename(currentPos).charAt(1) != color) {
            Log.d("x-1, y-2", currentPos + "");
            possibleMoves[currentPos] = true;
        }

        currentPos = (x + 1) + (8 * (y - 2));
        if (x + 1 < 8 && y - 2 >= 0 && getFilename(currentPos).charAt(1) != color) {
            Log.d("x+1, y-2", currentPos + "");
            possibleMoves[currentPos] = true;
        }

        currentPos = (x + 2) + (8 * (y - 1));
        if (x + 2 < 8 && y - 1 >= 0 && getFilename(currentPos).charAt(1) != color) {
            Log.d("x+2, y-1", currentPos + "");
            possibleMoves[currentPos] = true;
        }
    }


}

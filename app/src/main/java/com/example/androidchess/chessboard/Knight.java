package com.example.androidchess.chessboard;

import android.util.Log;

public class Knight {
    static Game game=new Game();

    public static void knightCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        if (Game.getFilename(currentPos).charAt(1) == 'w') {
            colorKnightCheck(position, 'w');
        }
        else {
            colorKnightCheck(position, 'b');
        }

    }

    public static void colorKnightCheck(int position, char color) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        currentPos = (x + 2) + (8 * (y + 1));
        if (x+2 < 8 && y+1 < 8 && Game.getFilename(currentPos).charAt(1) != color) {
            Log.d("x+2,y+1" , currentPos+"");
            game.possibleMoves[currentPos] = true;
        }

        currentPos = (x + 1) + (8 * (y + 2));
        if (x+1 < 8 && y+2 < 8 && Game.getFilename(currentPos).charAt(1) != color) {
            Log.d("x+1, y+2" , currentPos+"");
            game.possibleMoves[currentPos] = true;
        }

        currentPos = (x - 1) + (8 * (y + 2));
        if (x-1 >= 0 && y+2 < 8 && Game.getFilename(currentPos).charAt(1) != color) {
            Log.d("x-1, y+2" , currentPos+"");
            game.possibleMoves[currentPos] = true;
        }

        currentPos = (x - 2) + (8 * (y + 1));
        if (x-2 >= 0 && y+1 < 8 && Game.getFilename(currentPos).charAt(1) != color) {
            Log.d("x-2, y+1" , currentPos+"");
            game.possibleMoves[currentPos] = true;
        }

        currentPos = (x - 2) + (8 * (y - 1));
        if (x-2 >= 0 && y-1 >= 0 && Game.getFilename(currentPos).charAt(1) != color) {
            Log.d("x-2, y-1" , currentPos+"");
            game.possibleMoves[currentPos] = true;
        }

        currentPos = (x - 1) + (8 * (y - 2));
        if (x-1 >= 0 && y-2 >= 0 && Game.getFilename(currentPos).charAt(1) != color) {
            Log.d("x-1, y-2" , currentPos+"");
            game.possibleMoves[currentPos] = true;
        }

        currentPos = (x + 1) + (8 * (y - 2));
        if (x+1 < 8 && y-2 >= 0 && Game.getFilename(currentPos).charAt(1) != color) {
            Log.d("x+1, y-2" , currentPos+"");
            game.possibleMoves[currentPos] = true;
        }

        currentPos = (x + 2) + (8 * (y - 1));
        if (x+2 < 8 && y-1 >= 0 && Game.getFilename(currentPos).charAt(1) != color) {
            Log.d("x+2, y-1" , currentPos+"");
            game.possibleMoves[currentPos] = true;
        }
    }






}

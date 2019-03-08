package com.example.androidchess.chessboard;

import android.util.Log;
public class Rook {

    static Game game=new Game();



    public static void rookCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int i = x + 1;
        int n = y;
        boolean obstacle = false;


        while (i < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (Game.getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + Game.getFilename(position));
                obstacle = true;
            }
            game.possibleMoves[currentPos] = true;
            i++;

        }

        i = x;
        n = y + 1;
        obstacle = false;
        while (n < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (Game.getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + Game.getFilename(position));
                obstacle = true;
            }
            game.possibleMoves[currentPos] = true;

            n++;

        }


        i = x;
        n = y - 1;
        obstacle = false;
        while (n >= 0 && !obstacle) {
            int currentPos = i + 8 * n;
            if (Game.getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + Game.getFilename(position));
                obstacle = true;
            }
            game.possibleMoves[currentPos] = true;

            n--;

        }

        i = x - 1;
        n = y;
        obstacle = false;
        while (i >= 0 && !obstacle) {
            int currentPos = i + 8 * n;
            if (Game.getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + Game.getFilename(position));
                obstacle = true;
            }
            game.possibleMoves[currentPos] = true;
            i--;
        }

    }








}

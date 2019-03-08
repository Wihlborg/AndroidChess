package com.example.androidchess.chessboard;

import android.util.Log;

public class Bishop {
    static Game game=new Game();

    public static void bishopCheck(int position) {
        int x = position % 8;
        int y = position / 8;

        // diagonal towards bottom right
        int i = x + 1;
        int n = y + 1;
        boolean obstacle = false;
        while (i < 8 && n < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (Game.getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n+", "true @" + i + n + ", " + Game.getFilename(position));
                obstacle = true;
            }
            game.possibleMoves[currentPos] = true;
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

            if (Game.getFilename(currentPos).charAt(0) != 't') {
                Log.d("obstacle i- n-", "true @" + i + n + ", " + Game.getFilename(position));
                obstacle = true;
            }

            game.possibleMoves[currentPos] = true;

            i--;
            n--;
        }

        // diagonal towards top right
        i = x + 1;
        n = y - 1;
        obstacle = false;
        while (i < 8 && n >= 0 && !obstacle) {
            int currentPos = i + 8 * n;
            if (Game.getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i+ n-", "true @" + i + n + ", " + Game.getFilename(position));
                obstacle = true;
            }

            game.possibleMoves[currentPos] = true;

            i++;
            n--;
        }

        // diagonal toward bottom left
        i = x - 1;
        n = y + 1;
        obstacle = false;
        while (i >= 0 && n < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (Game.getFilename(currentPos).charAt(0) != 't') {

                Log.d("obstacle i- n+", "true @" + i + n + ", " + Game.getFilename(position));
                obstacle = true;
            }

            game.possibleMoves[currentPos] = true;
            i--;
            n++;
        }
    }


}

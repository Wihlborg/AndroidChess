package com.example.androidchess.chessboard.pieces;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.example.androidchess.R;

import static com.example.androidchess.chessboard.Game.getCell;
import static com.example.androidchess.chessboard.Game.getFilename;
import static com.example.androidchess.chessboard.Game.possibleMoves;

public class Bishop {

    public void bishopCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        if (getFilename(currentPos).charAt(1) == 'w') {
            colorBishopCheck(position, 'w');
        } else {
            colorBishopCheck(position, 'b');
        }
    }

    public void colorBishopCheck(int position, char color) {
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
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                possibleMoves[currentPos] = true;
            }
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
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                possibleMoves[currentPos] = true;
            }
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
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                possibleMoves[currentPos] = true;
            }
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
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                possibleMoves[currentPos] = true;
            }
            i--;
            n++;
        }
    }


}

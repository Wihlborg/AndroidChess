package com.example.androidchess.chessboard.pieces;

import com.example.androidchess.R;

import java.util.ArrayList;

import static com.example.androidchess.chessboard.GameActivity.*;
import static com.example.androidchess.chessboard.pieces.King.kingSafety;

public class Rook {

    public void rookCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        if (getFilename(currentPos).charAt(1) == 'w' && whiteTurn) {
            colorRookCheck(position, 'w');
        } else if (getFilename(currentPos).charAt(1) == 'b' && !whiteTurn){
            colorRookCheck(position, 'b');
        }
    }


    public void colorRookCheck(int position, char color) {
        int x = position % 8;
        int y = position / 8;
        int i = x + 1;
        int n = y;
        boolean obstacle = false;
        int currentPos;

        while (i < 8 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                //Log.d("obstacle i+ n", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
            }
            i++;

        }

        i = x;
        n = y + 1;
        obstacle = false;
        while (n < 8 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                //Log.d("obstacle i n+", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
            }
            n++;

        }

        i = x;
        n = y - 1;
        obstacle = false;
        while (n >= 0 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                //Log.d("obstacle i n-", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                //Log.d("boolean", "true" + i+n);
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
            }
            n--;

        }

        i = x - 1;
        n = y;
        obstacle = false;
        while (i >= 0 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {
                //Log.d("obstacle i- n", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
            }
            i--;
        }
    }

    public void setSquareValue(int currentPos, char color) {
        if (color == 'w') {
            if (attackedSquares[currentPos] == 0)
                attackedSquares[currentPos] = 1;
            else if (attackedSquares[currentPos] == 2)
                attackedSquares[currentPos] = 3;
        }
        else {
            if (attackedSquares[currentPos] == 0)
                attackedSquares[currentPos] = 2;
            else if (attackedSquares[currentPos] == 1)
                attackedSquares[currentPos] = 3;
        }
    }

    public void setAttackedSquares(int position, char color) {
        int x = position % 8;
        int y = position / 8;
        int i = x + 1;
        int n = y;
        boolean obstacle = false;
        int currentPos;

        while (i < 8 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {
                //Log.d("obstacle i+ n", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            setSquareValue(currentPos, color);
            i++;

        }

        i = x;
        n = y + 1;
        obstacle = false;
        while (n < 8 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {
                //Log.d("obstacle i n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            setSquareValue(currentPos, color);
            n++;

        }

        i = x;
        n = y - 1;
        obstacle = false;
        while (n >= 0 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {
                //Log.d("obstacle i n-", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            setSquareValue(currentPos, color);
            n--;

        }

        i = x - 1;
        n = y;
        obstacle = false;
        while (i >= 0 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {
                //Log.d("obstacle i- n", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            setSquareValue(currentPos, color);
            i--;
        }
    }

    public void checkMateAttackSquares(int kingPos, int sourcePos) {
        int kx = kingPos % 8;
        int ky = kingPos / 8;

        int sx = sourcePos % 8;
        int sy = sourcePos / 8;

        int x = sourcePos % 8;
        int y = sourcePos / 8;
        kingAttacker[x + (8 * y)] = true;


        // if they are positioned on the same x axis
        if (kx == sx) {
            if (sy > ky) {
                y--;
                while (y >= 0 && y > ky) {
                    kingAttacker[x+(8*y)] = true;
                    y--;
                }
            }
            else {
                y++;
                while (y < 8 && y < ky) {
                    kingAttacker[x+(8*y)] = true;
                    y++;
                }
            }
        }
        // if they are positioned on the same y axis
        else {
            if (sx > kx) {
                x--;
                while (x >= 0 && x > kx) {
                    kingAttacker[x+(8*y)] = true;
                    x--;
                }
            }
            else {
                x++;
                while (x < 8 && x < kx) {
                    kingAttacker[x+(8*y)] = true;
                    x++;
                }
            }
        }
    }

    public ArrayList<Integer> getPossibleMoves(int position, char color){
        ArrayList<Integer> theMoves = new ArrayList<>();
        int x = position % 8;
        int y = position / 8;
        int i = x + 1;
        int n = y;
        boolean obstacle = false;
        int currentPos;

        while (i < 8 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                //Log.d("obstacle i+ n", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
                    theMoves.add(currentPos);
            }
            i++;

        }

        i = x;
        n = y + 1;
        obstacle = false;
        while (n < 8 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                //Log.d("obstacle i n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
                    theMoves.add(currentPos);
            }
            n++;

        }

        i = x;
        n = y - 1;
        obstacle = false;
        while (n >= 0 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {

                //Log.d("obstacle i n-", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                //Log.d("boolean", "true" + i+n);
                if (kingSafety(currentPos, position))
                    theMoves.add(currentPos);
            }
            n--;

        }

        i = x - 1;
        n = y;
        obstacle = false;
        while (i >= 0 && !obstacle) {
            currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {
                //Log.d("obstacle i- n", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
                    theMoves.add(currentPos);
            }
            i--;
        }


        return theMoves;
    }

}

package com.example.androidchess.chessboard.pieces;

import com.example.androidchess.R;

import static com.example.androidchess.chessboard.GameActivity.*;
import static com.example.androidchess.chessboard.pieces.King.kingSafety;

public class Bishop {

    public void bishopCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        if (getFilename(currentPos).charAt(1) == 'w' && whiteTurn) {
            colorBishopCheck(position, 'w');
        } else if (getFilename(currentPos).charAt(1) == 'b' && !whiteTurn) {
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
                //Log.d("obstacle i+ n+", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
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
                //Log.d("obstacle i- n-", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
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

                //Log.d("obstacle i+ n-", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
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

                //Log.d("obstacle i- n+", "true @" + i + n + ", " + getFilename(position));
                if (getFilename(currentPos).charAt(1) != color)
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                obstacle = true;
            }
            if (getFilename(currentPos).charAt(1) != color) {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
            }
            i--;
            n++;
        }
    }

    public void setSquareValue(int currentPos, char color) {
        if (color == 'w') {
            if (attackedSquares[currentPos] == 0)
                attackedSquares[currentPos] = 1;
            else if (attackedSquares[currentPos] == 2)
                attackedSquares[currentPos] = 3;
        } else {
            if (attackedSquares[currentPos] == 0)
                attackedSquares[currentPos] = 2;
            else if (attackedSquares[currentPos] == 1)
                attackedSquares[currentPos] = 3;
        }
    }

    public void setAttackingSquares(int position, char color) {
        int x = position % 8;
        int y = position / 8;

        // diagonal towards bottom right
        int i = x + 1;
        int n = y + 1;
        boolean obstacle = false;
        while (i < 8 && n < 8 && !obstacle) {
            int currentPos = i + 8 * n;
            if (getFilename(currentPos).charAt(0) != 't') {
                //Log.d("obstacle i+ n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            setSquareValue(currentPos, color);
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
                //Log.d("obstacle i- n-", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            setSquareValue(currentPos, color);
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
                //Log.d("obstacle i+ n-", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            setSquareValue(currentPos, color);
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
                //Log.d("obstacle i- n+", "true @" + i + n + ", " + getFilename(position));
                obstacle = true;
            }
            setSquareValue(currentPos, color);
            i--;
            n++;
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

        if (kx < sx && ky < sy) {
            x--;
            y--;
            while (x >= 0 && y >= 0 && x > kx && y > ky) {
                kingAttacker[x + (8 * y)] = true;
                x--;
                y--;
            }
        } else if (kx > sx && ky < sy) {
            x++;
            y--;
            while (x < 8 && y >= 0 && x < kx && y > ky) {
                kingAttacker[x + (8 * y)] = true;
                x++;
                y--;
            }
        } else if (kx > sx && ky > sy) {
            x++;
            y++;
            while (x < 8 && y < 8 && x < kx && y < ky) {
                kingAttacker[x + (8 * y)] = true;
                x++;
                y++;
            }
        } else if (kx < sx && ky > sy) {
            x--;
            y++;
            while (x >= 0 && y < 8 && x > kx && y < ky) {
                kingAttacker[x + (8 * y)] = true;
                x--;
                y++;
            }
        }
    }

}

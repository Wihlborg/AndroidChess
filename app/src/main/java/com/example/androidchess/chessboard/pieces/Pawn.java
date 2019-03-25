package com.example.androidchess.chessboard.pieces;

import com.example.androidchess.R;

import static com.example.androidchess.chessboard.GameActivity.*;
import static com.example.androidchess.chessboard.pieces.King.kingSafety;

public class Pawn {

    public void pawnCheck(int position) {
        if (getFilename(position).charAt(1) == 'w' && whiteTurn) {
            pawnCheckWhite(position);
        } else if (getFilename(position).charAt(1) == 'b' && !whiteTurn){
            pawnCheckBlack(position);
        }
    }

    public void pawnCheckWhite(int position) {
        int x = position % 8;
        int y = position / 8;

        int i = x;
        int n = y - 1;
        int currentPos;
        if (y == 6) {
            boolean obstacle = false;
            while (n >= 4 && !obstacle) {
                currentPos = i + 8 * n;
                if (getFilename(currentPos).charAt(0) != 't') {

                    obstacle = true;
                }
                if (getFilename(currentPos).charAt(0) == 't') {
                    possibleMoves[currentPos] = true;
                }
                n--;
            }
            i = x - 1;
            n = y - 1;
            currentPos = i + 8 * n;
            if (n >= 0 && i >= 0 && getFilename(currentPos).charAt(1) == 'b') {
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                possibleMoves[currentPos] = true;
            }
            i = x + 1;
            n = y - 1;
            currentPos = i + 8 * n;
            if (n >= 0 && i < 8 && getFilename(currentPos).charAt(1) == 'b') {
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                possibleMoves[currentPos] = true;
            }
        } else {

            currentPos = i + 8 * n;

            // 1 step forward
            if (n >= 0 && getFilename(currentPos).charAt(0) == 't') {
                possibleMoves[currentPos] = true;
            }
            i = x - 1;
            n = y - 1;
            currentPos = i + 8 * n;
            if (n >= 0 && i >= 0 && (getFilename(currentPos).charAt(1) == 'b' || currentPos == enPassantPos)) {
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                possibleMoves[currentPos] = true;
            }
            i = x + 1;
            n = y - 1;
            currentPos = i + 8 * n;
            if (n >= 0 && i < 8 && (getFilename(currentPos).charAt(1) == 'b' || currentPos == enPassantPos)) {
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
                possibleMoves[currentPos] = true;
            }
        }
    }

    public void pawnCheckBlack(int position) {
        int x = position % 8;
        int y = position / 8;

        int i = x;
        int n = y + 1;
        int currentPos;
        if (y == 1) {
            boolean obstacle = false;
            while (n <= 3 && !obstacle) {
                currentPos = i + 8 * n;
                if (getFilename(currentPos).charAt(0) != 't') {

                    obstacle = true;
                }
                if (getFilename(currentPos).charAt(0) == 't') {
                    if (kingSafety(currentPos, position))
                        possibleMoves[currentPos] = true;
                }
                n++;
            }
            i = x + 1;
            n = y + 1;
            currentPos = i + 8 * n;
            if (n < 8 && i < 8 && getFilename(currentPos).charAt(1) == 'w') {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
            i = x - 1;
            n = y + 1;
            currentPos = i + 8 * n;
            if (n < 8 && i >=0 && getFilename(currentPos).charAt(1) == 'w') {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
        } else {
            currentPos = i + 8 * n;
            if (n < 8 && getFilename(currentPos).charAt(0) == 't') {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
            }

            i = x + 1;
            n = y + 1;
            currentPos = i + 8 * n;
            if (n < 8 && i < 8 && (getFilename(currentPos).charAt(1) == 'w' || currentPos == enPassantPos)) {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
            i = x - 1;
            n = y + 1;
            currentPos = i + 8 * n;
            if (n < 8 && i >=0 && (getFilename(currentPos).charAt(1) == 'w' || currentPos == enPassantPos)) {
                if (kingSafety(currentPos, position))
                    possibleMoves[currentPos] = true;
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
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

    public void setAttackedSqueres(int position) {

        int x = position % 8;
        int y = position / 8;

        int i;
        int n;
        int currentPos;

        // white pawn
        if (getFilename(position).charAt(1) == 'w') {
            i = x - 1;
            n = y - 1;
            currentPos = i + 8 * n;
            if (n >= 0 && i >= 0) {
                setSquareValue(currentPos, 'w');
            }
            i = x + 1;
            n = y - 1;
            currentPos = i + 8 * n;
            if (n >= 0 && i < 8) {
                setSquareValue(currentPos, 'w');
            }
        }

        // black pawn
        else {
            i = x + 1;
            n = y + 1;
            currentPos = i + 8 * n;
            if (n < 8 && i < 8) {
                setSquareValue(currentPos, 'b');
            }
            i = x - 1;
            n = y + 1;
            currentPos = i + 8 * n;
            if (n < 8 && i >= 0) {
                setSquareValue(currentPos, 'b');
            }
        }
    }

    public void checkMateAttackSquares(int kingPos, int sourcePos) {
        int kx = kingPos % 8;
        //int ky = kingPos / 8;

        int sx = sourcePos % 8;
        //int sy = sourcePos / 8;

        int x = sourcePos % 8;
        int y = sourcePos / 8;
        kingAttacker[x + (8 * y)] = true;


        String imgName = getFilename(sourcePos);

        if (imgName.charAt(1) == 'w') {
            y--;
        } else {
            y++;
        }

        if (kx < sx) {
            x--;
            kingAttacker[x + (8 * y)] = true;
        }
        else {
            x++;
            kingAttacker[x + (8 * y)] = true;
        }
    }

}

package com.example.androidchess.chessboard.pieces;

import com.example.androidchess.R;

import java.util.ArrayList;

import static com.example.androidchess.chessboard.GameActivity.*;

public class Knight {

    public void knightCheck(int position) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        if (getFilename(currentPos).charAt(1) == 'w' && whiteTurn) {
            colorKnightCheck(position, 'w');
        } else if (getFilename(currentPos).charAt(1) == 'b' && !whiteTurn){
            colorKnightCheck(position, 'b');
        }
    }

    public void colorKnightCheck(int position, char color) {
        int x = position % 8;
        int y = position / 8;
        int currentPos;

        //System.out.println("-----------------");
        currentPos = (x + 2) + (8 * (y + 1));
        if (x + 2 < 8 && y + 1 < 8 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x+2,y+1", currentPos + "");
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                //System.out.println(getFilename(currentPos) + (1));
                if (!getFilename(currentPos).equals("ts"))
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
        }

        currentPos = (x + 1) + (8 * (y + 2));
        if (x + 1 < 8 && y + 2 < 8 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x+1, y+2", currentPos + "");
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                //System.out.println(getFilename(currentPos) + (2));
                if (!getFilename(currentPos).equals("ts"))
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }

        }

        currentPos = (x - 1) + (8 * (y + 2));
        if (x - 1 >= 0 && y + 2 < 8 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x-1, y+2", currentPos + "");
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                //System.out.println(getFilename(currentPos) + (3));
                if (!getFilename(currentPos).equals("ts"))
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
        }

        currentPos = (x - 2) + (8 * (y + 1));
        if (x - 2 >= 0 && y + 1 < 8 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x-2, y+1", currentPos + "");
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                //System.out.println(getFilename(currentPos) + (4));
                if (!getFilename(currentPos).equals("ts"))
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
        }

        currentPos = (x - 2) + (8 * (y - 1));
        if (x - 2 >= 0 && y - 1 >= 0 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x-2, y-1", currentPos + "");
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                //System.out.println(getFilename(currentPos) + (5));
                if (!getFilename(currentPos).equals("ts"))
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
        }

        currentPos = (x - 1) + (8 * (y - 2));
        if (x - 1 >= 0 && y - 2 >= 0 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x-1, y-2", currentPos + "");
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                //System.out.println(getFilename(currentPos) + (6));
                if (!getFilename(currentPos).equals("ts"))
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }
        }

        currentPos = (x + 1) + (8 * (y - 2));
        if (x + 1 < 8 && y - 2 >= 0 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x+1, y-2", currentPos + "");
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                //System.out.println(getFilename(currentPos) + (7));
                if (!getFilename(currentPos).equals("ts"))
                    getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            }

        }

        currentPos = (x + 2) + (8 * (y - 1));
        if (x + 2 < 8 && y - 1 >= 0 && getFilename(currentPos).charAt(1) != color) {

            //Log.d("x+2, y-1", currentPos + "");
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                //System.out.println(getFilename(currentPos) + (8));
                if (!getFilename(currentPos).equals("ts"))
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

    public void setAttackedSquares(int position, char color) {
        int x = position % 8;
        int y = position / 8;
        int currentPos = x + 8 * y;

        currentPos = (x + 2) + (8 * (y + 1));
        if (x + 2 < 8 && y + 1 < 8) {
            //Log.d("x+2,y+1", currentPos + "");
            setSquareValue(currentPos, color);
        }

        currentPos = (x + 1) + (8 * (y + 2));
        if (x + 1 < 8 && y + 2 < 8) {
            //Log.d("x+1, y+2", currentPos + "");
            setSquareValue(currentPos, color);
        }

        currentPos = (x - 1) + (8 * (y + 2));
        if (x - 1 >= 0 && y + 2 < 8) {
            //Log.d("x-1, y+2", currentPos + "");
            setSquareValue(currentPos, color);
        }

        currentPos = (x - 2) + (8 * (y + 1));
        if (x - 2 >= 0 && y + 1 < 8) {
            //Log.d("x-2, y+1", currentPos + "");
            setSquareValue(currentPos, color);
        }

        currentPos = (x - 2) + (8 * (y - 1));
        if (x - 2 >= 0 && y - 1 >= 0) {
            //Log.d("x-2, y-1", currentPos + "");
            setSquareValue(currentPos, color);
        }

        currentPos = (x - 1) + (8 * (y - 2));
        if (x - 1 >= 0 && y - 2 >= 0) {
            //Log.d("x-1, y-2", currentPos + "");
            setSquareValue(currentPos, color);
        }

        currentPos = (x + 1) + (8 * (y - 2));
        if (x + 1 < 8 && y - 2 >= 0) {
            //Log.d("x+1, y-2", currentPos + "");
            setSquareValue(currentPos, color);
        }

        currentPos = (x + 2) + (8 * (y - 1));
        if (x + 2 < 8 && y - 1 >= 0) {
            //Log.d("x+2, y-1", currentPos + "");
            setSquareValue(currentPos, color);
        }
    }

    public void checkMateAttackSquares(int kingPos, int sourcePos) {
        //int kx = kingPos % 8;
        //int ky = kingPos / 8;

        //int sx = sourcePos % 8;
        //int sy = sourcePos / 8;

        int x = sourcePos % 8;
        int y = sourcePos / 8;

        kingAttacker[x+(8*y)] = true;
    }

    public ArrayList<Integer> getPossibleMoves(int position, char color){
        ArrayList<Integer> theMoves = new ArrayList<>();


        int x = position % 8;
        int y = position / 8;
        int currentPos;

        //System.out.println("-----------------");
        currentPos = (x + 2) + (8 * (y + 1));
        if (x + 2 < 8 && y + 1 < 8 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x+2,y+1", currentPos + "");
            if (kingSafety(currentPos, position)) {
                theMoves.add(currentPos);
                //System.out.println(getFilename(currentPos) + (1));
            }
        }

        currentPos = (x + 1) + (8 * (y + 2));
        if (x + 1 < 8 && y + 2 < 8 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x+1, y+2", currentPos + "");
            if (kingSafety(currentPos, position)) {
                theMoves.add(currentPos);
                //System.out.println(getFilename(currentPos) + (2));
            }

        }

        currentPos = (x - 1) + (8 * (y + 2));
        if (x - 1 >= 0 && y + 2 < 8 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x-1, y+2", currentPos + "");
            if (kingSafety(currentPos, position)) {
                theMoves.add(currentPos);
                //System.out.println(getFilename(currentPos) + (3));
            }
        }

        currentPos = (x - 2) + (8 * (y + 1));
        if (x - 2 >= 0 && y + 1 < 8 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x-2, y+1", currentPos + "");
            if (kingSafety(currentPos, position)) {
                theMoves.add(currentPos);
                //System.out.println(getFilename(currentPos) + (4));
            }
        }

        currentPos = (x - 2) + (8 * (y - 1));
        if (x - 2 >= 0 && y - 1 >= 0 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x-2, y-1", currentPos + "");
            if (kingSafety(currentPos, position)) {
                theMoves.add(currentPos);
                //System.out.println(getFilename(currentPos) + (5));
            }
        }

        currentPos = (x - 1) + (8 * (y - 2));
        if (x - 1 >= 0 && y - 2 >= 0 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x-1, y-2", currentPos + "");
            if (kingSafety(currentPos, position)) {
                theMoves.add(currentPos);
                //System.out.println(getFilename(currentPos) + (6));
            }
        }

        currentPos = (x + 1) + (8 * (y - 2));
        if (x + 1 < 8 && y - 2 >= 0 && getFilename(currentPos).charAt(1) != color) {
            //Log.d("x+1, y-2", currentPos + "");
            if (kingSafety(currentPos, position)) {
                theMoves.add(currentPos);
                //System.out.println(getFilename(currentPos) + (7));
            }

        }

        currentPos = (x + 2) + (8 * (y - 1));
        if (x + 2 < 8 && y - 1 >= 0 && getFilename(currentPos).charAt(1) != color) {

            //Log.d("x+2, y-1", currentPos + "");
            if (kingSafety(currentPos, position)) {
                theMoves.add(currentPos);
                //System.out.println(getFilename(currentPos) + (8));
            }
        }


        return theMoves;
    }

}

package com.example.androidchess.chessboard.Pieces;

import android.graphics.Color;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.*;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        this.setWhite(isWhite);
        if (isWhite) {
            this.setID(R.drawable.nw);
        } else {
            this.setID(R.drawable.nb);
        }
    }

    public void findPossibleMove(YX currentPos, YX sourcePos) {
        Board board = GameInfo.get().board;

        if (board.getSquare(currentPos).hasPiece()) {
            if (board.getSquare(currentPos).getPiece().isWhite() != board.getSquare(sourcePos).getPiece().isWhite()) {
                if (kingSafety(currentPos, sourcePos)) {
                    GameInfo.get().possibleToMove(currentPos);
                    board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        }
        else {
            if (kingSafety(currentPos, sourcePos))
                GameInfo.get().possibleToMove(currentPos);
        }
    }

    @Override
    public void calcPossibleMoves(YX sourcePos) {
        int y = sourcePos.y;
        int x = sourcePos.x;

        //System.out.println("-----------------");
        YX currentPos = new YX(0, 0);

        currentPos.y = y + 1;
        currentPos.x = x + 2;
        if (x + 2 < 8 && y + 1 < 8) {
            findPossibleMove(currentPos, sourcePos);
        }

        currentPos.y = y + 2;
        currentPos.x = x + 1;
        if (x + 1 < 8 && y + 2 < 8) {
            findPossibleMove(currentPos, sourcePos);
        }

        currentPos.y = y + 2;
        currentPos.x = x - 1;
        if (x - 1 >= 0 && y + 2 < 8) {
            findPossibleMove(currentPos, sourcePos);
        }

        currentPos.y = y + 1;
        currentPos.x = x - 2;
        if (x - 2 >= 0 && y + 1 < 8) {
            findPossibleMove(currentPos, sourcePos);
        }


        currentPos.y = y - 1;
        currentPos.x = x - 2;
        if (x - 2 >= 0 && y - 1 >= 0) {
            findPossibleMove(currentPos, sourcePos);
        }

        currentPos.y = y - 2;
        currentPos.x = x - 1;
        if (x - 1 >= 0 && y - 2 >= 0) {
            findPossibleMove(currentPos, sourcePos);
        }

        currentPos.y = y - 2;
        currentPos.x = x + 1;
        if (x + 1 < 8 && y - 2 >= 0) {
            findPossibleMove(currentPos, sourcePos);
        }


        currentPos.y = y - 1;
        currentPos.x = x + 2;
        if (x + 2 < 8 && y - 1 >= 0) {
            findPossibleMove(currentPos, sourcePos);
        }
    }

    @Override
    public void calcAttackedSquares(YX sourcePos) {
        int y = sourcePos.y;
        int x = sourcePos.x;

        //System.out.println("-----------------");
        YX currentPos = new YX(0, 0);

        currentPos.y = y + 1;
        currentPos.x = x + 2;
        if (x + 2 < 8 && y + 1 < 8) {
            this.setSquareAttackValue(currentPos);
        }

        currentPos.y = y + 2;
        currentPos.x = x + 1;
        if (x + 1 < 8 && y + 2 < 8) {
            this.setSquareAttackValue(currentPos);
        }

        currentPos.y = y + 2;
        currentPos.x = x - 1;
        if (x - 1 >= 0 && y + 2 < 8) {
            this.setSquareAttackValue(currentPos);
        }

        currentPos.y = y + 1;
        currentPos.x = x - 2;
        if (x - 2 >= 0 && y + 1 < 8) {
            this.setSquareAttackValue(currentPos);
        }

        currentPos.y = y - 1;
        currentPos.x = x - 2;
        if (x - 2 >= 0 && y - 1 >= 0) {
            this.setSquareAttackValue(currentPos);
        }

        currentPos.y = y - 2;
        currentPos.x = x - 1;
        if (x - 1 >= 0 && y - 2 >= 0) {
            this.setSquareAttackValue(currentPos);
        }

        currentPos.y = y - 2;
        currentPos.x = x + 1;
        if (x + 1 < 8 && y - 2 >= 0) {
            this.setSquareAttackValue(currentPos);
        }

        currentPos.y = y - 1;
        currentPos.x = x + 2;
        if (x + 2 < 8 && y - 1 >= 0) {
            this.setSquareAttackValue(currentPos);
        }
    }

    @Override
    public void calcKingAttackingSquares() {

    }
}

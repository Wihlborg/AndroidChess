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

    public void findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        if (boardState.hasPiece(currentPos)){
            if (this.isWhite() != boardState.getPiece(currentPos).isWhite()){
                if (this.kingSafety(currentPos, sourcePos)){
                    GameInfo.get().possibleToMove(currentPos);
                    boardState.markPossibleCaptures(currentPos);
                }
            }
        } else {
            if (this.kingSafety(currentPos, sourcePos))
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
            findPossibleMove(currentPos, sourcePos, GameInfo.get().boardState);
        }

        currentPos.y = y + 2;
        currentPos.x = x + 1;
        if (x + 1 < 8 && y + 2 < 8) {
            findPossibleMove(currentPos, sourcePos, GameInfo.get().boardState);
        }

        currentPos.y = y + 2;
        currentPos.x = x - 1;
        if (x - 1 >= 0 && y + 2 < 8) {
            findPossibleMove(currentPos, sourcePos, GameInfo.get().boardState);
        }

        currentPos.y = y + 1;
        currentPos.x = x - 2;
        if (x - 2 >= 0 && y + 1 < 8) {
            findPossibleMove(currentPos, sourcePos, GameInfo.get().boardState);
        }


        currentPos.y = y - 1;
        currentPos.x = x - 2;
        if (x - 2 >= 0 && y - 1 >= 0) {
            findPossibleMove(currentPos, sourcePos, GameInfo.get().boardState);
        }

        currentPos.y = y - 2;
        currentPos.x = x - 1;
        if (x - 1 >= 0 && y - 2 >= 0) {
            findPossibleMove(currentPos, sourcePos, GameInfo.get().boardState);
        }

        currentPos.y = y - 2;
        currentPos.x = x + 1;
        if (x + 1 < 8 && y - 2 >= 0) {
            findPossibleMove(currentPos, sourcePos, GameInfo.get().boardState);
        }


        currentPos.y = y - 1;
        currentPos.x = x + 2;
        if (x + 2 < 8 && y - 1 >= 0) {
            findPossibleMove(currentPos, sourcePos, GameInfo.get().boardState);
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
    public void calcKingAttackingSquares(YX kingPos, YX sourcePos) {

    }
}

package com.example.androidchess.chessboard.Pieces;

import android.graphics.Color;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.*;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        if (isWhite) {
            this.setID(R.drawable.rw);
        }
        else {
            this.setID(R.drawable.rb);
        }
    }

    public boolean findPossibleMove(YX currentPos, YX sourcePos) {
        Board board = GameInfo.get().board;
        boolean obstacle = false;
        if (board.getSquare(currentPos).hasPiece()) {
            obstacle = true;
            if (board.getSquare(currentPos).getPiece().isWhite() == this.isWhite()) {
                if (kingSafety(currentPos, sourcePos)) {
                    GameInfo.get().possibleToMove(currentPos);
                    board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        }
        else {
            GameInfo.get().possibleToMove(currentPos);
        }
        return obstacle;
    }

    @Override
    public void calcPossibleMoves(YX sourcePos) {

        // checking right
        int y = sourcePos.y;
        int x = sourcePos.x + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y, x);
        while (x < 8 && !obstacle) {
            currentPos.x = x;
            obstacle = findPossibleMove(currentPos, sourcePos);
            x++;
        }

        // checking up
        y = sourcePos.y + 1;
        x = sourcePos.x;
        obstacle = false;
        while (y < 8 && !obstacle) {
            currentPos.y = y;
            obstacle = findPossibleMove(currentPos, sourcePos);
            y++;
        }

        // checking down
        y = sourcePos.y - 1;
        x = sourcePos.x;
        obstacle = false;
        while (y >= 0 && !obstacle) {
            currentPos.y = y;
            obstacle = findPossibleMove(currentPos, sourcePos);
            y--;
        }

        // checking left
        y = sourcePos.y;
        x = sourcePos.x - 1;
        obstacle = false;
        while (x >= 0 && !obstacle) {
            currentPos.x = x;
            obstacle = findPossibleMove(currentPos, sourcePos);
            x--;
        }
    }

    public boolean calcAttackSquare(YX currentPos, YX sourcePos) {
        boolean obstacle = false;
        Board board = GameInfo.get().board;

        if (board.getSquare(currentPos).hasPiece()) {
            obstacle = true;
            if((this.isWhite()) != board.getSquare(currentPos).getPiece().isWhite()) {
                if (kingSafety(currentPos, sourcePos)) {
                    setSquareAttackValue(currentPos);
                }
            }
        }
        else {
            if (kingSafety(currentPos, sourcePos))
                setSquareAttackValue(currentPos);
        }

        return obstacle;
    }

    @Override
    public void calcAttackedSquares(YX sourcePos) {

        // checking right
        int y = sourcePos.y;
        int x = sourcePos.x + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y, x);
        while (x < 8 && !obstacle) {
            currentPos.x = x;
            obstacle = calcAttackSquare(currentPos, sourcePos);
            x++;
        }

        // checking up
        y = sourcePos.y + 1;
        x = sourcePos.x;
        obstacle = false;
        while (y < 8 && !obstacle) {
            currentPos.y = y;
            obstacle = calcAttackSquare(currentPos, sourcePos);
            y++;
        }

        // checking down
        y = sourcePos.y - 1;
        x = sourcePos.x;
        obstacle = false;
        while (y >= 0 && !obstacle) {
            currentPos.y = y;
            obstacle = calcAttackSquare(currentPos, sourcePos);
            y--;
        }

        // checking left
        y = sourcePos.y;
        x = sourcePos.x - 1;
        obstacle = false;
        while (x >= 0 && !obstacle) {
            currentPos.x = x;
            obstacle = calcAttackSquare(currentPos, sourcePos);
            x--;
        }
    }

    @Override
    public void calcKingAttackingSquares(YX kingPos, YX sourcePos) {
        YX currentPos = new YX(sourcePos.y, sourcePos.x);

        GameInfo.get().setKingAttackTrue(currentPos);

        // if they are positioned on the same x axis
        if (kingPos.x == sourcePos.x) {
            if (sourcePos.y > kingPos.y) {
                currentPos.y--;
                while (currentPos.y >= 0 && currentPos.y > kingPos.y) {
                    GameInfo.get().setKingAttackTrue(currentPos);
                    currentPos.y--;
                }
            }
            else {
                currentPos.y++;
                while (currentPos.y < 8 && currentPos.y < kingPos.y) {
                    GameInfo.get().setKingAttackTrue(currentPos);
                    currentPos.y++;
                }
            }
        }
        // if they are positioned on the same y axis
        else {
            if (sourcePos.x > kingPos.x) {
                currentPos.x--;
                while (currentPos.x >= 0 && currentPos.x > kingPos.x) {
                    GameInfo.get().setKingAttackTrue(currentPos);
                    currentPos.x--;
                }
            }
            else {
                currentPos.x++;
                while (currentPos.x < 8 && currentPos.x < kingPos.x) {
                    GameInfo.get().setKingAttackTrue(currentPos);
                    currentPos.x++;
                }
            }
        }
    }
}

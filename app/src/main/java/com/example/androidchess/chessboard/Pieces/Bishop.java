package com.example.androidchess.chessboard.Pieces;

import android.graphics.Color;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.*;


public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        this.setWhite(isWhite);
        if (isWhite) {
            this.setID(R.drawable.bw);
        }
        else {
            this.setID(R.drawable.bb);
        }
    }

    private boolean findPossibleMove(YX currentPos, YX sourcePos) {
        boolean obstacle = false;
        Board board = GameInfo.get().board;
        // found a piece = cant search further in the same direction
        if (board.getSquare(currentPos).hasPiece()) {
            obstacle = true;
            if ((this.isWhite() != board.getSquare(currentPos).getPiece().isWhite())) {
                if (this.kingSafety(currentPos, sourcePos)) {
                    GameInfo.get().possibleToMove(currentPos);
                    board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#00FFFF"));
                }
            }
        }
        else {
            if (this.kingSafety(currentPos, sourcePos)) {
                GameInfo.get().possibleToMove(currentPos);
            }
        }
        return obstacle;
    }

    @Override
    public void calcPossibleMoves(YX sourcePos) {
        int sourceY = sourcePos.y;
        int sourceX = sourcePos.x;

        // diagonal towards bottom right
        int y = sourceY + 1;
        int x = sourceX + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y ,x);
        while (x < 8 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos);

            x++;
            y++;

        }

        // diagonal towards top left
        x = sourceX - 1;
        y = sourceY - 1;
        obstacle = false;
        while (x >= 0 && y >= 0 && !obstacle) {

            // position in array currently getting looked at
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos);

            x--;
            y--;
        }

        // diagonal towards top right
        x = sourceX + 1;
        y = sourceY - 1;
        obstacle = false;
        while (x < 8 && y >= 0 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos);

            x++;
            y--;
        }

        // diagonal towards bottom left
        x = sourceX - 1;
        y = sourceY + 1;
        obstacle = false;
        while (x >= 0 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos);

            x--;
            y++;
        }
    }

    private boolean calcAttackSquare(YX currentPos, YX sourcePos) {
        boolean obstacle = false;
        Board board = GameInfo.get().board;
        // found a piece = cant search further in the same direction
        if (board.getSquare(currentPos).hasPiece()) {
            obstacle = true;
            if ((this.isWhite() != board.getSquare(currentPos).getPiece().isWhite())) {
                if (this.kingSafety(currentPos, sourcePos)) {
                    this.setSquareAttackValue(currentPos);
                }
            }
        }
        else {
            if (this.kingSafety(currentPos, sourcePos)) {
                this.setSquareAttackValue(currentPos);
            }
        }
        return obstacle;
    }

    @Override
    public void calcAttackedSquares(YX sourcePos) {
        int sourceY = sourcePos.y;
        int sourceX = sourcePos.x;

        // diagonal towards bottom right
        int y = sourceY + 1;
        int x = sourceX + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y ,x);
        while (x < 8 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = calcAttackSquare(currentPos, sourcePos);

            x++;
            y++;

        }

        // diagonal towards top left
        x = sourceX - 1;
        y = sourceY - 1;
        obstacle = false;
        while (x >= 0 && y >= 0 && !obstacle) {

            // position in array currently getting looked at
            currentPos.y = y;
            currentPos.x = x;

            obstacle = calcAttackSquare(currentPos, sourcePos);

            x--;
            y--;
        }

        // diagonal towards top right
        x = sourceX + 1;
        y = sourceY - 1;
        obstacle = false;
        while (x < 8 && y >= 0 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = calcAttackSquare(currentPos, sourcePos);

            x++;
            y--;
        }

        // diagonal towards bottom left
        x = sourceX - 1;
        y = sourceY + 1;
        obstacle = false;
        while (x >= 0 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = calcAttackSquare(currentPos, sourcePos);

            x--;
            y++;
        }
    }

    @Override
    public void calcKingAttackingSquares() {
        
    }
}

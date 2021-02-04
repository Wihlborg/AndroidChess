package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.BoardState;
import com.example.androidchess.chessboard.Move;
import com.example.androidchess.chessboard.YX;

import org.jetbrains.annotations.NotNull;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        this.setWhite(isWhite);
    }

    public boolean findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        boolean obstacle = false;

        if (boardState.hasPiece(currentPos)) {
            obstacle = true;
            if (this.isWhite() != boardState.getPiece(currentPos).isWhite()) {
                if (this.kingSafety(currentPos, sourcePos, boardState)) {
                    this.addMove(new Move(sourcePos, currentPos, this));
                }
            }
        } else {
            if (this.kingSafety(currentPos, sourcePos, boardState)) {
                this.addMove(new Move(sourcePos, currentPos, this));
            }
        }
        return obstacle;
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {

        // checking right
        int y = sourcePos.y;
        int x = sourcePos.x + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y, x);
        while (x < 8 && !obstacle) {
            currentPos.x = x;
            obstacle = findPossibleMove(currentPos, sourcePos, boardState);
            x++;
        }

        // checking left
        x = sourcePos.x - 1;
        obstacle = false;
        while (x >= 0 && !obstacle) {
            currentPos.x = x;
            obstacle = findPossibleMove(currentPos, sourcePos, boardState);
            x--;
        }

        // checking up
        y = sourcePos.y + 1;
        x = sourcePos.x;
        currentPos.x = x;
        obstacle = false;
        while (y < 8 && !obstacle) {
            currentPos.y = y;
            obstacle = findPossibleMove(currentPos, sourcePos, boardState);
            y++;
        }

        // checking down
        y = sourcePos.y - 1;
        obstacle = false;
        while (y >= 0 && !obstacle) {
            currentPos.y = y;
            obstacle = findPossibleMove(currentPos, sourcePos, boardState);
            y--;
        }

    }

    @NotNull
    @Override
    public String toString() {
        return isWhite() ? "rw" : "rb";
    }
}

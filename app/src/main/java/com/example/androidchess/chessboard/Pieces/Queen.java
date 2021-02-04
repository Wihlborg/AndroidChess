package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.BoardState;
import com.example.androidchess.chessboard.Move;
import com.example.androidchess.chessboard.YX;

import org.jetbrains.annotations.NotNull;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        this.setWhite(isWhite);
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {
        bishopCalcPossibleMoves(sourcePos, boardState);
        rookCalcPossibleMoves(sourcePos, boardState);
    }

    private boolean findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        boolean obstacle = false;

        // found a piece = cant search further in the same direction
        if (boardState.hasPiece(currentPos)) {
            obstacle = true;
            if ((this.isWhite() != boardState.getPiece(currentPos).isWhite())) {
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

    private void bishopCalcPossibleMoves(YX sourcePos, BoardState boardState) {
        // diagonal towards bottom right
        int y = sourcePos.y + 1;
        int x = sourcePos.x + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y, x);
        while (x < 8 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos, boardState);

            x++;
            y++;

        }

        // diagonal towards top left
        x = sourcePos.x - 1;
        y = sourcePos.y - 1;
        obstacle = false;
        while (x >= 0 && y >= 0 && !obstacle) {

            // position in array currently getting looked at
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos, boardState);

            x--;
            y--;
        }

        // diagonal towards top right
        x = sourcePos.x + 1;
        y = sourcePos.y - 1;
        obstacle = false;
        while (x < 8 && y >= 0 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos, boardState);

            x++;
            y--;
        }

        // diagonal towards bottom left
        x = sourcePos.x - 1;
        y = sourcePos.y + 1;
        obstacle = false;
        while (x >= 0 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos, boardState);

            x--;
            y++;
        }
    }

    private void rookCalcPossibleMoves(YX sourcePos, BoardState boardState) {
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
        return isWhite() ? "qw" : "qb";
    }
}

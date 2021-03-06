package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.BoardState;
import com.example.androidchess.chessboard.Move;
import com.example.androidchess.chessboard.YX;

import org.jetbrains.annotations.NotNull;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        this.setWhite(isWhite);
    }

    private boolean findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        boolean obstacle = false;

        // found a piece = cant search further in the same direction
        if (boardState.hasPiece(currentPos)) {
            obstacle = true;
            if ((this.isWhite() != boardState.getPiece(currentPos).isWhite())) {
                if (this.kingSafety(currentPos, sourcePos, boardState)) {
                    this.addMove(new Move(sourcePos, currentPos, this));
                    //boardState.markPossibleCaptures(currentPos);
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

    @NotNull
    @Override
    public String toString() {
        return isWhite()? "bw":"bb";
    }
}

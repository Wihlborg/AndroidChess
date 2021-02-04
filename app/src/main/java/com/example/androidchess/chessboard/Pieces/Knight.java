package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.BoardState;
import com.example.androidchess.chessboard.Move;
import com.example.androidchess.chessboard.YX;

import org.jetbrains.annotations.NotNull;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        this.setWhite(isWhite);
    }

    public void findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        if (boardState.hasPiece(currentPos)) {
            if (this.isWhite() != boardState.getPiece(currentPos).isWhite()){
                if (this.kingSafety(currentPos, sourcePos, boardState)){
                    this.addMove(new Move(sourcePos, currentPos, this));
                }
            }
        } else {
            if (this.kingSafety(currentPos, sourcePos, boardState)) {
                this.addMove(new Move(sourcePos, currentPos, this));
            }
        }
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {
        int y = sourcePos.y;
        int x = sourcePos.x;

        //System.out.println("-----------------");
        YX currentPos = new YX(0, 0);

        currentPos.y = y + 2;
        currentPos.x = x + 1;
        if (currentPos.y < 8 && currentPos.x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y + 1;
        currentPos.x = x + 2;
        if (currentPos.y < 8 && currentPos.x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y - 1;
        currentPos.x = x + 2;
        if (currentPos.y >= 0 && currentPos.x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y - 2;
        currentPos.x = x + 1;
        if (currentPos.y >= 0 && currentPos.x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }


        currentPos.y = y - 2;
        currentPos.x = x - 1;
        if (currentPos.y >= 0 && currentPos.x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y - 1;
        currentPos.x = x - 2;
        if (currentPos.y >= 0 && currentPos.x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y + 1;
        currentPos.x = x - 2;
        if (currentPos.y < 8 && currentPos.x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y + 2;
        currentPos.x = x - 1;
        if (currentPos.y < 8 && currentPos.x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }
    }

    @NotNull
    @Override
    public String toString() {
        return isWhite() ? "nw" : "nb";
    }
}

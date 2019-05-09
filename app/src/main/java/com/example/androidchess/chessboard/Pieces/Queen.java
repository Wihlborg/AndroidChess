package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.*;

public class Queen extends Piece {

    Bishop bishop;
    Rook rook;

    public Queen(boolean isWhite) {
        this.setWhite(isWhite);
        bishop = new Bishop(this.isWhite());
        rook = new Rook(this.isWhite());
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {
        bishop.calcPossibleMoves(sourcePos, boardState);
        rook.calcPossibleMoves(sourcePos, boardState);
    }

    @Override
    public void calcAttackedSquares(YX sourcePos, BoardState boardState) {
        bishop.calcAttackedSquares(sourcePos, boardState);
        rook.calcAttackedSquares(sourcePos, boardState);
    }

    @Override
    public void calcKingAttackingSquares(YX kingPos, YX sourcePos, BoardState boardState) {
        bishop.calcKingAttackingSquares(kingPos, sourcePos, boardState);
        rook.calcKingAttackingSquares(kingPos, sourcePos, boardState);
    }

    @Override
    public String toString() {
        if (isWhite())
            return "qw";
        else
            return "qb";
    }
}

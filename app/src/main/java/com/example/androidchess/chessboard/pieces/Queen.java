package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.R;
import com.example.androidchess.chessboard.*;

public class Queen extends Piece {

    Bishop bishop;
    Rook rook;

    public Queen(boolean isWhite) {
        if (isWhite) {
            this.setID(R.drawable.qw);
        }
        else {
            this.setID(R.drawable.qb);
        }

        bishop = new Bishop(this.isWhite());
        rook = new Rook(this.isWhite());
    }

    @Override
    public void calcPossibleMoves(YX sourcePos) {
        bishop.calcPossibleMoves(sourcePos);
        rook.calcPossibleMoves(sourcePos);
    }

    @Override
    public void calcAttackedSquares(YX sourcePos) {
        bishop.calcAttackedSquares(sourcePos);
        rook.calcAttackedSquares(sourcePos);
    }

    @Override
    public void calcKingAttackingSquares() {
        bishop.calcKingAttackingSquares();
        rook.calcKingAttackingSquares();
    }
}

package com.example.androidchess.chessboard.Pieces;

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

    @Override
    public void calcPossibleMoves(YX sourcePos) {

    }

    @Override
    public void calcAttackedSquares(YX sourcePos) {

    }

    @Override
    public void calcKingAttackingSquares() {

    }
}

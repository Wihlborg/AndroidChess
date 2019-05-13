package com.example.androidchess.chessboard;

import android.content.Context;
import android.util.AttributeSet;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.Pieces.*;
import org.jetbrains.annotations.Nullable;

public class Square extends android.support.v7.widget.AppCompatImageView {

    //Piece piece;
    String coordinate;

    public Square(Context context, String coordinate) {
        super(context);
        this.coordinate = coordinate;
    }

    public Square(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Square(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    public boolean hasPiece() {
        if (this.piece == null)
            return false;
        else
            return true;
    }*/

    public void setPiece(@Nullable Piece piece) {

        if (piece != null) {

            if (piece instanceof Rook) {
                this.setImageResource(piece.isWhite() ? R.drawable.rw : R.drawable.rb);
            } else if (piece instanceof Queen) {
                this.setImageResource(piece.isWhite() ? R.drawable.qw : R.drawable.qb);
            } else if (piece instanceof Bishop) {
                this.setImageResource(piece.isWhite() ? R.drawable.bw : R.drawable.bb);
            } else if (piece instanceof Knight) {
                this.setImageResource(piece.isWhite() ? R.drawable.nw : R.drawable.nb);
            } else if (piece instanceof King) {
                this.setImageResource(piece.isWhite() ? R.drawable.kw : R.drawable.kb);
            } else if (piece instanceof Pawn) {
                this.setImageResource(piece.isWhite() ? R.drawable.pw : R.drawable.pb);
            }
        }
        else {
            this.setImageResource(0);
        }

    }

    /*
    public Piece getPiece() {
        return piece;
    }*/

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}

package com.example.androidchess.chessboard;

import android.content.Context;
import android.util.AttributeSet;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.Pieces.Piece;

public class Square extends android.support.v7.widget.AppCompatImageView {

    Piece piece;
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

    public boolean hasPiece() {
        if (piece == null)
            return false;
        else
            return true;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        String name;
        if (piece == null)
            name = "";
        else
            name = piece.toString();

        switch (name.charAt(0)) {
            case 'r':
                if (name.charAt(1) == 'w')
                    this.setImageResource(R.drawable.rw);
                else
                    this.setImageResource(R.drawable.rb);
                break;
            case 'n':
                if (name.charAt(1) == 'w')
                    this.setImageResource(R.drawable.nw);
                else
                    this.setImageResource(R.drawable.nb);
                break;
            case 'b':
                if (name.charAt(1) == 'w')
                    this.setImageResource(R.drawable.bw);
                else
                    this.setImageResource(R.drawable.bb);
                break;
            case 'q':
                if (name.charAt(1) == 'w')
                    this.setImageResource(R.drawable.qw);
                else
                    this.setImageResource(R.drawable.qb);
                break;
            case 'k':
                if (name.charAt(1) == 'w')
                    this.setImageResource(R.drawable.kw);
                else
                    this.setImageResource(R.drawable.kb);
                break;
            case 'p':
                if (name.charAt(1) == 'w')
                    this.setImageResource(R.drawable.pw);
                else
                    this.setImageResource(R.drawable.pb);
                break;
            default:
                this.setImageResource(R.drawable.ts);
                this.setAlpha(0f);
                break;
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}

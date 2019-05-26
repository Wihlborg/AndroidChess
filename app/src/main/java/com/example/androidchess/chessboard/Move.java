package com.example.androidchess.chessboard;

import com.example.androidchess.chessboard.Pieces.Piece;

public class Move {
    YX source;
    YX destination;
    Piece piece;

    public Move(YX source, YX destination, Piece piece) {
        this.source = new YX(source.y, source.x);
        this.destination = new YX(destination.y, destination.x);
        this.piece = piece;
    }

    @Override
    public String toString() {
        return "Move{" +
                "source=" + source +
                ", destination=" + destination +
                ", piece=" + piece +
                ", rID=" + System.identityHashCode(this) +
                '}';
    }
}

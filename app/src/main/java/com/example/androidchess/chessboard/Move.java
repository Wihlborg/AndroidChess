package com.example.androidchess.chessboard;

import com.example.androidchess.chessboard.Pieces.Piece;

public class Move {
    YX source;
    YX destination;
    Piece piece;

    public Move(YX source, YX destination, Piece piece) {
        this.source = source;
        this.destination = destination;
        this.piece = piece;
    }
}

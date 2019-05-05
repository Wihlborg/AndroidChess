package com.example.androidchess.chessboard;

public class Move {
    int startPos;
    int stopPos;
    String piece;

    public Move(int startPos, int stopPos, String piece) {
        this.startPos = startPos;
        this.stopPos = stopPos;
        this.piece = piece;
    }
}

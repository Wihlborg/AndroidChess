package com.example.androidchess.chessboard;

import com.example.androidchess.chessboard.Pieces.Piece;

public class Move {
    YX source;
    YX destination;
    Piece piece;

    public Move(YX source, YX destination, Piece piece) {
        this.source = new YX(source.y, source.x);
        /*
        if (source.y > 8 || source.y < 0)
            System.out.println("tets");
        if (destination.x > 8 || destination.x < 0)
            System.out.println("etsst");
        if (destination.y > 8 || destination.y < 0) {
            System.out.println(destination.y);
            System.out.println("errrror");
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            System.out.println(stackTraceElements[0].getMethodName());
            System.out.println(stackTraceElements[1].getMethodName());
            System.out.println(stackTraceElements[2].getMethodName());
            System.out.println(stackTraceElements[3].getMethodName());
            System.out.println(stackTraceElements[4].getMethodName());

        }*/

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

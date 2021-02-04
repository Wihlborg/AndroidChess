package com.example.androidchess.chessboard;

import org.jetbrains.annotations.NotNull;

public class YX {
    public int y;
    public int x;

    public YX(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public boolean equals(YX pos) {
        return (y == pos.y && x == pos.x);
    }

    @NotNull
    @Override
    public String toString() {
        return "YX{" +
                "y=" + y +
                ", x=" + x +
                //", rID=" + System.identityHashCode(this) +
                '}';
    }
}

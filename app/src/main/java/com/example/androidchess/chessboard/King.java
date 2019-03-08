package com.example.androidchess.chessboard;

public class King {
    static Game game=new Game();
    public void kingCheck(int position) {
        if (Game.getFilename(position).charAt(1) == 'w')
            colorKingCheck(position, 'w');
        else
            colorKingCheck(position, 'b');
    }

    public void colorKingCheck(int position, char color) {
        int x = position % 8;
        int y = position / 8;

        int i = x;
        int n = y-1;
        int currentPos = i +8*n;
        if (n >= 0 && Game.getFilename(currentPos).charAt(1) != color) {
            game.possibleMoves[currentPos] = true;
        }

        i = x+1;
        n = y-1;
        currentPos = i +8*n;
        if (i < 8 && n >= 0 && Game.getFilename(currentPos).charAt(1) != color) {
            game.possibleMoves[currentPos] = true;
        }

        i = x+1;
        n = y;
        currentPos = i +8*n;
        if (i < 8 && Game.getFilename(currentPos).charAt(1) != color) {
            game.possibleMoves[currentPos] = true;
        }

        i = x+1;
        n = y+1;
        currentPos = i +8*n;
        if (i < 8 && n < 8 && Game.getFilename(currentPos).charAt(1) != color) {
            game.possibleMoves[currentPos] = true;
        }

        i = x;
        n = y+1;
        currentPos = i +8*n;
        if (n < 8 && Game.getFilename(currentPos).charAt(1) != color) {
            game.possibleMoves[currentPos] = true;
        }

        i = x-1;
        n = y+1;
        currentPos = i +8*n;
        if (i >= 0 && n < 8 && Game.getFilename(currentPos).charAt(1) != color) {
            game.possibleMoves[currentPos] = true;
        }

        i = x-1;
        n = y;
        currentPos = i +8*n;
        if (i >= 0 && Game.getFilename(currentPos).charAt(1) != color) {
            game.possibleMoves[currentPos] = true;
        }

        i = x-1;
        n = y-1;
        currentPos = i +8*n;
        if (i >= 0 && n >= 0 && Game.getFilename(currentPos).charAt(1) != color) {
            game.possibleMoves[currentPos] = true;
        }

    }





}

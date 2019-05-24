package com.example.androidchess.chessboard;

import java.util.LinkedList;
import java.util.Random;

public class AI {
    public Move getHenkeFishMove(BoardState boardState) {
        Random random = new Random();
        LinkedList<Move> moves = boardState.getAllMoves();
        return moves.get(random.nextInt(moves.size()));
    }
}

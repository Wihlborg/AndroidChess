package com.example.androidchess;

import com.example.androidchess.chessboard.BoardState;
import com.example.androidchess.chessboard.Move;
import java.util.LinkedList;
import java.util.Random;

public class AI {
    public Move getHenkeFishMove(BoardState boardState) {
        Random random = new Random();
        LinkedList<Move> moves = boardState.getAllMoves();
        return moves.get(random.nextInt(moves.size()));
    }
}

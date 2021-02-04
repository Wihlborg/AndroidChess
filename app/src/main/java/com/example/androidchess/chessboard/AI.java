package com.example.androidchess.chessboard;

import java.util.List;
import java.util.Random;


/**
 * Class for AI, currently ony HenkeFish (Random move).
 */
public class AI {
    Random random = new Random();
    public Move getHenkeFishMove(BoardState boardState) {
        List<Move> moves = boardState.getAllMoves();
        return moves.get(random.nextInt(moves.size()));
    }
}

package com.example.androidchess.chessboard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Node {
    final int DEPTH = 3;
    ArrayList<Node> children;
    //Node parent;
    BoardState boardState;
    double value;

    // constructor for root
    public Node(String FENStr) {
        children = new ArrayList<>();
        //parent = null;
        BoardState boardState = new BoardState(FENStr);
        List<Move> list = boardState.getAllMoves();
        int depth = 0;
        for (Move move: list) {
            addChild(move, depth, boardState);
        }
    }

    public Node(BoardState bs, int depth) {
        children = new ArrayList<>();
        //this.parent = parent;
        List<Move> list = bs.getAllMoves();
        depth++;
        for (Move move: list) {
            addChild(move, depth, bs);
        }

        /*
        if (depth == DEPTH) {
            //value = Evaluation.getEvaluation(bs);
            boardState = bs;
        }
        else if (depth == 1) {
            boardState = bs;
        }
        */

        boardState = bs;
    }

    public void addChild(Move move, int depth, BoardState bs) {
        if (depth < DEPTH)
            children.add(new Node(new BoardState(bs, move), depth));
    }

    @NotNull
    @Override
    public String toString() {
        String str;

        str = "c = " + children.size() + ", v = " + value;

        return str;
    }
}
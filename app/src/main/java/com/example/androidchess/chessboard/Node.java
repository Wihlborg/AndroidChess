package com.example.androidchess.chessboard;

import java.util.LinkedList;

public class Node {
    final int DEPTH = 4;
    LinkedList<Node> children;
    Node parent;
    BoardState boardState;

    // constructor for root
    public Node(String FENStr) {
        children = new LinkedList<>();
        parent = null;
        BoardState boardState = new BoardState(FENStr);
        LinkedList<Move> list = boardState.getAllMoves();
        int depth = 0;
        for (Move move: list) {
            addChild(move, depth, boardState);
        }
    }

    public Node(Node parent, BoardState bs, int depth) {
        children = new LinkedList<>();
        this.parent = parent;
        LinkedList<Move> list = bs.getAllMoves();
        depth++;
        for (Move move: list) {
            addChild(move, depth, bs);
        }
        if (depth == DEPTH || depth == 1)
            boardState = bs;
    }

    public void addChild(Move move, int depth, BoardState bs) {
        if (depth < DEPTH)
            children.add(new Node(this, new BoardState(bs, move), depth));
    }

    public String toString() {
        String str;

        str = "c = " + children.size();

        return str;
    }
}
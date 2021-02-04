package com.example.androidchess.chessboard;

public class Minimax {

    static double minimax(Node node, int depth, boolean maxPlayer) {

        if (depth == 0 || node.children.size() == 0){
            return Evaluation.getEvaluation(node.boardState);
        }

        double value;
        if (maxPlayer) {
            value = -99999;
            for (Node child: node.children) {
                value = Math.max(value, minimax(child, depth - 1, false));
            }
        }
        else /* minplayer*/ {
            value = +99999;
            for (Node child: node.children) {
                value = Math.min(value, minimax(child, depth - 1, true));
            }
        }
        return value;

    }
}

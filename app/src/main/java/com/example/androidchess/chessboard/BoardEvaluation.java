package com.example.androidchess.chessboard;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardEvaluation {

    /*
    Konstanter för att kunna värdesätta en position
     */

    private static final int KING = 900;
    private static final int QUEEN = 90;
    private static final int ROOK = 50;
    private static final int BISHOP = 30; //Ändra till 32 enligt Fisher?
    private static final int KNIGHT = 30;
    private static final int PAWN = 10;

    private static final double[][] kingArray = {
            {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
            {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
            {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
            {-3.0, -4.0, -4.0, -5.0, -5.0, -4.0, -4.0, -3.0},
            {-2.0, -3.0, -3.0, -4.0, -4.0, -3.0, -3.0, -2.0},
            {-1.0, -2.0, -2.0, -2.0, -2.0, -2.0, -2.0, -1.0},
            { 2.0,  2.0,  0.0,  0.0,  0.0,  0.0,  2.0,  2.0},
            { 2.0,  3.0,  1.0,  0.0,  0.0,  1.0,  3.0,  2.0}
    };
    private static final double[][] queenArray = {
            {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
            {-1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
            {-1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
            {-0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
            { 0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
            {-1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
            {-1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0},
            {-2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
    };
    private static final double[][] rookArray = {
            { 0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
            { 0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
            {-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
            {-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
            {-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
            {-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
            {-0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
            { 0.0,  0.0,  0.0,  0.5,  0.5,  0.0,  0.0,  0.0}
    };
    private static final double[][] bishopArray = {
            {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
            {-1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
            {-1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0},
            {-1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0},
            {-1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
            {-1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
            {-1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0},
            {-2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
    };
    private static final double[][] knightArray = {
            {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
            {-4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0},
            {-3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0},
            {-3.0,  0.5,  1.5,  2.0,  2.0,  1.5,  0.5, -3.0},
            {-3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0},
            {-3.0,  0.5,  1.0,  1.5,  1.5,  1.0,  0.5, -3.0},
            {-4.0, -2.0,  0.0,  0.5,  0.5,  0.0, -2.0, -4.0},
            {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
    };

    private static final double[][] pawnArray = {
            { 0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
            { 5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
            { 1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
            { 0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5},
            { 0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0},
            { 0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5},
            { 0.5,  1.0,  1.0, -2.0, -2.0,  1.0,  1.0,  0.5},
            { 0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
    };


    /*
    Den här metoden tar en array av pjäser (strings) och positioner (ints) och ger en evaluering.
    Hashmaps är teoretiskt bättre men funkade inte som förväntat
    Bör ändras beroende på ändring av pos till 2d-array
     */
    public static double getEvaluation(ArrayList<String> pieces, ArrayList<Integer> positions){
        double evaluation = 0;
        for (int i = 0; i < pieces.size(); i++) {
            boolean isWhite = pieces.get(i).charAt(1) == 'w';
            int pos = positions.get(i);
            switch (pieces.get(i).charAt(0)){
                case 'q':
                    evaluation += isWhite ? QUEEN : -QUEEN;

                    if (!isWhite){
                        pos = 63-pos;
                        evaluation -= queenArray[pos/8][pos%8];
                    } else {
                        evaluation += queenArray[pos/8][pos%8];
                    }

                    break;
                case 'k':
                    evaluation += isWhite ? KING : -KING;

                    if (!isWhite){
                        pos = 63-pos;
                        evaluation -= kingArray[pos/8][pos%8];

                    } else {
                        evaluation += kingArray[pos/8][pos%8];
                    }
                    break;
                case 'r':
                    evaluation += isWhite ? ROOK : -ROOK;

                    if (!isWhite){
                        pos = 63-pos;
                        evaluation -= rookArray[pos/8][pos%8];
                    }
                    else {
                        evaluation += rookArray[pos/8][pos%8];
                    }
                    break;
                case 'b':
                    evaluation += isWhite ? BISHOP : -BISHOP;

                    if (!isWhite){
                        pos = 63-pos;
                        evaluation -= bishopArray[pos/8][pos%8];
                    } else {
                        evaluation += bishopArray[pos / 8][pos % 8];
                    }
                    break;
                case 'n':
                    evaluation += isWhite ? KNIGHT : -KNIGHT;

                    if (!isWhite){
                        pos = 63-pos;
                        evaluation -= knightArray[pos/8][pos%8];
                    } else {
                        evaluation += knightArray[pos / 8][pos % 8];
                    }
                    break;
                case 'p':
                    evaluation += isWhite ? PAWN : -PAWN;

                    if (!isWhite){
                        pos = 63-pos;
                        evaluation -= pawnArray[pos/8][pos%8];
                    } else {
                        evaluation += pawnArray[pos / 8][pos % 8];
                    }
                    break;
            }
        }

        return evaluation;
    }
}
package com.example.androidchess.chessboard;

import com.example.androidchess.chessboard.Pieces.*;

public class Evaluation {


    /*
    Konstanter för att kunna värdesätta en position
     */

    private static final int KING = 900;
    private static final int QUEEN = 90;
    private static final int ROOK = 50;
    private static final int BISHOP = 30; //Ändra till 32 enligt Fischer?
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

    public static double getEvaluation(BoardState boardState){
        double evaluation = 0;

        for(int i = 0; i < boardState.squares.length; i++){
            for (int j = 0; j < boardState.squares[i].length;j++){
                if (boardState.squares[i][j] != null){
                    if (boardState.squares[i][j] instanceof King){
                        if (boardState.squares[i][j].isWhite()){
                            evaluation += KING;
                            evaluation += kingArray[i][j];
                        } else {
                            evaluation -= KING;
                            evaluation -= kingArray[7-i][7-j];
                        }
                    } else if (boardState.squares[i][j] instanceof Queen){
                        if (boardState.squares[i][j].isWhite()){
                            evaluation += QUEEN;
                            evaluation += queenArray[i][j];
                        } else {
                            evaluation -= QUEEN;
                            evaluation -= queenArray[7-i][7-j];
                        }
                    } else if (boardState.squares[i][j] instanceof Rook){
                        if (boardState.squares[i][j].isWhite()){
                            evaluation += ROOK;
                            evaluation += rookArray[i][j];
                        } else {
                            evaluation -= ROOK;
                            evaluation -= rookArray[7-i][7-j];
                        }
                    } else if (boardState.squares[i][j] instanceof Bishop){
                        if (boardState.squares[i][j].isWhite()){
                            evaluation += BISHOP;
                            evaluation += bishopArray[i][j];
                        } else {
                            evaluation -= BISHOP;
                            evaluation -= bishopArray[7-i][7-j];
                        }
                    } else if (boardState.squares[i][j] instanceof Knight){
                        if (boardState.squares[i][j].isWhite()){
                            evaluation += KNIGHT;
                            evaluation += knightArray[i][j];
                        } else {
                            evaluation -= KNIGHT;
                            evaluation -= knightArray[7-i][7-j];
                        }
                    } else if (boardState.squares[i][j] instanceof Pawn){
                        if (boardState.squares[i][j].isWhite()){
                            evaluation += PAWN;
                            evaluation += pawnArray[i][j];
                        } else {
                            evaluation -= PAWN;
                            evaluation -= pawnArray[7-i][7-j];
                        }
                    }
                }
            }
        }

        return evaluation;
    }

    public static double getEvaluation(String fenString){
        return getEvaluation(new BoardState(fenString));
    }
}

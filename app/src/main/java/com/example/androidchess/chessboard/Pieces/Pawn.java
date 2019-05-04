package com.example.androidchess.chessboard.Pieces;

import android.graphics.Color;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.*;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        this.setWhite(isWhite);
        if (isWhite) {
            this.setID(R.drawable.pw);
        } else {
            this.setID(R.drawable.pb);
        }
    }

    @Override
    public void calcPossibleMoves(YX sourcePos) {

        Board board = GameInfo.get().board;

        YX currentPos = new YX(0, 0);

        // white pawn logic
        if (this.isWhite()) {
            // standing in default position possible to move more than 1 square
            if (sourcePos.y == 1) {
                boolean obstacle = false;
                int y = sourcePos.y + 1;
                // check for obstacles
                while (y <= 3 && !obstacle) {
                    currentPos.y = y;
                    if (board.getSquare(currentPos).hasPiece()) {
                        obstacle = true;
                    } else {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                        }
                    }
                    y++;
                }

                currentPos.y = sourcePos.y + 1;
                currentPos.x = sourcePos.x + 1;

                if (currentPos.x < 8) {
                    if (board.getSquare(currentPos).hasPiece() && !board.getSquare(currentPos).getPiece().isWhite()) {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                            board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                        }
                    }
                }

                // Y value is the same
                currentPos.x = sourcePos.x - 1;
                if (currentPos.x >= 0) {
                    if (board.getSquare(currentPos).hasPiece() && !board.getSquare(currentPos).getPiece().isWhite()) {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                            board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                        }
                    }
                }
            }
            // possible moves from non starting position
            else {

                // check above
                currentPos.y = sourcePos.y + 1;
                currentPos.x = sourcePos.x;

                if (currentPos.y < 8 && !board.getSquare(currentPos).hasPiece()) {
                    if (kingSafety(currentPos, sourcePos)) {
                        GameInfo.get().possibleToMove(currentPos);
                    }
                }

                // check diagonal left
                // Y value is the same
                currentPos.x = sourcePos.x - 1;

                if (currentPos.y < 8 && currentPos.x >= 0) {
                    if (board.getSquare(currentPos).hasPiece()) {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                            board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                        }
                    }
                }

                // check diagonal right
                // Y value is the same
                currentPos.x = sourcePos.x + 1;

                if (currentPos.y < 8 && currentPos.x < 8) {
                    if (board.getSquare(currentPos).hasPiece() || currentPos == GameInfo.get().enPassantPos) {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                            board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                        }
                    }
                }

            }
        }
        // black pawn logic
        else {
            // standing in default position possible to move more than 1 square
            if (sourcePos.y == 6) {
                boolean obstacle = false;
                int y = sourcePos.y - 1;
                // check for obstacles
                while (y >= 4 && !obstacle) {
                    currentPos.y = y;
                    if (board.getSquare(currentPos).hasPiece()) {
                        obstacle = true;
                    } else {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                        }
                    }
                    y--;
                }

                currentPos.y = sourcePos.y - 1;
                currentPos.x = sourcePos.x + 1;

                if (currentPos.x < 8) {
                    if (board.getSquare(currentPos).hasPiece() && !board.getSquare(currentPos).getPiece().isWhite()) {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                            board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                        }
                    }
                }

                // Y value is the same
                currentPos.x = sourcePos.x - 1;
                if (currentPos.x >= 0) {
                    if (board.getSquare(currentPos).hasPiece() && !board.getSquare(currentPos).getPiece().isWhite()) {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                            board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                        }
                    }
                }
            }
            // possible move from non starting position
            else {

                currentPos.y = sourcePos.y - 1;
                currentPos.x = sourcePos.x;

                if (currentPos.y >= 0 && !board.getSquare(currentPos).hasPiece()) {
                    if (kingSafety(currentPos, sourcePos)) {
                        GameInfo.get().possibleToMove(currentPos);
                    }
                }

                // check diagonal left
                // Y value is the same
                currentPos.x = sourcePos.x - 1;

                if (currentPos.y >= 0 && currentPos.x >= 0) {
                    if (board.getSquare(currentPos).hasPiece()) {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                            board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                        }
                    }
                }

                // check diagonal right
                // Y value is the same
                currentPos.x = sourcePos.x + 1;

                if (currentPos.y >= 0 && currentPos.x < 8) {
                    if (board.getSquare(currentPos).hasPiece() || currentPos == GameInfo.get().enPassantPos) {
                        if (kingSafety(currentPos, sourcePos)) {
                            GameInfo.get().possibleToMove(currentPos);
                            board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                        }
                    }
                }
            }
        }

    }

    @Override
    public void calcAttackedSquares(YX sourcePos) {

    }

    @Override
    public void calcKingAttackingSquares() {

    }
}

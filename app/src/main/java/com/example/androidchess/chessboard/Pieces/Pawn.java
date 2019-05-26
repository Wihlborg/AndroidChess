package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.BoardState;
import com.example.androidchess.chessboard.Move;
import com.example.androidchess.chessboard.YX;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        this.setWhite(isWhite);
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {

        YX currentPos = new YX(sourcePos.y, sourcePos.x);

        // white pawn logic
        if (this.isWhite())
            whitePawnLogic(sourcePos, currentPos, boardState);

            // black pawn logic
        else
            blackPawnLogic(sourcePos, currentPos, boardState);

    }

    private void whitePawnLogic(YX sourcePos, YX currentPos, BoardState boardState) {
        // standing in default position possible to move more than 1 square
        if (sourcePos.y == 1) {
            boolean obstacle = false;
            int y = sourcePos.y + 1;
            // check for obstacles
            while (y <= 3 && !obstacle) {
                currentPos.y = y;
                if (boardState.hasPiece(currentPos)) {
                    obstacle = true;
                } else {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
                y++;
            }

            // check right diagonal
            currentPos.y = sourcePos.y + 1;
            currentPos.x = sourcePos.x + 1;

            if (currentPos.x < 8) {
                if (boardState.hasPiece(currentPos) && !boardState.getPiece(currentPos).isWhite()) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
            }

            // check left diagonal
            // Y value is the same
            currentPos.x = sourcePos.x - 1;
            if (currentPos.x >= 0) {
                if (boardState.hasPiece(currentPos) && !boardState.getPiece(currentPos).isWhite()) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
            }
        }
        // possible moves from non starting position
        else {

            // check above
            currentPos.y = sourcePos.y + 1;
            currentPos.x = sourcePos.x;

            if (currentPos.y < 8 && !boardState.hasPiece(currentPos)) {
                if (kingSafety(currentPos, sourcePos, boardState)) {
                    if (currentPos.y == 7) {
                        this.addMove(new Move(sourcePos, currentPos, new Queen(this.isWhite())));
                        this.addMove(new Move(sourcePos, currentPos, new Bishop(this.isWhite())));
                        this.addMove(new Move(sourcePos, currentPos, new Rook(this.isWhite())));
                        this.addMove(new Move(sourcePos, currentPos, new Knight(this.isWhite())));
                    }
                    else
                        this.addMove(new Move(sourcePos, currentPos, this));
                }
            }

            // check diagonal left
            // Y value is the same
            currentPos.x = sourcePos.x - 1;

            if (currentPos.y < 8 && currentPos.x >= 0) {
                if (boardState.hasPiece(currentPos)) {
                    if (this.isWhite() != boardState.getPiece(currentPos).isWhite()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            if (currentPos.y == 7) {
                                this.addMove(new Move(sourcePos, currentPos, new Queen(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Bishop(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Rook(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Knight(this.isWhite())));
                            }
                            else
                                this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                }
                else if (currentPos.equals(boardState.getEnPassantPos())) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
            }

            // check diagonal right
            // Y value is the same
            currentPos.x = sourcePos.x + 1;

            if (currentPos.y < 8 && currentPos.x < 8) {
                if (boardState.hasPiece(currentPos)) {
                    if (this.isWhite() != boardState.getPiece(currentPos).isWhite()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            if (currentPos.y == 7) {
                                this.addMove(new Move(sourcePos, currentPos, new Queen(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Bishop(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Rook(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Knight(this.isWhite())));
                            }
                            else
                                this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                }
                else if (currentPos.equals(boardState.getEnPassantPos())) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
            }



        }
    }

    private void blackPawnLogic(YX sourcePos, YX currentPos, BoardState boardState) {
        // standing in default position possible to move more than 1 square
        if (sourcePos.y == 6) {
            boolean obstacle = false;
            int y = sourcePos.y - 1;
            // check for obstacles
            while (y >= 4 && !obstacle) {
                currentPos.y = y;
                if (boardState.hasPiece(currentPos)) {
                    obstacle = true;
                } else {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
                y--;
            }

            currentPos.y = sourcePos.y - 1;
            currentPos.x = sourcePos.x + 1;

            if (currentPos.x < 8) {
                if (boardState.hasPiece(currentPos) && boardState.getPiece(currentPos).isWhite()) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
            }

            // Y value is the same
            currentPos.x = sourcePos.x - 1;
            if (currentPos.x >= 0) {
                if (boardState.hasPiece(currentPos) && boardState.getPiece(currentPos).isWhite()) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
            }
        }
        // possible move from non starting position
        else {

            // check below
            currentPos.y = sourcePos.y - 1;
            currentPos.x = sourcePos.x;

            if (currentPos.y >= 0 && !boardState.hasPiece(currentPos)) {
                if (kingSafety(currentPos, sourcePos, boardState)) {
                    if (currentPos.y == 0) {
                        this.addMove(new Move(sourcePos, currentPos, new Queen(this.isWhite())));
                        this.addMove(new Move(sourcePos, currentPos, new Bishop(this.isWhite())));
                        this.addMove(new Move(sourcePos, currentPos, new Rook(this.isWhite())));
                        this.addMove(new Move(sourcePos, currentPos, new Knight(this.isWhite())));
                    }
                    else
                        this.addMove(new Move(sourcePos, currentPos, this));
                }
            }

            // check diagonal left
            // Y value is the same
            currentPos.x = sourcePos.x - 1;

            if (currentPos.y >= 0 && currentPos.x >= 0) {
                if (boardState.hasPiece(currentPos)) {
                    if (this.isWhite() != boardState.getPiece(currentPos).isWhite()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            if (currentPos.y == 0) {
                                this.addMove(new Move(sourcePos, currentPos, new Queen(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Bishop(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Rook(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Knight(this.isWhite())));
                            }
                            else
                                this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                }
                else if (currentPos.equals(boardState.getEnPassantPos())) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
            }

            // check diagonal right
            // Y value is the same
            currentPos.x = sourcePos.x + 1;

            if (currentPos.y >= 0 && currentPos.x < 8) {
                if (boardState.hasPiece(currentPos)) {
                    if (this.isWhite() != boardState.getPiece(currentPos).isWhite()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            if (currentPos.y == 0) {
                                this.addMove(new Move(sourcePos, currentPos, new Queen(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Bishop(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Rook(this.isWhite())));
                                this.addMove(new Move(sourcePos, currentPos, new Knight(this.isWhite())));
                            }
                            else
                                this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                }
                else if (currentPos.equals(boardState.getEnPassantPos())) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }
            }

        }
    }

    /*
    @Override
    public void calcAttackedSquares(YX sourcePos, BoardState boardState) {

        YX currentPos = new YX(0, 0);

        // white pawn logic
        if (this.isWhite()) {
            // check diagonal left
            // Y value is the same
            currentPos.y = sourcePos.y + 1;
            currentPos.x = sourcePos.x - 1;

            if (currentPos.y < 8 && currentPos.x >= 0) {
                if (boardState.hasPiece(currentPos)) {
                    this.setSquareAttackValue(currentPos, boardState);

                }
            }

            // check diagonal right
            // Y value is the same
            currentPos.x = sourcePos.x + 1;

            if (currentPos.y < 8 && currentPos.x < 8) {
                if (boardState.hasPiece(currentPos) || currentPos == boardState.getEnPassantPos()) {
                    this.setSquareAttackValue(currentPos, boardState);

                }
            }
        }
        // black pawn logic
        else {
            currentPos.y = sourcePos.y - 1;
            currentPos.x = sourcePos.x - 1;

            if (currentPos.y >= 0 && currentPos.x >= 0) {
                if (boardState.hasPiece(currentPos)) {
                    this.setSquareAttackValue(currentPos, boardState);

                }
            }

            // check diagonal right
            // Y value is the same
            currentPos.x = sourcePos.x + 1;

            if (currentPos.y >= 0 && currentPos.x < 8) {
                if (boardState.hasPiece(currentPos) || currentPos == boardState.getEnPassantPos()) {
                    this.setSquareAttackValue(currentPos, boardState);

                }
            }
        }
    }
    */

    @Override
    public String toString() {
        if (isWhite())
            return "pw";
        else
            return "pb";
    }
}

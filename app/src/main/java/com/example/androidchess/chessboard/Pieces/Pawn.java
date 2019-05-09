package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.*;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        this.setWhite(isWhite);
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {


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
                    if (boardState.hasPiece(currentPos)) {
                        obstacle = true;
                    } else {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            System.out.println("pawn");
                            this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                    y++;
                }

                currentPos.y = sourcePos.y + 1;
                currentPos.x = sourcePos.x + 1;

                if (currentPos.x < 8) {
                    if (boardState.hasPiece(currentPos) && !boardState.getPiece(currentPos).isWhite()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            this.addMove(new Move(sourcePos, currentPos, this));
                            boardState.markPossibleCaptures(currentPos);
                        }
                    }
                }

                // Y value is the same
                currentPos.x = sourcePos.x - 1;
                if (currentPos.x >= 0) {
                    if (boardState.hasPiece(currentPos) && !boardState.getPiece(currentPos).isWhite()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            this.addMove(new Move(sourcePos, currentPos, this));
                            boardState.markPossibleCaptures(currentPos);
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
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }

                // check diagonal left
                // Y value is the same
                currentPos.x = sourcePos.x - 1;

                if (currentPos.y < 8 && currentPos.x >= 0) {
                    if (boardState.hasPiece(currentPos)) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            this.addMove(new Move(sourcePos, currentPos, this));
                            boardState.markPossibleCaptures(currentPos);
                        }
                    }
                }

                // check diagonal right
                // Y value is the same
                currentPos.x = sourcePos.x + 1;

                if (currentPos.y < 8 && currentPos.x < 8) {
                    if (boardState.hasPiece(currentPos) || currentPos == boardState.getEnPassantPos()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            this.addMove(new Move(sourcePos, currentPos, this));
                            boardState.markPossibleCaptures(currentPos);
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
                    if (boardState.hasPiece(currentPos) && !boardState.getPiece(currentPos).isWhite()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            this.addMove(new Move(sourcePos, currentPos, this));
                            boardState.markPossibleCaptures(currentPos);
                        }
                    }
                }

                // Y value is the same
                currentPos.x = sourcePos.x - 1;
                if (currentPos.x >= 0) {
                    if (boardState.hasPiece(currentPos) && !boardState.getPiece(currentPos).isWhite()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            this.addMove(new Move(sourcePos, currentPos, this));
                            boardState.markPossibleCaptures(currentPos);
                        }
                    }
                }
            }
            // possible move from non starting position
            else {

                currentPos.y = sourcePos.y - 1;
                currentPos.x = sourcePos.x;

                if (currentPos.y >= 0 && !boardState.hasPiece(currentPos)) {
                    if (kingSafety(currentPos, sourcePos, boardState)) {
                        this.addMove(new Move(sourcePos, currentPos, this));
                    }
                }

                // check diagonal left
                // Y value is the same
                currentPos.x = sourcePos.x - 1;

                if (currentPos.y >= 0 && currentPos.x >= 0) {
                    if (boardState.hasPiece(currentPos)) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            this.addMove(new Move(sourcePos, currentPos, this));
                            boardState.markPossibleCaptures(currentPos);
                        }
                    }
                }

                // check diagonal right
                // Y value is the same
                currentPos.x = sourcePos.x + 1;

                if (currentPos.y >= 0 && currentPos.x < 8) {
                    if (boardState.hasPiece(currentPos) || currentPos == boardState.getEnPassantPos()) {
                        if (kingSafety(currentPos, sourcePos, boardState)) {
                            this.addMove(new Move(sourcePos, currentPos, this));
                            boardState.markPossibleCaptures(currentPos);
                        }
                    }
                }
            }
        }

    }


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

    @Override
    public void calcKingAttackingSquares(YX kingPos, YX sourcePos, BoardState boardState) {
        boardState.setKingAttackTrue(sourcePos);
        YX currentPos;

        if (boardState.getPiece(sourcePos).isWhite()) {
            currentPos = new YX(sourcePos.y++, sourcePos.x);
        } else {
            currentPos = new YX(sourcePos.y--, sourcePos.x);
        }

        if (kingPos.x < sourcePos.x) {
            currentPos.x--;
            boardState.setKingAttackTrue(currentPos);
        } else {
            currentPos.x++;
            boardState.setKingAttackTrue(currentPos);
        }
    }

    @Override
    public String toString() {
        if (isWhite())
            return "pw";
        else
            return "pb";
    }
}

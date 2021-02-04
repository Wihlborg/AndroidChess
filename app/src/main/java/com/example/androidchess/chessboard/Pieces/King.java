package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.BoardState;
import com.example.androidchess.chessboard.Move;
import com.example.androidchess.chessboard.YX;

import org.jetbrains.annotations.NotNull;

public class King extends Piece {

    public King(boolean isWhite) {
        this.setWhite(isWhite);
    }

    private void findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        if (boardState.hasPiece(currentPos)) {
            // if the squares are different colors
            if (boardState.getPiece(currentPos).isWhite() != this.isWhite()) {
                if (kingSafety(currentPos, sourcePos, boardState)) {
                    this.addMove(new Move(sourcePos, currentPos, this));
                }
            }
        }
        else {
            if (kingSafety(currentPos, sourcePos, boardState)) {
                this.addMove(new Move(sourcePos, currentPos, this));
            }
        }
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {

        int y = sourcePos.y;
        int x = sourcePos.x;
        YX currentPos = new YX(y, x);

        // castle check white king
        if (this.isWhite()) {
            boolean obstacle = false;

            // right white rook
            //System.out.println(rookMoved[3]);
            if (!boardState.getCastleFlag(3)) {
                //TODO might be x++ to fix possible castling when king is checked

                if (!boardState.getPiece(boardState.getKingPos(true)).isSafeFromCheck(sourcePos, boardState)){
                    obstacle = true;
                }
                while (++x < 7 && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (boardState.hasPiece(currentPos)) {
                        obstacle = true;
                    } else {
                        if (!kingSafety(currentPos, sourcePos, boardState)) {
                            obstacle = true;
                        }
                    }
                }
                if (!obstacle) {
                    currentPos.x = sourcePos.x + 2;
                    this.addMove(new Move(sourcePos, currentPos, this));
                }
            }

            obstacle = false;
            x = sourcePos.x;
            // left white rook
            if (!boardState.getCastleFlag(2)) {
                if (!boardState.getPiece(boardState.getKingPos(true)).isSafeFromCheck(sourcePos, boardState)){
                    obstacle = true;
                }
                while (--x >= 1 && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (boardState.hasPiece(currentPos)) {
                        obstacle = true;
                    } else {
                        if (!kingSafety(currentPos, sourcePos, boardState)) {
                            obstacle = true;
                        }
                    }
                }
                if (!obstacle) {
                    currentPos.x = sourcePos.x - 2;
                    this.addMove(new Move(sourcePos, currentPos, this));
                }
            }
        } // end castle check white king

        // castle check black king
        else if (!this.isWhite()) {
            boolean obstacle = false;
            x = sourcePos.x;
            // right black rook
            if (!boardState.getCastleFlag(1)) {
                if (!boardState.getPiece(boardState.getKingPos(false)).isSafeFromCheck(sourcePos, boardState)){
                    obstacle = true;
                }
                while (++x < 7 && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (boardState.hasPiece(currentPos)) {
                        obstacle = true;
                    } else {
                        if (!kingSafety(currentPos, sourcePos, boardState)) {
                            obstacle = true;
                        }
                    }
                }
                if (!obstacle) {
                    currentPos.x = sourcePos.x + 2;
                    this.addMove(new Move(sourcePos, currentPos, this));
                }
            }

            obstacle = false;
            x = sourcePos.x;
            // left black rook
            if (!boardState.getCastleFlag(0)) {
                if (!boardState.getPiece(boardState.getKingPos(false)).isSafeFromCheck(sourcePos, boardState)) {
                    obstacle = true;
                }
                while (--x >= 1 && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (boardState.hasPiece(currentPos)) {
                        obstacle = true;
                    } else {
                        if (!kingSafety(currentPos, sourcePos, boardState)) {
                            obstacle = true;
                        }
                    }
                }
                if (!obstacle) {
                    currentPos.x = sourcePos.x - 2;
                    this.addMove(new Move(sourcePos, currentPos, this));
                }
            }
        } // end of castle check black


        x = sourcePos.x;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (y >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8 && y >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8 && y < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (y < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0 && y < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0 && y >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }
    }

    @NotNull
    @Override
    public String toString() {
        return isWhite() ? "kw" : "kb";
    }
}

package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.BoardState;
import com.example.androidchess.chessboard.Move;
import com.example.androidchess.chessboard.YX;


import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    // for example black queen is = qb
    private boolean isWhite;
    private List<Move> moves;

    public Piece() {
        moves = new ArrayList<>();
    }

    /**
    calculates the possible moves for this piece
    taking into account if the king is getting attacked using kingSafety() on every possible evaluated square
    */
    abstract public void calcPossibleMoves(YX sourcePos, BoardState boardState);

    /**
    swaps places of pieces/empty squares and calculates if the king stands in check.
    removes piece temporarily and swaps to simulate a capture if current checked position contains a piece
    */
    public boolean kingSafety(YX currentPos, YX sourcePos, BoardState boardState) {

        boolean safe;

        Piece piece = null;

        // remove enemy piece temporarily
        if (boardState.hasPiece(currentPos)) {
            piece = boardState.getPiece(currentPos);
            boardState.setPiece(null, currentPos);
        }

        // make a temporarily move
        boardState.movePiece(new Move(sourcePos, currentPos, this));

        // if source piece is king
        if (boardState.getPiece(currentPos) instanceof King) {
            boardState.setKingPos(boardState.getPiece(currentPos).isWhite(), currentPos);
        }

        // isSafeFromCheck
        YX pos = boardState.getKingPos(boardState.isWhiteTurn());
        safe = (boardState.getPiece(pos)).isSafeFromCheck(pos, boardState);

        /*
        // temporarily save the array to skip new reset and calculations
        int[][] attackedSquares = boardState.attackedSquares;

        // start of calculations

        if (whiteTir)

        // if the square is white, is white's turn and if the king stands on a attacked square
        if (boardState.getPiece(currentPos).isWhite()) {
            if (boardState.isWhiteTurn()) {
                if (boardState.getAttackedSquare(boardState.getKingPos(true)) > 1)
                    safe = false;
            }
        }
        // if the square is black, is black's turn and if the king stands on a attacked square
        else if (!boardState.getPiece(currentPos).isWhite()) {
            if (!boardState.isWhiteTurn()) {
                if (boardState.getAttackedSquare(boardState.getKingPos(false)) == 1 || boardState.getAttackedSquare(boardState.getKingPos(false)) == 3)
                    safe = false;
            }
        }

        // assign the temporarily saved array to return old values
        boardState.attackedSquares = attackedSquares;

        // end of calculations
        */

        // revert the first move
        boardState.movePiece(new Move(currentPos, sourcePos, this));

        // if piece is king
        if (boardState.getPiece(sourcePos) instanceof King) {
            boardState.setKingPos(boardState.getPiece(sourcePos).isWhite(), sourcePos);
        }

        if (piece != null) {
            boardState.setPiece(piece, currentPos);
        }

        return safe;
    }

    public boolean isSafeFromCheck(YX sourcePos, BoardState boardState) {
        boolean safe = isCheckedBishopMovement(sourcePos, boardState);

        if (safe) {
            safe = isCheckedRookMovement(sourcePos, boardState);
        }

        if (safe) {
            safe = isCheckedKnightMovement(sourcePos, boardState);
        }

        if (safe) {
            safe = isCheckedPawnMovement(sourcePos, boardState);
        }

        return safe;
    }

    private boolean isCheckedBishopMovement(YX sourcePos, BoardState boardState) {
        boolean safe = true;

        // diagonal towards top right
        boolean obstacle = false;
        YX currentPos = new YX(sourcePos.y + 1, sourcePos.x + 1);
        while (currentPos.y < 8 && currentPos.x < 8 && !obstacle) {

            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Bishop || piece instanceof Queen)
                        safe = false;
                }
                obstacle = true;
            }

            currentPos.y++;
            currentPos.x++;
        }

        // diagonal towards top left
        if (safe)
            obstacle = false;
        currentPos.y = sourcePos.y + 1;
        currentPos.x = sourcePos.x - 1;
        while (currentPos.y < 8 && currentPos.x >= 0 && !obstacle) {

            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Bishop || piece instanceof Queen)
                        safe = false;
                }
                obstacle = true;
            }

            currentPos.y++;
            currentPos.x--;
        }

        // diagonal towards bottom left
        if (safe)
            obstacle = false;
        currentPos.y = sourcePos.y - 1;
        currentPos.x = sourcePos.x - 1;
        while (currentPos.y >= 0 && currentPos.x >= 0 && !obstacle) {

            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Bishop || piece instanceof Queen)
                        safe = false;
                }
                obstacle = true;
            }

            currentPos.y--;
            currentPos.x--;
        }

        // diagonal towards bottom right
        if (safe)
            obstacle = false;
        currentPos.y = sourcePos.y - 1;
        currentPos.x = sourcePos.x + 1;
        while (currentPos.y >= 0 && currentPos.x < 8 && !obstacle) {

            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Bishop || piece instanceof Queen)
                        safe = false;
                }
                obstacle = true;
            }

            currentPos.y--;
            currentPos.x++;
        }

        return safe;
    }

    private boolean isCheckedRookMovement(YX sourcePos, BoardState boardState) {
        boolean safe = true;

        // checking right
        boolean obstacle = false;
        YX currentPos = new YX(sourcePos.y, sourcePos.x + 1);
        while (currentPos.x < 8 && !obstacle) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Rook || piece instanceof Queen)
                        safe = false;
                }
                obstacle = true;
            }
            currentPos.x++;
        }

        // checking left
        obstacle = false;
        currentPos.x = sourcePos.x - 1;
        while (currentPos.x >= 0 && !obstacle) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Rook || piece instanceof Queen)
                        safe = false;
                }
                obstacle = true;
            }
            currentPos.x--;
        }

        // checking up
        obstacle = false;
        currentPos.y = sourcePos.y + 1;
        currentPos.x = sourcePos.x;
        while (currentPos.y < 8 && !obstacle) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Rook || piece instanceof Queen)
                        safe = false;
                }
                obstacle = true;
            }
            currentPos.y++;
        }

        // checking down
        obstacle = false;
        currentPos.y = sourcePos.y - 1;
        while (currentPos.y >= 0 && !obstacle) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Rook || piece instanceof Queen)
                        safe = false;
                }
                obstacle = true;
            }
            currentPos.y--;
        }

        return safe;
    }

    private boolean isCheckedKnightMovement(YX sourcePos, BoardState boardState) {
        boolean safe = true;

        YX currentPos = new YX(0, 0);

        currentPos.y = sourcePos.y + 2;
        currentPos.x = sourcePos.x + 1;
        if (currentPos.y < 8 && currentPos.x < 8) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Knight)
                        safe = false;
                }
            }
        }

        currentPos.y = sourcePos.y + 1;
        currentPos.x = sourcePos.x + 2;
        if (currentPos.y < 8 && currentPos.x < 8) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Knight)
                        safe = false;
                }
            }
        }

        currentPos.y = sourcePos.y - 1;
        currentPos.x = sourcePos.x + 2;
        if (currentPos.y >= 0 && currentPos.x < 8) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Knight)
                        safe = false;
                }
            }
        }

        currentPos.y = sourcePos.y - 2;
        currentPos.x = sourcePos.x + 1;
        if (currentPos.y >= 0 && currentPos.x < 8) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Knight)
                        safe = false;
                }
            }
        }


        currentPos.y = sourcePos.y - 2;
        currentPos.x = sourcePos.x - 1;
        if (currentPos.y >= 0 && currentPos.x >= 0) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Knight)
                        safe = false;
                }
            }
        }

        currentPos.y = sourcePos.y - 1;
        currentPos.x = sourcePos.x - 2;
        if (currentPos.y >= 0 && currentPos.x >= 0) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Knight)
                        safe = false;
                }
            }
        }

        currentPos.y = sourcePos.y + 1;
        currentPos.x = sourcePos.x - 2;
        if (currentPos.y < 8 && currentPos.x >= 0) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Knight)
                        safe = false;
                }
            }
        }

        currentPos.y = sourcePos.y + 2;
        currentPos.x = sourcePos.x - 1;
        if (currentPos.y < 8 && currentPos.x >= 0) {
            if (boardState.hasPiece(currentPos)) {
                Piece piece = boardState.getPiece(currentPos);
                if (piece.isWhite() != this.isWhite()) {
                    if (piece instanceof Knight)
                        safe = false;
                }
            }
        }

        return safe;
    }

    private boolean isCheckedPawnMovement(YX sourcePos, BoardState boardState) {
        boolean safe = true;
        YX currentPos = new YX(sourcePos.y, sourcePos.x);

        if (!boardState.isWhiteTurn()) {

            // check right diagonal
            currentPos.y--;
            currentPos.x++;
            if (currentPos.y >= 0 && currentPos.x < 8) {
                if (boardState.hasPiece(currentPos)) {
                    Piece piece = boardState.getPiece(currentPos);
                    if (piece.isWhite() != this.isWhite())
                        if (piece instanceof Pawn)
                            safe = false;
                }
            }

            // check left diagonal
            currentPos.x -= 2;
            if (currentPos.y >= 0 && currentPos.x >= 0) {
                if (boardState.hasPiece(currentPos)) {
                    Piece piece = boardState.getPiece(currentPos);
                    if (piece.isWhite() != this.isWhite())
                        if (piece instanceof Pawn)
                            safe = false;
                }
            }


        }
        // black turn
        else {

            // check right diagonal
            currentPos.y++;
            currentPos.x++;
            if (currentPos.y < 8 && currentPos.x < 8) {
                if (boardState.hasPiece(currentPos)) {
                    Piece piece = boardState.getPiece(currentPos);
                    if (piece.isWhite() != this.isWhite())
                        if (piece instanceof Pawn)
                            safe = false;
                }
            }

            // check left diagonal
            currentPos.x -= 2;
            if (currentPos.y < 8 && currentPos.x >= 0) {
                if (boardState.hasPiece(currentPos)) {
                    Piece piece = boardState.getPiece(currentPos);
                    if (piece.isWhite() != this.isWhite())
                        safe = false;
                }
            }

        }

        return safe;
    }

    public void addMove(Move move) {
        moves.add(move);
    }

    public Move getMove(int index) {
        return moves.get(index);
    }

    public List<Move> getMoves() {
        return this.moves;
    }

    public void clearMoves() {
        moves.clear();
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }
}


package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.*;

import java.util.LinkedList;

public abstract class Piece {
    // for example black queen is = qb
    private boolean isWhite;
    private LinkedList<Move> moves;

    public Piece() {
        moves = new LinkedList<>();
    }
/*
    public Piece(Piece piece) {
        this.moves = new LinkedList<>();
        this.isWhite = piece.isWhite;
    }
    */

    /*
    calculates the possible moves for this piece
    taking into account if the king is getting attacked using kingSafety() on every possible evaluated square
    */
    abstract public void calcPossibleMoves(YX sourcePos, BoardState boardState);

    /*
    calculates which square this piece attacks
    */
    abstract public void calcAttackedSquares(YX sourcePos, BoardState boardState);

    public void setSquareAttackValue(YX currentPos, BoardState boardState) {
        int[][] attackedSquares = boardState.attackedSquares;
        if (isWhite) {
            if (attackedSquares[currentPos.y][currentPos.x] == 0)
                attackedSquares[currentPos.y][currentPos.x] = 1;
            else if (attackedSquares[currentPos.y][currentPos.x] == 2)
                attackedSquares[currentPos.y][currentPos.x] = 3;
        } else {
            if (attackedSquares[currentPos.y][currentPos.x] == 0)
                attackedSquares[currentPos.y][currentPos.x] = 2;
            else if (attackedSquares[currentPos.y][currentPos.x] == 1)
                attackedSquares[currentPos.y][currentPos.x] = 3;
        }
    }

    /*
    swaps places of pieces/empty squares and calculates if the king stands in check.
    removes piece temporarily and swaps to simulate a capture if current checked position contains a piece
    */
    public boolean kingSafety(YX currentPos, YX sourcePos, BoardState boardState) {

        boolean safe = true;

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
            if (boardState.getPiece(currentPos).isWhite())
                boardState.setKingPos(true, currentPos);

            else
                boardState.setKingPos(false, currentPos);
        }

        // temporarily save the array to skip new reset and calculations
        int[][] attackedSquares = boardState.attackedSquares;

        // start of calculations

        boardState.resetAttackedSquares();
        boardState.calcAllAttackedSquares();

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

        // end of calculations

        // revert the first move
        boardState.movePiece(new Move(currentPos, sourcePos, this));

        // if piece is king
        if (boardState.getPiece(sourcePos) instanceof King) {
            if (boardState.getPiece(sourcePos).isWhite())
                boardState.setKingPos(true, sourcePos);

            else
                boardState.setKingPos(false, sourcePos);
        }

        if (piece != null) {
            boardState.setPiece(piece, currentPos);
        }

        // assign the temporarily saved array to return old values
        boardState.attackedSquares = attackedSquares;

        return safe;
    }

    public void addMove(Move move) {


        moves.add(move);
    }

    public Move getMove(int index) {
        return moves.get(index);
    }

    public LinkedList<Move> getMoves() {
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

    abstract public String toString();
}


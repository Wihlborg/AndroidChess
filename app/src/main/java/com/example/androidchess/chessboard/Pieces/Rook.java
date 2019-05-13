package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.*;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        this.setWhite(isWhite);
    }

    public boolean findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        boolean obstacle = false;


        if (boardState.hasPiece(currentPos)) {
            obstacle = true;
            if (this.isWhite() != boardState.getPiece(currentPos).isWhite()) {
                if (this.kingSafety(currentPos, sourcePos, boardState)) {
                    this.addMove(new Move(sourcePos, currentPos, this));
                    boardState.markPossibleCaptures(currentPos);
                }
            }
        } else {
            if (this.kingSafety(currentPos, sourcePos, boardState)) {
                this.addMove(new Move(sourcePos, currentPos, this));
            }
        }
        return obstacle;
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {

        // checking right
        int y = sourcePos.y;
        int x = sourcePos.x + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y, x);
        while (x < 8 && !obstacle) {
            currentPos.x = x;
            obstacle = findPossibleMove(currentPos, sourcePos, boardState);
            x++;
        }

        // checking left
        x = sourcePos.x - 1;
        obstacle = false;
        while (x >= 0 && !obstacle) {
            currentPos.x = x;
            obstacle = findPossibleMove(currentPos, sourcePos, boardState);
            x--;
        }

        // checking up
        y = sourcePos.y + 1;
        x = sourcePos.x;
        currentPos.x = x;
        obstacle = false;
        while (y < 8 && !obstacle) {
            currentPos.y = y;
            obstacle = findPossibleMove(currentPos, sourcePos, boardState);
            y++;
        }

        // checking down
        y = sourcePos.y - 1;
        obstacle = false;
        while (y >= 0 && !obstacle) {
            currentPos.y = y;
            obstacle = findPossibleMove(currentPos, sourcePos, boardState);
            y--;
        }

    }

    private boolean calcAttackSquare(YX currentPos, BoardState boardState) {
        boolean obstacle = false;

        if (boardState.hasPiece(currentPos)) {
            obstacle = true;
            if ((this.isWhite()) != boardState.getPiece(currentPos).isWhite()) {
                setSquareAttackValue(currentPos, boardState);
            }
        } else {
            setSquareAttackValue(currentPos, boardState);
        }

        return obstacle;
    }

    @Override
    public void calcAttackedSquares(YX sourcePos, BoardState boardState) {

        // checking right
        int y = sourcePos.y;
        int x = sourcePos.x + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y, x);
        while (x < 8 && !obstacle) {
            currentPos.x = x;
            obstacle = calcAttackSquare(currentPos, boardState);
            x++;
        }

        // checking left
        //y = sourcePos.y;
        x = sourcePos.x - 1;
        obstacle = false;
        while (x >= 0 && !obstacle) {
            currentPos.x = x;
            obstacle = calcAttackSquare(currentPos, boardState);
            x--;
        }

        // checking up
        y = sourcePos.y + 1;
        x = sourcePos.x;
        currentPos.x = x;
        obstacle = false;
        while (y < 8 && !obstacle) {
            currentPos.y = y;
            obstacle = calcAttackSquare(currentPos, boardState);
            y++;
        }

        // checking down
        y = sourcePos.y - 1;
        //x = sourcePos.x;
        obstacle = false;
        while (y >= 0 && !obstacle) {
            currentPos.y = y;
            obstacle = calcAttackSquare(currentPos, boardState);
            y--;
        }
    }

    @Override
    public void calcKingAttackingSquares(YX kingPos, YX sourcePos, BoardState boardState) {
        YX currentPos = new YX(sourcePos.y, sourcePos.x);

        boardState.setKingAttackTrue(currentPos);

        // if they are positioned on the same x axis
        if (kingPos.x == sourcePos.x) {
            if (sourcePos.y > kingPos.y) {
                currentPos.y--;
                while (currentPos.y >= 0 && currentPos.y > kingPos.y) {
                    boardState.setKingAttackTrue(currentPos);
                    currentPos.y--;
                }
            } else {
                currentPos.y++;
                while (currentPos.y < 8 && currentPos.y < kingPos.y) {
                    boardState.setKingAttackTrue(currentPos);
                    currentPos.y++;
                }
            }
        }
        // if they are positioned on the same y axis
        else {
            if (sourcePos.x > kingPos.x) {
                currentPos.x--;
                while (currentPos.x >= 0 && currentPos.x > kingPos.x) {
                    boardState.setKingAttackTrue(currentPos);
                    currentPos.x--;
                }
            } else {
                currentPos.x++;
                while (currentPos.x < 8 && currentPos.x < kingPos.x) {
                    boardState.setKingAttackTrue(currentPos);
                    currentPos.x++;
                }
            }
        }
    }

    @Override
    public String toString() {
        if (isWhite())
            return "rw";
        else
            return "rb";
    }
}

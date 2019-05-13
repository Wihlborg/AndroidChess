package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.*;

public class Knight extends Piece {

    public Knight(boolean isWhite) {
        this.setWhite(isWhite);
    }

    public void findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        if (boardState.hasPiece(currentPos)) {
            if (this.isWhite() != boardState.getPiece(currentPos).isWhite()){
                if (this.kingSafety(currentPos, sourcePos, boardState)){
                    //System.out.println(this+" found victim @"+currentPos+", from: "+sourcePos);
                    this.addMove(new Move(sourcePos, currentPos, this));
                    boardState.markPossibleCaptures(currentPos);
                }
            }
        } else {
            if (this.kingSafety(currentPos, sourcePos, boardState)) {
                //System.out.println(currentPos);
                //Move move = new Move(sourcePos, currentPos, this);
                //System.out.println("KnightMove: "+move);
                this.addMove(new Move(sourcePos, currentPos, this));
                //System.out.println("KnightList: "+this.getMoves());
            }
        }
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {
        int y = sourcePos.y;
        int x = sourcePos.x;

        //System.out.println("-----------------");
        YX currentPos = new YX(0, 0);

        currentPos.y = y + 2;
        currentPos.x = x + 1;
        if (currentPos.y < 8 && currentPos.x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y + 1;
        currentPos.x = x + 2;
        if (currentPos.y < 8 && currentPos.x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y - 1;
        currentPos.x = x + 2;
        if (currentPos.y >= 0 && currentPos.x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y - 2;
        currentPos.x = x + 1;
        if (currentPos.y >= 0 && currentPos.x < 8) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }


        currentPos.y = y - 2;
        currentPos.x = x - 1;
        if (currentPos.y >= 0 && currentPos.x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y - 1;
        currentPos.x = x - 2;
        if (currentPos.y >= 0 && currentPos.x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y + 1;
        currentPos.x = x - 2;
        if (currentPos.y < 8 && currentPos.x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        currentPos.y = y + 2;
        currentPos.x = x - 1;
        if (currentPos.y < 8 && currentPos.x >= 0) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }
        //System.out.println("size: "+this.getMoves().size());
        //System.out.println("List: "+this.getMoves().toString());
        //System.out.println("list: "+System.identityHashCode(this.getMoves()));
        //System.out.println("object: "+System.identityHashCode(this));
    }

    @Override
    public void calcAttackedSquares(YX sourcePos, BoardState boardState) {
        int y = sourcePos.y;
        int x = sourcePos.x;

        //System.out.println("-----------------");
        YX currentPos = new YX(0, 0);

        currentPos.y = y + 1;
        currentPos.x = x + 2;
        if (currentPos.x < 8 && currentPos.y < 8) {
            this.setSquareAttackValue(currentPos, boardState);
        }

        currentPos.y = y + 2;
        currentPos.x = x + 1;
        if (currentPos.x < 8 && currentPos.y < 8) {
            this.setSquareAttackValue(currentPos, boardState);
        }

        currentPos.y = y + 2;
        currentPos.x = x - 1;
        if (currentPos.x >= 0 && currentPos.y < 8) {
            this.setSquareAttackValue(currentPos, boardState);
        }

        currentPos.y = y + 1;
        currentPos.x = x - 2;
        if (currentPos.x >= 0 && currentPos.y < 8) {
            this.setSquareAttackValue(currentPos, boardState);
        }

        currentPos.y = y - 1;
        currentPos.x = x - 2;
        if (x - 2 >= 0 && y - 1 >= 0) {
            this.setSquareAttackValue(currentPos, boardState);
        }

        currentPos.y = y - 2;
        currentPos.x = x - 1;
        if (currentPos.x >= 0 && currentPos.y >= 0) {
            this.setSquareAttackValue(currentPos, boardState);
        }

        currentPos.y = y - 2;
        currentPos.x = x + 1;
        if (currentPos.x < 8 && currentPos.y >= 0) {
            this.setSquareAttackValue(currentPos, boardState);
        }

        currentPos.y = y - 1;
        currentPos.x = x + 2;
        if (currentPos.x < 8 && currentPos.y >= 0) {
            this.setSquareAttackValue(currentPos, boardState);
        }
    }

    @Override
    public void calcKingAttackingSquares(YX kingPos, YX sourcePos, BoardState boardState) {
        boardState.setKingAttackTrue(sourcePos);
    }

    @Override
    public String toString() {
        if (isWhite())
            return "nw";
        else
            return "nb";
    }
}

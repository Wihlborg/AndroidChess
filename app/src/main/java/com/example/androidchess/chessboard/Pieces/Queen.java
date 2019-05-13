package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.*;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        this.setWhite(isWhite);
    }

    @Override
    public void calcPossibleMoves(YX sourcePos, BoardState boardState) {
        bishopCalcPossibleMoves(sourcePos, boardState);
        rookCalcPossibleMoves(sourcePos, boardState);
    }

    @Override
    public void calcAttackedSquares(YX sourcePos, BoardState boardState) {
        bishopCalcAttackedSquares(sourcePos, boardState);
        rookCalcAttackedSquares(sourcePos, boardState);
    }

    @Override
    public void calcKingAttackingSquares(YX kingPos, YX sourcePos, BoardState boardState) {
        bishopCalcKingAttackingSquares(kingPos, sourcePos, boardState);
        rookCalcKingAttackingSquares(kingPos, sourcePos, boardState);
    }

    private boolean findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        boolean obstacle = false;

        // found a piece = cant search further in the same direction
        if (boardState.hasPiece(currentPos)) {
            obstacle = true;
            if ((this.isWhite() != boardState.getPiece(currentPos).isWhite())) {
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

    private void bishopCalcPossibleMoves(YX sourcePos, BoardState boardState) {
        // diagonal towards bottom right
        int y = sourcePos.y + 1;
        int x = sourcePos.x + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y, x);
        while (x < 8 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos, boardState);

            x++;
            y++;

        }

        // diagonal towards top left
        x = sourcePos.x - 1;
        y = sourcePos.y - 1;
        obstacle = false;
        while (x >= 0 && y >= 0 && !obstacle) {

            // position in array currently getting looked at
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos, boardState);

            x--;
            y--;
        }

        // diagonal towards top right
        x = sourcePos.x + 1;
        y = sourcePos.y - 1;
        obstacle = false;
        while (x < 8 && y >= 0 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos, boardState);

            x++;
            y--;
        }

        // diagonal towards bottom left
        x = sourcePos.x - 1;
        y = sourcePos.y + 1;
        obstacle = false;
        while (x >= 0 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = findPossibleMove(currentPos, sourcePos, boardState);

            x--;
            y++;
        }
    }

    private void rookCalcPossibleMoves(YX sourcePos, BoardState boardState) {
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

        // found a piece = cant search further in the same direction
        if (boardState.hasPiece(currentPos)) {
            obstacle = true;
            if ((this.isWhite() != boardState.getPiece(currentPos).isWhite())) {
                this.setSquareAttackValue(currentPos, boardState);
            }
        } else {
            this.setSquareAttackValue(currentPos, boardState);
        }
        return obstacle;
    }

    private void bishopCalcAttackedSquares(YX sourcePos, BoardState boardState) {
        int sourceY = sourcePos.y;
        int sourceX = sourcePos.x;

        // diagonal towards bottom right
        int y = sourceY + 1;
        int x = sourceX + 1;
        boolean obstacle = false;
        YX currentPos = new YX(y, x);
        while (x < 8 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = calcAttackSquare(currentPos, boardState);

            x++;
            y++;

        }

        // diagonal towards top left
        x = sourceX - 1;
        y = sourceY - 1;
        obstacle = false;
        while (x >= 0 && y >= 0 && !obstacle) {

            // position in array currently getting looked at
            currentPos.y = y;
            currentPos.x = x;

            obstacle = calcAttackSquare(currentPos, boardState);

            x--;
            y--;
        }

        // diagonal towards top right
        x = sourceX + 1;
        y = sourceY - 1;
        obstacle = false;
        while (x < 8 && y >= 0 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = calcAttackSquare(currentPos, boardState);

            x++;
            y--;
        }

        // diagonal towards bottom left
        x = sourceX - 1;
        y = sourceY + 1;
        obstacle = false;
        while (x >= 0 && y < 8 && !obstacle) {
            currentPos.y = y;
            currentPos.x = x;

            obstacle = calcAttackSquare(currentPos, boardState);

            x--;
            y++;
        }
    }

    private void rookCalcAttackedSquares(YX sourcePos, BoardState boardState) {
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

    private void bishopCalcKingAttackingSquares(YX kingPos, YX sourcePos, BoardState boardState) {

        YX currentPos = new YX(sourcePos.y, sourcePos.x);

        boardState.setKingAttackTrue(sourcePos);

        // down left
        if (kingPos.x < sourcePos.x && kingPos.y < sourcePos.y) {
            currentPos.y--;
            currentPos.x--;
            while (currentPos.x >= 0 && currentPos.y >= 0 && currentPos.x > kingPos.x && currentPos.y > kingPos.y) {
                boardState.setKingAttackTrue(currentPos);
                currentPos.y--;
                currentPos.x--;
            }
        }
        // down right
        else if (kingPos.x > sourcePos.x && kingPos.y < sourcePos.y) {
            currentPos.y--;
            currentPos.x++;
            while (currentPos.x < 8 && currentPos.y >= 0 && currentPos.x < kingPos.x && currentPos.y > kingPos.y) {
                boardState.setKingAttackTrue(currentPos);
                currentPos.y--;
                currentPos.x++;
            }
        }
        // up right
        else if (kingPos.x > sourcePos.x && kingPos.y > sourcePos.y) {
            currentPos.y++;
            currentPos.x++;
            while (currentPos.x < 8 && currentPos.y < 8 && currentPos.x < kingPos.x && currentPos.y < kingPos.y) {
                boardState.setKingAttackTrue(currentPos);
                currentPos.y++;
                currentPos.x++;
            }
        }
        // up left
        else if (kingPos.x < sourcePos.x && kingPos.y > sourcePos.y) {
            currentPos.y++;
            currentPos.x--;
            while (currentPos.x >= 0 && currentPos.y < 8 && currentPos.x > kingPos.x && currentPos.y < kingPos.y) {
                boardState.setKingAttackTrue(currentPos);
                currentPos.y++;
                currentPos.x--;
            }
        }
    }

    private void rookCalcKingAttackingSquares(YX kingPos, YX sourcePos, BoardState boardState) {
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
            return "qw";
        else
            return "qb";
    }
}

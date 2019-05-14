package com.example.androidchess.chessboard.Pieces;

import com.example.androidchess.chessboard.*;

public class King extends Piece {

    public King(boolean isWhite) {
        this.setWhite(isWhite);
    }

    private boolean safetyCheck(YX currentPos, BoardState boardState) {
        if (this.isWhite())
            return !(boardState.attackedSquares[currentPos.y][currentPos.x] > 1);
        else
            return !(boardState.attackedSquares[currentPos.y][currentPos.x] == 1 || boardState.attackedSquares[currentPos.y][currentPos.x] == 3);
    }

    private void findPossibleMove(YX currentPos, YX sourcePos, BoardState boardState) {
        if (boardState.hasPiece(currentPos)) {
            // if the squares are different colors
            if (boardState.getPiece(currentPos).isWhite() != this.isWhite()) {
                if (kingSafety(currentPos, sourcePos, boardState)) {
                    this.addMove(new Move(sourcePos, currentPos, this));
                    boardState.markPossibleCaptures(currentPos);
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
                while (++x < 8 && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;

                    //System.out.println("i: " + i + ", squareValue: "+attackedSquares[currentPos]);
                    if (x != 7 && boardState.hasPiece(currentPos) || boardState.attackedSquares[y][x] > 1) {
                        //System.out.println(getFilename(currentPos));
                        //System.out.println("obstacle true");
                        obstacle = true;
                    } else {
                        //System.out.println("kingsafety: " + kingSafety(currentPos, sourcePos));
                        if (x == 7 && this.kingSafety(currentPos, sourcePos, boardState)) {
                            currentPos.x--;
                            this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                }
            }

            obstacle = false;
            x = sourcePos.x;
            // left white rook
            if (!boardState.getCastleFlag(2)) {
                while ((--x >= 0) && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (x != 0 && boardState.hasPiece(currentPos) || boardState.attackedSquares[currentPos.y][currentPos.x] > 1) {
                        obstacle = true;
                    } else {
                        if (x == 0 && kingSafety(currentPos, sourcePos, boardState)) {
                            currentPos.x = 2;
                            this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                }
            }
        } // end castle check white king

        // castle check black king
        else if (!this.isWhite()) {
            boolean obstacle = false;
            x = sourcePos.x;
            // right black rook
            if (!boardState.getCastleFlag(1)) {
                while (++x < 8 && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (x != 7 && boardState.hasPiece(currentPos) ||
                            (boardState.attackedSquares[currentPos.y][currentPos.x] == 1
                                    || boardState.attackedSquares[currentPos.y][currentPos.x] == 3)) {
                        obstacle = true;
                    } else {
                        if (x == 7 && kingSafety(currentPos, sourcePos, boardState)) {
                            currentPos.x--;
                            this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                }
            }

            obstacle = false;
            x = sourcePos.x;
            // left black rook
            if (!boardState.getCastleFlag(0)) {
                while ((--x >= 0) && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (x != 0 && boardState.hasPiece(currentPos) ||
                            (boardState.attackedSquares[currentPos.y][currentPos.x] == 1
                                    || boardState.attackedSquares[currentPos.y][currentPos.x] == 3)) {
                        obstacle = true;
                    } else {
                        if (x == 0 && kingSafety(currentPos, sourcePos, boardState)) {
                            currentPos.x = 2;
                            this.addMove(new Move(sourcePos, currentPos, this));
                        }
                    }
                }
            }
        } // end of castle check black


        x = sourcePos.x;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (y >= 0 && safetyCheck(currentPos, boardState)) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8 && y >= 0 && safetyCheck(currentPos, boardState)) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8 && safetyCheck(currentPos, boardState)) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8 && y < 8 && safetyCheck(currentPos, boardState)) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (y < 8 && safetyCheck(currentPos, boardState)) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0 && y < 8 && safetyCheck(currentPos, boardState)) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0 && safetyCheck(currentPos, boardState)) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0 && y >= 0 && safetyCheck(currentPos, boardState)) {
            findPossibleMove(currentPos, sourcePos, boardState);
        }
    }

    @Override
    public void calcAttackedSquares(YX sourcePos, BoardState boardState) {

        YX currentPos = new YX(sourcePos.y, sourcePos.x);
        int y = sourcePos.y;
        int x = sourcePos.x;
        currentPos.y = y - 1;
        currentPos.x = x;
        if (currentPos.y >= 0)
            this.setSquareAttackValue(currentPos, boardState);

        currentPos.y = y - 1;
        currentPos.x = x + 1;
        if (currentPos.y >= 0 && currentPos.x < 8)
            this.setSquareAttackValue(currentPos, boardState);

        currentPos.y = y;
        currentPos.x = x + 1;
        if (currentPos.x < 8)
            this.setSquareAttackValue(currentPos, boardState);

        currentPos.y = y + 1;
        currentPos.x = x + 1;
        if (currentPos.y < 8 && currentPos.x < 8)
            this.setSquareAttackValue(currentPos, boardState);

        currentPos.y = y + 1;
        currentPos.x = x;
        if (currentPos.y < 8)
            this.setSquareAttackValue(currentPos, boardState);

        currentPos.y = y + 1;
        currentPos.x = x - 1;
        if (currentPos.y < 8 && currentPos.x >= 0)
            this.setSquareAttackValue(currentPos, boardState);

        currentPos.y = y;
        currentPos.x = x - 1;
        if (currentPos.x >= 0)
            this.setSquareAttackValue(currentPos, boardState);

        currentPos.y = y - 1;
        currentPos.x = x - 1;
        if (currentPos.y >= 0 && currentPos.x >= 0)
            this.setSquareAttackValue(currentPos, boardState);

    }

    @Override
    public String toString() {
        if (isWhite())
            return "kw";
        else
            return "kb";
    }
}

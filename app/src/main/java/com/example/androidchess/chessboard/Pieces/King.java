package com.example.androidchess.chessboard.Pieces;

import android.graphics.Color;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.*;

public class King extends Piece {

    public King(boolean isWhite) {
        this.setWhite(isWhite);
        if (isWhite) {
            this.setID(R.drawable.kw);
        } else {
            this.setID(R.drawable.kb);
        }
    }

    private boolean safetyCheck(YX currentPos) {
        if (this.isWhite())
            return !(GameInfo.get().attackedSquares[currentPos.y][currentPos.x] > 1);
        else
            return !(GameInfo.get().attackedSquares[currentPos.y][currentPos.x] == 1 || GameInfo.get().attackedSquares[currentPos.y][currentPos.x] == 3);
    }

    private void findPossibleMove(YX currentPos, YX sourcePos) {
        Board board = GameInfo.get().board;

        if (board.getSquare(currentPos).hasPiece()) {
            // if the squares are different colors
            if (board.getSquare(currentPos).getPiece().isWhite() != this.isWhite()) {
                if (kingSafety(currentPos, sourcePos)) {
                    GameInfo.get().possibleToMove(currentPos);
                    board.getSquare(currentPos).setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        }
        else {
            if (kingSafety(currentPos, sourcePos)) {
                GameInfo.get().possibleToMove(currentPos);
            }
        }
    }

    @Override
    public void calcPossibleMoves(YX sourcePos) {
        Board board = GameInfo.get().board;

        int y = sourcePos.y;
        int x = sourcePos.x;
        YX currentPos = new YX(y, x);

        // castle check white king
        if (this.isWhite()) {
            boolean obstacle = false;

            // right white rook
            //System.out.println(rookMoved[3]);
            if (!GameInfo.get().castleFlag[3]) {
                //TODO might be x++ to fix possible castling when king is checked
                while (++x < 8 && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;

                    //System.out.println("i: " + i + ", squareValue: "+attackedSquares[currentPos]);
                    if (x != 7 && board.getSquare(currentPos).hasPiece() || GameInfo.get().attackedSquares[y][x] > 1) {
                        //System.out.println(getFilename(currentPos));
                        //System.out.println("obstacle true");
                        obstacle = true;
                    } else {
                        //System.out.println("kingsafety: " + kingSafety(currentPos, sourcePos));
                        if (x == 7 && this.kingSafety(currentPos, sourcePos))
                            GameInfo.get().possibleToMove(currentPos);
                    }
                }
            }

            obstacle = false;
            x = sourcePos.x;
            // left white rook
            if (!GameInfo.get().castleFlag[2]) {
                while ((--x >= 0) && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (x != 0 && board.getSquare(currentPos).hasPiece() || GameInfo.get().attackedSquares[currentPos.y][currentPos.x] > 1) {
                        obstacle = true;
                    } else {
                        if (x == 0 && kingSafety(currentPos, sourcePos))
                            GameInfo.get().possibleToMove(currentPos);
                    }
                }
            }
        } // end castle check white king

        // castle check black king
        else if (!this.isWhite()) {
            boolean obstacle = false;
            x = sourcePos.x;
            // right black rook
            if (!GameInfo.get().castleFlag[1]) {
                while (++x < 8 && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (x != 7 && board.getSquare(currentPos).hasPiece() ||
                            (GameInfo.get().attackedSquares[currentPos.y][currentPos.x] == 1
                                    || GameInfo.get().attackedSquares[currentPos.y][currentPos.x] == 3)) {
                        obstacle = true;
                    } else {
                        if (x == 7 && kingSafety(currentPos, sourcePos))
                            GameInfo.get().possibleToMove(currentPos);
                    }
                }
            }

            obstacle = false;
            x = sourcePos.x;
            // left black rook
            if (!GameInfo.get().castleFlag[0]) {
                while ((--x >= 0) && !obstacle) {
                    currentPos.y = y;
                    currentPos.x = x;
                    if (x != 0 && board.getSquare(currentPos).hasPiece() ||
                            (GameInfo.get().attackedSquares[currentPos.y][currentPos.x] == 1
                                    || GameInfo.get().attackedSquares[currentPos.y][currentPos.x] == 3)) {
                        obstacle = true;
                    } else {
                        if (x == 0 && kingSafety(currentPos, sourcePos))
                            GameInfo.get().possibleToMove(currentPos);
                    }
                }
            }
        } // end of castle check black


        x = sourcePos.x;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (y >= 0 && safetyCheck(currentPos)) {
            findPossibleMove(currentPos, sourcePos);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8 && y >= 0 && safetyCheck(currentPos)) {
            findPossibleMove(currentPos, sourcePos);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8 && safetyCheck(currentPos)) {
            findPossibleMove(currentPos, sourcePos);
        }

        x = sourcePos.x + 1;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x < 8 && y < 8 && safetyCheck(currentPos)) {
            findPossibleMove(currentPos, sourcePos);
        }

        x = sourcePos.x;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (y < 8 && safetyCheck(currentPos)) {
            findPossibleMove(currentPos, sourcePos);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y + 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0 && y < 8 && safetyCheck(currentPos)) {
            findPossibleMove(currentPos, sourcePos);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0 && safetyCheck(currentPos)) {
            findPossibleMove(currentPos, sourcePos);
        }

        x = sourcePos.x - 1;
        y = sourcePos.y - 1;
        currentPos.y = y;
        currentPos.x = x;
        if (x >= 0 && y >= 0 && safetyCheck(currentPos)) {
            findPossibleMove(currentPos, sourcePos);
        }
    }

    @Override
    public void calcAttackedSquares(YX sourcePos) {

        YX currentPos = new YX(sourcePos.y, sourcePos.x);
        int y = sourcePos.y;
        int x = sourcePos.x;
        currentPos.y = y - 1;
        currentPos.x = x;
        if (y >= 0)
            this.setSquareAttackValue(currentPos);

        currentPos.y = y - 1;
        currentPos.x = x + 1;
        if (y >= 0 && x < 8)
            this.setSquareAttackValue(currentPos);

        currentPos.y = y;
        currentPos.x = x + 1;
        if (x < 8)
            this.setSquareAttackValue(currentPos);

        currentPos.y = y + 1;
        currentPos.x = x + 1;
        if (y < 8 && x < 8)
            this.setSquareAttackValue(currentPos);

        currentPos.y = y + 1;
        currentPos.x = x;
        if (y < 8)
            this.setSquareAttackValue(currentPos);

        currentPos.y = y + 1;
        currentPos.x = x - 1;
        if (y < 8 && x >= 0)
            this.setSquareAttackValue(currentPos);

        currentPos.y = y;
        currentPos.x = x - 1;
        if (x >= 0)
            this.setSquareAttackValue(currentPos);

        currentPos.y = y - 1;
        currentPos.x = x - 1;
        if (y >= 0 && x >= 0)
            this.setSquareAttackValue(currentPos);

    }

    @Override
    public void calcKingAttackingSquares(YX kingPos, YX sourcePos) {

    }
}

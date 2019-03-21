package com.example.androidchess.chessboard.pieces;

import com.example.androidchess.R;

import static com.example.androidchess.chessboard.GameActivity.*;

public class King {

    public void kingCheck(int position) {
        if (whiteTurn && getFilename(position).charAt(1) == 'w') {
            colorKingCheck(position, 'w');
        } else if (!whiteTurn && getFilename(position).charAt(1) == 'b') {
            colorKingCheck(position, 'b');
        }
    }

    public void checkMate(int kingPos) {
        System.out.println("entered checkmate");
        printAttackedSquares();
        int attackNr;
        if (getFilename(kingPos).charAt(1) == 'w')
            attackNr = 2;
        else
            attackNr = 1;

        switch (getFilename(lastMove).charAt(0)) {
            case 'q':
                bishop.checkMateAttackSquares(kingPos, lastMove);
                rook.checkMateAttackSquares(kingPos, lastMove);
                break;
            case 'b':
                bishop.checkMateAttackSquares(kingPos, lastMove);
                break;
            case 'n':
                knight.checkMateAttackSquares(kingPos, lastMove);
                break;
            case 'r':
                rook.checkMateAttackSquares(kingPos, lastMove);
                break;
            case 'p':
                pawn.checkMateAttackSquares(kingPos, lastMove);
                break;
        }

        int x = kingPos % 8;
        int y = kingPos / 8;

        int i = x;
        int n = y - 1;
        int currentPos = i + 8 * n;

        if (n >= 0 && (attackedSquares[currentPos] == attackNr && kingAttacker[currentPos])) {
            checkMate = true;
        }

        i = x + 1;
        n = y - 1;
        currentPos = i + 8 * n;
        if (i < 8 && n >= 0 && (attackedSquares[currentPos] == attackNr && kingAttacker[currentPos]))
            checkMate = true;

        i = x + 1;
        n = y;
        currentPos = i + 8 * n;
        if (i < 8 && (attackedSquares[currentPos] == attackNr && kingAttacker[currentPos]))
            checkMate = true;

        i = x + 1;
        n = y + 1;
        currentPos = i + 8 * n;
        if (i < 8 && n < 8 && (attackedSquares[currentPos] == attackNr && kingAttacker[currentPos]))
            checkMate = true;

        i = x;
        n = y + 1;
        currentPos = i + 8 * n;
        if (n < 8 && (attackedSquares[currentPos] == attackNr && kingAttacker[currentPos]))
            checkMate = true;

        i = x - 1;
        n = y + 1;
        currentPos = i + 8 * n;
        if (i >= 0 && n < 8 && (attackedSquares[currentPos] == attackNr && kingAttacker[currentPos]))
            checkMate = true;

        i = x - 1;
        n = y;
        currentPos = i + 8 * n;
        if (i >= 0 && (attackedSquares[currentPos] == attackNr && kingAttacker[currentPos]))
            checkMate = true;

        i = x - 1;
        n = y - 1;
        currentPos = i + 8 * n;
        if (i >= 0 && n >= 0 && (attackedSquares[currentPos] == attackNr && kingAttacker[currentPos]))
            checkMate = true;

        if (checkMate)
            System.out.println("cheeeeeeeckMAAAAATE");

        resetAttackedSquares();
    }

    public boolean attackedCheck(int currentPos, char color) {
        if (color == 'w') {
            //return attackedSquares[currentPos] > 1;
            if (attackedSquares[currentPos] > 1)
                return false;
            else
                return true;
        } else {
            //return attackedSquares[currentPos] == 1 || attackedSquares[currentPos] == 3;
            if (attackedSquares[currentPos] == 1 || attackedSquares[currentPos] == 3)
                return false;
            else
                return true;
        }
    }

    public boolean colorKingCheck(int position, char color) {
        boolean possibleMove = false;
        int x = position % 8;
        int y = position / 8;

        int i = x;
        int n = y;
        int currentPos;

        // castle check white king
        if (position == 60 && getFilename(position).charAt(1) == 'w') {
            boolean obstacle = false;

            // right rook
            if (!rookFlag[3]) {
                while (++i < 8 && !obstacle) {
                    currentPos = i + (8 * n);
                    if (getFilename(currentPos).charAt(0) != 't' || attackedSquares[currentPos] > 1) {
                        obstacle = true;
                    }
                    else {
                        if (i == 7 && kingSafety(currentPos, position))
                            possibleMoves[position + 2] = true;
                    }
                }
            }

            // left rook
            if (!rookFlag[2]) {
                while ((--i >= 0) && !obstacle) {
                    currentPos = i + (8 * n);
                    if (getFilename(currentPos).charAt(0) != 't' || attackedSquares[currentPos] > 1) {
                        obstacle = true;
                    }
                    else {
                        if (i == 0 && kingSafety(currentPos, position))
                            possibleMoves[position - 2] = true;
                    }
                }
            }
        } // end castle check white king

        // castle check black king
        else if (position == 4 && getFilename(position).charAt(1) == 'b') {
            boolean obstacle = false;

            // right rook
            if (!rookFlag[1]) {
                while (++i < 8 && !obstacle) {
                    currentPos = i + (8 * n);
                    if (getFilename(currentPos).charAt(0) != 't' || (attackedSquares[currentPos] == 1 || attackedSquares[currentPos] == 3)) {
                        obstacle = true;
                    }
                    else {
                        if (i == 7 && kingSafety(currentPos, position))
                            possibleMoves[position + 2] = true;
                    }
                }
            }

            // left rook
            if (!rookFlag[0]) {
                while ((--i >= 0) && !obstacle) {
                    currentPos = i + (8 * n);
                    if (getFilename(currentPos).charAt(0) != 't' || (attackedSquares[currentPos] == 1 || attackedSquares[currentPos] == 3)) {
                        obstacle = true;
                    }
                    else {
                        if (i == 0 && kingSafety(currentPos, position))
                            possibleMoves[position - 2] = true;
                    }
                }
            }
        } // end of castle check black

        i = x;
        n = y - 1;
        currentPos = i + 8 * n;
        if (n >= 0 && getFilename(currentPos).charAt(1) != color && attackedCheck(currentPos, color)) {
            if (!getFilename(currentPos).equals("ts"))
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                possibleMove = true;
            }
        }

        i = x + 1;
        n = y - 1;
        currentPos = i + 8 * n;
        if (i < 8 && n >= 0 && getFilename(currentPos).charAt(1) != color && attackedCheck(currentPos, color)) {
            if (!getFilename(currentPos).equals("ts"))
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                possibleMove = true;
            }
        }

        i = x + 1;
        n = y;
        currentPos = i + 8 * n;
        if (i < 8 && getFilename(currentPos).charAt(1) != color && attackedCheck(currentPos, color)) {
            if (!getFilename(currentPos).equals("ts"))
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                possibleMove = true;
            }
        }

        i = x + 1;
        n = y + 1;
        currentPos = i + 8 * n;
        if (i < 8 && n < 8 && getFilename(currentPos).charAt(1) != color && attackedCheck(currentPos, color)) {
            if (!getFilename(currentPos).equals("ts"))
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                possibleMove = true;
            }
        }

        i = x;
        n = y + 1;
        currentPos = i + 8 * n;
        if (n < 8 && getFilename(currentPos).charAt(1) != color && attackedCheck(currentPos, color)) {
            if (!getFilename(currentPos).equals("ts"))
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                possibleMove = true;
            }
        }

        i = x - 1;
        n = y + 1;
        currentPos = i + 8 * n;
        if (i >= 0 && n < 8 && getFilename(currentPos).charAt(1) != color && attackedCheck(currentPos, color)) {
            if (!getFilename(currentPos).equals("ts"))
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                possibleMove = true;
            }
        }

        i = x - 1;
        n = y;
        currentPos = i + 8 * n;
        if (i >= 0 && getFilename(currentPos).charAt(1) != color && attackedCheck(currentPos, color)) {
            if (!getFilename(currentPos).equals("ts"))
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                possibleMove = true;
            }
        }

        i = x - 1;
        n = y - 1;
        currentPos = i + 8 * n;
        if (i >= 0 && n >= 0 && getFilename(currentPos).charAt(1) != color && attackedCheck(currentPos, color)) {
            if (!getFilename(currentPos).equals("ts"))
                getCell(currentPos).setBackgroundResource(R.drawable.redbackground);
            if (kingSafety(currentPos, position)) {
                possibleMoves[currentPos] = true;
                possibleMove = true;
            }
        }

        return possibleMove;
    }

    public void setSquareValue(int currentPos, int color) {
        if (color == 'w') {
            if (attackedSquares[currentPos] == 0)
                attackedSquares[currentPos] = 1;
            else if (attackedSquares[currentPos] == 2)
                attackedSquares[currentPos] = 3;
        } else {
            if (attackedSquares[currentPos] == 0)
                attackedSquares[currentPos] = 2;
            else if (attackedSquares[currentPos] == 1)
                attackedSquares[currentPos] = 3;
        }
    }

    public void setAttackedSquares(int position, int color) {
        int x = position % 8;
        int y = position / 8;

        int i = x;
        int n = y - 1;
        int currentPos = i + 8 * n;
        if (n >= 0) {
            setSquareValue(currentPos, color);
        }

        i = x + 1;
        n = y - 1;
        currentPos = i + 8 * n;
        if (i < 8 && n >= 0) {
            setSquareValue(currentPos, color);
        }

        i = x + 1;
        n = y;
        currentPos = i + 8 * n;
        if (i < 8) {
            setSquareValue(currentPos, color);
        }

        i = x + 1;
        n = y + 1;
        currentPos = i + 8 * n;
        if (i < 8 && n < 8) {
            setSquareValue(currentPos, color);
        }

        i = x;
        n = y + 1;
        currentPos = i + 8 * n;
        if (n < 8) {
            setSquareValue(currentPos, color);
        }

        i = x - 1;
        n = y + 1;
        currentPos = i + 8 * n;
        if (i >= 0 && n < 8) {
            setSquareValue(currentPos, color);
        }

        i = x - 1;
        n = y;
        currentPos = i + 8 * n;
        if (i >= 0) {
            setSquareValue(currentPos, color);
        }

        i = x - 1;
        n = y - 1;
        currentPos = i + 8 * n;
        if (i >= 0 && n >= 0) {
            setSquareValue(currentPos, color);
        }
    }
}

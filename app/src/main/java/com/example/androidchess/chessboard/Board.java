package com.example.androidchess.chessboard;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.Pieces.*;

public class Board {
    private Square[][] squares;

    public Board(int size ,GameActivity gameActivity, ConstraintLayout boardContainer) {
        this.squares = new Square[size][size];
        createSquares(gameActivity, boardContainer);
        ConstraintSet set = new ConstraintSet();
        set.clone(boardContainer);
        setConstraints(set, boardContainer);
        placeDefaultPieces();
    }

    YX firstPos = new YX(0, 0);
    int swapCounter = 0;

    public void move(YX pos) {

        // both squares has pieces and they are the same color
        // makes it responsive between clicks of same color
        if (this.getSquare(firstPos).hasPiece() && this.getSquare(pos).hasPiece()) {
            if (this.getSquare(firstPos).getPiece().isWhite() == this.getSquare(pos).getPiece().isWhite()) {

                swapCounter = 0;
                resetBackgrounds();
            }
        }

        if (++swapCounter == 1 && this.getSquare(pos).hasPiece() && turnCheck(pos)) {
            firstPos = pos;
            this.getSquare(pos).setBackgroundColor(Color.parseColor("#00FFFF"));
        }
        // a legal move is made
        else if (swapCounter == 2 && legalMove(pos)) {

            swapCounter = 0;
            swapTurn();
            resetBackgrounds();
        }
        //
        else {
            swapCounter = 0;
            resetBackgrounds();
        }
    }

    public void swap(YX firstPos, YX secondPos) {

        Square temp = this.getSquare(firstPos);

        this.setSquare(firstPos, this.getSquare(secondPos));

        this.setSquare(secondPos, temp);
    }



    public boolean turnCheck(YX pos) {

        if (this.getSquare(pos).hasPiece() && this.getSquare(pos).getPiece().isWhite() && GameInfo.get().whiteTurn)
            return true;
        else if (this.getSquare(pos).hasPiece() && !this.getSquare(pos).getPiece().isWhite() && !GameInfo.get().whiteTurn)
            return true;
        else
            return false;
    }

    public void swapTurn() {
        if (GameInfo.get().whiteTurn) {
            GameInfo.get().whiteTurn = false;
        } else {
            GameInfo.get().whiteTurn = true;
            GameInfo.get().fullMoveCounter++;
        }
    }

    public boolean legalMove(YX pos) {
        return GameInfo.get().possibleMoves[pos.y][pos.x];
    }

    public void resetBackgrounds() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (y % 2 == 0) {
                    if (x % 2 == 0)
                        squares[y][x].setBackgroundColor(Color.parseColor("#45515F"));
                    else
                        squares[y][x].setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    if (x % 2 == 0)
                        squares[y][x].setBackgroundColor(Color.parseColor("#FFFFFF"));
                    else
                        squares[y][x].setBackgroundColor(Color.parseColor("#45515F"));
                }
            }
        }
    }

    public void createSquares(GameActivity gameActivity, ConstraintLayout boardContainer) {
        DisplayMetrics metrics = gameActivity.getResources().getDisplayMetrics();

        int width = metrics.widthPixels;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                char a = 'a';
                String coordinate = "" + ((char) (a + y)) + (x + 1);
                System.out.println(coordinate);
                squares[y][x] = new Square(gameActivity, coordinate);
                boardContainer.addView(squares[y][x]);
                squares[y][x].setId(View.generateViewId());
                squares[y][x].getLayoutParams().width = width / 8;
                squares[y][x].getLayoutParams().height = width / 8;
                //board[y][x].setClickable(true);

                if (y % 2 == 0) {
                    if (x % 2 == 0)
                        squares[y][x].setBackgroundColor(Color.parseColor("#45515F"));
                    else
                        squares[y][x].setBackgroundColor(Color.parseColor("#FFFFFF"));
                } else {
                    if (x % 2 == 0)
                        squares[y][x].setBackgroundColor(Color.parseColor("#FFFFFF"));
                    else
                        squares[y][x].setBackgroundColor(Color.parseColor("#45515F"));
                }

                final YX yx = new YX(y, x);

                // TODO major game logic inside onClick
                squares[y][x].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        move(yx);
                    }
                });
            }
        }
    }

        public void setConstraints(ConstraintSet set, ConstraintLayout layout) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (x == 0) {
                    // connect left of source square to left edge
                    set.connect(squares[y][x].getId(), ConstraintSet.LEFT, layout.getId(), ConstraintSet.LEFT, 0);
                    // connect right of source square to left of right neighbor
                    set.connect(squares[y][x].getId(), ConstraintSet.RIGHT, squares[y][x + 1].getId(), ConstraintSet.LEFT, 0);

                } else if (x == 7) {
                    // connect left of source square to right of left neighbor
                    set.connect(squares[y][x].getId(), ConstraintSet.LEFT, squares[y][x - 1].getId(), ConstraintSet.RIGHT, 0);
                    // connect right of source square to right edge
                    set.connect(squares[y][x].getId(), ConstraintSet.RIGHT, layout.getId(), ConstraintSet.RIGHT, 0);
                } else {
                    // connect right of source square to left of right neighbor
                    set.connect(squares[y][x].getId(), ConstraintSet.LEFT, squares[y][x - 1].getId(), ConstraintSet.RIGHT, 0);
                    // connect left of source square to right of left neighbor
                    set.connect(squares[y][x].getId(), ConstraintSet.RIGHT, squares[y][x + 1].getId(), ConstraintSet.LEFT, 0);
                }

                if (y == 0) {
                    // connect bottom of source square to bottom edge
                    set.connect(squares[y][x].getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM, 0);
                    // connect top of souce square to bottom of top neighbor
                    set.connect(squares[y][x].getId(), ConstraintSet.TOP, squares[y + 1][x].getId(), ConstraintSet.BOTTOM, 0);
                } else if (y == 7) {
                    // connect bottom of source square to top of bottom neighbor
                    set.connect(squares[y][x].getId(), ConstraintSet.BOTTOM, squares[y - 1][x].getId(), ConstraintSet.TOP, 0);
                    // connect top of souce square to top edge
                    set.connect(squares[y][x].getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP, 0);
                } else {
                    // connect bottom of source square to top of bottom neighbor
                    set.connect(squares[y][x].getId(), ConstraintSet.BOTTOM, squares[y - 1][x].getId(), ConstraintSet.TOP, 0);
                    // connect top of souce square to bottom of top neighbor
                    set.connect(squares[y][x].getId(), ConstraintSet.TOP, squares[y + 1][x].getId(), ConstraintSet.BOTTOM, 0);
                }
                set.applyTo(layout);
            }
        }
    }

    public void placeDefaultPieces() {

        squares[0][0].setPiece(new Rook(true));
        squares[0][1].setPiece(new Bishop(true));
        squares[0][2].setPiece(new Knight(true));
        squares[0][3].setPiece(new Queen(true));
        squares[0][4].setPiece(new King(true));
        squares[0][5].setPiece(new Knight(true));
        squares[0][6].setPiece(new Bishop(true));
        squares[0][7].setPiece(new Rook(true));

        for (int x = 0; x < 8; x++) {
            squares[1][x].setPiece(new Pawn(true));
        }

        for (int y = 2; y < 6; y++) {
            for (int x = 0; x < 8; x++) {
                squares[y][x].setImageResource(R.drawable.ts);
                squares[y][x].setAlpha(0f);
            }
        }

        for (int x = 0; x < 8; x++) {
            squares[6][x].setPiece(new Pawn(false));
        }


        squares[7][0].setPiece(new Rook(false));
        squares[7][1].setPiece(new Bishop(false));
        squares[7][2].setPiece(new Knight(false));
        squares[7][3].setPiece(new Queen(false));
        squares[7][4].setPiece(new King(false));
        squares[7][5].setPiece(new Knight(false));
        squares[7][6].setPiece(new Bishop(false));
        squares[7][7].setPiece(new Rook(false));

    }

    public Square getSquare(YX yx) {
        return squares[yx.y][yx.x];
    }

    public Square getSquare(int y, int x) {
        return squares[y][x];
    }

    public void setSquare(YX yx, Square square) {
        squares[yx.y][yx.x] = square;
    }

}

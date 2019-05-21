package com.example.androidchess.chessboard;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.Pieces.*;

import java.util.LinkedList;

public class Board {
    private Square[][] squares;
    public BoardState boardState;

    public boolean[][] possibleClicks = new boolean[8][8];

    public Board(GameActivity gameActivity, ConstraintLayout boardContainer) {
        this.squares = new Square[8][8];
        createSquares(gameActivity, boardContainer);
        ConstraintSet set = new ConstraintSet();
        set.clone(boardContainer);
        setConstraints(set, boardContainer);
        setBoardState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        boardState.calcAllPossibleMoves();
    }

    YX firstPos = new YX(4, 4);
    int swapCounter = 0;

    // function for a square click
    public void move(YX pos) {
        Timer timer = new Timer();

        timer.startTimer();

        // both squares has pieces and they are the same color
        // makes it responsive between clicks of same color
        if (boardState.hasPiece(firstPos) && boardState.hasPiece(pos)) {
            if (boardState.getPiece(firstPos).isWhite() == boardState.getPiece(pos).isWhite()) {

                swapCounter = 0;
                clearVisibleMoves();
            }
        }

        // first click on a piece
        if (++swapCounter == 1 && boardState.hasPiece(pos) && turnCheck(pos)) {
            firstPos = pos;
            this.getSquare(pos).setBackgroundColor(Color.parseColor("#00FFFF"));
            showPossibleMoves(pos);
            //System.out.println(boardState.getPiece(pos).getMoves().toString());
            System.out.println(boardState);
        }
        // a legal move is made
        else if (swapCounter == 2 && legalMove(pos)) {
            boardState.doLegalMove(new Move(firstPos, pos, boardState.getPiece(firstPos)));
            updateBoard(boardState);

            swapCounter = 0;
            //swapTurn();

            clearVisibleMoves();
        }
        //
        else {
            swapCounter = 0;
            clearVisibleMoves();
        }

        timer.stopTimer();
        //System.out.println(timer.retreiveTime());
    }

    public void swap(YX firstPos, YX secondPos) {

        Square temp = this.getSquare(firstPos);

        this.setSquare(firstPos, this.getSquare(secondPos));

        this.setSquare(secondPos, temp);
    }

    public void updateBoard(BoardState boardState) {
        YX currentPos = new YX(0, 0);

        System.out.println("updateboard");
        for (; currentPos.y < 8; currentPos.y++) {
            for (currentPos.x = 0; currentPos.x < 8; currentPos.x++) {
                //System.out.println(currentPos);
                this.getSquare(currentPos).setPiece(boardState.getPiece(currentPos));
            }
        }
        //board.redrawViews();
    }

    public void setBoardState(String FENStr) {
        boardState = new BoardState(FENStr);
        updateBoard(boardState);
    }

    public void setBoardState(BoardState boardState) {
        this.boardState = boardState;
        updateBoard(this.boardState);
    }

    // fen string example
    // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    // board positions | whos turn | castle options | en passant | half move counter | full move counter
    public String getFENstring(BoardState boardState) {
        String FENStr = "";

        // board positions
        int emptyCellCounter = 0;
        // start at y = 8, x = 0
        YX currentPos = new YX(7, 0);
        for (; currentPos.y >= 0; currentPos.y--) {
            for (; currentPos.x < 8; currentPos.x++) {

                if (boardState.hasPiece(currentPos)) {
                    if (emptyCellCounter != 0)
                        FENStr += Integer.toString(emptyCellCounter);

                    Piece piece = boardState.getPiece(currentPos);
                    if (piece instanceof Rook) {
                        FENStr += (piece.isWhite() ? "R" : "r");
                    } else if (piece instanceof Queen) {
                        FENStr += (piece.isWhite() ? "Q" : "q");
                    } else if (piece instanceof Bishop) {
                        FENStr += (piece.isWhite() ? "B" : "b");
                    } else if (piece instanceof Knight) {
                        FENStr += (piece.isWhite() ? "N" : "n");
                    } else if (piece instanceof King) {
                        FENStr += (piece.isWhite() ? "K" : "k");
                    } else if (piece instanceof Pawn) {
                        FENStr += (piece.isWhite() ? "P" : "p");
                    }
                } else {
                    emptyCellCounter++;
                }
            }
            if (emptyCellCounter != 0)
                FENStr += Integer.toString(emptyCellCounter);
            FENStr += "/";

        }

        if (boardState.isWhiteTurn())
            FENStr += " w ";
        else
            FENStr += " b ";

        // castle
        boolean castleAvailable = false;
        if (!boardState.getCastleFlag(3)) {
            castleAvailable = true;
            FENStr += "K";
        }
        if (!boardState.getCastleFlag(2)) {
            castleAvailable = true;
            FENStr += "Q";
        }
        if (!boardState.getCastleFlag(1)) {
            castleAvailable = true;
            FENStr += "k";
        }
        if (!boardState.getCastleFlag(0)) {
            castleAvailable = true;
            FENStr += "q";
        }
        if (!castleAvailable) {
            FENStr += "- ";
        }

        // enpassant
        YX enpassantPos = boardState.getEnPassantPos();
        if (enpassantPos.y != -1)
            FENStr += this.getSquare(enpassantPos).coordinate + " ";
        else {
            FENStr += "- ";
        }

        // half move counter
        //TODO half move counter implementation
        FENStr += "0 ";

        // full move counter
        FENStr += Integer.toString(boardState.getFullMoveCounter());

        return FENStr;
    }

    public boolean turnCheck(YX pos) {
        if (boardState.hasPiece(pos)) {
            if (boardState.getPiece(pos).isWhite() && boardState.isWhiteTurn())
                return true;
            else if (!boardState.getPiece(pos).isWhite() && !boardState.isWhiteTurn())
                return true;
        }
        return false;
    }

    /*
    public void swapTurn() {
        if (boardState.isWhiteTurn()) {
            boardState.setWhiteTurn(false);
        } else {
            boardState.setWhiteTurn(true);
            boardState.incrementFullMoveCounter();
        }
    }
    */

    public void showPossibleMoves(YX sourcePos) {
        LinkedList<Move> listOfMoves = boardState.getPiece(sourcePos).getMoves();
        //System.out.println("object: "+System.identityHashCode(boardState.getPiece(sourcePos)));
        //System.out.println("list: "+System.identityHashCode(listOfMoves));
        //System.out.println(listOfMoves.toString());
        for (Move move : listOfMoves) {
            if (boardState.hasPiece(move.destination)) {
                this.getSquare(move.destination).setBackgroundColor(Color.parseColor("#FF0000"));
            } else
                this.getSquare(move.destination).setAlpha(1f);

            setPossibleClick(move.destination);
        }
    }

    public void resetPossibleClicks() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++)
                possibleClicks[y][x] = false;
        }
    }

    public void setPossibleClick(YX position) {
        possibleClicks[position.y][position.x] = true;
    }

    public boolean legalMove(YX pos) {
        return possibleClicks[pos.y][pos.x];
    }

    public void clearVisibleMoves() {
        resetBackgrounds();
        YX currentPos = new YX(0, 0);
        for (currentPos.y = 0; currentPos.y < 8; currentPos.y++) {
            for (currentPos.x = 0; currentPos.x < 8; currentPos.x++) {
                if (boardState.hasPiece(currentPos))
                    squares[currentPos.y][currentPos.x].setAlpha(1f);
                else
                    squares[currentPos.y][currentPos.x].setAlpha(0f);
            }
        }
        resetPossibleClicks();
        //redrawViews();
    }

    public void resetBackgrounds() {
        for (Square[] sArray : squares) {
            for (Square square : sArray) {
                square.setBackgroundResource(0);
            }
        }
    }

    /*
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
    */

    public void createSquares(GameActivity gameActivity, ConstraintLayout boardContainer) {
        DisplayMetrics metrics = gameActivity.getResources().getDisplayMetrics();

        int width = metrics.widthPixels;

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                char a = 'a';
                String coordinate = "" + ((char) (a + y)) + (x + 1);
                //System.out.println(coordinate);
                squares[y][x] = new Square(gameActivity, coordinate);
                boardContainer.addView(squares[y][x]);
                squares[y][x].setId(View.generateViewId());
                squares[y][x].getLayoutParams().width = width / 8;
                squares[y][x].getLayoutParams().height = width / 8;
                //board[y][x].setClickable(true);

                /*
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
                */

                final YX yx = new YX(y, x);

                // TODO major game logic inside onClick
                squares[y][x].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String fen = getFENstring(boardState);
                        move(yx);
                        if (!boardState.isWhiteTurn()) {
                            if (fen != getFENstring(boardState)) {
                                //String rootFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
                                String rootFen = boardState.getFENString();
                                Node root = new Node(rootFen);
                                int increment=root.DEPTH;

                                MinMax minMax = new MinMax();
                                LinkedList<Node>bottomBIATCH=new LinkedList<>();
                                int h=0;
                                    for (Node node: root.children) {
                                        if (increment==1){
                                            h=node.children.size();
                                            bottomBIATCH.clear();
                                            bottomBIATCH=node.children;
                                        }
                                    for (Node node1: node.children) {
                                  if (increment==2){
                                      h=node1.children.size();
                                      bottomBIATCH.clear();
                                      bottomBIATCH=node1.children;
                                  }
                                    for (Node node2:node1.children){
                                     if (increment==3){
                                         h=node2.children.size();
                                         bottomBIATCH=node2.children;
                                     break;
                                     }
                                        }
                                    }
                                }


                                int res = minMax.minimax(increment, 0,bottomBIATCH , h);
                                System.out.println("Testing:  " + res);
                                root.children.get(res).boardState.printBoardState();
                                boardState = new BoardState(root.children.get(res).boardState.getFENString());
                                updateBoard(boardState);
                                clearVisibleMoves();
                            }
                        }
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
                squares[y][x].setAlpha(1f);
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

    public void redrawViews() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                squares[y][x].postInvalidate();
            }
        }
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

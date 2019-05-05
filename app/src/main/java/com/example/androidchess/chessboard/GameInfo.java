package com.example.androidchess.chessboard;

import com.example.androidchess.chessboard.Pieces.King;

public class GameInfo {
    public static GameInfo info;

    private GameInfo() {
    }

    public static GameInfo get() {
        if (info == null) {
            info = new GameInfo();
        }
        return info;
    }

    public static void reset() {
        info = null;
    }

    /*
    set the game info/state using fen notation
    fen string example:
    rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    board positions | who's turn | castle options | en passant | half move counter | full move counter
    */
    public void setGameState(String fenStr) {

    }

    // board
    public Board board;

    // flags
    public boolean whiteTurn;
    public boolean checkMate;

    // postition where enpassant is possible
    public YX enPassantPos;

    // coordinate of last move. Used when king is placed in check since only the last move can place kings in check
    public int lastMovePos;

    // full move is when both sides has made a move
    // increment everytime black makes a move
    public int fullMoveCounter;


    /*
    used to calc/display the possible moves for a clicked piece
    */
    public boolean[][] possibleMoves;
    public void resetPossibleMoves() {
        for (int y=0; y < 8; y++) {
            for (int x=0; x<8; x++)
                possibleMoves[y][x] = false;
        }
    }
    public void possibleToMove(YX position) {
        possibleMoves[position.y][position.x] = true;
    }

    /*
    calcs the squares who are attacking the king
     */
    public boolean[][] kingAttacker;
    public void setKingAttackTrue(YX position) {
        kingAttacker[position.y][position.x] = true;
    }

    /*
    0 = no attacker
    1 = only white attacker
    2 = only black attacker
    3 = attacked by both sides
    */
    public int[][] attackedSquares;

    public void resetAttackedSquares() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++)
                attackedSquares[y][x] = 0;
        }
    }

    public void calcAllAttackedSquares() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                YX currentPos = new YX(y, x);
                if (board.getSquare(currentPos).hasPiece())
                    board.getSquare(currentPos).getPiece().calcAttackedSquares(currentPos);
            }
        }
    }

    /*
    [0] = coordinate of white king
    [1] = coordinate of black king
    */
    public YX[] kingPos;

    public void updatePosOfKings() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                YX currentPos = new YX(y,x);
                Square sq = board.getSquare(currentPos);
                if (sq.hasPiece()) {
                    if(sq.getPiece() instanceof King) {
                        if (sq.getPiece().isWhite())
                            kingPos[0] = currentPos;
                        else
                            kingPos[1] = currentPos;
                    }
                }
            }
        }
    }

    /*
    flag for castling if the king has moved.
    [0] = white king
    [1] = black king
    */
    public boolean[] kingMoved;

    /*
    flag for castling if the rook has moves
    [0] = left black rook
    [1] = right black rook
    [2] = left white rook
    [3] = right white rook
     */
    public boolean[] rookMoved;

    String winner, winCondition;

}

package com.example.androidchess.chessboard;

/*
contains game info for the visible game
*/
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

    public String winner, winCondition;

    public ChessClock blackClock, whiteClock;



    /*
    set the game info/state using fen notation
    fen string example:
    rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
    board positions | who's turn | castle options | en passant | half move counter | full move counter
    */

    // board state used for calculations
    //public BoardState boardState;
    // also updates the view

    // visible board
    //public Board board;


    /*
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
    */

    /*
    used to calc/display the possible moves for a clicked piece
    */
/*
    public boolean[][] possibleMoves = new boolean[8][8];
    public void resetPossibleMoves() {
        for (int y=0; y < 8; y++) {
            for (int x=0; x<8; x++)
                possibleMoves[y][x] = false;
        }
    }
    public void setPossibleMove(YX position) {
        possibleMoves[position.y][position.x] = true;
    }
*/

    /*
    calcs the squares who are attacking the king

    public boolean[][] kingAttacker;
    public void setKingAttackTrue(YX position) {
        kingAttacker[position.y][position.x] = true;
    }
    */

    /*
    0 = no attacker
    1 = only white attacker
    2 = only black attacker
    3 = attacked by both sides

    public int[][] attackedSquaresss;

    public void resetAttackedSquares() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++)
                attackedSquaresss[y][x] = 0;
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
    */

    /*
    [0] = coordinate of white king
    [1] = coordinate of black king

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
    */

    /*
    flag for castling
    [0] = black queen side castle
    [1] = black king side castle
    [2] = white queen side castle
    [3] = white king side castle
     */
    /*
    public boolean[] castleFlag = new boolean[4];
    */
}

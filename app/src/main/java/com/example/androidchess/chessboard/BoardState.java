package com.example.androidchess.chessboard;

import com.example.androidchess.chessboard.Pieces.*;

import java.util.Arrays;
import java.util.LinkedList;

public class BoardState {
    // logical presentation of the board
    Piece[][] squares;

    // flags
    private boolean whiteTurn;
    private boolean checkMate;

    // postition where enpassant is possible
    private YX enPassantPos;

    //public YX promotionPos;


    // full move is when both sides has made a move
    // increment everytime black makes a move
    private int fullMoveCounter;

    /*
    0 = no attacker
    1 = only white attacker
    2 = only black attacker
    3 = attacked by both sides
    */
    //public int[][] attackedSquares;

    /*
    public int getAttackedSquare(YX position) {
        return attackedSquares[position.y][position.x];
    }
    */

    /*
    [0] = coordinate of white king
    [1] = coordinate of black king
    */
    private YX[] kingPos;

    /*
    calculates the squares who are attacking the king
    */
    //private boolean[][] kingAttacker;

    /*
    flag for castling
    [0] = black queen side castle
    [1] = black king side castle
    [2] = white queen side castle
    [3] = white king side castle
     */
    private boolean[] castleFlag;

    // is only used by UI for the player turn
    //public boolean[][] possibleCaptures;


    /*
    creates board with a fenString
     */
    public BoardState(String FENStr) {
        this.squares = new Piece[8][8];
        //this.possibleCaptures = new boolean[8][8];
        this.enPassantPos = new YX(0 ,0);
        this.castleFlag = new boolean[4];
        this.kingPos = new YX[2];
        this.kingPos[0] = new YX(0, 4);
        this.kingPos[1] = new YX(7, 4);
        this.checkMate = false;

        // fen string example
        // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
        // board positions | who's turn | castle options | en passant | half move counter | full move counter
        // q = black queen, Q = white queen

        boolean stop = false;
        int strIndex = 0;
        YX arrayPos = new YX(7, 0);
        while (!stop) {
            //System.out.println(fenString.charAt(strIndex));
            //System.out.println(arrayPos);
            switch (FENStr.charAt(strIndex)) {
                case 'q':
                    this.setPiece(new Queen(false), arrayPos);
                    break;
                case 'Q':
                    this.setPiece(new Queen(true), arrayPos);
                    break;
                case 'k':
                    this.setPiece(new King(false), arrayPos);
                    break;
                case 'K':
                    this.setPiece(new King(true), arrayPos);
                    break;
                case 'n':
                    this.setPiece(new Knight(false), arrayPos);
                    break;
                case 'N':
                    this.setPiece(new Knight(true), arrayPos);
                    break;
                case 'b':
                    this.setPiece(new Bishop(false), arrayPos);
                    break;
                case 'B':
                    this.setPiece(new Bishop(true), arrayPos);
                    break;
                case 'r':
                    this.setPiece(new Rook(false), arrayPos);
                    break;
                case 'R':
                    this.setPiece(new Rook(true), arrayPos);
                    break;
                case 'p':
                    this.setPiece(new Pawn(false), arrayPos);
                    break;
                case 'P':
                    this.setPiece(new Pawn(true), arrayPos);
                    break;
                case ' ':
                    stop = true;
                    //System.out.println("stop");
                    break;
                case '/':
                    arrayPos.x = -1;
                    arrayPos.y--;
                    break;
                default:
                    for (int t = 0; t < Character.getNumericValue(FENStr.charAt(strIndex)); t++) {
                        squares[arrayPos.y][arrayPos.x++] = null;
                    }
                    arrayPos.x--;
                    break;
            }
            strIndex++;
            arrayPos.x++;
        }

        if (FENStr.charAt(strIndex) == 'w')
            whiteTurn = true;
        else
            whiteTurn = false;

        strIndex += 2;
        stop = false;

        if (FENStr.charAt(strIndex) == '-')
            stop = true;
        while (!stop) {
            switch (FENStr.charAt(strIndex)) {
                case 'K':
                    castleFlag[3] = false;
                    break;
                case 'Q':
                    castleFlag[2] = false;
                    break;
                case 'k':
                    castleFlag[1] = false;
                    break;
                case 'q':
                    castleFlag[0] = false;
                    break;
                case ' ':
                    stop = true;
                    break;
            }
            //System.out.println(fenString.charAt(strIndex));
            strIndex++;
        }

        // rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq f6 0 1
        // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1

        enPassantPos = new YX(0, 0);

        if (FENStr.charAt(strIndex) == '-') {
            enPassantPos.x = -1;
            enPassantPos.y = -1;
        } else {

            int x = FENStr.charAt(strIndex);
            int y = Character.getNumericValue(FENStr.charAt(++strIndex));
            x = x - 'a';
            enPassantPos.x = x;
            enPassantPos.y = y;
        }

        strIndex += 2;
        // half time clock inbetween

        //fenNotation.length()-1
        fullMoveCounter = Character.getNumericValue(FENStr.charAt(FENStr.length() - 1));

        calcAllPossibleMoves();
    }

    /*
    creates a new boardstate with old boardstate and a move
     */
    public BoardState(BoardState parentBoardState, Move move) {
        //this.possibleCaptures = parentBoardState.possibleCaptures;
        this.squares = new Piece[8][8];
        this.enPassantPos = new YX(-1, -1);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                this.squares[y][x] = parentBoardState.squares[y][x];
            }
        }

        this.checkMate = false;
        this.whiteTurn = parentBoardState.whiteTurn;
        this.castleFlag = Arrays.copyOf(parentBoardState.castleFlag, parentBoardState.castleFlag.length);
        //this.fullMoveCounter = parentBoardState.fullMoveCounter + 1;
        this.kingPos = new YX[2];

        this.kingPos[0] = new YX(parentBoardState.kingPos[0].y, parentBoardState.kingPos[0].x);
        this.kingPos[1] = new YX(parentBoardState.kingPos[1].y, parentBoardState.kingPos[1].x);

        this.doLegalMove(move);

        /*
        YX currentPos = new YX(0, 0);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                currentPos.y = y;
                currentPos.x = x;
                if (this.hasPiece(currentPos)) {
                    this.getPiece(currentPos).calcPossibleMoves(currentPos, parentBoardState);
                }
            }
        }
        */

    }

    // tree search
    public BoardState getNewBoardState(Move move) {
        return new BoardState(this, move);
    }

    public LinkedList<Move> getAllMoves() {
        LinkedList<Move> listOfMoves = new LinkedList<>();
        for (Piece[] pArray: squares) {
            for (Piece piece: pArray) {
                if (piece != null) {
                    if (whiteTurn) {
                        if (piece.isWhite())
                            listOfMoves.addAll(piece.getMoves());
                    }
                    else {
                        if (!piece.isWhite())
                            listOfMoves.addAll(piece.getMoves());
                    }
                }
            }
        }

        //System.out.println(listOfMoves);
        return listOfMoves;
    }

    public void calcAllPossibleMoves() {
        this.clearPossibleMoves();

        YX currentPos = new YX(0, 0);
        for (currentPos.y = 0; currentPos.y < 8; currentPos.y++) {
            for (currentPos.x = 0; currentPos.x < 8; currentPos.x++) {
                if (this.hasPiece(currentPos)) {
                    Piece piece = this.getPiece(currentPos);
                    if (whiteTurn) {
                        if (piece.isWhite())
                            piece.calcPossibleMoves(currentPos, this);
                    }
                    else {
                        if (!piece.isWhite())
                            piece.calcPossibleMoves(currentPos, this);
                    }
                }
            }
        }
    }

    public void clearPossibleMoves() {
        for (Piece[] pArray : squares) {
            for (Piece piece : pArray) {
                if (piece != null)
                    piece.clearMoves();
            }
        }
    }

    public void doLegalMove(Move move) {
        // pawn
        if (move.piece instanceof Pawn) {
            pawnMoveLogic(move);
        }
        // king
        else if (move.piece instanceof King) {
            kingMoveLogic(move);
        }
        // rook
        else if (move.piece instanceof Rook) {
            rookMoveLogic(move);
        }

        movePiece(move);
        this.whiteTurn = !whiteTurn;

        this.calcAllPossibleMoves();

        if (isCheckMate())
            System.out.println("CHECKMATE!!!");
    }

    private void pawnMoveLogic(Move move) {
        if (move.source.y - move.destination.y == 2) {
            this.enPassantPos = new YX(move.source.y, move.source.x);
            this.enPassantPos.y--;
        } else if (move.source.y - move.destination.y == -2) {
            this.enPassantPos = new YX(move.source.y, move.source.x);
            this.enPassantPos.y++;
        }
    }

    private void kingMoveLogic(Move move) {
        // checking if the move is a castle move
        if (move.source.x - move.destination.x > 1) {
            // white queen side
            if (move.piece.isWhite()) {
                YX rookPos = new YX(0, 0);
                this.movePiece(new Move(rookPos, new YX(0, 3), getPiece(rookPos)));
            }
            // black queen side
            else {
                YX rookPos = new YX(7, 0);
                this.movePiece(new Move(rookPos, new YX(7, 3), getPiece(rookPos)));
            }
        } else if (move.source.x - move.destination.x < -1) {
            // white king side
            if (move.piece.isWhite()) {
                YX rookPos = new YX(0, 7);
                this.movePiece(new Move(rookPos, new YX(0, 5), getPiece(rookPos)));
            }
            // black king side
            else {
                YX rookPos = new YX(7, 7);
                this.movePiece(new Move(rookPos, new YX(7, 5), getPiece(rookPos)));
            }
        }

        // mark the castle flags and update the position of the moved king
        if (move.piece.isWhite()) {
            if (!this.castleFlag[2] || !this.castleFlag[3]) {
                this.castleFlag[2] = true;
                this.castleFlag[3] = true;
            }
            this.kingPos[0] = move.destination;
        } else {
            if (!this.castleFlag[0] || !this.castleFlag[1]) {
                this.castleFlag[1] = true;
                this.castleFlag[0] = true;
            }
            this.kingPos[1] = move.destination;
        }
    }

    private void rookMoveLogic(Move move) {
        if (move.piece.isWhite()) {
            if (move.source.y == 0) {
                // queen side white
                if (move.source.x == 0) {
                    this.castleFlag[2] = true;
                }
                // king side white
                else if (move.source.x == 8) {
                    this.castleFlag[3] = true;
                }
            }
        } else {
            if (move.source.y == 8) {
                // queen side black
                if (move.source.x == 0) {
                    this.castleFlag[0] = true;
                }
                // king side black
                else if (move.source.x == 8) {
                    this.castleFlag[1] = true;
                }
            }
        }
    }

    public void movePiece(Move move) {
        setPiece(null, move.source);
        setPiece(move.piece, move.destination);
    }

    public boolean isCheckMate() {
        int nrOfMoves = 0;
        YX currentPos = new YX(0, 0);
        for (currentPos.y = 0; currentPos.y < 8; currentPos.y++) {
            for (currentPos.x = 0; currentPos.x < 8; currentPos.x++) {
                if (this.hasPiece(currentPos)) {
                    if (whiteTurn) {
                        if (this.getPiece(currentPos).isWhite()) {
                            //System.out.println(this.getPiece(currentPos)+", "+this.getPiece(currentPos).getMoves().size()+", "+currentPos);
                            nrOfMoves += this.getPiece(currentPos).getMoves().size();
                        }
                    }
                    // black turn
                    else {
                        if (!this.getPiece(currentPos).isWhite()) {
                            //System.out.println(this.getPiece(currentPos)+", "+this.getPiece(currentPos).getMoves().size()+", "+currentPos);
                            nrOfMoves += this.getPiece(currentPos).getMoves().size();
                        }
                    }
                }
            }
        }

        //System.out.println("nrOfMoves: "+nrOfMoves);
        if(nrOfMoves == 0) {
            checkMate = true;
        }

        return this.checkMate;
    }

    /*
    public void resetAttackedSquares() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                attackedSquares[y][x] = 0;
            }
        }
    }
*/
    public int[][] calcAllAttackedSquares() {
        int[][] calcAllAttackedSquares = new int[8][8];

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                //calcAllAttackedSquares[y][x]
            }
        }

        return calcAllAttackedSquares;
    }

    public YX getKingPos(boolean isWhite) {
        if (isWhite)
            return kingPos[0];
        else
            return kingPos[1];
    }

    public void setKingPos(boolean isWhite, YX position) {
        if (isWhite)
            kingPos[0] = new YX(position.y, position.x);
        else
            kingPos[1] = new YX(position.y, position.x);
    }

    public boolean hasPiece(YX position) {
        /*
        if (position.y < 0 || position.y > 8) {
            System.out.println(position);
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            System.out.println(stackTraceElements[0].getMethodName());
            System.out.println(stackTraceElements[1].getMethodName());
            System.out.println(stackTraceElements[2].getMethodName());
            System.out.println(stackTraceElements[3].getMethodName());
            System.out.println(stackTraceElements[4].getMethodName());
        }
        */
        return squares[position.y][position.x] != null;
    }

    public Piece getPiece(YX position) {
        return squares[position.y][position.x];
    }

    public void setPiece(Piece piece, YX position) {
        squares[position.y][position.x] = piece;
    }

    public Piece[][] getSquares() {
        return squares;
    }

    public boolean getCastleFlag(int i) {
        return castleFlag[i];
    }

    public boolean[] getCastleFlags() {
        return castleFlag;
    }

    public void setCastleFlag(boolean[] castleFlag) {
        this.castleFlag = castleFlag;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    public YX getEnPassantPos() {
        return enPassantPos;
    }

    public void setEnPassantPos(YX enPassantPos) {
        this.enPassantPos = enPassantPos;
    }

    public int getFullMoveCounter() {
        return fullMoveCounter;
    }

    public void setFullMoveCounter(int fullMoveCounter) {
        this.fullMoveCounter = fullMoveCounter;
    }

    public void incrementFullMoveCounter() {
        this.fullMoveCounter++;
    }

    public void printBoardState() {
        String str = "boardState\n";
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x < 8; x++) {
                if (squares[y][x] != null)
                    str += "[" + squares[y][x].toString() + "] ";
                else
                    str += "[  ] ";

            }
            str += "\t" + y + "\n";
        }
        System.out.println(str);
    }
}

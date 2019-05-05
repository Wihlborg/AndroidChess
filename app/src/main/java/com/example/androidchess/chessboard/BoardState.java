package com.example.androidchess.chessboard;

import com.example.androidchess.chessboard.Pieces.*;

public class BoardState {
    Piece[][] squares;
    boolean whiteTurn;
    boolean[] castleFlag;
    YX enPassantPos;
    int fullMoveCounter;

    // is only used by UI for the player turn
    boolean[][] possibleCaptures;

    public BoardState(String fenString) {
        castleFlag = new boolean[4];
        for (int i = 0; i < castleFlag.length; i++)
            castleFlag[i] = false;

        // fen string example
        // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
        // board positions | who's turn | castle options | en passant | half move counter | full move counter
        // q = black queen, Q = white queen

        boolean stop = false;
        int strIndex = 0;
        YX arrayPos = new YX(0, 0);
        while (!stop) {
            switch (fenString.charAt(strIndex)) {
                case 'q':
                    squares[arrayPos.y][arrayPos.x] = new Queen(false);
                    break;
                case 'Q':
                    squares[arrayPos.y][arrayPos.x] = new Queen(true);
                    break;
                case 'k':
                    squares[arrayPos.y][arrayPos.x] = new King(false);
                    break;
                case 'K':
                    squares[arrayPos.y][arrayPos.x] = new King(true);
                    break;
                case 'n':
                    squares[arrayPos.y][arrayPos.x] = new Knight(false);
                    break;
                case 'N':
                    squares[arrayPos.y][arrayPos.x] = new Knight(true);
                    break;
                case 'b':
                    squares[arrayPos.y][arrayPos.x] = new Bishop(false);
                    break;
                case 'B':
                    squares[arrayPos.y][arrayPos.x] = new Bishop(true);
                    break;
                case 'r':
                    squares[arrayPos.y][arrayPos.x] = new Rook(false);
                    break;
                case 'R':
                    squares[arrayPos.y][arrayPos.x] = new Rook(true);
                    break;
                case 'p':
                    squares[arrayPos.y][arrayPos.x] = new Pawn(false);
                    break;
                case 'P':
                    squares[arrayPos.y][arrayPos.x] = new Pawn(true);
                    break;
                case ' ':
                    stop = true;
                    break;
                case '/':
                    arrayPos.x = 0;
                    arrayPos.y++;
                    break;
                default:
                    for (int t = 0; t < Character.getNumericValue(fenString.charAt(strIndex)); t++) {
                        /*
                        System.out.println("t:"+t);
                        System.out.println("n:"+gridPos);
                        System.out.println("----");
                        */
                        squares[arrayPos.y][arrayPos.x++] = null;
                    }
                    arrayPos.x--;
                    break;
            }
            strIndex++;
            arrayPos.x++;
        }

        if (fenString.charAt(strIndex) == 'w')
            whiteTurn = true;
        else
            whiteTurn = false;

        strIndex += 2;
        stop = false;

        if (fenString.charAt(strIndex) == '-')
            stop = true;
        while (!stop) {
            switch (fenString.charAt(strIndex)) {
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
            System.out.println(fenString.charAt(strIndex));
            strIndex++;
        }

        // rnbqkbnr/pppppppp/8/8/5P2/8/PPPPP1PP/RNBQKBNR b KQkq f6 0 1
        // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1

        //System.out.println("innan fitta"+fenNotation.charAt(charPos));
        if (fenString.charAt(strIndex) == '-') {
            enPassantPos.x = -1;
            enPassantPos.y = -1;
        }
        else {
            //System.out.println("fitta"+Character.toString(fenNotation.charAt(charPos))+ "dsa"+charPos);
            /*for (int i=(charPos-3); i <= (charPos+3); i++)
                System.out.println("kkk "+i+": "+Character.toString(fenNotation.charAt(i)));*/

            int x = fenString.charAt(strIndex);
            int y = Character.getNumericValue(fenString.charAt(++strIndex));
            //System.out.println("x: "+x);
            x = x - 'a';
            //System.out.println("x: "+x);
            //System.out.println("y: "+y);
            enPassantPos.x = x;
            enPassantPos.y = y;
            //System.out.println("inside setboard"+enPassantPos);
        }

        //setBoardGameState("pppppppp/kqbbnnrr/8/8/8/8/PPPPPPPP/RNBQKBNR b - e6 0 2");

        strIndex += 2;
        // half time clock inbetween

        //fenNotation.length()-1
        fullMoveCounter = Character.getNumericValue(fenString.charAt(fenString.length()-1));

    }

    public boolean hasPiece(YX position) {
        return squares[position.y][position.x] != null;
    }

    public Piece getPiece(YX position) {
        return squares[position.y][position.x];
    }

    public boolean[] getCastleFlag() {
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

    public void markPossibleCaptures(YX position) {
        this.possibleCaptures[position.y][position.x] = true;
    }
}

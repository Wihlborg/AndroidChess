package com.example.androidchess.chessboard;


import com.example.androidchess.R;
import com.example.androidchess.chessboard.Pieces.King;

public abstract class Piece {
    // for example black queen is = qb
    private boolean isWhite;
    private int ID;

    /*
    calculates the possible moves
    taking into account if the king is getting attacked if the move was to be made
    using kingSafety()
    */
    abstract public void calcPossibleMoves(YX sourcePos);

    /*
    calculates which square this piece attacks
    */
    abstract public void calcAttackedSquares(YX sourcePos);

    public void setSquareAttackValue(YX currentPos) {
        int[][] attackedSquares = GameInfo.get().attackedSquares;
        if (isWhite) {
            if (attackedSquares[currentPos.y][currentPos.x] == 0)
                attackedSquares[currentPos.y][currentPos.x] = 1;
            else if (attackedSquares[currentPos.y][currentPos.x] == 2)
                attackedSquares[currentPos.y][currentPos.x] = 3;
        } else {
            if (attackedSquares[currentPos.y][currentPos.x] == 0)
                attackedSquares[currentPos.y][currentPos.x] = 2;
            else if (attackedSquares[currentPos.y][currentPos.x] == 1)
                attackedSquares[currentPos.y][currentPos.x] = 3;
        }
    }

    /*
    calculates based on the lastmove made what squares are attacking the king
    used to calculate if other pieces can block the check created by the king attacker
    */

    abstract public void calcKingAttackingSquares();

    /*
    swaps places of pieces/empty squares and calculates if the king stands in check.
    removes piece temporarily and swaps to simulate a capture if current checked position contains a piece
    */
    public boolean kingSafety(YX currentPos, YX sourcePos) {
        Board board = GameInfo.get().board;

        boolean safe = true;
        //String imgName = board.getSquare(pos).getPiece().getName();
        //String imgName = getFilename(sourcePos);

        //int imgID = 0;
        Piece piece = null;

        // remove enemy piece temporarily
        if (board.getSquare(currentPos).hasPiece()) {
            //System.out.println(imgID);
            //System.out.println(R.drawable.pw);
            //imgID = board.getSquare(currentPos).getId();
            piece = board.getSquare(currentPos).getPiece();
            board.getSquare(currentPos).setPiece(null);
            board.getSquare(currentPos).setImageResource(R.drawable.ts);
        }


        board.swap(sourcePos, currentPos);
        // if piece is king
        if (board.getSquare(sourcePos).getPiece() instanceof King)
            GameInfo.get().updatePosOfKings();

        GameInfo.get().resetAttackedSquares();
        GameInfo.get().calcAllAttackedSquares();
        //printAttackedSquares();

        // if the square is white, is white's turn and if the king stands on a attacked square
        if (board.getSquare(sourcePos).getPiece().isWhite) {
            if (GameInfo.get().whiteTurn) {
                int y = GameInfo.get().kingPos[0].y;
                int x = GameInfo.get().kingPos[0].x;
                if (GameInfo.get().attackedSquares[y][x] > 1)
                    safe = false;
            }
        }
        // if the square is black, is black's turn and if the king stands on a attacked square
        else if (!board.getSquare(sourcePos).getPiece().isWhite) {
            if (!GameInfo.get().whiteTurn) {
                int y = GameInfo.get().kingPos[0].y;
                int x = GameInfo.get().kingPos[0].x;
                if (GameInfo.get().attackedSquares[y][x] == 1 || GameInfo.get().attackedSquares[y][x] == 3)
                    safe = false;
            }
        }

        board.swap(sourcePos, currentPos);
        // if piece is king
        if (board.getSquare(sourcePos).getPiece() instanceof King)
            GameInfo.get().updatePosOfKings();


        if (piece != null) {
            board.getSquare(currentPos).setPiece(piece);
            board.getSquare(currentPos).setImageResource(piece.getID());
        }

        GameInfo.get().resetAttackedSquares();
        GameInfo.get().calcAllAttackedSquares();
        //printAttackedSquares();

        //System.out.println(safe);
        return safe;
    }

    public String toString() {
        return Integer.toString(ID);
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}

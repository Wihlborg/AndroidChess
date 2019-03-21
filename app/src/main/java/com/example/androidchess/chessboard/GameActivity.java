package com.example.androidchess.chessboard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.pieces.*;

public class GameActivity extends AppCompatActivity {
    public static Rook rook = new Rook();
    public static Knight knight = new Knight();
    public static Pawn pawn = new Pawn();
    public static Bishop bishop = new Bishop();
    public static King king = new King();

    static GridView board;
    static ImageAdapter imageAdapter;
    public static boolean whiteTurn = true;
    public static boolean[] possibleMoves = new boolean[64];
    public static int[] attackedSquares = new int[64];
    public static int[] kingPos = new int[2];
    public static boolean checkMate = false;
    public static int lastMove = 0;
    public static boolean[] kingAttacker = new boolean[64];
    public static int enPassantPos = -1;
    public static boolean[] rookFlag = new boolean[4];
    int fullMoveCounter = 1;
    //public static Map<Integer, Boolean> rookFlag = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getSupportActionBar().hide();

        for (int i = 0; i < rookFlag.length; i++) {
            rookFlag[i] = false;
        }

        kingPos[0] = 60;
        kingPos[1] = 5;

        resetPossibleMoves();
        board = findViewById(R.id.board);
        imageAdapter = new ImageAdapter(this);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        int width = metrics.widthPixels;

        ImageView boardImg = findViewById(R.id.boardImg);
        boardImg.getLayoutParams().width = width;
        boardImg.getLayoutParams().height = width;

        board.setAdapter(imageAdapter);
        board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // true if white king is in checkAttackedSquares
                if (whiteTurn && attackedSquares[kingPos[0]] > 1 && !king.colorKingCheck(kingPos[0], 'w')) {
                    resetAttackedSquares();
                    checkAttackedSquares('w');
                    king.checkMate(kingPos[0]);
                    //printAttackedSquares();
                    //System.out.println("black checkAttackedSquares");
                    //Log.d("checkAttackedSquares","white king in checkAttackedSquares");

                }
                // true if black king is in checkAttackedSquares
                else if (!whiteTurn && (attackedSquares[kingPos[1]] == 1 || attackedSquares[kingPos[1]] == 3) && !king.colorKingCheck(kingPos[1], 'b')) {
                    resetAttackedSquares();
                    checkAttackedSquares('b');
                    king.checkMate(kingPos[1]);
                    //printAttackedSquares();
                    //System.out.println("white checkAttackedSquares");
                    //Log.d("checkAttackedSquares", "black king in checkAttackedSquares");
                }

                move(position);
                System.out.println(getFenNotation());

                if (whiteTurn && attackedSquares[kingPos[0]] > 1 && !king.colorKingCheck(kingPos[0], 'w')) {
                    resetAttackedSquares();
                    checkAttackedSquares('w');
                    king.checkMate(kingPos[0]);
                    //printAttackedSquares();
                    //System.out.println("black checkAttackedSquares");
                    //Log.d("checkAttackedSquares","white king in checkAttackedSquares");

                }
                // true if black king is in checkAttackedSquares
                else if (!whiteTurn && (attackedSquares[kingPos[1]] == 1 || attackedSquares[kingPos[1]] == 3) && !king.colorKingCheck(kingPos[1], 'b')) {
                    resetAttackedSquares();
                    checkAttackedSquares('b');
                    king.checkMate(kingPos[1]);
                    //printAttackedSquares();
                    //System.out.println("white checkAttackedSquares");
                    //Log.d("checkAttackedSquares", "black king in checkAttackedSquares");
                }

                if (checkMate) {
                    System.out.println("checkMate");
                    Log.d("checkAttackedSquares", "checkmate");
                    endGame();
                }
            }
        });
    }

    public void endGame() {

    }

    boolean turnCheck(int position) {
        if (getFilename(position).charAt(1) == 'w' && whiteTurn)
            return true;
        else if (getFilename(position).charAt(1) == 'b' && !whiteTurn)
            return true;
        else
            return false;
    }

    public static boolean kingSafety(int currentPos, int sourcePos) {
        boolean safe = true;
        String imgName = getFilename(sourcePos);
        int imgID = 0;

        if (getFilename(currentPos).charAt(0) != 't') {
            imgID = imageAdapter.pieceIds[currentPos];
            //System.out.println(imgID);
            //System.out.println(R.drawable.pw);
            imageAdapter.pieceIds[currentPos] = R.drawable.ts;
        }

        swap(sourcePos, currentPos);
        if (imgName.charAt(0) == 'k')
            findKings();

        resetAttackedSquares();
        calcAttackedSquares();
        //printAttackedSquares();

        if (imgName.charAt(1) == 'w') {
            if (whiteTurn && attackedSquares[kingPos[0]] > 1) {
                safe = false;
            }
        } else if (imgName.charAt(1) == 'b') {
            if (!whiteTurn && (attackedSquares[kingPos[1]] == 1 || attackedSquares[kingPos[1]] == 3)) {
                safe = false;
            }
        }

        swap(sourcePos, currentPos);
        if (imgName.charAt(0) == 'k')
            findKings();

        if (imgID != 0 && imageAdapter.pieceIds[currentPos] != imgID) {
            imageAdapter.pieceIds[currentPos] = imgID;
        }

        resetAttackedSquares();
        calcAttackedSquares();
        //printAttackedSquares();

        //System.out.println(safe);
        return safe;
    }

    int swapCounter = 0;
    int firstPos;

    public void move(int position) {

        if (getFilename(firstPos).charAt(1) == getFilename(position).charAt(1)) {
            swapCounter = 0;
            resetPossibleMoves();
            refreshViews();
            resetBackgrounds();
        }

        if (++swapCounter == 1 && getFilename(position).charAt(0) != 't' && turnCheck(position)) {
            firstPos = position;
            possibleMoves(position);
        } else if (swapCounter == 2 && legalMove(position)) {

            if (getFilename(firstPos).charAt(0) == 'r') {
                switch (firstPos) {
                    case 0:
                        rookFlag[0] = true;
                        break;
                    case 7:
                        rookFlag[1] = true;
                        break;
                    case 56:
                        rookFlag[2] = true;
                        break;
                    case 63:
                        rookFlag[3] = true;
                        break;
                }
            }

            // checks if first piece has different name than the second clicked piece and if its not a empty piece
            if (getFilename(firstPos).charAt(1) != getFilename(position).charAt(1) && !getFilename(position).equals("ts")) {
                imageAdapter.pieceIds[position] = R.drawable.ts;

                swap(firstPos, position);

                lastMove = position;

                findKings();
                refreshViews();
                swapCounter = 0;
                resetPossibleMoves();
                resetBackgrounds();
                swapTurn();
                resetAttackedSquares();
                calcAttackedSquares();
            } else {
                //Log.d("move", getFilename(firstPos) + ", " + getFilename(position));

                //System.out.println("p:" + position + ", ep: " + enPassantPos);
                //System.out.println(getFilename(firstPos));


                if (getFilename(firstPos).charAt(0) == 'p') {
                    // en passant check
                    if (position == enPassantPos) {
                        int x = enPassantPos % 8;
                        int y = enPassantPos / 8;
                        //System.out.println("p true, p = ep true");
                        //System.out.println(""+(enPassantPos / 8));
                        if (y == 2) {
                            //System.out.println("y == 2");
                            imageAdapter.pieceIds[x + ((y + 1) * 8)] = R.drawable.ts;
                        } else if (y == 5) {
                            imageAdapter.pieceIds[x + ((y - 1) * 8)] = R.drawable.ts;
                        }
                    }

                    // promotion check
                    if ((position == 0 && getFilename(firstPos).charAt(1) == 'w') || (position == 7 && getFilename(firstPos).charAt(1) == 'b')) {
                        promotion(position);
                    }
                }

                // castling check
                if (getFilename(firstPos).charAt(0) == 'k') {
                    int difference = firstPos-position;

                    // castle with left rook
                    if (difference == 2) {
                        if (getFilename(firstPos).charAt(1) == 'w') {
                            swap(56, 59);
                            rookFlag[2] = true;
                        }
                        else {
                            swap(0, 3);
                            rookFlag[0] = true;
                        }
                        findKings();
                    }

                    // castle with right rook
                    else if (difference == -2) {
                        if (getFilename(firstPos).charAt(1) == 'w') {
                            swap(63, 61);
                            rookFlag[3] = true;
                        }
                        else {
                            swap(7, 5);
                            rookFlag[1] = true;
                        }
                    }
                    findKings();
                 }

                swap(firstPos, position);

                lastMove = 0;

                findKings();
                refreshViews();
                //Log.d("move", getFilename(firstPos) + ", " + getFilename(position));
                swapCounter = 0;
                resetPossibleMoves();
                resetBackgrounds();
                swapTurn();
                resetAttackedSquares();
                calcAttackedSquares();
                enPassantPos = -1;
                if (getFilename(position).charAt(0) == 'p') {
                    int x = position % 8;
                    int sourceY = position / 8;
                    int newY = firstPos / 8;
                    if ((sourceY - newY) == 2)
                        enPassantPos = (x + (8 * (sourceY - 1)));
                    else if ((sourceY - newY) == -2)
                        enPassantPos = (x + (8 * (sourceY + 1)));
                }
            }
        } else {
            swapCounter = 0;
            resetPossibleMoves();
            refreshViews();
            resetBackgrounds();
            //System.out.println(getCell(position).getTag().toString());
        }
        //print2DArray();
        //printAttackedSquares();
    }

    public static void swap(int firstPos, int secondPos) {
        int temp = imageAdapter.pieceIds[secondPos];

        imageAdapter.pieceIds[secondPos] = imageAdapter.pieceIds[firstPos];

        imageAdapter.pieceIds[firstPos] = temp;
    }

    public void promotion(int position) {
        // TODO promotion
        /*
        option should return a value like 'q' if queen in is taken
        */
        char choice = 0;
            switch (choice) {
                case 'q':
                    if (getFilename(position).charAt(1) == 'w')
                        imageAdapter.pieceIds[position] = R.drawable.qw;
                    else
                        imageAdapter.pieceIds[position] = R.drawable.qb;
                    break;
                case 'b':
                    if (getFilename(position).charAt(1) == 'w')
                        imageAdapter.pieceIds[position] = R.drawable.bw;
                    else
                        imageAdapter.pieceIds[position] = R.drawable.bb;
                    break;
                case 'n':
                    if (getFilename(position).charAt(1) == 'w')
                        imageAdapter.pieceIds[position] = R.drawable.nw;
                    else
                        imageAdapter.pieceIds[position] = R.drawable.nb;
                    break;
                case 'r':
                    if (getFilename(position).charAt(1) == 'w')
                        imageAdapter.pieceIds[position] = R.drawable.rw;
                    else
                        imageAdapter.pieceIds[position] = R.drawable.rb;
                    break;
            }
    }

    public static void findKings() {
        int k = 0;
        for (int i = 0; i < 64; i++) {
            if (getFilename(i).charAt(0) == 'k') {
                if (getFilename(i).charAt(1) == 'w') {
                    kingPos[0] = i;
                } else {
                    kingPos[1] = i;
                }
            }
        }
    }

    public static void checkAttackedSquares(char kingColor) {
        for (int i = 0; i < 64; i++) {
            String imgName = getFilename(i);
            if (imgName.charAt(1) == 'w') {
                char color = 'w';
                switch (imgName.charAt(0)) {
                    case 'k':
                        if (kingColor != color)
                            king.setAttackedSquares(i, color);
                        break;
                    case 'q':
                        bishop.setAttackingSquares(i, color);
                        rook.setAttackedSquares(i, color);
                        break;
                    case 'b':
                        bishop.setAttackingSquares(i, color);
                        break;
                    case 'n':
                        knight.setAttackedSquares(i, color);
                        break;
                    case 'r':
                        rook.setAttackedSquares(i, color);
                        break;
                    case 'p':
                        pawn.setAttackedSqueres(i);
                        break;
                }
            } else if (imgName.charAt(1) == 'b') {
                char color = 'b';
                switch (imgName.charAt(0)) {
                    case 'k':
                        if (kingColor != color)
                            king.setAttackedSquares(i, color);
                        break;
                    case 'q':
                        bishop.setAttackingSquares(i, color);
                        rook.setAttackedSquares(i, color);
                        break;
                    case 'b':
                        bishop.setAttackingSquares(i, color);
                        break;
                    case 'n':
                        knight.setAttackedSquares(i, color);
                        break;
                    case 'r':
                        rook.setAttackedSquares(i, color);
                        break;
                    case 'p':
                        pawn.setAttackedSqueres(i);
                        break;
                }
            }
        }
    }

    public static void resetAttackedSquares() {
        for (int i = 0; i < 64; i++)
            attackedSquares[i] = 0;
    }

    public static void calcAttackedSquares() {
        for (int i = 0; i < 64; i++) {
            String imgName = getFilename(i);
            if (imgName.charAt(1) == 'w') {
                char color = 'w';
                switch (imgName.charAt(0)) {
                    case 'k':
                        king.setAttackedSquares(i, color);
                        break;
                    case 'q':
                        bishop.setAttackingSquares(i, color);
                        rook.setAttackedSquares(i, color);
                        break;
                    case 'b':
                        bishop.setAttackingSquares(i, color);
                        break;
                    case 'n':
                        knight.setAttackedSquares(i, color);
                        break;
                    case 'r':
                        rook.setAttackedSquares(i, color);
                        break;
                    case 'p':
                        pawn.setAttackedSqueres(i);
                        break;
                }
            } else if (imgName.charAt(1) == 'b') {
                char color = 'b';
                switch (imgName.charAt(0)) {
                    case 'k':
                        king.setAttackedSquares(i, color);
                        break;
                    case 'q':
                        bishop.setAttackingSquares(i, color);
                        rook.setAttackedSquares(i, color);
                        break;
                    case 'b':
                        bishop.setAttackingSquares(i, color);
                        break;
                    case 'n':
                        knight.setAttackedSquares(i, color);
                        break;
                    case 'r':
                        rook.setAttackedSquares(i, color);
                        break;
                    case 'p':
                        pawn.setAttackedSqueres(i);
                        break;
                }
            }
        }
    }

    public void swapTurn() {
        if (whiteTurn)
            whiteTurn = false;
        else {
            whiteTurn = true;
            fullMoveCounter++;
        }
    }

    public void possibleMoves(int position) {
        switch (getFilename(position).charAt(0)) {
            // queen
            case 'q':
                bishop.bishopCheck(position);
                rook.rookCheck(position);
                break;
            // king
            case 'k':
                king.kingCheck(position);
                break;
            // rook
            case 'r':
                rook.rookCheck(position);
                break;
            // knight
            case 'n':
                knight.knightCheck(position);
                break;
            // bishop
            case 'b':
                bishop.bishopCheck(position);
                break;
            // pawn
            case 'p':
                pawn.pawnCheck(position);
                break;
        }
        refreshViews();
    }

    public String getFenNotation() {
        // fen string example
        // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1

        // board positions
        String fenStr = imageAdapter.getBoardStr();

        // current turn
        if (whiteTurn)
            fenStr += " w ";
        else
            fenStr += " b ";

        // castle
        if (kingPos[0] == 60) {
            if (rookFlag[3]) {
                fenStr += "K";
            }
            if (rookFlag[2])
                fenStr += "Q";
        }
        else if (kingPos[1] == 4) {
            if (rookFlag[1])
                fenStr += "k";
            if (rookFlag[0])
                fenStr += "q";
        }
        else {
            fenStr += "-";
        }

        // en passant
        if (enPassantPos != -1) {
            //System.out.println(getCell(enPassantPos).getTag().toString());
            fenStr += " " + getCell(enPassantPos).getTag().toString();
        } else {
            fenStr += " -";
        }

        // half move counter, not used
        fenStr += " 0";

        // full move counter
        fenStr += " " + fullMoveCounter;

        return fenStr;
    }

    public boolean legalMove(int position) {
        if (possibleMoves[position])
            return true;
        else
            return false;
    }

    public void resetBackgrounds() {
        for (int i = 0; i < 64; i++) {
            getCell(i).setBackgroundResource(0);
        }
    }

    public void refreshViews() {
        imageAdapter.currentCells = 0;
        imageAdapter.notifyDataSetChanged();
        board.invalidateViews();
    }

    public void resetPossibleMoves() {
        for (int i = 0; i < 64; i++) {
            possibleMoves[i] = false;
        }
    }

    public void printArray(ImageView[] pieces, String tag) {
        Log.d("gridview size", Integer.toString(board.getChildCount()));
        String toString = "";
        for (int i = 0; i < pieces.length; i++) {
            toString += "[" + pieces[i].getTag() + "], ";
        }
        //toString = squares.toString();
        Log.d(tag, toString);
    }

    public void print2DArray() {
        Log.d("", "----------------");
        for (int n = 0; n < 8; n++) {
            String row = "";
            for (int i = 0; i < 8; i++) {
                if (GameActivity.possibleMoves[i + n * 8])
                    row += "1";

                else
                    row += "0";
            }
            Log.d("", row + "..." + (n + 1));
        }
    }

    public static void printAttackedSquares() {
        Log.d("", "----------------");
        for (int n = 0; n < 8; n++) {
            String row = "";
            for (int i = 0; i < 8; i++) {
                row += Integer.toString(attackedSquares[i + n * 8]);
            }
            Log.d("", row + "..." + (n + 1));
        }
    }

    public static ImageView getCell(int position) {
        return ((ImageView) board.getItemAtPosition(position));
    }

    public static String getFilename(int position) {
        String fileName = getCell(position).getResources().getResourceName(imageAdapter.pieceIds[position]);
        fileName = fileName.charAt(fileName.length() - 2) + "" + fileName.charAt(fileName.length() - 1);
        //Log.d("filename", fileName);
        return fileName;
    }

}


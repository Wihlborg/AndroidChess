package com.example.androidchess.chessboard;

import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.androidchess.Activities.MainActivity;
import com.example.androidchess.Activities.WifiConnection;
import com.example.androidchess.GameMode;
import com.example.androidchess.Database.Database;
import com.example.androidchess.R;
import com.example.androidchess.User;
import com.example.androidchess.Utils;
import com.example.androidchess.chessboard.pieces.*;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.security.SecureRandom;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    //Objects of AsyncTask subclasses set to null
    protected UserWinsTask mAuthWinTask = null;
    protected UserLossesTask mAuthLoseTask = null;



    public static Rook rook = new Rook();
    public static Knight knight = new Knight();
    public static Pawn pawn = new Pawn();
    public static Bishop bishop = new Bishop();
    public static King king = new King();

    static GridView board;
    public static ImageAdapter imageAdapter;
    public static boolean whiteTurn;
    public static boolean[] possibleMoves;
    public static int[] attackedSquares;
    public static int[] kingPos;
    public static boolean[] kingMoved;
    public static boolean checkMate;
    public static int lastMove;
    public static boolean[] kingAttacker;
    public static int enPassantPos;
    public static boolean[] rookMoved;
    private SecureRandom random = new SecureRandom();
    public static int fullMoveCounter;
    boolean popup = false;
    public static String winner;
    public static String winCondition;
    private SoundPool soundPool;
    private int moveSound, checkMateSound;
    ChessClock blackClock;
    ChessClock whiteClock;
    WifiConnection wifi;
    //public static Map<Integer, Boolean> rookFlag = new HashMap<>();



    @Override
    public void onBackPressed() {
        this.finish();
        if (blackClock != null)
            blackClock.stopTimer();
        if (whiteClock != null)
            whiteClock.stopTimer();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();

        resetVariables();

        for (int i = 0; i < rookMoved.length; i++) {
            rookMoved[i] = false;
        }

        kingPos[0] = 60;
        kingPos[1] = 4;

        resetAttackedSquares();
        for (int i = 0; i < kingAttacker.length; i++) {
            kingAttacker[i] = false;
        }
        resetPossibleMoves();
        board = findViewById(R.id.board);
        imageAdapter = new ImageAdapter(this);

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        int width = metrics.widthPixels;

        ImageView boardImg = findViewById(R.id.boardImg);
        boardImg.getLayoutParams().width = width;
        boardImg.getLayoutParams().height = width;

        if (TimerInfo.INSTANCE.getEnable()) {
            TextView bt = findViewById(R.id.timerblack);
            TextView wt = findViewById(R.id.timerwhite);

            bt.setVisibility(View.VISIBLE);
            wt.setVisibility(View.VISIBLE);

            blackClock = new ChessClock(0, 5, 0, bt, this);
            whiteClock = new ChessClock(0, 5, 0, wt, this);

        }

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build();

        moveSound = soundPool.load(this, R.raw.move, 1);
        checkMateSound = soundPool.load(this, R.raw.gameover, 1);

        board.setAdapter(imageAdapter);
        board.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String fen = getFenNotation();
                if (GameMode.INSTANCE.getMode() == GameMode.Mode.Online) {
                    onlineMove(position);
                }
                else if (GameMode.INSTANCE.getMode() == GameMode.Mode.Local){
                    localMove(position);
                }
                else {
                    vsAIMove(position);
                }
                if (User.INSTANCE.getSounds() && !fen.equals(getFenNotation()))
                soundPool.play(moveSound, 1, 1, 2, 0, 1);
            }
        });
    }

    public void basicMove(int position) {
        king.check();

        move(position);
        //System.out.println(getFenNotation());

        king.checkMateCheck();
    }

    public void onlineMove(int position) {
        if (GameMode.INSTANCE.getPlayer() == GameMode.Player.pOne && whiteTurn) {
            basicMove(position);
        }
        else if(GameMode.INSTANCE.getPlayer() == GameMode.Player.pTwo && !whiteTurn) {
            basicMove(position);
        }
        if (checkMate) {
            System.out.println("checkMate");
            Log.d("checkAttackedSquares", "checkmate");
            winCondition = "checkmate";
            endGame();
        }
        WifiConnection.getInstance().send(getFenNotation());
    }

    public void vsAIMove(int position) {
        String fen = getFenNotation();
        basicMove(position);
        if (checkMate) {
            System.out.println("checkMate");
            Log.d("checkAttackedSquares", "checkmate");
            winCondition = "checkmate";
            endGame();
        } else if (GameMode.INSTANCE.getMode() == GameMode.Mode.AI && !fen.equals(getFenNotation()) && !popup) {
            makeRandomComputerMove();
        }
    }

    public void localMove(int position) {
        basicMove(position);
        if (checkMate) {
            System.out.println("checkMate");
            Log.d("checkAttackedSquares", "checkmate");
            winCondition = "checkmate";
            endGame();
        }
    }

    public void endGame() {
        if (User.INSTANCE.getSounds())
        soundPool.play(checkMateSound, (float)1.0, (float)1.0, 0, 0, (float)1.0);
        System.out.println("endGame()");
        findViewById(R.id.winContainer).setVisibility(View.VISIBLE);
        findViewById(R.id.winContainer).animate().alpha(1f).setDuration(500).setListener(null);

        ((ImageButton) findViewById(R.id.shareButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage();
            }
        });

        if (!GameMode.INSTANCE.equals("online")) {
            // LOCAL OR VS AI
            if (winner.equals("w")) {
                ((TextView) findViewById(R.id.winnerString)).setText("White wins");
                ((TextView) findViewById(R.id.winCondition)).setText(User.INSTANCE.getName() + " wins by " + winCondition);
                ((TextView) findViewById(R.id.elotxtwhite)).setText(User.INSTANCE.getName() + "\n" + Double.toString(User.INSTANCE.getElo()));
                ((TextView) findViewById(R.id.elotextblack)).setText("Computer\n1337");
                // TODO set elo difference with elo calculation
                // replace 12 with elo function
                ((TextView) findViewById(R.id.elodifferencewhite)).setText("+" + 0);
                ((TextView) findViewById(R.id.elodifferenceblack)).setText("-" + 0);
                ((TextView) findViewById(R.id.elodifferencewhite)).setTextColor(0xFF00CC00);
                ((TextView) findViewById(R.id.elodifferenceblack)).setTextColor(0xFFEE0000);

            } else if (winner.equals("b")) {
                ((TextView) findViewById(R.id.winnerString)).setText("black wins");
                ((TextView) findViewById(R.id.winCondition)).setText(User.INSTANCE.getName() + " wins by " + winCondition);

                // replace 12 with elo function
                ((TextView) findViewById(R.id.elodifferencewhite)).setText("-" + 0);
                ((TextView) findViewById(R.id.elodifferenceblack)).setText("+" + 0);
                ((TextView) findViewById(R.id.elodifferencewhite)).setTextColor(0xFFEE0000);
                ((TextView) findViewById(R.id.elodifferenceblack)).setTextColor(0xFF00CC00);
                mAuthWinTask = new UserWinsTask();
            } else {
                ((TextView) findViewById(R.id.winnerString)).setText("Draw");
                ((TextView) findViewById(R.id.winCondition)).setText("Draw by stalemate");

                // replace 12 with elo function
                ((TextView) findViewById(R.id.elodifferencewhite)).setText("-" + 0);
                ((TextView) findViewById(R.id.elodifferenceblack)).setText("+" + 0);
                ((TextView) findViewById(R.id.elodifferencewhite)).setTextColor(0xFFEE0000);
                ((TextView) findViewById(R.id.elodifferenceblack)).setTextColor(0xFF00CC00);
                mAuthWinTask = new UserWinsTask();
            }
        } else {
            //ONLINE GAME
            if (winner.equals("w")) {
                ((TextView) findViewById(R.id.winnerString)).setText("White wins");
                ((TextView) findViewById(R.id.winCondition)).setText(User.INSTANCE.getName() + " wins by " + winCondition);
                ((TextView) findViewById(R.id.elotxtwhite)).setText(User.INSTANCE.getName() + "\n" + Double.toString(User.INSTANCE.getElo()));

                // TODO set elo difference with elo calculation
                // replace 12 with elo function
                ((TextView) findViewById(R.id.elodifferencewhite)).setText("+" + 12);
                ((TextView) findViewById(R.id.elodifferenceblack)).setText("-" + 12);
                ((TextView) findViewById(R.id.elodifferencewhite)).setTextColor(0xFF00CC00);
                ((TextView) findViewById(R.id.elodifferenceblack)).setTextColor(0xFFEE0000);
                mAuthWinTask = new UserWinsTask();
            } else if (winner.equals("b")) {
                ((TextView) findViewById(R.id.winnerString)).setText("black wins");
                ((TextView) findViewById(R.id.winCondition)).setText(User.INSTANCE.getName() + " wins by " + winCondition);

                // replace 12 with elo function
                ((TextView) findViewById(R.id.elodifferencewhite)).setText("-" + 12);
                ((TextView) findViewById(R.id.elodifferenceblack)).setText("+" + 12);
                ((TextView) findViewById(R.id.elodifferencewhite)).setTextColor(0xFFEE0000);
                ((TextView) findViewById(R.id.elodifferenceblack)).setTextColor(0xFF00CC00);
                mAuthWinTask = new UserWinsTask();
            } else {
                ((TextView) findViewById(R.id.winnerString)).setText("Draw");
                ((TextView) findViewById(R.id.winCondition)).setText("Draw by stalemate");

                // replace 12 with elo function
                ((TextView) findViewById(R.id.elodifferencewhite)).setText("-" + 0);
                ((TextView) findViewById(R.id.elodifferenceblack)).setText("+" + 0);
                ((TextView) findViewById(R.id.elodifferencewhite)).setTextColor(0xFFEE0000);
                ((TextView) findViewById(R.id.elodifferenceblack)).setTextColor(0xFF00CC00);
            }
        }

    }

    boolean turnCheck(int position) {
        if (getFilename(position).charAt(1) == 'w' && whiteTurn)
            return true;
        else if (getFilename(position).charAt(1) == 'b' && !whiteTurn)
            return true;
        else
            return false;
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
            getCell(position).setBackgroundResource(R.drawable.tealbackground);
            refreshViews();
        } else if (swapCounter == 2 && legalMove(position)) {

            if (getFilename(firstPos).charAt(0) == 'r') {
                switch (firstPos) {
                    case 0:
                        rookMoved[0] = true;
                        break;
                    case 7:
                        rookMoved[1] = true;
                        break;
                    case 56:
                        rookMoved[2] = true;
                        break;
                    case 63:
                        rookMoved[3] = true;
                        break;
                }
            }

            if (getFilename(firstPos).charAt(0) == 'k') {
                switch (firstPos) {
                    case 4:
                        // black king
                        kingMoved[1] = true;
                        break;
                    case 60:
                        // white king
                        kingMoved[0] = true;
                        break;
                }
            }
            if (getFilename(firstPos).charAt(0) == 'k') {
                switch (firstPos) {
                    case 4:
                        // black king
                        kingMoved[1] = true;
                        break;
                    case 60:
                        // white king
                        kingMoved[0] = true;
                        break;
                }
            }

            // checks if first piece has different name than the second clicked piece and if its not a empty piece
            if (getFilename(firstPos).charAt(1) != getFilename(position).charAt(1) && !getFilename(position).equals("ts")) {
                imageAdapter.pieceIds[position] = R.drawable.ts;

                if (getFilename(firstPos).charAt(0) == 'p') {
                    if ((position / 8 == 0 && getFilename(firstPos).charAt(1) == 'w') || (position / 8 == 7 && getFilename(firstPos).charAt(1) == 'b')) {
                        if (GameMode.INSTANCE.getMode().equals("AI") && getFilename(firstPos).charAt(1) == 'b') {
                            imageAdapter.pieceIds[firstPos] = R.drawable.qb;
                        } else {
                            popup = true;
                            promotionUI(firstPos);
                            promotionPos = position;
                        }
                    }
                }

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

                    // promotionUI check
                    if ((position / 8 == 0 && getFilename(firstPos).charAt(1) == 'w') || (position / 8 == 7 && getFilename(firstPos).charAt(1) == 'b')) {
                        popup = true;
                        promotionUI(firstPos);
                        promotionPos = position;
                    }
                }

                // castling check
                if (getFilename(firstPos).charAt(0) == 'k') {
                    int difference = firstPos - position;

                    // castle with left rook
                    if (difference == 2) {
                        if (getFilename(firstPos).charAt(1) == 'w') {
                            swap(56, 59);
                            rookMoved[2] = true;
                        } else {
                            swap(0, 3);
                            rookMoved[0] = true;
                        }
                        findKings();
                    }

                    // castle with right rook
                    else if (difference == -2) {
                        if (getFilename(firstPos).charAt(1) == 'w') {
                            swap(63, 61);
                            rookMoved[3] = true;
                        } else {
                            swap(7, 5);
                            rookMoved[1] = true;
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

    int promotionPos;

    public void promotionUI(int position) {
        findViewById(R.id.promotionblock).setVisibility(View.VISIBLE);
        ImageView imgBishop = findViewById(R.id.imgbishop);
        ImageView imgRook = findViewById(R.id.imgrook);
        ImageView imgKnight = findViewById(R.id.imgknight);
        ImageView imgQueen = findViewById(R.id.imgqueen);
        if (getFilename(position).charAt(1) == 'w') {
            imgBishop.setImageResource(R.drawable.bw);
            imgBishop.setTag("bw");
            imgRook.setImageResource(R.drawable.rw);
            imgRook.setTag("rw");
            imgKnight.setImageResource(R.drawable.nw);
            imgKnight.setTag("nw");
            imgQueen.setImageResource(R.drawable.qw);
            imgQueen.setTag("qw");
        } else {
            imgBishop.setImageResource(R.drawable.bb);
            imgBishop.setTag("bb");
            imgRook.setImageResource(R.drawable.rb);
            imgRook.setTag("rb");
            imgKnight.setImageResource(R.drawable.nb);
            imgKnight.setTag("nb");
            imgQueen.setImageResource(R.drawable.qb);
            imgQueen.setTag("qb");
        }
    }

    public void promotion(View v) {
        String imgName = v.getTag().toString();
        char choice = imgName.charAt(0);

        switch (choice) {
            case 'q':
                System.out.println(imgName);
                if (imgName.charAt(1) == 'w')
                    imageAdapter.pieceIds[promotionPos] = R.drawable.qw;
                else
                    imageAdapter.pieceIds[promotionPos] = R.drawable.qb;
                break;
            case 'b':
                if (imgName.charAt(1) == 'w')
                    imageAdapter.pieceIds[promotionPos] = R.drawable.bw;
                else
                    imageAdapter.pieceIds[promotionPos] = R.drawable.bb;
                break;
            case 'n':
                if (imgName.charAt(1) == 'w')
                    imageAdapter.pieceIds[promotionPos] = R.drawable.nw;
                else
                    imageAdapter.pieceIds[promotionPos] = R.drawable.nb;
                break;
            case 'r':
                if (imgName.charAt(1) == 'w')
                    imageAdapter.pieceIds[promotionPos] = R.drawable.rw;
                else
                    imageAdapter.pieceIds[promotionPos] = R.drawable.rb;
                break;
        }
        findViewById(R.id.promotionblock).setVisibility(View.GONE);
        refreshViews();
        popup = false;
        if (GameMode.INSTANCE.getMode() == GameMode.Mode.AI) {
            makeRandomComputerMove();
        }

        king.check();

        king.checkMateCheck();

        if (checkMate) {
            System.out.println("checkMate");
            Log.d("checkAttackedSquares", "checkmate");
            winCondition = "checkmate";
            endGame();
        }
    }


    public static void findKings() {
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
        if (whiteTurn) {
            whiteTurn = false;
            if (TimerInfo.INSTANCE.getEnable()) {
                whiteClock.stopTimer();
                if (!blackClock.isAlive()) {
                    blackClock = new ChessClock((TextView) findViewById(R.id.timerblack), this);
                }
                blackClock.startTimer();
            }
        } else {
            whiteTurn = true;
            fullMoveCounter++;
            if (TimerInfo.INSTANCE.getEnable()) {
                blackClock.stopTimer();
                if (!whiteClock.isAlive()) {
                    whiteClock = new ChessClock((TextView) findViewById(R.id.timerwhite), this);
                }
                whiteClock.startTimer();
            }
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
        // board positions | whos turn | castle options | en passant | half move counter | full move counter

        // board positions
        String fenStr = imageAdapter.getBoardStr();

        // current turn
        if (whiteTurn)
            fenStr += " w ";
        else
            fenStr += " b ";

        // castle
        boolean castleAvailable = false;
        if (kingPos[0] == 60) {
            if (!rookMoved[3]) {
                fenStr += "K";
                castleAvailable = true;
            }
            if (!rookMoved[2]) {
                fenStr += "Q";
                castleAvailable = true;
            }
        }
        if (kingPos[1] == 4) {
            if (!rookMoved[1]) {
                fenStr += "k";
                castleAvailable = true;
            }
            if (!rookMoved[0]) {
                fenStr += "q";
                castleAvailable = true;
            }
        }
        if (!castleAvailable) {
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

    public static void setBoardGameState(String fenNotation) {
        // fen string example
        // rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
        // board positions | who's turn | castle options | en passant | half move counter | full move counter

        System.out.println(fenNotation);

        boolean stop = false;
        int charPos = 0;
        int gridPos = 0;
        while (!stop) {
            /*
            System.out.println("-----------");
            System.out.println("c:"+fenNotation.charAt(charPos));
            System.out.println("n:"+gridPos);
            */

            switch (fenNotation.charAt(charPos)) {
                case 'q':
                    imageAdapter.pieceIds[gridPos] = R.drawable.qb;
                    break;
                case 'Q':
                    imageAdapter.pieceIds[gridPos] = R.drawable.qw;
                    break;
                case 'k':
                    imageAdapter.pieceIds[gridPos] = R.drawable.kb;
                    break;
                case 'K':
                    imageAdapter.pieceIds[gridPos] = R.drawable.kw;
                    break;
                case 'n':
                    imageAdapter.pieceIds[gridPos] = R.drawable.nb;
                    break;
                case 'N':
                    imageAdapter.pieceIds[gridPos] = R.drawable.nw;
                    break;
                case 'b':
                    imageAdapter.pieceIds[gridPos] = R.drawable.bb;
                    break;
                case 'B':
                    imageAdapter.pieceIds[gridPos] = R.drawable.bw;
                    break;
                case 'r':
                    imageAdapter.pieceIds[gridPos] = R.drawable.rb;
                    break;
                case 'R':
                    imageAdapter.pieceIds[gridPos] = R.drawable.rw;
                    break;
                case 'p':
                    imageAdapter.pieceIds[gridPos] = R.drawable.pb;
                    break;
                case 'P':
                    imageAdapter.pieceIds[gridPos] = R.drawable.pw;
                    break;
                case ' ':
                    stop = true;
                    break;
                case '/':
                    gridPos--;
                    break;
                default:
                    for (int t = 0; t < Character.getNumericValue(fenNotation.charAt(charPos)); t++) {
                        /*
                        System.out.println("t:"+t);
                        System.out.println("n:"+gridPos);
                        System.out.println("----");
                        */
                        imageAdapter.pieceIds[gridPos++] = R.drawable.ts;

                    }
                    gridPos--;
                    break;
            }
            charPos++;
            gridPos++;
            //System.out.println("n++:"+gridPos);
        }
        refreshViews();

        if (fenNotation.charAt(charPos) == 'w')
            whiteTurn = true;
        else
            whiteTurn = false;

        charPos += 2;
        stop = false;

        if (fenNotation.charAt(charPos) == '-')
            stop = true;
        while (!stop) {
            switch (fenNotation.charAt(charPos)) {
                case 'K':
                    rookMoved[3] = false;
                    kingMoved[0] = false;
                    break;
                case 'Q':
                    rookMoved[2] = false;
                    kingMoved[0] = false;
                    break;
                case 'k':
                    rookMoved[1] = false;
                    kingMoved[1] = false;
                    break;
                case 'q':
                    kingMoved[1] = false;
                    rookMoved[0] = false;
                    break;
                case ' ':
                    stop = true;
                    break;
            }
            charPos++;
        }

        charPos += 2;
        if (fenNotation.charAt(charPos) == '-')
            enPassantPos = -1;
        else {
            int x = fenNotation.charAt(charPos);
            int y = fenNotation.charAt(++charPos);
            x = x - 48;
            enPassantPos = x + (8 * y);
        }

        //setBoardGameState("pppppppp/kqbbnnrr/8/8/8/8/PPPPPPPP/RNBQKBNR b - e6 0 2");

        charPos += 4;
        // half time clock inbetween

        //fenNotation.length()-1
        fullMoveCounter = Character.getNumericValue(fenNotation.charAt(fenNotation.length()-1));

        refreshViews();
    }

    public boolean legalMove(int position) {
        System.out.println(possibleMoves[position]);
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

    public static void refreshViews() {
        imageAdapter.notifyDataSetChanged();
        board.invalidateViews();
    }

    public void resetPossibleMoves() {
        for (int i = 0; i < 64; i++) {
            possibleMoves[i] = false;
        }
    }

    public void resetVariables() {
        whiteTurn = true;
        possibleMoves = new boolean[64];
        attackedSquares = new int[64];
        kingPos = new int[2];
        kingMoved = new boolean[2];
        checkMate = false;
        lastMove = 0;
        kingAttacker = new boolean[64];
        enPassantPos = -1;
        rookMoved = new boolean[4];
        fullMoveCounter = 1;
        winner = "";
        winCondition = "";
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

    public void printPossibleMoves() {
        Log.d("", "----------------");
        for (int n = 0; n < 8; n++) {
            String row = "";
            for (int i = 0; i < 8; i++) {
                if (possibleMoves[i + (n * 8)])
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
        return ((ImageView) board.getChildAt(position));
    }

    public static String getFilename(int position) {
        String fileName = getCell(position).getResources().getResourceName(imageAdapter.pieceIds[position]);
        fileName = fileName.charAt(fileName.length() - 2) + "" + fileName.charAt(fileName.length() - 1);
        //Log.d("filename", fileName);
        return fileName;
    }

    void makeRandomComputerMove() {
        ArrayList<Integer> myPieces = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (getFilename(i).charAt(1) == 'b') {
                myPieces.add(i);
            }
        }

        king.check();

        ArrayList<Integer> moves = new ArrayList<>();
        int chosenPiece;
        do {
            chosenPiece = myPieces.get(random.nextInt(myPieces.size()));
            myPieces.remove((Object) chosenPiece);
            switch (getFilename(chosenPiece).charAt(0)) {
                // queen
                case 'q':
                    moves = bishop.getPossibleMoves(chosenPiece, 'b');
                    moves.addAll(rook.getPossibleMoves(chosenPiece, 'b'));
                    break;
                // king
                case 'k':
                    moves = king.getPossibleMoves(chosenPiece, 'b');
                    printAttackedSquares();
                    break;
                // rook
                case 'r':
                    moves = rook.getPossibleMoves(chosenPiece, 'b');
                    break;
                // knight
                case 'n':
                    moves = knight.getPossibleMoves(chosenPiece, 'b');
                    break;
                // bishop
                case 'b':
                    moves = bishop.getPossibleMoves(chosenPiece, 'b');
                    break;
                // pawn
                case 'p':
                    moves = pawn.getPossibleMoves(chosenPiece, 'b');
                    break;
            }
            System.out.println("Chosen piece: " + chosenPiece);
            System.out.println("Which is a: " + getFilename(chosenPiece).charAt(0));
            System.out.println("Possible moves");

            for (int move : moves) {
                System.out.println(move);
            }
        } while (moves.size() == 0 && myPieces.size() > 0);

        if (myPieces.size() == 0 && (attackedSquares[kingPos[1]] == 1 || attackedSquares[kingPos[1]] == 3)) {
            //checkmate
            System.out.println("checkMate");
            Log.d("checkAttackedSquares", "checkmate");
            winCondition = "checkmate";
            endGame();
            return;
        } else if (myPieces.size() == 0 && !(attackedSquares[kingPos[1]] == 1 || attackedSquares[kingPos[1]] == 3)) {
            winCondition = "draw";
            endGame();
            return;
        }
        king.check();
        move(chosenPiece);
        king.checkMateCheck();
        king.check();
        move(moves.get(random.nextInt(moves.size())));
        king.checkMateCheck();
    }

    private void checkDraw(boolean isWhite) {
        ArrayList<Integer> pieces = new ArrayList<>();
        char color = isWhite ? 'w' : 'b';
        for (int i = 0; i < 64; i++) {
            if (getFilename(i).charAt(1) == color) {
                pieces.add(i);
            }
        }

        do {
            int chosenPiece = pieces.get(0);
            pieces.remove(0);
            ArrayList moves = new ArrayList();
            switch (getFilename(chosenPiece).charAt(0)) {
                // queen
                case 'q':
                    moves = bishop.getPossibleMoves(chosenPiece, 'b');
                    moves.addAll(rook.getPossibleMoves(chosenPiece, 'b'));
                    break;
                // king
                case 'k':
                    moves = king.getPossibleMoves(chosenPiece, 'b');
                    printAttackedSquares();
                    break;
                // rook
                case 'r':
                    moves = rook.getPossibleMoves(chosenPiece, 'b');
                    break;
                // knight
                case 'n':
                    moves = knight.getPossibleMoves(chosenPiece, 'b');
                    break;
                // bishop
                case 'b':
                    moves = bishop.getPossibleMoves(chosenPiece, 'b');
                    break;
                // pawn
                case 'p':
                    moves = pawn.getPossibleMoves(chosenPiece, 'b');
                    break;
            }
            if (!moves.isEmpty()) {
                return;
            }
        } while (pieces.size() > 0);
        endGame();
    }

    private void shareImage(){
        Bitmap image = Utils.takeScreenShot(this);
        SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();

        ShareContent shareContent = new ShareMediaContent.Builder().addMedium(photo).build();
        new ShareDialog(this).show(shareContent, ShareDialog.Mode.AUTOMATIC);

    }

    class UserWinsTask extends AsyncTask<Void, Void, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            Database.getInstance().addWins();
            Database.getInstance().getElo(User.INSTANCE.getName());
            //Need to figure what to do about second user
            String secondUser = "second user";
            Database.getInstance().updateElo(User.INSTANCE.getName(), secondUser, User.INSTANCE.getElo());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mAuthWinTask = null;
            if (success) {
                //finish();
                //Intent returnTo = new Intent(GameActivity.this, MenuActivity.class);
                //Toast.makeText(getApplicationContext(),"Recovery success", Toast.LENGTH_SHORT).show();
                //startActivity(returnTo);
            } else {
                // Toast.makeText(getApplicationContext(),"Recovery failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthWinTask = null;
        }
    }

    class UserLossesTask extends AsyncTask<Void, Void, Boolean> {

        UserLossesTask() {


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Database.getInstance().addLosses();
            //String holder
            String secondUser = "second user";
            Database.getInstance().updateElo(secondUser, User.INSTANCE.getName(), User.INSTANCE.getElo());
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            mAuthLoseTask = null;
            if (success) {
                //finish();
                //Intent returnTo = new Intent(GameActivity.this, MenuActivity.class);
                //Toast.makeText(getApplicationContext(),"Recovery success", Toast.LENGTH_SHORT).show();
                //startActivity(returnTo);
            } else {
                //Toast.makeText(getApplicationContext(),"Recovery failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthLoseTask = null;
        }
    }

}
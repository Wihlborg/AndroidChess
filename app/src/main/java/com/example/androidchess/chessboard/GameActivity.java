package com.example.androidchess.chessboard;

import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.androidchess.Database.Database;
import com.example.androidchess.GameMode;
import com.example.androidchess.R;
import com.example.androidchess.User;
import com.example.androidchess.Utils;
import com.example.androidchess.chessboard.Pieces.*;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareDialog;

public class GameActivity extends AppCompatActivity {

    //Objects of AsyncTask subclasses set to null
    protected UserWinsTask mAuthWinTask = null;
    protected UserLossesTask mAuthLoseTask = null;

    Board board;

    boolean popup;

    private SoundPool soundPool;
    private int moveSound, checkMateSound;

    public ChessClock blackClock;
    public ChessClock whiteClock;

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

        ConstraintLayout boardContainer = findViewById(R.id.boardContainer);
        GameInfo.get().game = this;
        board = new Board(this, boardContainer);

        if (TimerInfo.INSTANCE.getEnable()) {
            TextView bt = findViewById(R.id.timerblack);
            TextView wt = findViewById(R.id.timerwhite);

            bt.setVisibility(View.VISIBLE);
            wt.setVisibility(View.VISIBLE);

            blackClock = new ChessClock(0, 5, 0, bt, this);
            whiteClock = new ChessClock(0, 5, 0, wt, this);

        }

        //GameInfo.get().boardState.setWhiteTurn(true);
        /*
        if (TimerInfo.INSTANCE.getEnable()) {
            TextView bt = findViewById(R.id.timerblack);
            TextView wt = findViewById(R.id.timerwhite);

            bt.setVisibility(View.VISIBLE);
            wt.setVisibility(View.VISIBLE);

            GameInfo.get().blackClock = new ChessClock(0, 5, 0, bt, this);
            GameInfo.get().whiteClock = new ChessClock(0, 5, 0, wt, this);

        }

        popup = false;
        */
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

    }


    public void endGame(String winner, String winCondition) {
        if (User.INSTANCE.getSounds())
            soundPool.play(checkMateSound, (float)1.0, (float)1.0, 0, 0, (float)1.0);
        System.out.println("endGame()");
        findViewById(R.id.winContainer).setVisibility(View.VISIBLE);
        findViewById(R.id.winContainer).animate().alpha(1f).setDuration(500).setListener(null);
        System.out.println("Winner " + winner);

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
                ((TextView) findViewById(R.id.elotxtwhite)).setText(User.INSTANCE.getName() + "\n" + Double.toString(User.INSTANCE.getElo()));
                ((TextView) findViewById(R.id.elotextblack)).setText("Computer\n1337");
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


    private void shareImage(){
        Bitmap image = Utils.takeScreenShot(this);
        SharePhoto photo = new SharePhoto.Builder().setBitmap(image).build();

        ShareContent shareContent = new ShareMediaContent.Builder().addMedium(photo).build();
        new ShareDialog(this).show(shareContent, ShareDialog.Mode.AUTOMATIC);

    }

    public void promotionUI() {
        findViewById(R.id.promotionblock).setVisibility(View.VISIBLE);
        ImageView imgBishop = findViewById(R.id.imgbishop);
        ImageView imgRook = findViewById(R.id.imgrook);
        ImageView imgKnight = findViewById(R.id.imgknight);
        ImageView imgQueen = findViewById(R.id.imgqueen);
        if (board.boardState.getPiece(GameInfo.get().promotionPos).isWhite()) {
            imgBishop.setImageResource(R.drawable.bw);
            imgBishop.setTag("bw");
            imgRook.setImageResource(R.drawable.rw);
            imgRook.setTag("rw");
            imgKnight.setImageResource(R.drawable.nw);
            imgKnight.setTag("nw");
            imgQueen.setImageResource(R.drawable.qw);
            imgQueen.setTag("qw");
        }
        //
        else {
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
        System.out.println("promotion()");
        String imgName = v.getTag().toString();
        char choice = imgName.charAt(0);

        BoardState boardState = board.boardState;
        switch (choice) {
            case 'q':
                System.out.println(imgName);
                if (imgName.charAt(1) == 'w')
                    boardState.setPiece(new Queen(true), GameInfo.get().promotionPos);
                else
                    boardState.setPiece(new Queen(false), GameInfo.get().promotionPos);
                break;
            case 'b':
                if (imgName.charAt(1) == 'w')
                    boardState.setPiece(new Bishop(true), GameInfo.get().promotionPos);
                else
                    boardState.setPiece(new Bishop(false), GameInfo.get().promotionPos);
                break;
            case 'n':
                if (imgName.charAt(1) == 'w')
                    boardState.setPiece(new Knight(true), GameInfo.get().promotionPos);
                else
                    boardState.setPiece(new Knight(false), GameInfo.get().promotionPos);
                break;
            case 'r':
                if (imgName.charAt(1) == 'w')
                    boardState.setPiece(new Rook(true), GameInfo.get().promotionPos);
                else
                    boardState.setPiece(new Rook(false), GameInfo.get().promotionPos);
                break;
        }

        findViewById(R.id.promotionblock).setVisibility(View.GONE);
        board.updateBoard(boardState);

        //findViewById(R.id.promotionblock).setVisibility(View.GONE);
        //popup = false;
        /*
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
        */
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

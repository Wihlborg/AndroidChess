package com.example.androidchess.chessboard;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;
import com.example.androidchess.R;

import static com.example.androidchess.chessboard.GameActivity.*;

import java.util.ArrayList;

public class ChessClock extends Thread {

    private GameActivity game;
    private int sec;
    private int min;
    private int hour;
    private TextView txt;
    private Handler handler = new Handler();

    public ChessClock(int sec, int min, int hour, TextView txt, GameActivity game) {
        this.game = game;
        this.sec = sec;
        this.min = min;
        this.hour = hour;
        this.txt = txt;
        updateTime();
    }

    public ChessClock(TextView txt, GameActivity game) {
        this.game = game;
        this.txt = txt;
        getTime();
    }

    /*public ChessClock() {
        setTimerInfo();
    }*/

    @Override
    public void run() {
        try {
            for (int i = (sec + (60 * min) + (60 * 60 * hour)); i > 0; i--) {
                System.out.println(i);
                this.sleep(1000);
                sec--;
                if (sec < 0) {
                    if (min > 0)
                        min--;
                    sec = 59;
                }
                if (min < 0) {
                    if (hour > 0)
                        hour--;
                    min = 59;
                }
                updateTime();
            }
            if (txt.getTag().toString().equals("white")) {
                GameInfo.get().winner = "b";
            }
            else {
                GameInfo.get().winner = "w";
            }
            GameInfo.get().winCondition = "timer running out";
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //TODO fix
                    // game.endGame();
                }
            });

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void updateTime() {
        String timeString = "";

        if (hour != 0) {
            if (hour < 10)
                timeString = "0" + hour + ":";
            else
                timeString = "" + hour + ":";
        }

        if (min < 10)
            timeString += "0" + min + ":";
        else
            timeString += min + ":";

        if (sec < 10)
            timeString += "0" + sec;
        else
            timeString += "" + sec;

        final String finalTimeString = timeString;
        handler.post(new Runnable() {
            @Override
            public void run() {
                txt.setText(finalTimeString);
            }
        });
    }

    public void getTime() {
        String time = txt.getText().toString();
        if (time.length() > 5) {
            int i = 0;
            ArrayList<Integer> values = new ArrayList<>();
            while (time.charAt(i) != ':') {
                values.add(Character.getNumericValue(time.charAt(i)));
                i++;
            }
            int multiplier = 1;
            for (int n = i; n >= 0; n--) {
                hour += values.get(n) * multiplier;
                multiplier *= 10;
            }
            min = Character.getNumericValue(time.charAt(++i)) * 10;
            min += Character.getNumericValue(time.charAt(++i));

            i++;
            sec = Character.getNumericValue(time.charAt(++i)) * 10;
            sec = Character.getNumericValue(time.charAt(++i));
        } else {
            min = Character.getNumericValue(time.charAt(0)) * 10;
            min += Character.getNumericValue(time.charAt(1));

            sec = Character.getNumericValue(time.charAt(3)) * 10;
            sec += Character.getNumericValue(time.charAt(4));
        }

    }

    public void addTime(int addTime) {
        sec += addTime;
        if (sec >= 60) {
            min++;
            sec = sec % 60;
        }
        if (min >= 60) {
            hour++;
            min = min % 60;
        }
    }

    public void startTimer() {
        this.start();
    }

    public void stopTimer() {
        //giveTimerInfo();
        this.interrupt();
    }

    public void resetTimer() {
        this.sec = 0;
        this.min = 0;
        this.hour = 0;
        //giveTimerInfo();
    }


}

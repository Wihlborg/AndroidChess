package com.example.androidchess.chessboard;

public class Timer {
    private long startTime;
    private long stopTime;

    public void startTimer() {
        startTime = 0;
        startTime = System.nanoTime();
        stopTime = 0;
    }

    public float stopTimer() {
        stopTime = System.nanoTime();
        return retrieveTime();
    }

    public float retrieveTime() {
        return (float)(stopTime-startTime)/1000000000;
    }

    public void printTime() {
        System.out.println((float)(stopTime-startTime)/1000000000);
    }

}

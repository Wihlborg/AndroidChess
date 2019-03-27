package com.example.androidchess.Activities;

import android.os.Handler;

public class WifiConnection {

    private static WifiConnection wifiConnection;


    public static MainActivity.ServerClass serverClass;
    public static MainActivity.ClientClass clientClass;
    public static MainActivity.SendReceive sendReceive;
    public static Handler handler;

    private WifiConnection() {

    }

    public static WifiConnection getInstance() {
        if (wifiConnection == null)
            wifiConnection = new WifiConnection();
        return wifiConnection;
    }

    public void send(final String fenString) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    sendReceive.write(fenString.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setWifiConnection(MainActivity.ServerClass serverClass, MainActivity.ClientClass clientClass, MainActivity.SendReceive sendReceive, Handler handler) {
        this.serverClass = serverClass;
        this.clientClass = clientClass;
        this.sendReceive = sendReceive;
        this.handler = handler;
    }

}

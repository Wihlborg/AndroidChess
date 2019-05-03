package com.example.androidchess.Activities;

import android.os.Handler;

public class WifiConnection {

    private static WifiConnection wifiConnection;


    public static LobbyActivity.ServerClass serverClass;
    public static LobbyActivity.ClientClass clientClass;
    public static LobbyActivity.SendReceive sendReceive;
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

    public void setWifiConnection(LobbyActivity.ServerClass serverClass, LobbyActivity.ClientClass clientClass, LobbyActivity.SendReceive sendReceive, Handler handler) {
        this.serverClass = serverClass;
        this.clientClass = clientClass;
        this.sendReceive = sendReceive;
        this.handler = handler;
    }

}

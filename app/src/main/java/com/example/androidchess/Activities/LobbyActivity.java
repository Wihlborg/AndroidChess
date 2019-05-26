package com.example.androidchess.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.androidchess.GameMode;
import com.example.androidchess.R;
import com.example.androidchess.chessboard.GameActivity;
import com.example.androidchess.chessboard.GameInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LobbyActivity extends AppCompatActivity {

    Button btnOnOFF, btnDiscover, btnSend;
    ListView listView;
    TextView read_msg_box, ConnectionStatus;
    EditText writeMsg;

    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;

    static final int MESSAGE_READ = 1;
    ServerClass serverClass;
    ClientClass clientClass;
    SendReceive sendReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        getSupportActionBar().hide();
        btnOnOFF = (Button) findViewById(R.id.onOff);
        btnDiscover = (Button) findViewById(R.id.discover);
        btnSend = (Button) findViewById(R.id.sendButton);
        listView = (ListView) findViewById(R.id.listView);
        read_msg_box = (TextView) findViewById(R.id.readMsg);
        ConnectionStatus = (TextView) findViewById(R.id.connectionStatus);
        writeMsg = (EditText) findViewById(R.id.writeMsg);

        initialWork();
        exqListner();
        WifiConnection.getInstance();
    }

    public static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff, 0, msg.arg1);
                    //TODO receiving board game state from opponent
                    //GameInfo.setBoardGameState(tempMsg);

                    //System.out.println(tempMsg);
                    //read_msg_box.setText(tempMsg);

                    break;

            }

            return true;
        }
    });

    private void exqListner() {
        btnOnOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                    btnOnOFF.setText("ON");
                } else {
                    wifiManager.setWifiEnabled(true);
                    btnOnOFF.setText("OFF");
                }

            }
        });

        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        ConnectionStatus.setText("Discovery Started");
                    }

                    @Override
                    public void onFailure(int i) {
                        ConnectionStatus.setText("Discovery Starting Failed");
                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final WifiP2pDevice device = deviceArray[i];
                WifiP2pConfig config = new WifiP2pConfig();

                config.deviceAddress = device.deviceAddress;
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Connected to " + device.deviceName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i) {
                        Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try  {
                            String msg=writeMsg.getText().toString();
                            sendReceive.write(msg.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                WifiConnection.getInstance().setWifiConnection(serverClass, clientClass, sendReceive, handler);
                Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
                GameMode.INSTANCE.setMode(GameMode.Mode.Online);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    private void initialWork() {


        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);

        //filter to get actions from wifidirect broadcast


        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);


    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerlist) {

            //Looking for peers!

            if (!peerlist.getDeviceList().equals(peers)) {
                peers.clear();
                peers.addAll(peerlist.getDeviceList());
                deviceNameArray = new String[peerlist.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerlist.getDeviceList().size()];
                int index = 0;

                //Add peers to listview
                for (WifiP2pDevice device : peerlist.getDeviceList()) {
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    index++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                listView.setAdapter(adapter);
            }
            if (peers.size() == 0) {
                Toast.makeText(getApplicationContext(), "no Device Found", Toast.LENGTH_SHORT).show();
                return;

            }

        }
    };


    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
            final InetAddress groupOwnerAdress = wifiP2pInfo.groupOwnerAddress;
            //First to connect host
            //sätt spelare
            if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
                ConnectionStatus.setText("Host");
                GameMode.INSTANCE.setPlayer(GameMode.Player.pOne);

                serverClass = new ServerClass();
                serverClass.start();

            }

            //second client
            //spelare 2
            else if (wifiP2pInfo.groupFormed) {
                ConnectionStatus.setText("Client");
                GameMode.INSTANCE.setPlayer(GameMode.Player.pTwo);
                clientClass = new ClientClass(groupOwnerAdress);
                clientClass.start();

            }


        }
    };


    public class ServerClass extends Thread {
        Socket socket;
        ServerSocket serverSocket;

        @Override
        public void run() {
            super.run();

            try {
                serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public class SendReceive extends Thread {
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public SendReceive(Socket skt) {

            socket = skt;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            super.run();
            byte[] buffer = new byte[1024];
            int bytes;
            while (socket != null) {

                try {
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ClientClass extends Thread {
        Socket socket;
        String hostAdd;


        public ClientClass(InetAddress hostAdress) {
            hostAdd = hostAdress.getHostAddress();
            socket = new Socket();

        }

        @Override
        public void run() {
            super.run();
            try {
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
                sendReceive = new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

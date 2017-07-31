package com.example.super_yu.myexample.websocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.super_yu.myexample.R;

public class WebSocketActivity extends AppCompatActivity implements WebSocketClient.AGV2ServerConnectionStatusListener {

    private Button mConnectBtn;
    private Button mSendBtn;
    private EditText mContentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);

        mConnectBtn = (Button) findViewById(R.id.connect);
        mSendBtn = (Button) findViewById(R.id.send);
        mContentText = (EditText) findViewById(R.id.content);
        mContentText.setText("");

        connectThread.start();

        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected) {
                    Toast.makeText(WebSocketActivity.this, "未连接", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WebSocketActivity.this, "已连接", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.a2ServerMessage(mContentText.getText().toString());
                mContentText.setText("");
            }
        });


    }

    private WebSocketClientCall client;
    private boolean isConnected = true;
    Thread connectThread = new Thread() {
        @Override
        public void run() {
            super.run();
            while (isConnected) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connect("ws://192.168.1.115:8888/AGVSys/websocket");
            }
        }
    };

    private void connect(String wsUrl) {
        client = new WebSocketClientCall(wsUrl);
        client.aGV2ServerDisconnect(this);
    }

    @Override
    public void onAGV2ServerConnect() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isConnected = false;
                Toast.makeText(WebSocketActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAGV2ServerDisconnect(boolean normal, String reason, int code) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isConnected = true;
                Toast.makeText(WebSocketActivity.this, "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAGV2ServerMessage(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebSocketActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onAGV2ServerError(Exception ex) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                isConnected = true;
                Toast.makeText(WebSocketActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

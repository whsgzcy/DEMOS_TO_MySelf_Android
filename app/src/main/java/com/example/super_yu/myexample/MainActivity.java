package com.example.super_yu.myexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.anim.Anim2Activity;
import com.example.super_yu.myexample.customview.CustomView2Activity;
import com.example.super_yu.myexample.dialog.Dialog2Activity;
import com.example.super_yu.myexample.eventbus.EventBus2Activity;
import com.example.super_yu.myexample.files.SFiles2Activity;
import com.example.super_yu.myexample.json.Json2Activity;
import com.example.super_yu.myexample.notification.Notification2Activity;
import com.example.super_yu.myexample.recylerview.RefreshRecyclerview2Activity;
import com.example.super_yu.myexample.remedia.Remarker2Activity;
import com.example.super_yu.myexample.serialp.SerialPort2Activity;
import com.example.super_yu.myexample.socket.Socket2Activity;
import com.example.super_yu.myexample.websocket.WebSocketActivity;
import com.example.super_yu.myexample.websocket.okhttp.OkHttpWebSocketActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button jsonBtn = (Button) findViewById(R.id.json);
        jsonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Json2Activity.class);
                startActivity(intent);
            }
        });

        Button notificationBtn = (Button) findViewById(R.id.notification);
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notification2Activity.class);
                startActivity(intent);
            }
        });

        Button socketBtn = (Button) findViewById(R.id.socket);
        socketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Socket2Activity.class);
                startActivity(intent);
            }
        });

        Button filesBtn = (Button) findViewById(R.id.files);
        filesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SFiles2Activity.class);
                startActivity(intent);
            }
        });

        Button animBtn = (Button) findViewById(R.id.anim);
        animBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Anim2Activity.class);
                startActivity(intent);
            }
        });

        Button dialogBtn = (Button) findViewById(R.id.dialog);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dialog2Activity.class);
                startActivity(intent);
            }
        });

        Button remarkerBtn = (Button) findViewById(R.id.remarker);
        remarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Remarker2Activity.class);
                startActivity(intent);
            }
        });

        Button seralPortBtn = (Button) findViewById(R.id.serial);
        seralPortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SerialPort2Activity.class);
                startActivity(intent);
            }
        });

        Button refreshBtn = (Button) findViewById(R.id.refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RefreshRecyclerview2Activity.class);
                startActivity(intent);
            }
        });

        Button eventsBtn = (Button) findViewById(R.id.events);
        eventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventBus2Activity.class);
                startActivity(intent);
            }
        });

        Button customViewBtn = (Button) findViewById(R.id.customview);
        customViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomView2Activity.class);
                startActivity(intent);
            }
        });

        Button webSockeyBtn = (Button) findViewById(R.id.websocket);
        webSockeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebSocketActivity.class);
                startActivity(intent);
            }
        });

        Button okHttp2WebSockeyBtn = (Button) findViewById(R.id.okhttp2websocket);
        okHttp2WebSockeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OkHttpWebSocketActivity.class);
                startActivity(intent);
            }
        });
    }
}

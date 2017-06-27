package com.example.super_yu.myexample;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.anim.Anim2Activity;
import com.example.super_yu.myexample.files.SFiles2Activity;
import com.example.super_yu.myexample.json.Json2Activity;
import com.example.super_yu.myexample.notification.Notification2Activity;
import com.example.super_yu.myexample.socket.Socket2Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button jsonBtn = (Button)findViewById(R.id.json);
        jsonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Json2Activity.class);
                startActivity(intent);
            }
        });

        Button notificationBtn = (Button)findViewById(R.id.notification);
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notification2Activity.class);
                startActivity(intent);
            }
        });

        Button socketBtn = (Button)findViewById(R.id.socket);
        socketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Socket2Activity.class);
                startActivity(intent);
            }
        });

        Button filesBtn = (Button)findViewById(R.id.files);
        filesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SFiles2Activity.class);
                startActivity(intent);
            }
        });

        Button animBtn = (Button)findViewById(R.id.anim);
        animBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Anim2Activity.class);
                startActivity(intent);
            }
        });
    }
}

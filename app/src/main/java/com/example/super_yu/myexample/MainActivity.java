package com.example.super_yu.myexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.json.Json2Activity;
import com.example.super_yu.myexample.notification.Notification2Activity;

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
    }
}

package com.example.super_yu.myexample.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.R;

import org.greenrobot.eventbus.EventBus;

public class EventsBus4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_bus4);

        Button sendStickyBtn = (Button) findViewById(R.id.sendStickyBtn);
        sendStickyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new MessageEvent("粘性事件"));
            }
        });

        Button sendPostBtn = (Button) findViewById(R.id.sendPostBtn);
        sendPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent("默认事件"));
            }
        });

    }
}

package com.example.super_yu.myexample.daemon;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.super_yu.myexample.R;

import static com.example.super_yu.myexample.MyExample.getAppContext;

public class OnePxMain2Activity extends AppCompatActivity {

    private BootStartReceiver mBootCompleteReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_px_main2);


        if (mBootCompleteReceiver == null) {
            mBootCompleteReceiver = new BootStartReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_ON);
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            registerReceiver(mBootCompleteReceiver, filter);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(1000);
                        Log.i("super_yu", "keep alive");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        getAppContext().startActivity(intent);
    }
}

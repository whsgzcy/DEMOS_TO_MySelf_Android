package com.example.super_yu.myexample.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.super_yu.myexample.daemon.OnePxMain2Activity;

/**
 * Created by super_yu on 2017/8/1.
 */

public class MonitorBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {

            Toast.makeText(context, "ACTION_SCREEN_OFF", Toast.LENGTH_SHORT).show();
            Log.e("super_yu", "ACTION_SCREEN_OFF");

        } else if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {

            Toast.makeText(context, "ACTION_SCREEN_ON", Toast.LENGTH_SHORT).show();
            Log.e("super_yu", "ACTION_SCREEN_ON");

        } else if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            context.startActivity(new Intent(context,OnePxMain2Activity.class));
            Toast.makeText(context, "ACTION_BOOT_COMPLETED", Toast.LENGTH_SHORT).show();
            Log.e("super_yu", "ACTION_BOOT_COMPLETED");

        } else if (intent.getAction().equals("android.intent.action.TEST")) {

            Toast.makeText(context, "TEST", Toast.LENGTH_SHORT).show();
            Log.e("super_yu", "android.intent.action.TEST");

        } else if (intent.getAction().equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {

            Toast.makeText(context, "android.intent.action.LOCKED_BOOT_COMPLETED", Toast.LENGTH_SHORT).show();
            Log.e("super_yu", "android.intent.action.LOCKED_BOOT_COMPLETED");

        }
    }
}
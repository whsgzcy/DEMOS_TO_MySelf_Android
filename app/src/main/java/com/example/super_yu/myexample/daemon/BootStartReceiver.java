package com.example.super_yu.myexample.daemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by super_yu on 2017/7/31.
 */

public class BootStartReceiver extends BroadcastReceiver {

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            OnePx2Activity.startOnePx2Activity();
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            OnePx2Activity.killOnePx2Activity();
        }
    }
}

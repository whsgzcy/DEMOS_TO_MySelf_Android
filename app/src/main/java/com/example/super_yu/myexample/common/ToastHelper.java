package com.example.super_yu.myexample.common;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastHelper {

    public static void setToatBytTime(Context c, String info, int time) {
        final Toast toast = Toast.makeText(c, info, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                toast.cancel();
            }
        }, time);
    }
}

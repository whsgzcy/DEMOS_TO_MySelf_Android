package com.example.super_yu.myexample;

import android.app.Application;
import android.content.Context;

/**
 * Created by super_yu on 2017/7/21.
 */

public class MyExample extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }
}

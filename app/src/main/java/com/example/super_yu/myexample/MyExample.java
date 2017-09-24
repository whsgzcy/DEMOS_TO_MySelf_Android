package com.example.super_yu.myexample;

import android.app.Application;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.webkit.WebView;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by super_yu on 2017/7/21.
 */

public class MyExample extends Application {

    private static Context mContext;
    public static MyExample instace;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        WebView mWebView=new WebView(new MutableContextWrapper(this));

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static MyExample getIntance() {
        return instace;
    }
}

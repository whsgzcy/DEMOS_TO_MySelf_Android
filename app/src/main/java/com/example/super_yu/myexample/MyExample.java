package com.example.super_yu.myexample;

import android.app.Application;
import android.content.Context;
import android.content.MutableContextWrapper;
import android.webkit.WebView;

import com.iflytek.cloud.SpeechUtility;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by super_yu on 2017/7/21.
 */

public class MyExample extends Application {

    private static Context mContext;
    public static MyExample instace;

    @Override
    public void onCreate() {

        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符
        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        SpeechUtility.createUtility(MyExample.this, "appid=" + getString(R.string.app_id));

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);

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

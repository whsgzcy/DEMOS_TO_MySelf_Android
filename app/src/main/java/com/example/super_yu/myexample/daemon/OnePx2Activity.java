package com.example.super_yu.myexample.daemon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.super_yu.myexample.MyExample;

public class OnePx2Activity extends Activity {

    private static OnePx2Activity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        Window window = getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = 0;
        params.y = 0;
        params.height = 1;
        params.width = 1;
        window.setAttributes(params);
    }

    /**
     * 开启保活页面
     */
    public static void startOnePx2Activity() {
        Intent intent = new Intent(MyExample.getAppContext(), OnePx2Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyExample.getAppContext().startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    /**
     * 关闭保活页面
     */
    public static void killOnePx2Activity() {
        if (instance != null) {
            instance.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

package com.example.super_yu.myexample.lock;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.super_yu.myexample.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 锁屏
 */
public class Lock2Activity extends AppCompatActivity implements View.OnClickListener{

    private Handler mHandler;
    private SildingFinishLayout mView;
//    private SimpleDraweeView mBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        Intent intent = new Intent();
//        intent.setAction(MediaService.LOCK_SCREEN);
//        sendBroadcast(intent);
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                        // bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_lock2);
        mView = (SildingFinishLayout) findViewById(R.id.lock_root);
//        mBack = (SimpleDraweeView) findViewById(R.id.lock_background);
        mView.setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {

            @Override
            public void onSildingFinish() {
                finish();
            }
        });
        mView.setTouchView(getWindow().getDecorView());
        mHandler = HandlerUtil.getInstance(this);
        mHandler.post(updateRunnable);
    }

    @Override
    protected void onUserLeaveHint() {
        Log.d("lock", "onUserLeaveHint");
        super.onUserLeaveHint();
//        Intent intent = new Intent();
//        intent.setAction(MediaService.LOCK_SCREEN);
//        intent.putExtra("islock", false);
//        sendBroadcast(intent);
//        finish();
    }

    Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm-MM月dd日 E", Locale.CHINESE);
            String date[] = simpleDateFormat.format(new Date()).split("-");
            mHandler.postDelayed(updateRunnable, 300);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lock", " on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("lock", " on pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("lock", " on stop");
    }

    @Override
    protected void onDestroy() {
//        Intent intent = new Intent();
//        intent.setAction(MediaService.LOCK_SCREEN);
//        intent.putExtra("islock", false);
//        sendBroadcast(intent);
        mHandler.removeCallbacks(updateRunnable);
        super.onDestroy();
        Log.e("lock", " on destroy");

    }

    @Override
    public void onBackPressed() {
        // do nothing
    }


    @Override
    public void onClick(View v) {

    }

}

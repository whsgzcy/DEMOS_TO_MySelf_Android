package com.example.super_yu.myexample.remedia;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.R;

import java.io.IOException;

public class Remarker2Activity extends AppCompatActivity implements View.OnClickListener {

    private Vibrator vibrator;
    private boolean shouldPlayBeep;

    private Camera camera;
    private Boolean isShanshuo = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remarker2);

        Button shakeBtn = (Button) findViewById(R.id.shake);
        Button shakeCancleBtn = (Button) findViewById(R.id.cancel_shake);

        shakeBtn.setOnClickListener(this);
        shakeCancleBtn.setOnClickListener(this);

        Button ringBtn = (Button) findViewById(R.id.ring);
        Button ringCancleBtn = (Button) findViewById(R.id.cancel_ring);

        ringBtn.setOnClickListener(this);
        ringCancleBtn.setOnClickListener(this);

        Button shineBtn = (Button) findViewById(R.id.shine);
        Button shineCancleBtn = (Button) findViewById(R.id.cancel_shine);

        shineBtn.setOnClickListener(this);
        shineCancleBtn.setOnClickListener(this);

        // 开启闪关灯闪烁
        mShineThread.start();
    }

//    private MediaPlayer accessFile() {
//        AssetFileDescriptor file = this.getResources().openRawResourceFd(
//                R.raw.Ding);
//        try {
//            mediaPlayer.setDataSource(file.getFileDescriptor(),
//                    file.getStartOffset(), file.getLength());
//            file.close();
//            mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
//            mediaPlayer.prepare();
//        } catch (IOException ioe) {
//            mediaPlayer = null;
//        }
//        return mediaPlayer;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shake:
                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {800, 2000, 800, 2000}; // 停止 开启 停止 开启
                vibrator.vibrate(pattern, 2); //重复两次上面的pattern 如果只想震动一次，index设为-1
                break;
            case R.id.cancel_shake:
                vibrator.cancel();
                break;
            case R.id.ring:
                BackgroundMusic.getInstance(this).playBackgroundMusic(this,R.raw.hello,true);
                break;
            case R.id.cancel_ring:
                BackgroundMusic.getInstance(this).stopBackgroundMusic();
                break;
            case R.id.shine:
                isShanshuo = true;
                break;
            case R.id.cancel_shine:
                isShanshuo = false;
                break;
        }
    }

    /**
     * 打开闪光灯
     *
     * @return
     */
    private void open() {
        try {
            camera = Camera.open();
            camera.startPreview();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭闪光灯
     *
     * @return
     */
    private void close() {
        try {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.release();
            camera = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Thread mShineThread = new Thread() {
        @Override
        public void run() {
            while (isShanshuo) {
                open();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                close();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}

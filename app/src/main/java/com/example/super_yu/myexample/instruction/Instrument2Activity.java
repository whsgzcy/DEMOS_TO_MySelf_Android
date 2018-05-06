package com.example.super_yu.myexample.instruction;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.super_yu.myexample.R;

public class Instrument2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument2);


        clickThread.start();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Toast.makeText(Instrument2Activity.this, "x = " + ev.getX() + " y = " + ev.getY(), Toast.LENGTH_SHORT).show();

        return super.dispatchTouchEvent(ev);
    }

    Thread clickThread = new Thread() {

        @Override
        public void run() {
            super.run();
            try {

                Thread.sleep(2000);

                Instrumentation mInst = new Instrumentation();
//                //按键事件
//                mInst.sendKeyDownUpSync(KeyEvent.KEYCODE_A);
                //触摸按下
                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 1138, 1216, 0));
                //触摸抬起
                mInst.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 1138, 1216, 0));

                Thread.sleep(3000);
                Instrumentation mInsts = new Instrumentation();
                //按键事件
//                mInsts.sendKeyDownUpSync(KeyEvent.KEYCODE_A);
                //触摸按下
                mInsts.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 1138, 1216, 0));
                //触摸抬起
                mInsts.sendPointerSync(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 1138, 1216, 0));

            } catch (Exception e) {

            }

        }
    };
}
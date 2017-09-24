package com.example.super_yu.myexample.daemon;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.super_yu.myexample.MainActivity;
import com.example.super_yu.myexample.MyExample;

/**
 * Created by super_yu on 2017/9/24.
 */

public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler defaultUEH;
    Activity activity;

    public DefaultExceptionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        try {

            Intent intent = new Intent(activity, MainActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    MyExample.getIntance().getBaseContext(), 0, intent, intent.getFlags());

            //Following code will restart your application after 2 seconds
            AlarmManager mgr = (AlarmManager) MyExample.getIntance().getBaseContext()
                    .getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
                    pendingIntent);

            //This will finish your activity manually
            activity.finish();

            //This will stop your application and take out from it.
            System.exit(2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

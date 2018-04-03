package com.example.super_yu.myexample.aidl.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.codezjx.andlinker.AndLinkerBinder;
import com.example.super_yu.myexample.aidl.IRemoteService;

public class RemoteService extends Service {

    private AndLinkerBinder mLinkerBinder;

    @Override
    public IBinder onBind(Intent intent) {
        return mLinkerBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLinkerBinder = AndLinkerBinder.Factory.newBinder();
        mLinkerBinder.registerObject(mRemoteService);
    }

    private final IRemoteService mRemoteService = new IRemoteService() {

        @Override
        public int getPid() {
            return android.os.Process.myPid();
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {
            // Does something
            Log.d("t", "anInt = " + anInt);
            Log.d("t", "aLong = " + aLong);
            Log.d("t", "aBoolean = " + aBoolean);
            Log.d("t", "aFloat = " + aFloat);
            Log.d("t", "aDouble = " + aDouble);
            Log.d("t", "aString = " + aString);
        }
    };
}

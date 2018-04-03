package com.example.super_yu.myexample.aidl.client;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.codezjx.andlinker.AndLinker;
import com.codezjx.andlinker.adapter.OriginalCallAdapterFactory;
import com.codezjx.andlinker.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.aidl.IRemoteService;

public class BindingActivity extends AppCompatActivity {

    private AndLinker mLinker;
    private IRemoteService mRemoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);

        mLinker = new AndLinker.Builder(this)
                .packageName("com.example.super_yu.myexample")
                .action("com.example.super_yu.myexample.hello")
                .addCallAdapterFactory(OriginalCallAdapterFactory.create()) // Basic
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // RxJava2
                .build();
        mLinker.bind();

        mRemoteService = mLinker.create(IRemoteService.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRemoteService.basicTypes(1, 2L, true, 3.0f, 4.0d, "str");
            }
        },3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLinker.unbind();
    }
}

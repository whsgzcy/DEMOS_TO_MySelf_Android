package com.example.super_yu.myexample.webview;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.super_yu.myexample.R;

/**
 * https://github.com/Justson/AgentWeb
 */
public class SimpleWebView extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    public static final String TYPE_KEY = "type_key";
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_web_view);

        mFrameLayout = (FrameLayout) this.findViewById(R.id.container_framelayout);
        mFragmentManager = this.getSupportFragmentManager();
    }
}

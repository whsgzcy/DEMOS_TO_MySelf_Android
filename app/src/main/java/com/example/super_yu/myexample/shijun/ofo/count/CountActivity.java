package com.example.super_yu.myexample.shijun.ofo.count;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.super_yu.myexample.R;

public class CountActivity extends AppCompatActivity {
    private CountDownView mCdView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        mCdView1 = (CountDownView) findViewById(R.id.cd_view1);
        mCdView1.start();
        mCdView1.setOnLoadingFinishListener(new CountDownView.OnLoadingFinishListener() {
            @Override
            public void finish() {
                Toast.makeText(CountActivity.this, "加载动画执行结束", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

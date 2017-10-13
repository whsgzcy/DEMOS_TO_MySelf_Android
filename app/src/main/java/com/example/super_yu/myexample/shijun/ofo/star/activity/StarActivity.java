package com.example.super_yu.myexample.shijun.ofo.star.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.shijun.ofo.star.activity.star.activity.view.StarRatingView;

public class StarActivity extends AppCompatActivity {
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        final StarRatingView srv = (StarRatingView) findViewById(R.id.srv);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                srv.setRate(count);
            }
        });
        final TextView tv = (TextView) findViewById(R.id.tv);
        StarRatingView srv_ratable = (StarRatingView) findViewById(R.id.srv_ratable);
        srv_ratable.setOnRateChangeListener(new StarRatingView.OnRateChangeListener() {
            @Override
            public void onRateChange(int rate) {
                tv.setText(rate+"分");
            }
        });
    }
}

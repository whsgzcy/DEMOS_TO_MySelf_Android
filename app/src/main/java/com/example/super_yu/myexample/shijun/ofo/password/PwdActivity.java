package com.example.super_yu.myexample.shijun.ofo.password;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.super_yu.myexample.R;

import butterknife.ButterKnife;

public class PwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pwd);
        ButterKnife.bind(this);
    }
}

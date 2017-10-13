package com.example.super_yu.myexample.anim;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.super_yu.myexample.R;

public class Anim2Activity extends AppCompatActivity {

    private WaterWaveView mWaterWave;
    int progress = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim2);
        mWaterWave = (WaterWaveView) findViewById(R.id.waterWaveView);
    }

    public void start(View view) {
        mWaterWave.startWave();
    }

    public void stop(View view) {
        mWaterWave.stopWave();
    }

    public void progressUp(View view) {
        mWaterWave.setProgress((progress++));
    }

    public void progressDown(View view) {
        mWaterWave.setProgress((progress--));
    }

}

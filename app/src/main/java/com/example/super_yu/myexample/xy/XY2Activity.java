package com.example.super_yu.myexample.xy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.example.super_yu.myexample.R;

public class XY2Activity extends AppCompatActivity {

    private TextView mOneText;
    private TextView mTwoText;
    private TextView mShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xy2);

//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        int width = metrics.widthPixels;
//        int height = metrics.heightPixels;
//
//        mOneText = (TextView) findViewById(R.id.one);
//        mTwoText = (TextView) findViewById(R.id.two);
//        mShowText = (TextView) findViewById(R.id.show);
//
//        int[] inOutLocation = new int[2];
//        mOneText.getLocationInWindow(inOutLocation);
//        int oneX = inOutLocation[0];
//        int oneY = inOutLocation[1];
//
//        int[] twoLocation = new int[2];
//        mTwoText.getLocationInWindow(twoLocation);
//        int twoX = twoLocation[0];
//        int twoY = twoLocation[1];
//
//        mShowText.setText(width + "," + height + ";" + oneX + "," + oneY + ";" + twoX + "," + twoY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        mOneText = (TextView) findViewById(R.id.one);
        mTwoText = (TextView) findViewById(R.id.two);
        mShowText = (TextView) findViewById(R.id.show);

        int[] inOutLocation = new int[2];
        mOneText.getLocationInWindow(inOutLocation);
        int oneX = inOutLocation[0];
        int oneY = inOutLocation[1];

        int[] twoLocation = new int[2];
        mTwoText.getLocationInWindow(twoLocation);
        int twoX = twoLocation[0];
        int twoY = twoLocation[1];

        mShowText.setText(width + "," + height + ";" + oneX + "," + oneY + ";" + twoX + "," + twoY);
    }
}

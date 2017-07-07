package com.example.super_yu.myexample.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.dialog.viewer.FourView;
import com.example.super_yu.myexample.dialog.viewer.OneView;
import com.example.super_yu.myexample.dialog.viewer.ThreeView;
import com.example.super_yu.myexample.dialog.viewer.TwoView;
import com.example.superalertdialoglibrary.SweetAlertDialog;


public class Dialog2Activity extends AppCompatActivity {

    private LinearLayout mContainerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dialog2);

        mContainerView = (LinearLayout) findViewById(R.id.container);

        Button one = (Button) findViewById(R.id.one);
        Button two = (Button) findViewById(R.id.two);
        Button three = (Button) findViewById(R.id.three);
        Button four = (Button) findViewById(R.id.four);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainerView.removeAllViews();
                OneView one = new OneView(Dialog2Activity.this);
                one.test();
                mContainerView.addView(one);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainerView.removeAllViews();
                TwoView two = new TwoView(Dialog2Activity.this);
                two.test();
                mContainerView.addView(two);
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainerView.removeAllViews();
                ThreeView three = new ThreeView(Dialog2Activity.this);
                three.test();
                mContainerView.addView(three);
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainerView.removeAllViews();
                FourView four = new FourView(Dialog2Activity.this);
                four.test();
                mContainerView.addView(four);
            }
        });


//        Button successBtn = (Button) findViewById(R.id.success);
//        successBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                new SweetAlertDialog(Dialog2Activity.this, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("Good job!")
//                        .setContentText("You clicked the button!")
//                        .show();
//            }
//        });
//
//        Button wordBtn = (Button) findViewById(R.id.word);
//        wordBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActionMonitorDialog d = ActionMonitorDialog.showDialog(Dialog2Activity.this);
//                d.show();
//            }
//        });


    }
}

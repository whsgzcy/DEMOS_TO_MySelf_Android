package com.example.super_yu.myexample.shijun.ofo.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.shijun.ofo.view.MenuBrawable;
import com.example.super_yu.myexample.shijun.ofo.view.OfoContentLayout;
import com.example.super_yu.myexample.shijun.ofo.view.OfoMenuLayout;

public class OfoMainActivity extends AppCompatActivity {
    //最外层的view，用来管理title和content的动画
    OfoMenuLayout ofoMenuLayout;
    //contennt中列表view，主要是控制自己的动画
    OfoContentLayout ofoContentLayout;
    FrameLayout menu;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofo_main);
        initView();
    }

    private void initView(){
        ofoMenuLayout = ((OfoMenuLayout) findViewById(R.id.ofo_menu));
        ofoContentLayout = ((OfoContentLayout) findViewById(R.id.ofo_content));
        menu = (FrameLayout) findViewById(R.id.menu_content);
        menu.setBackground(new MenuBrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.bitmap), OfoMainActivity.this));
        startBtn = (Button) findViewById(R.id.start_ofo);
        //启动menu
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBtn.setVisibility(View.GONE);
                ofoMenuLayout.setVisibility(View.VISIBLE);
                ofoMenuLayout.open();

            }
        });
        //关闭menu
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ofoMenuLayout.close();
            }
        });
        //menu的监听
        ofoMenuLayout.setOfoMenuStatusListener(new OfoMenuLayout.OfoMenuStatusListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {
                startBtn.setVisibility(View.VISIBLE);
            }
        });
        //给menu设置content部分
        ofoMenuLayout.setOfoContentLayout(ofoContentLayout);
    }
}

package com.example.super_yu.myexample.dialog.viewer;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by super_yu on 2017/7/7.
 */

public class MainView extends LinearLayout {

    protected Context mContext = null;

    public MainView(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public LinearLayout.LayoutParams createCommonLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }

    private void initView(Context context) {
        this.setLayoutParams(createCommonLayoutParams());
        this.setGravity(Gravity.CENTER);
    }
}

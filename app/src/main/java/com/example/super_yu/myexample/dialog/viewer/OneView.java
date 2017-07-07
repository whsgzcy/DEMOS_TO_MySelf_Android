package com.example.super_yu.myexample.dialog.viewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.super_yu.myexample.R;

/**
 * Created by super_yu on 2017/7/7.
 */

public class OneView extends MainView {

    private TextView mView;

    public OneView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        this.setOrientation(VERTICAL);
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.one, this);
        mView = (TextView) rootView.findViewById(R.id.one);
    }

    public void test() {
        mView.setText("111");
    }
}

package com.example.super_yu.myexample.dialog.viewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.super_yu.myexample.R;

/**
 * Created by super_yu on 2017/7/7.
 */

public class FourView extends MainView {

    private TextView mView;

    public FourView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        this.setOrientation(VERTICAL);
        LinearLayout rootView = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.four, this);
        mView = (TextView) rootView.findViewById(R.id.four);
    }

    public void test() {
        mView.setText("444");
    }

}

package com.example.super_yu.myexample.dialog.viewer;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.super_yu.myexample.MyExample;

/**
 * Created by super_yu on 2017/7/21.
 */

public class BussInessViewStateMachine {

    private Context mContext;

    private LinearLayout mContainerView;

    private static BussInessViewStateMachine mInstance;

    private BussInessViewStateMachine() {
        mContext = MyExample.getAppContext();
    }

    public static BussInessViewStateMachine getInstance() {
        if (mInstance == null) {
            mInstance = new BussInessViewStateMachine();
        }
        return mInstance;
    }

    public void setContainerView(LinearLayout mContainerView) {
        this.mContainerView = mContainerView;
    }

    public void test1() {
        mContainerView.removeAllViews();
        OneView one = new OneView(mContext);
        mContainerView.addView(one);
        one.test();
    }

    public void test2() {
        mContainerView.removeAllViews();
        TwoView one = new TwoView(mContext);
        mContainerView.addView(one);
        one.test();
    }


    public void test3() {
        mContainerView.removeAllViews();
        ThreeView one = new ThreeView(mContext);
        mContainerView.addView(one);
        one.test();
    }

    public void test4() {
        mContainerView.removeAllViews();
        FourView one = new FourView(mContext);
        mContainerView.addView(one);
        one.test();
    }


}

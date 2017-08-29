package com.example.super_yu.myexample.customview;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.super_yu.myexample.R;


/**
 * dialog 选择站点数据
 * Created by super_yu on 2017/8/28.
 */

public class SelectPointsDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogView = inflater.inflate(R.layout.dialog_point_select, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialogView;
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels - dm.widthPixels / 10, getDialog().getWindow().getAttributes().height);
    }


}

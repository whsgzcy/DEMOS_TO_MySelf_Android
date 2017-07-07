package com.example.super_yu.myexample.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.customview.IdentifyingCodeView;

/**
 * Created by super_yu on 2017/7/7.
 */

public class ActionMonitorDialog extends Dialog {

    private Context context;
    private static ActionMonitorDialog dialog;
    private TextView mTitle;
    private TextView mStart;
    private IdentifyingCodeView mWordView;

    private static View layout;

    public ActionMonitorDialog(Context context) {
        super(context);
        this.context = context;
    }

    public ActionMonitorDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }

    //显示dialog的方法
    public static ActionMonitorDialog showDialog(Context context) {
        dialog = new ActionMonitorDialog(context, R.style.MyDialog);//dialog样式

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = inflater.inflate(R.layout.dialog_content, null);
        dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //        dialog.setContentView(R.layout.dialog_content);//dialog布局文件

        dialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && dialog != null) {
            mTitle = (TextView) dialog.findViewById(R.id.title);
            mStart = (TextView) dialog.findViewById(R.id.action);
            mWordView = (IdentifyingCodeView) dialog.findViewById(R.id.wrod);
        }
    }
}

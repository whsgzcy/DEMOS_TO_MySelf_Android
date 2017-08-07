package com.example.super_yu.myexample.customview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.super_yu.myexample.R;

/**
 * PopupWindow集合类
 */

public class PopHelper {

    private Context mContext;
    private PopupWindow pw;
    private CityPicker cityPicker;

    public PopHelper(Context context) {
        mContext = context;
    }

    /**
     * @param view
     * @param itemsOnClick 抛出一个OnClickListener接口
     */
    public void showAddressPop(View view, final Activity activity, View.OnClickListener itemsOnClick) {
        // 设置pw里的点击事件
        LinearLayout addressView = (LinearLayout) View.inflate(mContext, R.layout.pop_address, null);
        Button btnPopAddressCencel = (Button) addressView.findViewById(R.id.btn_pop_address_cencel);
        btnPopAddressCencel.setOnClickListener(itemsOnClick);
        Button btnPopAddressEnter = (Button) addressView.findViewById(R.id.btn_pop_address_enter);
        btnPopAddressEnter.setOnClickListener(itemsOnClick);
        cityPicker = (CityPicker) addressView.findViewById(R.id.citypicker);
        //-2是包裹自己，-1是填充父容器
        pw = new PopupWindow(addressView, -1, -2);
        //先关闭
        colsePopupwindow();
        //必须要设置一个背景
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //点击外边可关闭pw
        pw.setOutsideTouchable(true);
        //pw内可获取焦点
        pw.setFocusable(true);
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        activity.getWindow().setAttributes(lp);
        //退出时恢复透明度
        pw.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
        //设置动画
        pw.setAnimationStyle(R.style.take_photo_anim);
        //显示。y轴距离底部100是因为部分手机有虚拟按键，所以实际项目中要动态设置
        pw.showAtLocation(view, Gravity.BOTTOM, 0, 100);
    }

    /**
     * @return 获取控件中的地址数据
     */
    public String getAddressData() {
        String cData = cityPicker.getCity_string();
        return cData;
    }

    /**
     * 关闭pw
     */
    public void colsePopupwindow() {
        if (pw != null && pw.isShowing()) {
            pw.dismiss();
            pw = null;
        }
    }

}

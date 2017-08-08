package com.example.super_yu.myexample.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.super_yu.myexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表Picker
 *
 * @author
 */
public class CityPicker extends LinearLayout {
    /**
     * 滑动控件
     */
    private ScrollerNumberPicker provincePicker;
    private ScrollerNumberPicker cityPicker;
    /**
     * 选择监听
     */
    private OnSelectingListener onSelectingListener;
    /**
     * 刷新界面
     */
    private static final int REFRESH_VIEW = 0x001;
    /**
     * 临时日期
     */
    private int tempProvinceIndex = -1;
    private int temCityIndex = -1;
    private Context context;

    private String city_string;

    private TT tt;

    public CityPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setData();
        // TODO Auto-generated constructor stub
    }

    public CityPicker(Context context) {
        super(context);
        this.context = context;
        // TODO Auto-generated constructor stub
    }

    // 设置数据
    public void setData() {
        tt = new TT();
        List<TT.DatasBean> mDatasBeanList = new ArrayList<TT.DatasBean>();
        for (int i = 0; i < 3; i++) {
            TT.DatasBean t = new TT.DatasBean();
            List<TT.DatasBean.DataBean> tList = new ArrayList<TT.DatasBean.DataBean>();

            if (i == 0) {
                t.setDepart("门诊部");
            } else if (i == 1) {
                t.setDepart("卫生部");
            } else if (i == 2) {
                t.setDepart("研发部");
            }

            for (int m = 0; m < 3; m++) {
                TT.DatasBean.DataBean d = new TT.DatasBean.DataBean();
                if (i == 0) {
                    d.setName("A" + m + 1);
                } else if (i == 1) {
                    d.setName("B" + m + 1);
                } else if (i == 2) {
                    d.setName("C" + m + 1);
                }
                tList.add(d);
            }
            t.setData(tList);
            mDatasBeanList.add(t);
        }
        tt.setName("测试数据");
        tt.setDatas(mDatasBeanList);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.city_picker, this);
        // 获取控件引用
        provincePicker = (ScrollerNumberPicker) findViewById(R.id.province);
        cityPicker = (ScrollerNumberPicker) findViewById(R.id.city);

        provincePicker.setData(StringHelper.objectA2StringArray(tt.getDatas()));
        provincePicker.setDefault(0);
        cityPicker.setData(StringHelper.objectB2StringArray(tt.getDatas(), 0));
        cityPicker.setDefault(0);

        provincePicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                // TODO Auto-generated method stub
                System.out.println("id-->" + id + "text----->" + text);
                if (text.equals("") || text == null)
                    return;
                if (tempProvinceIndex != id) {
                    System.out.println("endselect");
                    String selectDay = cityPicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    // 城市数组
                    cityPicker.setData(StringHelper.objectB2StringArray(tt.getDatas(), id));
                    cityPicker.setDefault(1);

                    int lastDay = Integer.valueOf(provincePicker.getListSize());
                    if (id > lastDay) {
                        provincePicker.setDefault(lastDay - 1);
                    }
                }
                tempProvinceIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub
            }
        });
        cityPicker.setOnSelectListener(new ScrollerNumberPicker.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                // TODO Auto-generated method stub
                if (text.equals("") || text == null)
                    return;
                if (temCityIndex != id) {
                    String selectDay = provincePicker.getSelectedText();
                    if (selectDay == null || selectDay.equals(""))
                        return;
                    int lastDay = Integer.valueOf(cityPicker.getListSize());
                    if (id > lastDay) {
                        cityPicker.setDefault(lastDay - 1);
                    }
                }
                temCityIndex = id;
                Message message = new Message();
                message.what = REFRESH_VIEW;
                handler.sendMessage(message);
            }

            @Override
            public void selecting(int id, String text) {
                // TODO Auto-generated method stub

            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_VIEW:
                    if (onSelectingListener != null)
                        onSelectingListener.selected(true);
                    break;
                default:
                    break;
            }
        }

    };

    public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
        this.onSelectingListener = onSelectingListener;
    }

    public String getCity_string() {
        city_string = provincePicker.getSelectedText()
                + cityPicker.getSelectedText();
        return city_string;
    }

    public interface OnSelectingListener {

        public void selected(boolean selected);
    }
}

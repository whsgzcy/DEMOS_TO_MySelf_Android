package com.example.super_yu.myexample.shijun.ofo.Indicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.super_yu.myexample.R;

import java.util.ArrayList;
import java.util.List;

public class IndicatorActivity extends AppCompatActivity {

    private int[] resId = {R.drawable.ic_1, R.drawable.ic_3, R.drawable.ic_4};
    private String str="abc#bcd#efg";
    private List<String> strArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator);
        IndicatorView circleIndicator = (IndicatorView) findViewById(R.id.indicator_circle);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new ViewPagerAdapter(this, resId));
        circleIndicator.setupViewPager(viewPager);


        String strs[] = str.split("#");
        for (int i = 0; i<strs.length; i++){
            strArray.add(strs[i]);
        }
        Log.d("shijun","切割字符串"+strArray);

    }


    class ViewPagerAdapter extends PagerAdapter {

        private Context context;

        private int[] url;

        public ViewPagerAdapter(Context context, int[] url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public int getCount() {
            return url.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(url[position]);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView(container.getChildAt(position));
        }
    }


    //使用String的split 方法
    public static String[] convertStrToArray(String str){
        String[] strArray = null;
        strArray = str.split("[#]"); //拆分字符为"#" ,然后把结果交给数组strArray
        return strArray;
    }

}
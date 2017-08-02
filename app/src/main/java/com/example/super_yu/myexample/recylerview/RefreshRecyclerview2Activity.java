package com.example.super_yu.myexample.recylerview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.recylerview.recyclerviewlib.LFRecyclerView;
import com.example.super_yu.myexample.recylerview.recyclerviewlib.OnItemClickListener;

import java.util.ArrayList;

public class RefreshRecyclerview2Activity extends AppCompatActivity implements OnItemClickListener, LFRecyclerView.LFRecyclerViewListener, LFRecyclerView.LFRecyclerViewScrollChange {

    private LFRecyclerView recycleview;
    private boolean b;
    private ArrayList<String> list;
    private RefreshRecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recyclerview2);

        list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add("whsgzcy" + i);
        }

        recycleview = (LFRecyclerView) findViewById(R.id.recycleview);
        recycleview.setLoadMore(true);
        recycleview.setRefresh(true);
        recycleview.setNoDateShow();
        recycleview.setAutoLoadMore(true);
        recycleview.setOnItemClickListener(this);
        recycleview.setLFRecyclerViewListener(this);
        recycleview.setScrollChangeListener(this);
        recycleview.setItemAnimator(new DefaultItemAnimator());

        TextView tv = new TextView(RefreshRecyclerview2Activity.this);
        tv.setText("这是头部");
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.RED);
        recycleview.setHeaderView(tv);

        adapter = new RefreshRecyclerviewAdapter(list);
        recycleview.setAdapter(adapter);

        simulateClick(recycleview);

    }

    @Override
    public void onClick(int position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(int po) {
        Toast.makeText(this, "Long:" + po, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                b = !b;
                list.add(0, "whsgzcy" + "==onRefresh");
                recycleview.stopRefresh(b);
                adapter.notifyItemInserted(0);
                adapter.notifyItemRangeChanged(0, list.size());

            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                recycleview.stopLoadMore();
//                list.add(list.size(), "whsgzcy" + "==onLoadMore");
////                list.add(list.size(), "leefeng.me" + "==onLoadMore");
//                adapter.notifyItemRangeInserted(list.size() - 1, 1);

            }
        }, 2000);
    }

    private void simulateClick(final View view) {

        final MotionEvent downEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 300, 400, 0);
        view.onTouchEvent(downEvent);
        downEvent.recycle();

        final MotionEvent moveEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE, 300, 1000, 0);
        view.onTouchEvent(moveEvent);
        moveEvent.recycle();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final MotionEvent upEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 300, 1000, 0);
                view.onTouchEvent(upEvent);
                upEvent.recycle();
            }
        }, 300);

    }

    @Override
    public void onRecyclerViewScrollChange(View view, int i, int i1) {

    }
}

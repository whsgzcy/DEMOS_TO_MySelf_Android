package com.example.super_yu.myexample.gsonorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.super_yu.myexample.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GsonOrder2Activity extends AppCompatActivity {

    @BindView(R.id.text11)
    TextView text11;
    @BindView(R.id.text2)
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_order2);
        ButterKnife.bind(this);

        Test t = new Test();
        t.setA("q");
        t.setB("w");
        List<String> m = new ArrayList<>();
        m.add("t");
        m.add("t");
        t.setList(m);

        String tt = new Gson().toJson(t);

        EventC ec = new EventC();
        ec.setT("abc");
        ec.setN("d");
        ec.setM(tt);

        String json = JSON.toJSONString(ec);

        System.out.println(json);

        text11.setText(json);

        EventC e = new Gson().fromJson(json,EventC.class);

        Test tst = new Gson().fromJson(e.getM().toString(),Test.class);

        String content = tst.getA() + " " + tst.getB() + " " + tst.getList().get(0).toString();
        text2.setText(content);

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(EventC.class, new EventTypeAdapter())
//                //registerTypeAdapter可以重复使用
//                .create();
//
//        Test t = new Test();
//        t.setA("q");
//        t.setB("w");
//        List<String> m = new ArrayList<>();
//        m.add("t");
//        m.add("t");
//        t.setList(m);
//
//        String tt = new Gson().toJson(t);
//
//        EventC ec = new EventC();
//        ec.setT("abc");
//        ec.setN("d");
//        ec.setM(tt);
//
//
//
//        String l = gson.toJson(ec,EventC.class);
//        text11.setText(l);
//
//        Gson gson2 = new GsonBuilder()
//                .registerTypeAdapter(EventC.class, new Event2TypeAdaper())
//                //registerTypeAdapter可以重复使用
//                .create();
//
//        EventC ec2 = new EventC();
//        ec2.setT("abc");
//        ec2.setN("d");
//
//        ec2.setM("123");
//
//        String ll = gson2.toJson(ec2);
//        text2.setText(ll);

    }
}
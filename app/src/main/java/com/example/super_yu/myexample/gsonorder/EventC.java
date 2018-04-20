package com.example.super_yu.myexample.gsonorder;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * Created by super_yu on 2018/4/20.
 */

@JSONType(orders = { "t", "n", "m"})
public class EventC {

//        {
//            "n": "001",
//            "t": "poi",
//            "m": "o"
//    }

    /**
     * n : 001
     * t : poi
     * m : o
     */

    public String n;
    public String t;
    public Object m;

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Object getM() {
        return m;
    }

    public void setM(Object m) {
        this.m = m;
    }
}
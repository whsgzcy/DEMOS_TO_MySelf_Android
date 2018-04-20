package com.example.super_yu.myexample.gsonorder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by super_yu on 2018/4/20.
 */

public class Test {

    String a;
    String b;
    List<String> list;

    public String getA() {
        return a == null ? "" : a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b == null ? "" : b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public List<String> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}

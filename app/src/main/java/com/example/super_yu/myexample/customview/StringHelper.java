package com.example.super_yu.myexample.customview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by super_yu on 2017/8/7.
 */

public class StringHelper {

    public static ArrayList<String> objectA2StringArray(List<TT.DatasBean> datas) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < datas.size(); i++) {
            arrayList.add(datas.get(i).getDepart().toString());
        }
        return arrayList;
    }

    public static ArrayList<String> objectB2StringArray(List<TT.DatasBean> datas, int num) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < datas.get(num).getData().size(); i++) {
            arrayList.add(datas.get(num).getData().get(i).getName().toString());
        }
        return arrayList;
    }
}

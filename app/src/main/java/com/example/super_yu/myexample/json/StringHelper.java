package com.example.super_yu.myexample.json;

/**
 * Created by super_yu on 2017/6/13.
 */

public class StringHelper {


    public static int str2Floor(String point){
        int floor;
        point = point.replace("map", "");
        // _4_A_403
        int a = point.indexOf("_");
        int length = point.length();
        point = point.substring(a+1, length);
        // 4_A_403
        int b = point.indexOf("_");
        floor = Integer.parseInt(point.substring(0,b));
        return floor;
    }

    public static String str2Classify(String point){
        String classify;
        point = point.replace("map", "");
        // _4_A_403
        int a = point.indexOf("_");
        int length = point.length();
        point = point.substring(a+1, length);
        // 4_A_403
        int b = point.indexOf("_");
        int c = point.lastIndexOf("_");
        classify = point.substring(b+1,c);
        return classify;
    }

    public static String str2Room(String point){
        String room;
        point = point.replace("map", "");
        // _4_A_403
        int a = point.indexOf("_");
        int length = point.length();
        point = point.substring(a+1, length);
        int c = point.lastIndexOf("_");
        length = point.length();
        room = point.substring(c+1,length);
        return room;
    }




}

package com.example.super_yu.myexample.gsonorder;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 *
 * Created by super_yu on 2018/4/20.
 */

public class Event2TypeAdaper extends TypeAdapter<EventC> {

    @Override
    public void write(JsonWriter out, EventC value) throws IOException {
        out.beginObject();
        //按自定义顺序输出字段信息
        out.name("m").value(value.m + "");
        out.name("t").value(value.t);
        out.name("n").value(value.n);
        out.endObject();
    }

    @Override
    public EventC read(JsonReader in) throws IOException {
        return null;
    }
}
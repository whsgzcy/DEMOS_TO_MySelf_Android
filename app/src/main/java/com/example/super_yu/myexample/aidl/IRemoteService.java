package com.example.super_yu.myexample.aidl;

import com.codezjx.andlinker.annotation.RemoteInterface;

/**
 * Created by super_yu on 2018/4/3.
 */
@RemoteInterface
public interface IRemoteService {

    int getPid();

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                    double aDouble, String aString);
}

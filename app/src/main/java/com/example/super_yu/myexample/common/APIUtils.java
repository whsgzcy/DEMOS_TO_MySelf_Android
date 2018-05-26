package com.example.super_yu.myexample.common;

import android.os.Build;

/**
 * Used API当前版本判断类
 */
public class APIUtils {

    public static boolean hasFroyo()
    {
        return Build.VERSION.SDK_INT >= 8;
    }

    public static boolean hasGingerbread()
    {
        return Build.VERSION.SDK_INT >= 9;
    }

    public static boolean hasHoneycomb()
    {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean hasHoneycombMR1()
    {
        return Build.VERSION.SDK_INT >= 12;
    }

    public static boolean hasICS()
    {
        return Build.VERSION.SDK_INT >= 14;
    }

    public static boolean hasICSMR1()
    {
        return Build.VERSION.SDK_INT >= 15;
    }

    public static boolean hasJellyBean()
    {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean hasJellyBeanMR1()
    {
        return Build.VERSION.SDK_INT >= 17;
    }

    public static boolean hasJellyBeanMR2()
    {
        return Build.VERSION.SDK_INT >= 18;
    }

    public static boolean hasKitKat()
    {
        return Build.VERSION.SDK_INT >= 19;
    }
}

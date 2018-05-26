package com.example.super_yu.myexample.common;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Used 公共类：各种非常用方法的集合
 */
public class Utility {


    /**获取SD卡缓存目录*/
    public static String getExternalCacheDir(Context paramContext)
    {
        if (APIUtils.hasFroyo());
        for (File localFile = paramContext.getExternalCacheDir(); ; )
        {
            localFile = Environment.getExternalStorageDirectory();
            if (localFile == null)
                localFile = paramContext.getCacheDir();
            if (localFile == null)
                break;
            return localFile.getAbsolutePath();
        }
        return null;
    }

    /**获取程序的包名*/
    public static String getPkgName(Context localContext)
    {
        if (localContext != null)
            return localContext.getPackageName();
        return "";
    }


    public static void closeSafely(Cursor paramCursor)
    {
        if (paramCursor != null);
        try
        {
            if (!paramCursor.isClosed())
                paramCursor.close();
            return;
        }
        catch (Throwable localThrowable)
        {
            localThrowable.printStackTrace();
        }
    }

    public static boolean isActivityFinishing(Activity paramActivity)
    {
        return (paramActivity != null) && (paramActivity.isFinishing());
    }

    public static boolean isMainThread()
    {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static String urlEncode(String paramString)
    {
        String localObject = paramString;
        if (!TextUtils.isEmpty((CharSequence)localObject));
        try
        {
            String str = URLEncoder.encode(paramString, "UTF-8");
            localObject = str;
            return localObject;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
            localUnsupportedEncodingException.printStackTrace();
        }
        return localObject;
    }
}

package com.example.super_yu.myexample.json;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.MainActivity;
import com.example.super_yu.myexample.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Json2Activity extends AppCompatActivity {

    private static final String TAG = "whsgzcy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        Button sysnBtn = (Button) findViewById(R.id.sysn);
        sysnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 读取json文件
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String jsonString = getJson(Json2Activity.this, "mapData.txt");
                        Log.d(TAG, "Json2Activitym jsonString = " + jsonString);
                        // json解析
                        Json2MapResult json2MapResult = new Json2MapResult();
                        Gson gson = new Gson();
                        json2MapResult = gson.fromJson(jsonString, Json2MapResult.class);
                        // 设置Json2Result
                        Json2ShowResult json2ShowResult = new Json2ShowResult();

//                        json2Resul

                    }
                });
            }
        });


    }


    /**
     * 从asset路径下读取对应文件转String输出
     *
     * @param mContext
     * @return
     */
    public static String getJson(Context mContext, String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(
                    am.open(fileName));
            BufferedReader br = new BufferedReader(inputStreamReader);
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        } finally {
            if (inputStreamReader != null) try {
                inputStreamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }
}

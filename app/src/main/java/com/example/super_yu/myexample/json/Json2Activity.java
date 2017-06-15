package com.example.super_yu.myexample.json;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
                        MapResult json2MapResult = new MapResult();
                        Gson gson = new Gson();
                        json2MapResult = gson.fromJson(jsonString, MapResult.class);

                        // 获取站点数据 List
                        List<String> mWayPointsList = new ArrayList<String>();
                        for(int i = 0; i < json2MapResult.getWaypoints().size(); i++){
                            mWayPointsList.add(json2MapResult.getWaypoints().get(i).getName());
                        }

                        // map_6_A_601,map_6_B_602,map_6_A_603,map_6_B_604,map_6_A_605,map_6_C_606
                        Json2ShowResult json2ShowResult = new Json2ShowResult();
//                        List<Json2ShowResult.MapInfoBean> mapInfoBeenList = new ArrayList<Json2ShowResult.MapInfoBean>();
                        Json2ShowResult.MapInfoBean mapInfo = new Json2ShowResult.MapInfoBean();

                        String point = mWayPointsList.get(0).toString();
                        int floor = StringHelper.str2Floor(point);
                        // 默认只有固定一层楼
                        mapInfo.setFloor(floor);

                        Json2ShowResult.MapInfoBean.RoomBean roomBean = new Json2ShowResult.MapInfoBean.RoomBean();

                        List<Json2ShowResult.MapInfoBean.RoomBean.ABean> mABeanList = new ArrayList<Json2ShowResult.MapInfoBean.RoomBean.ABean>();
                        List<Json2ShowResult.MapInfoBean.RoomBean.BBean> mBBeanList = new ArrayList<Json2ShowResult.MapInfoBean.RoomBean.BBean>();
                        List<Json2ShowResult.MapInfoBean.RoomBean.CBean> mCBeanList = new ArrayList<Json2ShowResult.MapInfoBean.RoomBean.CBean>();

                        for(int i = 0; i < mWayPointsList.size(); i++) {
                            // 分类 calssify
                            String classify = StringHelper.str2Classify(mWayPointsList.get(i));
                            // "A"
                            if(classify.equals("A")){
                                Json2ShowResult.MapInfoBean.RoomBean.ABean A = new Json2ShowResult.MapInfoBean.RoomBean.ABean();
                                String map_name = mWayPointsList.get(i).toString();
                                String room = StringHelper.str2Room(map_name);
                                A.setMap_name(map_name);
                                A.setRoom_bumber(room);
                                mABeanList.add(A);
                            }
                            // "B"
                            if(classify.equals("B")){
                                Json2ShowResult.MapInfoBean.RoomBean.BBean B = new Json2ShowResult.MapInfoBean.RoomBean.BBean();
                                String map_name = mWayPointsList.get(i).toString();
                                String room = StringHelper.str2Room(map_name);
                                B.setMap_name(map_name);
                                B.setRoom_bumber(room);
                                mBBeanList.add(B);
                            }
                            // "C"
                            if(classify.equals("C")){
                                Json2ShowResult.MapInfoBean.RoomBean.CBean C = new Json2ShowResult.MapInfoBean.RoomBean.CBean();
                                String map_name = mWayPointsList.get(i).toString();
                                String room = StringHelper.str2Room(map_name);
                                C.setMap_name(map_name);
                                C.setRoom_bumber(room);
                                mCBeanList.add(C);
                            }
                            // "D"
                            if(classify.equals("D")){

                            }
                        }

                        roomBean.setA(mABeanList);
                        roomBean.setB(mBBeanList);
                        roomBean.setC(mCBeanList);

                        mapInfo.setRoom(roomBean);

                        json2ShowResult.setMapInfo(mapInfo);

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

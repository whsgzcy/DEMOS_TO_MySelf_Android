package com.example.super_yu.myexample.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.super_yu.myexample.R;

import java.util.List;

public class Base2Activity extends AppCompatActivity {

    private static final String TAG = "Base2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base2);

        final TextView mTextView1 = (TextView)findViewById(R.id.textview1);
        final TextView mTextView2 = (TextView)findViewById(R.id.textview2);

        // 获取基站信息
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                // 返回值MCC + MNC
                String operator = mTelephonyManager.getNetworkOperator();
                int mcc = Integer.parseInt(operator.substring(0, 3));
                int mnc = Integer.parseInt(operator.substring(3));

                // 中国移动和中国联通获取LAC、CID的方式
                GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
                int lac = location.getLac();
                int cellId = location.getCid();

                Log.i(TAG, " MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId);

                mTextView1.setText(" MCC = " + mcc + "\t MNC = " + mnc + "\t LAC = " + lac + "\t CID = " + cellId);

                // 中国电信获取LAC、CID的方式
                /*CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                lac = location1.getNetworkId();
                cellId = location1.getBaseStationId();
                cellId /= 16;*/
                // 获取邻区基站信息
                List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
                StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
                for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
                    sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
                    sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
                    sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
                }
                Log.i(TAG, " 获取邻区基站信息:" + sb.toString());
                mTextView2.setText(" 获取邻区基站信息:" + sb.toString());
            }
        });
    }

}

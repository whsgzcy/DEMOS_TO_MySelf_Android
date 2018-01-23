package com.example.super_yu.myexample.product;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.super_yu.myexample.R;

import java.util.ArrayList;
import java.util.List;

public class Data2Activity extends AppCompatActivity {

    /********client*********/
    private RadioButton mA;
    private RadioButton mB;
    private RadioButton mC;

    private RadioButton mE;
    private RadioButton mF;

    private TextView mNavStatus;

    private TextView mSendBtn;

    String[] status = new String[3];

    List<String> mStatusList = new ArrayList<String>();

    /********Server*********/
    TextView mNavStatusDesc;
    TextView mNavStatusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data2);

        mA = (RadioButton) findViewById(R.id.A);
        mB = (RadioButton) findViewById(R.id.B);
        mC = (RadioButton) findViewById(R.id.C);

        status[0] = "001";

        mA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    status[1] = "A";
                    mNavStatus.setText(status[0] + " " + status[1] + " " + status[2]);
                }
            }
        });

        mB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    status[1] = "B";
                    mNavStatus.setText(status[0] + " " + status[1] + " " + status[2]);
                }
            }
        });

        mC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    status[1] = "C";
                    mNavStatus.setText(status[0] + " " + status[1] + " " + status[2]);
                }
            }
        });

        mE = (RadioButton) findViewById(R.id.E);
        mF = (RadioButton) findViewById(R.id.F);

        mE.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    status[2] = "1";
                    mNavStatus.setText(status[0] + " " + status[1] + " " + status[2]);
                }
            }
        });

        mF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    status[2] = "3";
                    mNavStatus.setText(status[0] + " " + status[1] + " " + status[2]);
                }
            }
        });

        mNavStatus = (TextView) findViewById(R.id.nav_state_);

        mSendBtn = (Button) findViewById(R.id.nav_state_send);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status[1] == null) return;
                if(status[2] == null) return;
                mNavStatus.setText("");
                mStatusList.add(status[0] + "  " + status[1] + "  " + status[2]);
                status[1] = null;
                status[2] = null;
                String mStatus = "";
                for(int i = 0; i < mStatusList.size(); i++){
                    mStatus = mStatus + mStatusList.get(i) + "\n";
                }
                mNavStatusList.setText(mStatus);

                // 开始分析


            }
        });

        mNavStatusDesc = (TextView)findViewById(R.id.server_state_desc);
        mNavStatusList = (TextView)findViewById(R.id.server_state_list);
    }
}
package com.example.super_yu.myexample.shareprefen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.super_prefser_library.Prefser;
import com.example.super_yu.myexample.R;

/**
 * 参考https://github.com/pwittchen/prefser
 */
public class SharePrefence2Activity extends AppCompatActivity {

    private Prefser mPrefser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_prefence2);

        mPrefser = new Prefser(this);

        // 存入custom对象
        Button saveBtn = (Button) findViewById(R.id.save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomClass c = new CustomClass();
                c.setErrCode(1);
                c.setPointCode("11");
                c.setPointName("111");

                mPrefser.put("iwant", c);

                Toast.makeText(SharePrefence2Activity.this, "success", Toast.LENGTH_SHORT).show();
            }
        });

        // 读取custom对象
        Button readBtn = (Button) findViewById(R.id.read);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomClass c = mPrefser.get("iwant", CustomClass.class, null);
                if (c != null) {
                    String str = c.getErrCode() + c.getPointCode() + c.getPointName();
                    Toast.makeText(SharePrefence2Activity.this, str, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

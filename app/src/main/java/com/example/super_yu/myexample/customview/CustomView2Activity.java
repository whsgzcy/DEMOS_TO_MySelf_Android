package com.example.super_yu.myexample.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.super_yu.myexample.R;

public class CustomView2Activity extends AppCompatActivity {

    private PopHelper popHelper;//pop

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view2);

        final IdentifyingCodeView word = (IdentifyingCodeView) findViewById(R.id.wrod);
        word.setInputCompleteListener(new IdentifyingCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                Log.d("whsgzcy", word.getTextContent());
            }

            @Override
            public void deleteContent() {
                Log.d("whsgzcy", word.getTextContent());
            }
        });

        Button clearBtn = (Button) findViewById(R.id.clear);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.clearAllText();
            }
        });

        Button popBtn = (Button) findViewById(R.id.pop);
        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popHelper = new PopHelper(getApplicationContext());
                popHelper.showAddressPop(v, CustomView2Activity.this, onClickListener);
            }
        });
    }

    /**
     * 实现popHelper中的item接口
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_pop_address_cencel:
                    popHelper.colsePopupwindow();
                    break;
                case R.id.btn_pop_address_enter:
                    String addressData = popHelper.getAddressData();
                    Toast.makeText(CustomView2Activity.this, addressData, Toast.LENGTH_LONG).show();
                    popHelper.colsePopupwindow();
                    break;
            }
        }
    };
}

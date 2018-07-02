package com.example.super_yu.myexample.device;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.common.ToastHelper;

public class BarCode2Activity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code2);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mEditText = (EditText) findViewById(R.id.container);

        mEditText.setInputType(InputType.TYPE_NULL);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                ToastHelper.setToatBytTime(BarCode2Activity.this, "beforeTextChanged --> s = " + s, 1000);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                ToastHelper.setToatBytTime(BarCode2Activity.this, "onTextChanged --> s = " + s, 1000);
                ToastHelper.setToatBytTime(BarCode2Activity.this, "onTextChanged --> count = " + count, 1000);
                if (count >= 12) {
                    ToastHelper.setToatBytTime(BarCode2Activity.this, "onTextChanged --> s = " + s, 1000);
                    mEditText.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                ToastHelper.setToatBytTime(BarCode2Activity.this, "afterTextChanged --> s = " + s, 1000);
            }
        });
    }
}

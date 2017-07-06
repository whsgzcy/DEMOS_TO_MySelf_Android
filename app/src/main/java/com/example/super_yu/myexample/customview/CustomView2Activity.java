package com.example.super_yu.myexample.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.R;

public class CustomView2Activity extends AppCompatActivity {

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

    }
}

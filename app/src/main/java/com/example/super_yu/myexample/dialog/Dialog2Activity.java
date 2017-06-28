package com.example.super_yu.myexample.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.super_yu.myexample.R;
import com.example.superalertdialoglibrary.SweetAlertDialog;


public class Dialog2Activity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog2);

        Button successBtn = (Button)findViewById(R.id.success);
        successBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(Dialog2Activity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good job!")
                        .setContentText("You clicked the button!")
                        .show();
            }
        });
    }
}

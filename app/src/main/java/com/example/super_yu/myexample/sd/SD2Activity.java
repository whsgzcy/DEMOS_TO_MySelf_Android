package com.example.super_yu.myexample.sd;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.super_yu.myexample.R;
import com.example.super_yu.myexample.common.FileUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SD2Activity extends Activity {

//    @BindView(R.id.sd_read_buttton)
//    Button sdReadButtton;
//    @BindView(R.id.sd_show_text)
//    TextView sdShowText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sd2);

        final TextView contentText = (TextView)findViewById(R.id.sd_show_text);

        Button read = (Button)findViewById(R.id.sd_read_buttton);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = FileUtils.getSDPath() + File.separator + "fileUtilDir" + File.separator + "file.txt";
                String content = FileUtils.readFile(filePath);
                contentText.setText(content + "");
            }
        });

//        ButterKnife.bind(this);
    }

//    @OnClick(R.id.sd_read_buttton)
//    void read() {
//
////        String filePath = FileUtils.getSDPath() + File.separator + "loongman" + File.separator + "config" + File.separator + "atr_transport_automated_robot_config.txt";
//        String filePath = FileUtils.getSDPath() + File.separator + "fileUtilDir" + File.separator + "file.txt";
//
//
//        String content = FileUtils.readFile(filePath);
//
//        sdShowText.setText(content + "");
//    }
}
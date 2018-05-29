package com.example.super_yu.myexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.super_yu.myexample.aidl.client.BindingActivity;
import com.example.super_yu.myexample.anim.Anim2Activity;
import com.example.super_yu.myexample.baidu.SpeechBActivity;
import com.example.super_yu.myexample.base.Base2Activity;
import com.example.super_yu.myexample.common.FileUtils;
import com.example.super_yu.myexample.customview.CustomView2Activity;
import com.example.super_yu.myexample.daemon.DefaultExceptionHandler;
import com.example.super_yu.myexample.daemon.OnePxMain2Activity;
import com.example.super_yu.myexample.device.BarCode2Activity;
import com.example.super_yu.myexample.dialog.Dialog2Activity;
import com.example.super_yu.myexample.eventbus.EventBus2Activity;
import com.example.super_yu.myexample.files.SFiles2Activity;
import com.example.super_yu.myexample.gsonorder.GsonOrder2Activity;
import com.example.super_yu.myexample.iflytek.Speech2Activity;
import com.example.super_yu.myexample.instruction.Instrument2Activity;
import com.example.super_yu.myexample.json.Json2Activity;
import com.example.super_yu.myexample.lock.Lock2Activity;
import com.example.super_yu.myexample.notification.Notification2Activity;
import com.example.super_yu.myexample.openanim.OpenAnim2Activity;
import com.example.super_yu.myexample.product.Data2Activity;
import com.example.super_yu.myexample.recylerview.RefreshRecyclerview2Activity;
import com.example.super_yu.myexample.remedia.Remarker2Activity;
import com.example.super_yu.myexample.sd.SD2Activity;
import com.example.super_yu.myexample.serialp.SerialPort2Activity;
import com.example.super_yu.myexample.shareprefen.SharePrefence2Activity;
import com.example.super_yu.myexample.shijun.ofo.Indicator.IndicatorActivity;
import com.example.super_yu.myexample.shijun.ofo.activity.OfoMainActivity;
import com.example.super_yu.myexample.shijun.ofo.camera.CameraActivity;
import com.example.super_yu.myexample.shijun.ofo.count.CountActivity;
import com.example.super_yu.myexample.shijun.ofo.password.PwdActivity;
import com.example.super_yu.myexample.shijun.ofo.star.activity.StarActivity;
import com.example.super_yu.myexample.socket.Socket2Activity;
import com.example.super_yu.myexample.test.Test2Activity;
import com.example.super_yu.myexample.textanim.TextViewScaleAnim2Activity;
import com.example.super_yu.myexample.usb2serialp.UsbSerialp2Activity;
import com.example.super_yu.myexample.usb2serialtry.DeviceListActivity;
import com.example.super_yu.myexample.websocket.WebSocketActivity;
import com.example.super_yu.myexample.websocket.okhttp.OkHttpWebSocketActivity;
import com.example.super_yu.myexample.webview.WebView2Activity;
import com.example.super_yu.myexample.xy.XY2Activity;
import com.example.super_yu.myexample.yunzhisheng.YunZhiShengActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogRecorder logRecorder
                = new LogRecorder.Builder(this)
                .setLogFolderName("foldername")
                .setLogFolderPath("/sdcard/foldername")
                .setLogFileNameSuffix("super")
                .setLogFileSizeLimitation(256)
                .setLogLevel(2)
                .addLogFilterTag("ActivityManager")
                .setPID(android.os.Process.myPid())
                .build();
        logRecorder.start();

        // 监测应用崩溃重启
        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this));

        Button jsonBtn = (Button) findViewById(R.id.json);
        jsonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Json2Activity.class);
                startActivity(intent);
            }
        });

        Button notificationBtn = (Button) findViewById(R.id.notification);
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Notification2Activity.class);
                startActivity(intent);
            }
        });

        Button socketBtn = (Button) findViewById(R.id.socket);
        socketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Socket2Activity.class);
                startActivity(intent);
            }
        });

        Button filesBtn = (Button) findViewById(R.id.files);
        filesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SFiles2Activity.class);
                startActivity(intent);
            }
        });

        Button animBtn = (Button) findViewById(R.id.anim);
        animBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Anim2Activity.class);
                startActivity(intent);
            }
        });

        Button dialogBtn = (Button) findViewById(R.id.dialog);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dialog2Activity.class);
                startActivity(intent);
            }
        });

        Button remarkerBtn = (Button) findViewById(R.id.remarker);
        remarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Remarker2Activity.class);
                startActivity(intent);
            }
        });

        Button seralPortBtn = (Button) findViewById(R.id.serial);
        seralPortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SerialPort2Activity.class);
                startActivity(intent);
            }
        });

        Button refreshBtn = (Button) findViewById(R.id.refresh);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RefreshRecyclerview2Activity.class);
                startActivity(intent);
            }
        });

        Button eventsBtn = (Button) findViewById(R.id.events);
        eventsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventBus2Activity.class);
                startActivity(intent);
            }
        });

        Button customViewBtn = (Button) findViewById(R.id.customview);
        customViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomView2Activity.class);
                startActivity(intent);
            }
        });

        Button webSockeyBtn = (Button) findViewById(R.id.websocket);
        webSockeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebSocketActivity.class);
                startActivity(intent);
            }
        });

        Button okHttp2WebSockeyBtn = (Button) findViewById(R.id.okhttp2websocket);
        okHttp2WebSockeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OkHttpWebSocketActivity.class);
                startActivity(intent);
            }
        });

        Button daemonBtn = (Button) findViewById(R.id.daemon);
        daemonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OnePxMain2Activity.class);
                startActivity(intent);
                // 结束栈
                MainActivity.this.finish();
            }
        });

        Button xyBtn = (Button) findViewById(R.id.xy);
        xyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, XY2Activity.class);
                startActivity(intent);
            }
        });

        Button shareBtn = (Button) findViewById(R.id.customShare);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SharePrefence2Activity.class);
                startActivity(intent);
            }
        });

        Button lockBtn = (Button) findViewById(R.id.lock);
        lockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lock2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button baseBtn = (Button) findViewById(R.id.base);
        baseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Base2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button textBtn = (Button) findViewById(R.id.textAnim);
        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, TextViewAnim2Activity.class);
                Intent intent = new Intent(MainActivity.this, TextViewScaleAnim2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        Button webViewBtn = (Button) findViewById(R.id.webview);
        webViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebView2Activity.class);
                startActivity(intent);
            }
        });

        Button btn_startofo = (Button) findViewById(R.id.start_ofo);
        btn_startofo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OfoMainActivity.class);
                startActivity(intent);
            }
        });
        Button btn_star = (Button) findViewById(R.id.btn_comment);
        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StarActivity.class);
                startActivity(intent);
            }
        });


        Button iflytekBtn = (Button) findViewById(R.id.speech);
        iflytekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Speech2Activity.class);
                startActivity(intent);
            }
        });

        Button baidukBtn = (Button) findViewById(R.id.baidu);
        baidukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpeechBActivity.class);
                startActivity(intent);
            }
        });
        Button btn_count = (Button) findViewById(R.id.count);
        btn_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountActivity.class);
                startActivity(intent);
            }
        });
        Button btn_pwd = (Button) findViewById(R.id.btn_pwd);
        btn_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PwdActivity.class);
                startActivity(intent);
            }
        });
        Button btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
        Button btn_indiactor = (Button) findViewById(R.id.btn_indiactor);
        btn_indiactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IndicatorActivity.class);
                startActivity(intent);
            }
        });

        Button yunzhishengBtn = (Button) findViewById(R.id.yunzhisheng);
        yunzhishengBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, YunZhiShengActivity.class);
                startActivity(intent);
            }
        });

        Button pBtn = (Button) findViewById(R.id.data2server);
        pBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Data2Activity.class);
                startActivity(intent);
            }
        });

        Button open_anim = (Button) findViewById(R.id.open_anim);
        open_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OpenAnim2Activity.class);
                startActivity(intent);
            }
        });

        Button test = (Button) findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Test2Activity.class);
                startActivity(intent);
            }
        });

        Button aidl = (Button) findViewById(R.id.aidl);
        aidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BindingActivity.class);
                startActivity(intent);
            }
        });

        Button usb_seriapl = (Button) findViewById(R.id.usb_seriapl);
        usb_seriapl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UsbSerialp2Activity.class);
                startActivity(intent);
            }
        });

        Button usb_seriapl2 = (Button) findViewById(R.id.usb_seriapl2);
        usb_seriapl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(intent);
            }
        });

        Button gson_order = (Button) findViewById(R.id.gson_order);
        gson_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GsonOrder2Activity.class);
                startActivity(intent);
            }
        });

        Button instrument_click = (Button) findViewById(R.id.instrument_click);
        instrument_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Instrument2Activity.class);
                startActivity(intent);
            }
        });

        Button sd_click = (Button) findViewById(R.id.sd);
        sd_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filePath = FileUtils.getSDPath() + File.separator + "fileUtilDir" + File.separator + "file.txt";
                String content = FileUtils.readFile(filePath);
                Toast.makeText(MainActivity.this, content + "", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, SD2Activity.class);
//                startActivity(intent);
            }
        });

        Button code_click = (Button) findViewById(R.id.device);
        code_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BarCode2Activity.class);
                startActivity(intent);
            }
        });
    }
}
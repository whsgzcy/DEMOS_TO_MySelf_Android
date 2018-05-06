package com.example.super_yu.myexample.serialp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.super_yu.myexample.LogRecorder;
import com.example.super_yu.myexample.R;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import weiqian.hardware.SerialPort;

public class SerialPort2Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "whsgzcy";

    private TextView mSerial;
    private TextView mBaud;
    private TextView mData;
    private TextView mCompile;
    private TextView mStop;
    private TextView mState;
    private Button mSend;
    private Button mOpen;
    private Button mClose;

    private SerialPort mSerialPort;

    private boolean mIsStop = false;

    private Button red;
    private Button yellow;
    private Button green;
    private Button light;

    private boolean mIsLight = false;

    // 超声波和锁
    private TextView mSenorText;
    private TextView mDoorText;
    //https://pan.baidu.com/s/1nvDWnix
    //https://pan.baidu.com/s/1c33uUi

    private TextView tn;
    private TextView tnn;
    private TextView tnnn;

    private TextView ttime;

    private Button mF05;
    private Button mF5;
    private Button mF06;
    private Button mF6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port2);

        LogRecorder logRecorder
                = new LogRecorder.Builder(this)
                .setLogFolderName("foldername")
                .setLogFolderPath("/sdcard/foldername")
                .setLogFileNameSuffix("spuer")
                .setLogFileSizeLimitation(102400)
                .setLogLevel(2)
                .addLogFilterTag(TAG)
                .setPID(android.os.Process.myPid())
                .build();
        logRecorder.start();

        mSerial = (TextView) findViewById(R.id.serial);
        mBaud = (TextView) findViewById(R.id.baud);
        mData = (TextView) findViewById(R.id.data);
        mCompile = (TextView) findViewById(R.id.compile);
        mStop = (TextView) findViewById(R.id.stop);
        mState = (TextView) findViewById(R.id.state);

        ttime = (TextView) findViewById(R.id.time);

        mSerial.setText("com2");
        mBaud.setText("9600");
        mData.setText("8");
        mCompile.setText("none");
        mStop.setText("1");

        mSend = (Button) findViewById(R.id.send);

        mOpen = (Button) findViewById(R.id.open);
        mClose = (Button) findViewById(R.id.close);
        light = (Button) findViewById(R.id.light);

        mSend.setOnClickListener(this);
        mOpen.setOnClickListener(this);
        mClose.setOnClickListener(this);
        light.setOnClickListener(this);

        mSend.setEnabled(false);
        mClose.setEnabled(false);

        // 超声波和锁
        mSenorText = (TextView) findViewById(R.id.senor);
        mDoorText = (TextView) findViewById(R.id.door);

        tn = (TextView) findViewById(R.id.n);
        tnn = (TextView) findViewById(R.id.nn);
        tnnn = (TextView) findViewById(R.id.nnn);

        mSerialPort = new SerialPort();

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        Runnable command = new Runnable() {
            @Override
            public void run() {
                if (mIsStop) {

                    long current = System.currentTimeMillis();

                    byte[] d = HexToByte("aa");
                    int m = d.length;
                    int f = mSerialPort.write(d, m);

                    long mCurrent = System.currentTimeMillis();

                    Log.d("Serial", (mCurrent - current) + "ms");

                    String msg = mSerialPort.read();

                    Message amessage = new Message();
                    amessage.what = 1;
                    amessage.obj = msg;
                    mHandler.sendMessage(amessage);

                    Message bmessage = new Message();
                    bmessage.what = 2;
                    bmessage.obj = msg;
                    mHandler.sendMessage(bmessage);

                    Message cmessage = new Message();
                    cmessage.what = 3;
                    cmessage.obj = msg;
                    mHandler.sendMessage(cmessage);
                }
            }
        };
//        scheduledThreadPoolExecutor.scheduleAtFixedRate(command, 1, 1, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(command, 1, 87, TimeUnit.MILLISECONDS);

        ScheduledThreadPoolExecutor lightScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        Runnable lightCommand = new Runnable() {
            @Override
            public void run() {
                if (mIsLight) {

                    byte[] rry = HexToByte("f6");
                    int ry = rry.length;
                    int y = mSerialPort.write(rry, ry);

                    byte[] gy = HexToByte("05");
                    int ly = gy.length;
                    int by = mSerialPort.write(gy, ly);

                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            byte[] ggy = HexToByte("f5");
                            int lly = ggy.length;
                            int bby = mSerialPort.write(ggy, lly);

                            byte[] gy = HexToByte("06");
                            int ly = gy.length;
                            int by = mSerialPort.write(gy, ly);
                        }
                    }, 500);

                }
            }
        };
        lightScheduledThreadPoolExecutor.scheduleAtFixedRate(lightCommand, 1, 1,
                TimeUnit.SECONDS);


        red = (Button) findViewById(R.id.red);
        yellow = (Button) findViewById(R.id.yellow);
        green = (Button) findViewById(R.id.green);

        red.setOnClickListener(this);
        yellow.setOnClickListener(this);
        green.setOnClickListener(this);

        mF05 = (Button) findViewById(R.id.f05);
        mF5 = (Button) findViewById(R.id.f5);
        mF06 = (Button) findViewById(R.id.f06);
        mF6 = (Button) findViewById(R.id.f6);

        mF05.setOnClickListener(this);
        mF5.setOnClickListener(this);
        mF06.setOnClickListener(this);
        mF6.setOnClickListener(this);
    }

    long aa_time = 0;
    int n = 0;
    int nn = 0;
    int nnn = 0;
    int flag = 0;

    long b_time = 0;
    int bn = 0;
    int bnn = 0;
    int bnnn = 0;
    int bflag = 0;

    long c_time = 0;
    int cn = 0;
    int cnn = 0;
    int cnnn = 0;
    int cflag = 0;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    long current = System.currentTimeMillis();
                    String str1 = msg.obj.toString().replaceAll("0x", "");
                    String str2 = str1.toString().replaceAll("00", "");
                    mState.setText(str2);
                    Log.d("ww", msg.obj.toString());

                    if (!str2.contains("B1")) {
                        flag = 0;
                    }

                    if (flag == 1) return;

                    if (str2.contains("B1")) {
                        aa_time = System.currentTimeMillis();
                        mSenorText.setText("NO！！！");
                        flag = 1;
                    } else {
                        flag = 0;
                        mSenorText.setText("YES！！！");
                        long a_time = System.currentTimeMillis();
                        if (aa_time == 0) return;
                        long time = a_time - aa_time;
                        if (time < 50) {
                            n++;
                            tn.setText("<50ms " + n);
                        } else if (time >= 50 && time < 100) {
                            nn++;
                            tnn.setText("<100ms " + nn);
                        } else if (time >= 100) {
                            nnn++;
                            tnnn.setText(">100ms " + nnn);
                        }
                        Log.d(TAG, "A time = " + time);
                        aa_time = 0;
                    }

                    long mCurrent = System.currentTimeMillis();

                    Log.d("Serial_P", (mCurrent - current) + "ms");

                    break;

                case 2:
                    long bcurrent = System.currentTimeMillis();
                    String bstr1 = msg.obj.toString().replaceAll("0x", "");
                    String bstr2 = bstr1.toString().replaceAll("00", "");
                    mState.setText(bstr2);
                    Log.d("ww", msg.obj.toString());

                    if (!bstr2.contains("B2")) {
                        bflag = 0;
                    }
                    if (bflag == 1) return;

                    if (bstr2.contains("B2")) {
                        b_time = System.currentTimeMillis();
                        mSenorText.setText("NO！！！");
                        bflag = 1;
                    } else {
                        bflag = 0;
                        mSenorText.setText("YES！！！");
                        long a_time = System.currentTimeMillis();
                        if (b_time == 0) return;
                        long time = a_time - b_time;
                        if (time < 50) {
                            bn++;
                            tn.setText("<50ms " + bn);
                        } else if (time >= 50 && time < 100) {
                            bnn++;
                            tnn.setText("<100ms " + bnn);
                        } else if (time >= 100) {
                            bnnn++;
                            tnnn.setText(">100ms " + bnnn);
                        }
                        Log.d(TAG, "B time = " + time);
                        b_time = 0;
                    }
                    long mbCurrent = System.currentTimeMillis();
                    Log.d("Serial_P", (mbCurrent - bcurrent) + "ms");
                    break;

                case 3:
                    long ccurrent = System.currentTimeMillis();
                    String cstr1 = msg.obj.toString().replaceAll("0x", "");
                    String cstr2 = cstr1.toString().replaceAll("00", "");
                    mState.setText(cstr2);
                    Log.d("ww", msg.obj.toString());

                    if (!cstr2.contains("B2") && !cstr2.contains("B1")) {
                        cflag = 0;
                    }
                    if (cflag == 1) return;

                    if (cstr2.contains("B2") && cstr2.contains("B1")) {
                        c_time = System.currentTimeMillis();
                        mSenorText.setText("NO！！！");
                        cflag = 1;
                    } else {
                        cflag = 0;
                        mSenorText.setText("YES！！！");
                        long a_time = System.currentTimeMillis();
                        if (c_time == 0) return;
                        long time = a_time - c_time;
                        if (time < 50) {
                            cn++;
                            tn.setText("<50ms " + cn);
                        } else if (time >= 50 && time < 100) {
                            cnn++;
                            tnn.setText("<100ms " + cnn);
                        } else if (time >= 100) {
                            cnnn++;
                            tnnn.setText(">100ms " + cnnn);
                        }
                        Log.d(TAG, "AB time = " + time);
                        c_time = 0;
                    }
                    long mcCurrent = System.currentTimeMillis();
                    Log.d("Serial_P", (mcCurrent - ccurrent) + "ms");
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send:
                byte[] c = HexToByte("01");
                int l = c.length;
                int b = mSerialPort.write(c, l);
                Log.d(TAG, "SerialPort2Activity send = " + b);

                mSend.setClickable(false);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        byte[] cc = HexToByte("f1");
                        int ll = cc.length;
                        int bb = mSerialPort.write(cc, ll);
                        mSend.setClickable(true);
                    }
                }, 1000);

                break;
            case R.id.open:
                mSend.setEnabled(true);
                mClose.setEnabled(true);
                mOpen.setEnabled(false);
                mSerialPort.open("COM2", 9600, 8, "None", 1);
                mIsStop = true;
                break;
            case R.id.close:
                mOpen.setEnabled(true);
                mSend.setEnabled(false);
                mClose.setEnabled(false);
                mSerialPort.close();
                mIsStop = false;
                break;
            case R.id.red:
                if (r == 0) {
                    r = 1;
                    byte[] cred = HexToByte("04");
                    int lred = cred.length;
                    int bred = mSerialPort.write(cred, lred);
                    Log.d(TAG, "SerialPort2Activity send = " + bred);
                    return;
                }
                r = 0;
                byte[] cred = HexToByte("f4");
                int lred = cred.length;
                int bred = mSerialPort.write(cred, lred);
                Log.d(TAG, "SerialPort2Activity send = " + bred);
                break;
            case R.id.yellow:
                if (y == 0) {
                    y = 1;
                    byte[] cy = HexToByte("05");
                    int ly = cy.length;
                    int by = mSerialPort.write(cy, ly);
                    Log.d(TAG, "SerialPort2Activity send = " + by);
                    return;
                }
                y = 0;
                byte[] cy = HexToByte("f5");
                int ly = cy.length;
                int by = mSerialPort.write(cy, ly);
                Log.d(TAG, "SerialPort2Activity send = " + by);
                break;
            case R.id.green:
                if (g == 0) {
                    g = 1;
                    byte[] cg = HexToByte("06");
                    int lg = cg.length;
                    int bg = mSerialPort.write(cg, lg);
                    Log.d(TAG, "SerialPort2Activity send = " + bg);
                    return;
                }
                g = 0;
                byte[] cg = HexToByte("f6");
                int lg = cg.length;
                int bg = mSerialPort.write(cg, lg);
                Log.d(TAG, "SerialPort2Activity send = " + bg);
                break;

            case R.id.light:

                if (mIsLight) {

                    mIsLight = false;

                    byte[] rry = HexToByte("f6");
                    int ry = rry.length;
                    int y = mSerialPort.write(rry, ry);

                    byte[] gy = HexToByte("f5");
                    int my = gy.length;
                    mSerialPort.write(gy, my);

                } else {
                    mIsLight = true;
                }
                break;

            case R.id.f05:
                byte[] super05 = HexToByte("05");
                int lsuper05 = super05.length;
                int nsuper05 = mSerialPort.write(super05, lsuper05);
                break;
            case R.id.f5:
                byte[] superf5 = HexToByte("f5");
                int lsuperf5 = superf5.length;
                int nsuperf5 = mSerialPort.write(superf5, lsuperf5);
                break;
            case R.id.f06:
                byte[] super06 = HexToByte("06");
                int lsuper06 = super06.length;
                int nsuper06 = mSerialPort.write(super06, lsuper06);
                break;
            case R.id.f6:
                byte[] superf6 = HexToByte("f6");
                int lsuperf6 = superf6.length;
                int nsuperf6 = mSerialPort.write(superf6, lsuperf6);
                break;
        }
    }

    int r = 0;
    int y = 0;
    int g = 0;

    //16进制字符串转换为byte[]
    public byte[] HexToByte(String hexString) {
        int len = hexString.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return b;
    }

//    protected void onDataReceived(byte[] buffer, int size) {
//        final byte[] nbuffer = buffer.clone();
//        final int nsize = size;
//        String msg = bytesToHexString(nbuffer, nsize);
//        Message message = new Message();
//        message.obj = msg;
//        message.what = 1;
//        mHandler.sendMessage(message);
//    }
//
//    private String bytesToHexString(byte[] bArray, int size) {
//
//        return new String(bArray);

//        StringBuffer strBuf = new StringBuffer(bArray.length);
//        String strTemp;
//        for (int i = 0; i < size; i++) {
//            strTemp = Integer.toHexString(0xAA & bArray[i]);
//            strBuf.append("0x");
//            if (strTemp.length() < 2) {
//                strBuf.append(0);
//            }
//            strBuf.append(strTemp.toUpperCase());
//            strBuf.append(" ");
//        }
//        return strBuf.toString();
//    }
}

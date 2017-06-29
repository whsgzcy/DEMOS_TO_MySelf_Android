package com.example.super_yu.myexample.serialp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.super_yu.myexample.R;

public class SerialPort2Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView mSerial;
    private TextView mBaud;
    private TextView mData;
    private TextView mCompile;
    private TextView mStop;
    private TextView mState;
    private EditText mCmd;
    private Button mSend;
    private Button mOpen;
    private Button mClose;

    private SerialPort mSerialPort;

    private boolean mIsStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_port2);

        mSerial = (TextView) findViewById(R.id.serial);
        mBaud = (TextView) findViewById(R.id.baud);
        mData = (TextView) findViewById(R.id.data);
        mCompile = (TextView) findViewById(R.id.compile);
        mStop = (TextView) findViewById(R.id.stop);
        mState = (TextView) findViewById(R.id.state);

        mSerial.setText("com1");
        mBaud.setText("9600");
        mData.setText("8");
        mCompile.setText("none");
        mStop.setText("1");

        mCmd = (EditText) findViewById(R.id.cmd);
        mSend = (Button) findViewById(R.id.send);

        mOpen = (Button) findViewById(R.id.open);
        mClose = (Button) findViewById(R.id.close);

        mSend.setOnClickListener(this);
        mOpen.setOnClickListener(this);
        mClose.setOnClickListener(this);

        mSend.setEnabled(false);
        mClose.setEnabled(false);

        mSerialPort = new SerialPort();

//        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
//                1);
//        Runnable command = new Runnable() {
//            @Override
//            public void run() {
//                if (mIsStop) {
//                    byte[] buffer = new byte[64];
//                    int size = mSerialPort.read(buffer, buffer.length);
//
//
//                }
//            }
//        };
//        scheduledThreadPoolExecutor.scheduleAtFixedRate(command, 1, 2,
//                TimeUnit.SECONDS);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.send:
                String data = mCmd.getText().toString();
                byte[] c = HexToByte(data);
                int l = c.length;
                mSerialPort.write(c, l);
                break;
            case R.id.open:
                mSend.setEnabled(true);
                mClose.setEnabled(true);
                mOpen.setEnabled(false);
                mSerialPort.open("COM1", 9600, 8, "None", 1);
                mIsStop = true;
                break;
            case R.id.close:
                mOpen.setEnabled(true);
                mSend.setEnabled(false);
                mClose.setEnabled(false);
                mSerialPort.close();
                mIsStop = false;
                break;
        }
    }

    //16进制字符串转换为byte[]
    public byte[] HexToByte(String hexString){
        int len = hexString.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
                    .digit(hexString.charAt(i + 1), 16));
        }
        return b;
    }
}

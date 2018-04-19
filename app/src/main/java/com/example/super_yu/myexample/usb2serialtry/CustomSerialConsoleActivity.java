/* Copyright 2011-2013 Google Inc.
 * Copyright 2013 mike wakerly <opensource@hoho.com>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * Project home page: https://github.com/mik3y/usb-serial-for-android
 */

package com.example.super_yu.myexample.usb2serialtry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.super_yu.myexample.R;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.util.HexDump;

/**
 * Monitors a single {@link UsbSerialPort} instance, showing all data
 * received.
 *
 * @author mike wakerly (opensource@hoho.com)
 */
public class CustomSerialConsoleActivity extends Activity {

    /**
     * Driver instance, passed in statically via
     *
     * <p/>
     * This is a devious hack; it'd be cleaner to re-create the driver using
     * arguments passed in with the {@link #startActivity(Intent)} intent. We
     * can get away with it because both activities will run in the same
     * process, and this is a simple demo.
     */
    private TextView mDumpTextView;
    private ScrollView mScrollView;
    private TextView mTitleTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serial_console2);
        mDumpTextView = (TextView) findViewById(R.id.consoleText);
        mScrollView = (ScrollView) findViewById(R.id.demoScroller);
        mTitleTextView = (TextView) findViewById(R.id.demoTitle);
        UsbSerialPortTopIO.GetInstance().RegConnectListener(mConnectListener);
        UsbSerialPortTopIO.GetInstance().RegIOListener(topDataIOListener);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsbSerialPortTopIO.GetInstance().Connect();
            }
        });
    }

    //接收数据IO
    private TopDataIOListener topDataIOListener = new TopDataIOListener() {
        @Override
        public void OnIOCallBack(int nLength,final byte[] data) {
            CustomSerialConsoleActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CustomSerialConsoleActivity.this.updateReceivedData(data);
                }
            });
        }
    };

    private ConnectListener mConnectListener = new ConnectListener() {
        @Override
        public void OnConnectStatusCallBack(boolean bSucceed) {
            if (bSucceed){
                mTitleTextView.setText("succeed");
            }else {
                mTitleTextView.setText("faile");
            }
        }

        @Override
        public void OnDisConnectCallBack() {

        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        UsbSerialPortTopIO.GetInstance().DisConnect();
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void updateReceivedData(byte[] Data) {
        final String message = "Read " + Data.length + " bytes: \n"
                + HexDump.dumpHexString(Data) + "\n\n";
        mDumpTextView.append(message);
        mScrollView.smoothScrollTo(0, mDumpTextView.getBottom());
    }

}

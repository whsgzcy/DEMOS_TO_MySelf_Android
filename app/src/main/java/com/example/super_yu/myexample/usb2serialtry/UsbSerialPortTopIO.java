package com.example.super_yu.myexample.usb2serialtry;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.util.Log;

import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsbSerialPortTopIO {
    private volatile static UsbSerialPortTopIO usbSerialPortTopIO = null;
    private static UsbSerialPort sPort = null;
    private final String TAG = "Show";
    private static Context mContext = null;
    public static UsbSerialPortTopIO GetInstance() {
        if (usbSerialPortTopIO == null ) {
            synchronized (UsbSerialPortTopIO.class){
                if (usbSerialPortTopIO == null){
                    usbSerialPortTopIO = new UsbSerialPortTopIO();
                }
            }
        }
        return usbSerialPortTopIO;
    }


    ConnectListener mConnectListener = null;

    public void RegConnectListener(ConnectListener arg0) {
        mConnectListener = arg0;
    }

    TopDataIOListener mIOListener = null;

    public void RegIOListener(TopDataIOListener arg0) {
        mIOListener = arg0;
    }

    public void UnRegIOListener() {
        mIOListener = null;
    }

    public boolean SendData(int nLength, byte[] data) {
        if (mSerialIoManager != null) {
            mSerialIoManager.writeAsync(data);
        }
        return false;
    }

    public void Connect() {
        onDeviceStateChange();

        Log.d(TAG, "Resumed, port=" + sPort);
        if (sPort == null) {
            if (null != mConnectListener)
                mConnectListener.OnConnectStatusCallBack(false);
        } else {
            final UsbManager usbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);

            UsbDeviceConnection connection = usbManager.openDevice(sPort.getDriver().getDevice());
            if (connection == null) {
                if (null != mConnectListener)
                    mConnectListener.OnConnectStatusCallBack(false);
                return;
            }

            try {
                sPort.open(connection);
                sPort.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

            } catch (IOException e) {
                if (null != mConnectListener)
                    mConnectListener.OnConnectStatusCallBack(false);
                try {
                    sPort.close();
                } catch (IOException e2) {
                    if (null != mConnectListener)
                        mConnectListener.OnConnectStatusCallBack(false);
                }
                sPort = null;
                return;
            }
            if (null != mConnectListener)
                mConnectListener.OnConnectStatusCallBack(true);
        }
    }

    public void DisConnect() {
        stopIoManager();
        if (sPort != null) {
            try {
                sPort.close();
            } catch (IOException e) {
                // Ignore.
            }
            sPort = null;
        }
    }

    public boolean SetUsbSerialPort(Context context, UsbSerialPort port) {
        sPort = port;
        mContext = context.getApplicationContext();
        return true;
    }

    public UsbSerialPortTopIO() {
    }

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private SerialInputOutputManager mSerialIoManager;

    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.d("Show", "Runner stopped.");
                    if (mIOListener != null)
                        mIOListener.OnIOCallBack(e.toString().getBytes().length, e.toString().getBytes());
                    onDeviceStateChange();
                }

                @Override
                public void onNewData(final byte[] data) {
                    if (mIOListener != null)
                        mIOListener.OnIOCallBack(data.length, data);
                }

            };

    private void stopIoManager() {
        if (mSerialIoManager != null) {
            Log.i(TAG, "Stopping io manager ..");
            mSerialIoManager.stop();
            mSerialIoManager = null;
        }
    }

    private void startIoManager() {
        if (sPort != null) {
            Log.i(TAG, "Starting io manager ..");
            mSerialIoManager = new SerialInputOutputManager(sPort, mListener);
            mExecutor.submit(mSerialIoManager);
        }
    }

    private void onDeviceStateChange() {
        stopIoManager();
        startIoManager();
    }

}

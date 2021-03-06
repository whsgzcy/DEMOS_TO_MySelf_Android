package com.example.super_yu.myexample.usb2serialp;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import robot.loongman.iwant.com.super_usb_serialp_library.usbserial.CDCSerialDevice;
import robot.loongman.iwant.com.super_usb_serialp_library.usbserial.UsbSerialDevice;
import robot.loongman.iwant.com.super_usb_serialp_library.usbserial.UsbSerialInterface;

public class LoraMessageService extends Service {

    public static final String ACTION_USB_READY = "com.felhr.connectivityservices.USB_READY";
    public static final String ACTION_USB_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    public static final String ACTION_USB_NOT_SUPPORTED = "com.felhr.usbservice.USB_NOT_SUPPORTED";
    public static final String ACTION_NO_USB = "com.felhr.usbservice.NO_USB";
    public static final String ACTION_USB_PERMISSION_GRANTED = "com.felhr.usbservice.USB_PERMISSION_GRANTED";
    public static final String ACTION_USB_PERMISSION_NOT_GRANTED = "com.felhr.usbservice.USB_PERMISSION_NOT_GRANTED";
    public static final String ACTION_USB_DISCONNECTED = "com.felhr.usbservice.USB_DISCONNECTED";
    public static final String ACTION_CDC_DRIVER_NOT_WORKING = "com.felhr.connectivityservices.ACTION_CDC_DRIVER_NOT_WORKING";
    public static final String ACTION_USB_DEVICE_NOT_WORKING = "com.felhr.connectivityservices.ACTION_USB_DEVICE_NOT_WORKING";

    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private boolean serialPortConnected;
    public static boolean SERVICE_CONNECTED = false;
    // BaudRate. Change this value if you need
    private static final int BAUD_RATE = 9600;

    private UsbManager usbManager;
    private UsbDevice device;
    private UsbSerialDevice serialPort;
    private UsbDeviceConnection connection;

    private Context context;

    private LoraMessageCallBack mLoraMessageCallBack;

    public class MyBindler extends Binder {

        /**
         * 获取串口连接状态
         *
         * @return
         */
        public boolean getIsSerialPortConnected() {
            return serialPortConnected;
        }

        /**
         * 或者server运行状态
         *
         * @return
         */
        public boolean getServiceConnection() {
            return SERVICE_CONNECTED;
        }

        /**
         * 转换为16进制 写入
         *
         * @param content
         * @return
         */
        public int write(String content) {
            if (serialPort == null) return -1;
            // content 转换 为 16 进制
//            String miss = Hex2StrHelper.str2HexStr(content);
            serialPort.syncWrite(content.getBytes(), 0);
            return 1;
        }

        /**
         * 消息回调
         *
         * @param loraMessageCallBack
         */
        public void setLoraMessageCallBackListener(LoraMessageCallBack loraMessageCallBack) {
            mLoraMessageCallBack = loraMessageCallBack;
        }

        public void readBufferListener() {
//            mreadThread.start();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBindler();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 默认 串口没有连接
        serialPortConnected = false;
        // 当前Service 没有 dead
        SERVICE_CONNECTED = true;
        // 注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(ACTION_USB_DETACHED);
        filter.addAction(ACTION_USB_ATTACHED);
        registerReceiver(usbReceiver, filter);
        // 获取usb管理单例
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        // 查找串口设备
        findSerialPortDevice();
    }

    /**
     * 查找串口设备
     */
    private void findSerialPortDevice() {
        // This snippet will try to open the first encountered usb device connected, excluding usb root hubs
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                int devicePID = device.getProductId();
                if (deviceVID != 0x1d6b && (devicePID != 0x0001 && devicePID != 0x0002 && devicePID != 0x0003)) {
                    // There is a device connected to our Android device. Try to open it as a Serial Port.
                    requestUserPermission();
                    keep = false;
                } else {
                    connection = null;
                    device = null;
                }
                if (!keep) {
                    Toast.makeText(LoraMessageService.this, "33", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            Toast.makeText(LoraMessageService.this, "44", Toast.LENGTH_SHORT).show();
            if (!keep) {
                // There is no USB devices connected (but usb host were listed). Send an intent to MainActivity.
                Intent intent = new Intent(ACTION_NO_USB);
                sendBroadcast(intent);
            }
        } else {
            // There is no USB devices connected. Send an intent to MainActivity
            Intent intent = new Intent(ACTION_NO_USB);
            sendBroadcast(intent);
        }
    }

    /**
     * Request user permission. The response will be received in the BroadcastReceiver
     */
    private void requestUserPermission() {
        PendingIntent mPendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        usbManager.requestPermission(device, mPendingIntent);
    }

    /*
    *  Data received from serial port will be received here. Just populate onReceivedData with your code
    *  In this particular example. byte stream is converted to String and send to UI thread to
    *  be treated there.
    */
    private UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() {
        @Override
        public void onReceivedData(byte[] arg0) {
            try {
                String data = new String(arg0, "UTF-8");
                if (mLoraMessageCallBack != null) {
                    String content = Hex2StrHelper.hexStr2Str(data);
                    mLoraMessageCallBack.onLoraMessage_Serial_Port(content);
                }
                Log.d("t", "data1 " + data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };

    /*
     * State changes in the CTS line will be received here
     */
    private UsbSerialInterface.UsbCTSCallback ctsCallback = new UsbSerialInterface.UsbCTSCallback() {
        @Override
        public void onCTSChanged(boolean state) {
            if (mLoraMessageCallBack != null)
                mLoraMessageCallBack.onCts_Change(state);
            Log.d("t", "data2 " + state);
        }
    };

    /*
     * State changes in the DSR line will be received here
     */
    private UsbSerialInterface.UsbDSRCallback dsrCallback = new UsbSerialInterface.UsbDSRCallback() {
        @Override
        public void onDSRChanged(boolean state) {
            if (mLoraMessageCallBack != null)
                mLoraMessageCallBack.onDsr_Change(state);
            Log.d("t", "data3 " + state);
        }
    };

    /**
     * Different notifications from OS will be received here (USB attached, detached, permission responses...)
     * About BroadcastReceiver: http://developer.android.com/reference/android/content/BroadcastReceiver.html
     */
    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            if (arg1.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = arg1.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                // User accepted our USB connection. Try to open the device as a serial port
                if (granted) {
                    Intent intent = new Intent(ACTION_USB_PERMISSION_GRANTED);
                    arg0.sendBroadcast(intent);
//                    connection = usbManager.openDevice(device);
                    new ConnectionThread().start();
                } else // User not accepted our USB connection. Send an Intent to the Main Activity
                {
                    Intent intent = new Intent(ACTION_USB_PERMISSION_NOT_GRANTED);
                    arg0.sendBroadcast(intent);
                }
            } else if (arg1.getAction().equals(ACTION_USB_ATTACHED)) {
                if (!serialPortConnected)
                    // A USB device has been attached. Try to open it as a Serial port
                    findSerialPortDevice();
            } else if (arg1.getAction().equals(ACTION_USB_DETACHED)) {
                // Usb device was disconnected. send an intent to the Main Activity
                Intent intent = new Intent(ACTION_USB_DISCONNECTED);
                arg0.sendBroadcast(intent);
                if (serialPortConnected) {
                    serialPort.syncClose();
                }
                serialPortConnected = false;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (mLoraMessageCallBack != null) {
                    String receivedStr = msg.obj.toString();
                    mLoraMessageCallBack.onLoraMessage_Sycn_Read(receivedStr);
                }
            }
        }
    };

    /**
     * A simple thread to open a serial port.
     * Although it should be a fast operation. moving usb operations away from UI thread is a good thing.
     */
    private class ConnectionThread extends Thread {
        @Override
        public void run() {

            connection = usbManager.openDevice(device);

            serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
            if (serialPort != null) {
                if (serialPort.syncOpen()) {
                    serialPortConnected = true;
                    serialPort.setBaudRate(BAUD_RATE);
                    serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                    serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                    serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                    /**
                     * Current flow control Options:
                     * UsbSerialInterface.FLOW_CONTROL_OFF
                     * UsbSerialInterface.FLOW_CONTROL_RTS_CTS only for CP2102 and FT232
                     * UsbSerialInterface.FLOW_CONTROL_DSR_DTR only for CP2102 and FT232
                     */
                    serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                    serialPort.read(mCallback);
                    serialPort.getCTS(ctsCallback);
                    serialPort.getDSR(dsrCallback);

                    new ReadThread().start();
//                    mreadThread.start();

                    //
                    // Some Arduinos would need some sleep because firmware wait some time to know whether a new sketch is going
                    // to be uploaded or not
                    //Thread.sleep(2000); // sleep some. YMMV with different chips.

                    // Everything went as expected. Send an intent to MainActivity
                    Intent intent = new Intent(ACTION_USB_READY);
                    context.sendBroadcast(intent);
                } else {
                    // Serial port could not be opened, maybe an I/O error or if CDC driver was chosen, it does not really fit
                    // Send an Intent to Main Activity
                    if (serialPort instanceof CDCSerialDevice) {
                        Intent intent = new Intent(ACTION_CDC_DRIVER_NOT_WORKING);
                        context.sendBroadcast(intent);
                    } else {
                        Intent intent = new Intent(ACTION_USB_DEVICE_NOT_WORKING);
                        context.sendBroadcast(intent);
                    }
                }
            } else {
                // No driver for given device, even generic CDC driver could not be loaded
                Intent intent = new Intent(ACTION_USB_NOT_SUPPORTED);
                context.sendBroadcast(intent);
            }
        }
    }

//    private class ReadThread extends Thread {
//        @Override
//        public void run() {
////            while (true) {
////                byte[] buffer = new byte[200];
////                // 阻塞线程
////                int n = serialPort.syncRead(buffer, 0);
////                if (n > 0) {
////                    byte[] received = new byte[n];
////
////                }
////            }
//
//            Log.d("t", "*****1");
//            byte[] bytes = {};
//            byte[] readBuffer = new byte[100];
//            int n = serialPort.syncRead(readBuffer, 0);
//            Log.d("t", "*****2");
//            while (n > 0) {
//                bytes = concat(bytes, readBuffer);
//                n = serialPort.syncRead(readBuffer, 0);
//                Log.d("t", "*****3");
//            }
//            Log.d("t", "*****4");
//        }
//    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            while (true) {
                byte[] buffer = new byte[100];
                int n = serialPort.syncRead(buffer, 0);
                byte[] bytes = {};
                if (n > 0) {
                    byte[] received = new byte[n];
                    System.arraycopy(buffer, 0, received, 0, n);

                    Log.d("t", "n = " + n);
                } else if (bytes.length > 0) {

                    Message message = new Message();
                    message.what = 1;
                    String receivedStr = new String(bytes);
                    message.obj = receivedStr;

                    mHandler.sendMessage(message);
                    Log.d("t", "receivedStr = " + receivedStr);

                }

            }
        }
    }

//    Thread mreadThread = new Thread() {
//        @Override
//        public void run() {
//            super.run();
//
//                byte[] bytes = {};
//                byte[] buffer = new byte[1];
//                int n = serialPort.syncRead(buffer, 0);
//                while (n > 0) {
//                    bytes = concat(bytes, buffer);
//                    n = serialPort.syncRead(buffer, 0);
//                    String receivedStr = new String(bytes);
//                    Log.d("yu", receivedStr);
//                }
//        }
//    };

    public String readBuffer() {
        byte[] bytes = {};
        byte[] buffer = new byte[100];
        int n = serialPort.syncRead(buffer, 0);
        while (n > 0) {
            bytes = concat(bytes, buffer);
            n = serialPort.syncRead(buffer, 0);
            String receivedStr = new String(bytes);
            Log.d("yu", receivedStr);
            return receivedStr;
        }
        String receivedStr = new String(bytes);
        Log.d("yu", receivedStr);
        return receivedStr;
    }

    /**
     * 合并数组
     *
     * @param firstArray  第一个数组
     * @param secondArray 第二个数组
     * @return 合并后的数组
     */
    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            if (firstArray != null)
                return firstArray;
            if (secondArray != null)
                return secondArray;
            return null;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length,
                secondArray.length);
        return bytes;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    public interface LoraMessageCallBack {
        void onLoraMessage_Serial_Port(String message);

        void onLoraMessage_Sycn_Read(String message);

        void onCts_Change(boolean state);

        void onDsr_Change(boolean state);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(usbReceiver);
        SERVICE_CONNECTED = false;
    }
}
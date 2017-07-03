package weiqian.hardware;

import android.util.Log;

import java.io.UnsupportedEncodingException;

public class SerialPort {

    private int mFd;

    public SerialPort() {
        mFd = -1;
    }

    public void open(String path, int baud, int databits, String parity, int stopbits) {
        mFd = HardwareControl.OpenSerialPort(path, baud, databits, parity, stopbits);
    }

    public void close() {
        HardwareControl.CloseSerialPort(mFd);
    }

    public int read(byte[] buff, int count) {
        return HardwareControl.ReadSerialPort(mFd, buff, count);
    }

    public int write(byte[] buff, int count) {
        return HardwareControl.WriteSerialPort(mFd, buff, count);
    }

    public String read() {
        byte[] buff = new byte[64];
        StringBuffer strBuf = new StringBuffer(buff.length);
        read(buff, 64);

//        for (int i = 0; i < buff.length; i++) {
//            String hex = Integer.toHexString(buff[i] & 0xFF);
//            if (hex.length() == 1) {
//                hex = '0' + hex;
//            }
//            strBuf.append(hex.toUpperCase());
//        }


        String strTemp = "";
        for (int i = 0; i < buff.length; i++) {
            strTemp = Integer.toHexString(0xFF & buff[i]);
            strBuf.append("0x");
            if (strTemp.length() < 2) {
                strBuf.append(0);
            }
            strBuf.append(strTemp.toUpperCase());
            strBuf.append(" ");
        }
        Log.d("whsgzcy", "data = " + strTemp);
        return strBuf.toString();
    }

//	public String read() {
//		byte[] buff = new byte[64];
//		read(buff, 64);
//		return buff.toString();
//	}

//    public int read() {
//        byte[] buff = new byte[64];
//        read(buff, 64);
//
////        String data = "";
////
////        try {
////            data = new String(buff,"GBK");
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        Log.d("whsgzcy","data = " + data);
////
////        return data;
//
////        return buff & 0xFF;
//    }

    public void write(String buff) {
        write(buff.getBytes(), buff.length());
    }
}

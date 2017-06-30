package weiqian.hardware;

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

//	public String read() {
//		byte[] buff = new byte[64];
//		read(buff, 64);
//		return buff.toString();
//	}

    public String read() {
        byte[] buff = new byte[64];
        read(buff, 64);

        String data = "";

        try {
            data = new String(buff, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return data;

//		return buff.toString();
    }

    public void write(String buff) {
        write(buff.getBytes(), buff.length());
    }
}

package robot.loongman.iwant.com.super_usb_serialp_library.usbserial;

import java.io.OutputStream;

public class SerialOutputStream extends OutputStream
{
    protected final UsbSerialInterface device;

    public SerialOutputStream(UsbSerialInterface device)
    {
        this.device = device;
    }

    @Override
    public void write(int b)
    {
        device.write(new byte[] { (byte)b });
    }

    @Override
    public void write(byte[] b)
    {
        device.write(b);
    }
}

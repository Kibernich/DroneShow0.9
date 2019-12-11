import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class udpReceiver extends Thread
{
    DatagramSocket socket;
    udpPacketConverter converter;

    // test reusage of objects
    byte[] receiveData = new byte[12];


    public udpReceiver(DatagramSocket socket, udpPacketConverter converter)
    {
        this.socket = socket;
        this.converter = converter;
    }
    @Override
    public void run() {
        while (true) // forever
        {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                converter.packets.add(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

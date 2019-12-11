import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

public class udpPacketConverter extends Thread {

    DatagramSocket socket;
    udpPacketConverter converter;

    byte[] receiveData = new byte[12];


    public udpPacketConverter(DatagramSocket socket, udpPacketConverter converter)
    {
        this.socket = socket;
        this.converter = converter;
    }


    //This boolean is a "better" solution to our while loops, since we have the opportunity to stop the loop and not have the program run forever.
    public boolean stop = false;

    public LinkedList<DatagramPacket> packets = new LinkedList<>();

    public udpPacketConverter() {

    }

    @Override
    public void run() {
        while (!stop)
        {
            try {
                handlePacket(packets.getFirst());
            }
            catch (Exception e)
            {
             //   System.out.println("queue empty!");
            }

        }
    }

    private void handlePacket(DatagramPacket packet) {

        //Converting the data packet to a String of chars.
        char[] c = new char[packet.getData().length];
        for (int i = 0; i < packet.getData().length; i++) {
            c[i] = (char)packet.getData()[i];
        }

        System.out.println(c);
       // System.out.print(Arrays.toString(receiveData));
        System.out.print(" from ");
        System.out.println(packet.getAddress().toString().trim());

        // fjern pakken fra køen
        packets.remove(packet);

    }
}

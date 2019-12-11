import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class DroneSwarm {

    public DatagramSocket socket; //socket is used for both sending and receiving messages
    udpReceiver receiver;
    udpPacketConverter converter;

    //The drones UDP port is instantiated and is a constant int
    private int udpPort = 8889;
    private boolean isConnected = false;

    //The drone of drones is created
    private ArrayList<TelloDrone> droneSwarm = new ArrayList<>();

    public DroneSwarm() {
        try {
            //create socket for communication
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        converter = new udpPacketConverter();
        converter.start();

        receiver = new udpReceiver(socket, converter);
        receiver.start();
    }

    //function for adding drones to the arraylist
    public void addDrone(TelloDrone drone)
    {
        droneSwarm.add(drone);
    }


    public boolean sendCommand(String command, InetAddress ip) {
        try {
            byte[] sendData = command.getBytes();  // byte array from string
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, udpPort);
            socket.send(sendPacket);
            System.out.println("send "+ command + " to " + ip.getHostAddress());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Starting SDK mode for the drones in the drone swarm
    public void connectAll() {
        if (isConnected==false);
        for (TelloDrone drone: droneSwarm) {
            sendCommand("command", drone.ip);
            //Thread.sleep(500);
        }

        if (isConnected == true) {
            for (TelloDrone drone: droneSwarm) {
                drone.setState(TelloDrone.State.OK);
            }
        }
    }

    public boolean sendToSpecific(String command, int i) {
        try {
            byte[] sendData = command.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length);
            socket.send(sendPacket);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void droneState(TelloDrone drone) {

    }

    public void swarmState() {

    }

    public boolean moveAll(Direction direction, int cm)
    {
        for (TelloDrone drone: droneSwarm) {
            sendCommand("move" + direction + " " + cm, drone.ip);
            return true;
        }
        return false;
    }

    //Following commands are drone actions and movements used to move the drones
    public boolean move (Direction direction, int cm,  TelloDrone drone) {
        if (cm >= 20 && cm <= 500) {
            sendCommand(direction + " " + cm, drone.ip) ;
            return true;
        }
        return false;

    }


    public void takeOff() {
        for (TelloDrone drone: droneSwarm) {
            sendCommand("takeoff", drone.ip);
        }
    }

    public void land() {
        for (TelloDrone drone: droneSwarm) {
            sendCommand("land", drone.ip);
        }
    }

    public boolean allOk() {
        for (TelloDrone drone : droneSwarm) {
            if (drone.getState() != TelloDrone.State.OK) {
                return false;
            }
        }
        return true;
    }


}

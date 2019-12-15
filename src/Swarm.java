import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Swarm {

    public DatagramSocket socket; //socket is used for both sending and receiving messages
    udpReceiver receiver;
    udpPacketConverter converter;

    //The drones UDP port is instantiated and is a constant int
    private final int udpPort = 8889;
    private boolean isConnected = false;

    //The array of drones is created
    private ArrayList<TelloDrone> droneSwarm = new ArrayList<>();

    public Swarm() {
        try {
            //create socket for communication
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        converter = new udpPacketConverter(droneSwarm);
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

            // test - missing replies from drones
            //Thread.sleep(500);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Starting SDK mode for the drones in the drone swarm
    public void connectAll() {
        if (!isConnected);
        for (TelloDrone drone: droneSwarm) {
            sendCommand("command", drone.ip);
            drone.setState(TelloDrone.State.OK);
        }
/**
        if (isConnected == true) {
            for (TelloDrone drone: droneSwarm) {
                drone.setState(TelloDrone.State.OK);
            }
        }*/
    }

    public boolean sendToSpecific(String command, InetAddress ip) {
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
        // Programmet siger unknown command
    public boolean moveAll(Direction direction, int cm)
    {
        for (TelloDrone drone: droneSwarm) {
            sendCommand("move" + direction + " " + cm, drone.ip);
            drone.setState(TelloDrone.State.OK);
            return true;
        }
        return false;
    }
            // Er det her rigtigt?
    public boolean allOk() {
        for (TelloDrone drone : droneSwarm) {
            if (drone.getState() != TelloDrone.State.OK && drone.getState() != TelloDrone.State.DISCONNECTED) {
                return false;
            }
        }
        return true;
    }

    public static void waitForAllDronesToBeOK(Swarm swarm) {
        while (!swarm.allOk())
        {
            // venter og venter - laver ingenting
            System.out.print(".");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
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




}

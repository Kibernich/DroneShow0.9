import java.io.IOException;
import java.net.DatagramPacket;
import java.lang.String;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class TelloDrone {

    //public final String ip;
    public InetAddress ip;
    private State state;

    public TelloDrone(String ip) {
        try {
            this.ip = InetAddress.getByName(ip);
        }
        catch (UnknownHostException e) {
            //e.printStackTrace();
            System.out.println("IP address not valid!");
        }
        this.state = State.DISCONNECTED;
}

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    enum State {
        OK, ERROR, BUSY, DISCONNECTED;
    }
}
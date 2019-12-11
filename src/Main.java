public class Main {
    /*
    This program is inspired by Ebbe Vang, lektor at RUC, and his existing program TelloDroneJavaConnect.
     */

    public static void main(String[] args) throws InterruptedException {
        //Create drone swarm
        DroneSwarm swarm = new DroneSwarm();

        //swarm.addDrone(new TelloDrone(("192.168.1.9")));
        //swarm.addDrone(new TelloDrone(("192.168.1.8")));
        //swarm.addDrone(new TelloDrone(("192.168.1.7")));
        swarm.addDrone(new TelloDrone(("192.168.1.6")));

        // send commands to all drones in drone swarm
        swarm.connectAll();
        Thread.sleep(2000);
        waitForAllDronesToBeOK(swarm);
        swarm.takeOff();
        waitForAllDronesToBeOK(swarm);
        swarm.moveAll(Direction.UP, 20);
        waitForAllDronesToBeOK(swarm);
        swarm.moveAll(Direction.FORWARD, 30);
        waitForAllDronesToBeOK(swarm);
        swarm.moveAll(Direction.BACK, 30);
        waitForAllDronesToBeOK(swarm);
        swarm.land();
        waitForAllDronesToBeOK(swarm);

        // never ending
        while (true)
        {
        }

    }

    public static void waitForAllDronesToBeOK(DroneSwarm swarm) {
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
}


/**
 * BridgeSimulator.java
 * This program simulates the crossing of a bridge.
 *
 * @version   1
 *
 * @author1    Sri Praneeth Iyyapu
 * @author2    Manasi Sunil Bharde
 * Revisions:
 *      $Log$
 */

import java.util.Random;
public class BridgeSimulator {
    public static Object obj = "";
    static int truck_weight = 0; //initializing the truck_weight, combined weight of the trucks and the
    static int tot_weight = 0;  //truck count to zero
    static int truck_count = 0;

    public static int getRandomInteger(){
        int max = 100000; //maximum weight of the truck
        int min = 100;    //minimum weight of the truck
        Random rand_weight = new Random();                 //generates a random weight between
        return rand_weight.nextInt(max - min + 1) + min;   //100 to 100000lbs
    }

    public static void main(String [] args)
    {
        Truck leftTruck = new Truck();
        Truck rightTruck = new Truck();
        new Thread(leftTruck, "LeftTruck").start();   //generates trucks which cross the bridge from the left lane
        new Thread(rightTruck, "RightTruck").start();  //generates trucks which cross the bridge from the right lane
    }
}

class Truck extends Thread {

    public void run() {
    	while(true){
        synchronized (BridgeSimulator.obj) {
        	
        	BridgeSimulator.obj.notify();      	
            BridgeSimulator.truck_weight = BridgeSimulator.getRandomInteger(); //generates a truck with random weight
            BridgeSimulator.tot_weight = BridgeSimulator.tot_weight + BridgeSimulator.truck_weight;   //stores the combined weight of the trucks on the bridge
            BridgeSimulator.truck_count = BridgeSimulator.truck_count + 1;            //stores the truck count

            // checks for the condition that the combined weight of the trucks on the bridge should not be greater
            // than 200000lbs and the truck count should not be more than 4
            if ((BridgeSimulator.tot_weight <= 200000) && (BridgeSimulator.truck_count <= 4)) {
                System.out.println(Thread.currentThread().getName() + " weighing " + BridgeSimulator.truck_weight + " lbs"
                        + " crossing the left lane on the bridge");
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                }
            } else {
                System.out.println("====================================================================");
                BridgeSimulator.tot_weight = 0;
                BridgeSimulator.truck_count = 0;    
            }
            try {
				BridgeSimulator.obj.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }}
    }
}
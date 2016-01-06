import java.rmi.Naming;
import java.net.*;
public class HelloServer {

    public HelloServer() {

        try {
			
            HelloInterface cc = new HelloImplementation();
            Naming.rebind("rmi://localhost:1099/thisOne", cc);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error " + e);
        }
    }

    public static void main(String args[]) {
        new HelloServer();
    }
}
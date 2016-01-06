import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.net.*;

public class HelloC {

	public static void localRemoteTest(HelloInterface obj)	{
		
		try {
			
			  String get_class = obj.getClass().getName(); 
			  System.out.println(obj.test(get_class));	  
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[] ) {

		try {
			localRemoteTest(  (HelloInterface)Naming.lookup("rmi://localhost:1099/thisOne") );
			localRemoteTest(  new HelloImplementation());
		} catch (Exception e) {
			e.printStackTrace();
		}

        }
}

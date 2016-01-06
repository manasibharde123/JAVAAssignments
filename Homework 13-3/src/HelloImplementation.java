import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;

public class HelloImplementation extends  UnicastRemoteObject
        implements HelloInterface {
	
	public HelloImplementation()    throws  RemoteException {
		super();
		
    }
		
    public String test(String msg)
            throws  RemoteException {
	
		if (msg.contains("proxy")){
			msg = "localRemoteTest is invoked remotely";
		}
		else {
			msg = "localRemoteTest is invoked locally";
		}
		
		return msg;
        
    }


}
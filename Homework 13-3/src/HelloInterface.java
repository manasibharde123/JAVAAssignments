import java.rmi.Remote ;
import java.rmi.RemoteException;

public interface HelloInterface extends Remote {
	
         public String test(String msg) throws RemoteException;
}
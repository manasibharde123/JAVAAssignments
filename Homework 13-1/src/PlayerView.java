import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerView extends Remote{
	
	public String getMessage(int n) throws RemoteException;
	
	public int messageQSize() throws RemoteException;

}

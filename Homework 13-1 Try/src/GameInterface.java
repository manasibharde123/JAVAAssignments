import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote{
	
	public static final Object sharedObject = new Object();
	
	public void setNextMove(boolean val) throws RemoteException;
	
	public boolean getGameOver() throws RemoteException;
}

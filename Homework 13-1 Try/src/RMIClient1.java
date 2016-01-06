import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient1 {

	public static void main(String[] args) {
		String serverURL = "rmi://localhost/GameServer";
		String viewURL = "rmi://localhost/ViewServer";
		
        try {
			GameInterface gameIntf = (GameInterface)Naming.lookup(serverURL);
			PlayerView view = (PlayerView)Naming.lookup(viewURL);
			while(!gameIntf.getGameOver()){
				synchronized(GameInterface.sharedObject){
					GameInterface.sharedObject.notify();
					System.out.println(view.getMessage(0));
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					if(br.readLine().contains("y"))
						gameIntf.setNextMove(true);
					GameInterface.sharedObject.wait();
				}			
			}
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;

public class RMIClient {

	public static void main(String[] args) {
		String serverURL = "rmi://localhost/GameServer";
		String viewURL = "rmi://localhost/ViewServer";
		String myPlayerID = UUID.randomUUID().toString();
		BufferedReader br;
		int messageCount = 0;
		System.out.println(myPlayerID);
        try {
			GameInterface gameIntf = (GameInterface)Naming.lookup(serverURL);
			PlayerView view = (PlayerView)Naming.lookup(viewURL);
			gameIntf.addPlayerId(myPlayerID);
			while(!gameIntf.getGameOver()){
				Thread.sleep(100);
				while(view.messageQSize()>messageCount){
					System.out.println(view.getMessage(messageCount++));
					}
				if(gameIntf.getCurrentPlayerID().equals(myPlayerID)){
					br = new BufferedReader(new InputStreamReader(System.in));
					if(br.readLine().contains("y"))
						gameIntf.setNextMove(true);
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

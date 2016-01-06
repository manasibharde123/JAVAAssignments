import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMIClient {

	public static void main(String[] args) {
		String serverURL = "rmi://localhost/GameServer";
		String viewURL = "rmi://localhost/ViewServer";
		int messageCount = 0;
        try {
			GameInterface gameIntf = (GameInterface)Naming.lookup(serverURL);
			PlayerView view = (PlayerView)Naming.lookup(viewURL);
			while(!gameIntf.getGameOver()){
				while(view.messageQSize()>messageCount)
					System.out.println(view.getMessage(messageCount++));
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
					if(br.readLine().contains("y"))
						gameIntf.setNextMove(true);
				}			
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}       
	}
}

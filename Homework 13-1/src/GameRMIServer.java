import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class GameRMIServer {

	TwoPlayerController controller;
	TwoPlayerView view;
	TwoPlayerModel game;
	String playerNames[] = new String[game.noOfPlayers];
	
	public GameRMIServer(){
		try {
			game = new TwoPlayerModel();
			Naming.rebind("GameServer", game);
			view = new TwoPlayerView();
			Naming.rebind("ViewServer", view);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startGameServer(){
		Integer count =0;	 
		game.distributeCards(game.remainingCards);
		Thread[] t = new Thread[game.noOfPlayers]; 
		while(count < (game.noOfPlayers)){
			t[count] = new Thread(game.p[count]);
			t[count].start();
			count++;}
		controller = new TwoPlayerController(game, view);
		for(int i = 0; i<game.noOfPlayers; i++){
           	game.p[i].addObserver(controller);  
		}		
		view.drawCard(game.p[0]);
	}
	
	public static void main(String[] args){		
		GameRMIServer gs  = new GameRMIServer();
		gs.startGameServer();
	}
}
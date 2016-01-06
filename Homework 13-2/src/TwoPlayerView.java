import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class TwoPlayerView extends UnicastRemoteObject implements PlayerView{

	protected TwoPlayerView() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	ArrayList<String> messageQueue = new ArrayList<String>();
	
	public void displayMove(Player p, Card c){
		System.out.println("Player "+ p + " played " + c.card);
		view("Player "+ p + " played " + c.card);
	}
	
	public void declareWinner(String winner){
		System.out.println("Winner is "+ winner);
		view("Winner is "+ winner);
	}	
	
	public void drawCard(Player p){
		System.out.println(p + " press Y/y to draw next card");	
		view(""+p + " press Y/y to draw next card");
	}
	
	public void cardRedistributed(){
		System.out.println("Cards redistributed");
		view("Cards redistributed");
	}
	
	public void wishToContinue(){
		System.out.println("Do you want to continue? y/n");
		view("Do you want to continue? y/n");
	}
	
	public void wonMove(Player p){
		System.out.println("Player " + p + " got cards in stack, new stack started");
		view("Player " + p + " got cards in stack, new stack started");
	}
	
	public void gameOver(){
		System.out.println("Game is Over");
		view("Game is Over");
	}
	
	public void view(String op){
		messageQueue.add(op);
	}

	@Override
	public String getMessage(int n) {
		return messageQueue.get(n);
	}

	@Override
	public int messageQSize() {
		return messageQueue.size();
	}
}
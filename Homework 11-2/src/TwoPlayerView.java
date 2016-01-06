import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

public class TwoPlayerView implements Serializable{

	BufferedWriter[] out, backup;
	
	TwoPlayerView(BufferedWriter[] out){
		this.out = out;
	}
	
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
		for(int i=0; i < out.length; i++){
			try {
				out[i].write(op);
				out[i].newLine();
				out[i].flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
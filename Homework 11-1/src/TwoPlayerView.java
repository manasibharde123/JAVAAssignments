
public class TwoPlayerView {

	public void displayMove(Player p, Card c){
		System.out.println("Player "+ p + " played " + c.card);
	}
	
	public void declareWinner(String winner){
		System.out.println("Winner is "+ winner);
	}	
	
	public void drawCard(Player p){
		System.out.println("Next player press Y/y to draw next card");		
	}
	
	public void cardRedistributed(){
		System.out.println("Cards redistributed");
	}
	
	public void wishToContinue(){
		System.out.println("Do you want to continue? y/n");
	}
	
	public void wonMove(Player p){
		System.out.println("Player " + p + " got cards in stack, new stack started");
	}
	
}
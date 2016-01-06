import java.io.Serializable;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;

public class Player extends Observable implements Runnable{
	public Vector<Card> myCards = new Vector<Card>();
	Card currentCard;
	String name;
	TwoPlayerModel game;
	int myNumber;
	
	public Player(String name, TwoPlayerModel game, int playerNumber){
		this.name = name;
		this.game = game;
		this.myNumber = playerNumber;
	}
	
	public void addCards(Vector<Card> wonCards){
		myCards.addAll(wonCards);
	}
	
	public String toString()
	{
		return name;
	}
	
	public void playMove(){
		Random generator = new Random();
		int i = generator.nextInt(myCards.size());
		Card card = myCards.get(i); 
		myCards.remove(i);
		currentCard = card;
		setChanged();
		notifyObservers();
	}
	
	public void run(){
		while(!game.getGameOver()){			
			while(game.currentPlayer != myNumber)
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			while(!game.getNextMove())
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			playMove();
			game.setNextMove(false);
		}
	}
}


class Card implements Serializable{
	public String color;
	public int value;
	public String image;
	public String card ;
	
	public Card(int color, int value){
		this.color = Colors.colors[color];
		this.value = value;
		this.image = CardImages.images[value];
		this.card = this.image + " of "+ this.color;
	}
}

class CardImages{
	public static String[] images = new String[]{"Ace", "Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King"};
}

class Colors{
	public static String[] colors = new String[]{"Heart","Diamond","Spade","Club"};
}
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class TwoPlayerModel{
	public static final Object o = new Object();
	static Vector<Card> deck = new Vector<Card>();
	static Vector<String> cardsInDeck = new Vector<String>();
	static Vector<Card> remainingCards = new Vector<Card>();
	static Vector<Card> playedCards = new Vector<Card>();
	String winner;
	Player p1;
	Player p2;
	public static int moves = 0;
	public static Card lastCard;
	public static boolean gameOver = false;
	static boolean nextMove = false;
	
	static{
		for(int i = 0; i < 13; i++ )
		{
			for(int j = 0; j < 4; j++){
				Card card = new Card(j,i);
				deck.addElement(card);
				cardsInDeck.add(card.card);
				remainingCards.addElement(card);
			}
		}
	}

	public TwoPlayerModel(Observer observer){
		p1 = new Player(observer, "Player 1");
		p2 = new Player(observer, "Player 2");
	}
	
	public void distributeCards(Vector<Card> availableCards){
		int j = 0;
		while(availableCards.size() != 0){
			Random generator = new Random();
			int i = generator.nextInt(availableCards.size());
			Card card = availableCards.get(i); 
			availableCards.remove(i);
			if (j++%2 == 0)
				p1.myCards.add(card);
			else
				p2.myCards.add(card);
			}
	}
	
	public void checkMoves(Player p){
		playedCards.add(p.currentCard);
		if(moves > 1 && p.currentCard.color==lastCard.color){
			if(p.currentCard.value>lastCard.value){
				p.addCards(playedCards);
				playedCards = new Vector<Card>();
			}						
		}
		else{
			lastCard = p.currentCard;
		}
		moves++;
	}
	
	public void checkPlayerCards(){
		if(playedCards.size()==52 | p1.myCards.size()==0 | p2.myCards.size() ==0){
			distributeCards(playedCards);
			playedCards = new Vector<Card>();
			moves = 0;
		}
	}
	
	public boolean checkWinner(){
		if(p1.myCards.size()== 52){
			winner = "Player 1";
			return true;
		}
		else if(p2.myCards.size()== 52){
			winner = "Player 1";
			return true;
		}
		else 
			return false;
	}
}

class Player extends Observable implements Runnable{
	public Vector<Card> myCards = new Vector<Card>();
	Card currentCard;
	boolean nextMove = false;
	String name;
	
	public Player(Observer observer, String name){
		addObserver(observer);
		this.name = name;
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
		while(!TwoPlayerModel.gameOver){
			synchronized(TwoPlayerModel.o){
				while(!TwoPlayerModel.nextMove)
					try {
						Thread.sleep(80);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				TwoPlayerModel.o.notify();
				playMove();
				nextMove = false;
				try {
					TwoPlayerModel.o.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}

class Card{
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

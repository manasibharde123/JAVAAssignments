/**
       * @version   1
       *
       * @author1    Manasi Sunil Bharde
       * @author2    Sri Praneeth Iyyapu
       * Revisions:
       *      $Log$
 */

import java.io.Serializable;
import java.util.Observable;
import java.util.Random;
import java.util.Vector;

public class TwoPlayerModel extends Observable  implements Serializable{
	public static final Object o = new Object();
	public static final int noOfPlayers = 4;
	static Vector<Card> deck = new Vector<Card>();
	static Vector<String> cardsInDeck = new Vector<String>();
	static Vector<Card> remainingCards = new Vector<Card>();
	static Vector<Card> playedCards = new Vector<Card>();
	String winner;
	public Player[] p = new Player[noOfPlayers];
	int currentPlayer = 0;
	public int moves = 0;
	Card lastCard;
	public static Boolean gameOver = false;
	static Boolean nextMove = false;
	
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

	public Vector<Card> getRemainingCards(){
		return remainingCards;
	}
	
	public void setGameOver(boolean val){
		gameOver = val;
	}
	
	public int getMoves(){
		return moves;
	}
	
	public void setNextMove(boolean val){
		nextMove = val;
	}
	
	public boolean getNextMove(){
		return nextMove;
	}
	
	public static boolean getGameOver()
	{
		return gameOver;
	}

	public TwoPlayerModel(){
		for(int i=0; i< noOfPlayers ; i++){
			p[i] = new Player("Player "+i);
		}
	}
	
	public void distributeCards(Vector<Card> availableCards){
		int j = 0;
		while(availableCards.size() != 0){
			Random generator = new Random();
			int i = generator.nextInt(availableCards.size());
			Card card = availableCards.get(i); 
			availableCards.remove(i);
			p[j++%noOfPlayers].myCards.add(card);
			}
			setChanged();
			notifyObservers();
	}
	
	public void checkMoves(Player p){
		playedCards.add(p.currentCard);

		if(moves > 1 && p.currentCard.color==lastCard.color){
			if(p.currentCard.value>lastCard.value){
				p.addCards(playedCards);
				playedCards = new Vector<Card>();
			}						
		}
		else
			lastCard = p.currentCard;
		moves++;
	}
	
	public void checkPlayerCards(){
		if(playedCards.size()==52 | p[0].myCards.size()==0 | p[1].myCards.size() ==0){
			distributeCards(playedCards);
			playedCards = new Vector<Card>();
			moves = 0;
		}
	}
	
	public boolean checkWinner(){
		for(int i =0; i < noOfPlayers; i++){
			if(p[i].myCards.size()== 52){
				winner = "Player 1";
				return true;
			}
		}
		return false;
	}

}
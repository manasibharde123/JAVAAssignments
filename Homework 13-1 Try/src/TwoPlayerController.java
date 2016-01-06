import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class TwoPlayerController implements Observer, Controller{

	TwoPlayerModel game;
	TwoPlayerView view;
	
	public TwoPlayerController(TwoPlayerModel game, TwoPlayerView view){
		this.view = view;
		this.game = game;
	}
	
	public void setGame(TwoPlayerModel game){
		this.game = game;
	}

	public void setView(TwoPlayerView view){
		this.view = view;
	}
	
	@Override	
	public synchronized void update(Observable o, Object arg) {
		if(o instanceof Player){
			updateGameStatus((Player)o);
		}		
	}
	
	@Override
	public void updateGameStatus(Player p){
		view.displayMove(p,p.currentCard);
		game.checkMoves(p);
		if(game.playedCards.size()==0){
			view.wonMove(p);
		}
		if(game.checkWinner()){
			view.declareWinner(game.winner);
			game.gameOver = true;
			view.gameOver();
			return;
		}
		game.checkPlayerCards();
		if(game.moves==0){
			view.cardRedistributed();
			view.wishToContinue();
			Scanner br = new Scanner(System.in);
			String line = br.next();
			if(!line.contains("y")){
				game.gameOver = true;
				view.gameOver();
				return;
			}
		}			
		view.drawCard(game.p[(game.currentPlayer+1)%game.noOfPlayers]);
		game.currentPlayer = game.getMoves()%game.noOfPlayers;
	}
}
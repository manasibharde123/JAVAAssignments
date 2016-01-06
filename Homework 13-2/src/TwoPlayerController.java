import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class TwoPlayerController implements Observer{

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
			view.displayMove((Player)o,((Player)o).currentCard);
			game.checkMoves((Player) o);
			if(game.playedCards.size()==0){
				view.wonMove((Player) o);
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
			game.currentPlayer = game.getMoves()%game.noOfPlayers;
			view.drawCard(game.p[game.currentPlayer]);
			//	game.currentPlayer = game.getMoves()%game.noOfPlayers;
			System.out.println(game.currentPlayer);
		}		
	}
}
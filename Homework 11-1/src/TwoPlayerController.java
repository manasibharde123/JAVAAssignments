import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class TwoPlayerController implements Observer {

	TwoPlayerModel game;
	TwoPlayerView view;
	
	public static void main(String[] args) {
		TwoPlayerController con = new TwoPlayerController();
		con.startGame();
	}
	
	public void startGame(){
		game = new TwoPlayerModel(this);
		view = new TwoPlayerView();
		game.distributeCards(game.remainingCards);
		Thread t1 = new Thread((game).p1, "Player1");
		Thread t2 = new Thread((game).p2, "Player2");
		t1.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
		view.drawCard(game.p1);
		Scanner br = new Scanner(System.in);
		String line = br.next();
		game.nextMove = true;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Player){
			game.checkMoves((Player) o);
			if(game.playedCards.size()==0){
				view.wonMove((Player) o);
			}
			if(game.checkWinner()){
				view.declareWinner(game.winner);
				game.gameOver = true;
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
					return;
				}
			}
			view.displayMove((Player)o,((Player)o).currentCard);
			view.drawCard((Player) o);
			Scanner br = new Scanner(System.in);
			String line = br.next();
			game.nextMove = true;
		}		
	}
}

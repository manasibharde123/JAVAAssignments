import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;


public class TwoPlayerController implements Observer{

	TwoPlayerModel game;
	TwoPlayerView view;
/*	public TwoPlayerController(){
		try {
			socket = new Socket("localhost", 8080);
			System.out.println("Connected to "+socket.toString());
			is = socket.getInputStream();
			ObjectInputStream oos = new ObjectInputStream(is); 
			setGame((TwoPlayerModel)oos.readObject());
			setPlayer((Player)oos.readObject());
			setView((TwoPlayerView)oos.readObject());
			in = new BufferedReader(new InputStreamReader(is));
			out = new PrintWriter(socket.getOutputStream());
			view.out = out;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		view.drawCard(p);
		Scanner br = new Scanner(System.in);
		String line = br.next();
		this.game.nextMove = true;
	}*/
	
	public TwoPlayerController(TwoPlayerModel game, TwoPlayerView view){
		this.view = view;
		this.game = game;
	}
	
	public void setGame(TwoPlayerModel game){
		this.game = game;
		this.game.addObserver(this);
	}

	public void setView(TwoPlayerView view){
		this.view = view;
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
			view.drawCard(game.p[(game.currentPlayer+1)%game.noOfPlayers]);
		}		
	}
}

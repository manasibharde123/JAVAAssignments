import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class GameTCPSocket implements GameSocket{

	TwoPlayerController controller, tpc2;
	TwoPlayerView view, tpv2;
	final int PORT = 8080;
	TwoPlayerModel game;
	Thread[] t = new Thread[game.noOfPlayers];
	Socket[] s = new Socket[game.noOfPlayers-1];
	InputStreamReader[] is = new InputStreamReader[game.noOfPlayers-1];
	OutputStreamWriter[] os = new OutputStreamWriter[game.noOfPlayers-1];
	BufferedWriter[] o = new BufferedWriter[game.noOfPlayers-1];
	
	public GameTCPSocket(){
		game = new TwoPlayerModel();
	}
	
	public void startServer(){
		int count =0;
        game.distributeCards(game.remainingCards);
		try {
            ServerSocket server = new ServerSocket(9991);
            System.out.println("MiniServer active " + 9991);
            while(count < game.noOfPlayers-1){
            	s[count] = server.accept();
            	System.out.println("Got connected to player "+count);
            	is[count] = new InputStreamReader(s[count].getInputStream());
            	os[count] = new OutputStreamWriter(s[count].getOutputStream());
            	o[count] = new BufferedWriter(os[count]);
            	count++;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }		
	}
	
	public void startGame(){
		view = new TwoPlayerView(o);
		controller = new TwoPlayerController(game, view);
		for(int i = 0; i<game.noOfPlayers; i++){
           	game.p[i].addObserver(controller);
           	t[i] = new Thread(game.p[i]);
        	t[i].start();
		}
		view.drawCard(game.p[0]);
		while(!game.getGameOver()){
			for(int i = 0; i < game.noOfPlayers; i++){
				String line;
				if(i==0)
					line = (new Scanner(new InputStreamReader(System.in))).nextLine(); 
				else
					line = (new Scanner(is[i-1])).nextLine();
				if(line.contains("y"))
					game.p[i].nextMove = true;
				game.currentPlayer = i;
			}
		}
	}
	
	public static void main(String[] args){
		GameTCPSocket gs  = new GameTCPSocket();
		gs.startServer();
		gs.startGame();
	}
}

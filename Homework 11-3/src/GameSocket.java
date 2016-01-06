import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class GameSocket {

	TwoPlayerController controller, tpc2;
	TwoPlayerView view, tpv2;
	final int PORT = 8080;
	TwoPlayerModel game;
	
	public GameSocket(){
		game = new TwoPlayerModel();
	}
	
	public static void main(String[] args){
		int count =0;
		GameSocket gs  = new GameSocket();
		Thread[] t = new Thread[gs.game.noOfPlayers]; 
		Socket[] s = new Socket[gs.game.noOfPlayers];
		InputStream[] is = new InputStream[gs.game.noOfPlayers];
		OutputStreamWriter[] os = new OutputStreamWriter[gs.game.noOfPlayers];
		BufferedWriter[] o = new BufferedWriter[gs.game.noOfPlayers];
		try {
            ServerSocket server = new ServerSocket(9991);
            System.out.println("MiniServer active " + 9991);
            gs.game.distributeCards(gs.game.remainingCards);
            while(count < gs.game.noOfPlayers){
            	s[count] = server.accept();
            	System.out.println("Got connected to player "+count);
            	is[count] = s[count].getInputStream();
            	os[count] = new OutputStreamWriter(s[count].getOutputStream());
            	o[count] = new BufferedWriter(os[count]);
            	//oos.writeObject(gs.game);
            	//oos.writeObject(gs.game.p[count]);
            	//oos.writeObject(gs.tpv1);
            	t[count] = new Thread(gs.game.p[count]);
            	t[count].start();
            	count++;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		Scanner br;
		gs.view = new TwoPlayerView(o);
		gs.controller = new TwoPlayerController(gs.game, gs.view);
		for(int i = 0; i<gs.game.noOfPlayers; i++){
           	gs.game.p[i].addObserver(gs.controller);            
		}
		gs.view.drawCard(gs.game.p[0]);
		while(!gs.game.getGameOver()){
			for(int i = 0; i < gs.game.noOfPlayers; i++){
				gs.game.currentPlayer = i;
				br = new Scanner(is[i]);
				String line = br.next();
				if(line.contains("y"))
					gs.game.p[i].nextMove = true;
			}
		}
	}
}

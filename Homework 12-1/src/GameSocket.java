import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GameSocket {

	TwoPlayerController controller;
	TwoPlayerView view;
	TwoPlayerModel game;
	DatagramSocket serverSocket;
	InetAddress IPAdressesList[] = new InetAddress[game.noOfPlayers-1];
	Integer portList[] = new Integer[game.noOfPlayers-1];
	String playerNames[] = new String[game.noOfPlayers-1];
	private int port = 9876;
	
	public GameSocket(){
		game = new TwoPlayerModel();
	}
	
	public void startServer(){
		int count =0;
		game.distributeCards(game.remainingCards);
		Thread[] t = new Thread[game.noOfPlayers]; 
		try {
			serverSocket = new DatagramSocket(port);
			System.out.println("MiniServer active " + port);
            while(count < (game.noOfPlayers-1)){
            	byte[] receiveData = new byte[64];
            	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            	serverSocket.receive(receivePacket); 
    			String playerName = new String( receivePacket.getData());
    			playerName = playerName.trim();
    			IPAdressesList[count] = receivePacket.getAddress();                   
    			portList[count] = receivePacket.getPort();                   
            	count++;}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view = new TwoPlayerView(serverSocket,IPAdressesList,portList );
		controller = new TwoPlayerController(game, view);
		for(int i = 0; i<game.noOfPlayers; i++){
           	game.p[i].addObserver(controller);  
           	t[i] = new Thread(game.p[i]);
        	t[i].start();
		}		
	}
	
	public void startGame(){
		view.drawCard(game.p[0]);
		try {
		while(!game.getGameOver()){
			for(int i = 0; i < game.noOfPlayers; i++){				
				String line;
				if(i == 0){
					line  = (new BufferedReader(new InputStreamReader(System.in))).readLine();
				}
				else{
					byte[] receiveData = new byte[64];
					DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					serverSocket.receive(receivePacket);
					line = new String(receivePacket.getData());
					IPAdressesList[i-1] = receivePacket.getAddress(); 
					portList[i-1] = receivePacket.getPort();
					//System.out.println(address + " "+ port + " "+ line);
					System.out.println(IPAdressesList[i-1] + " "+portList[i-1]+" " );
				}
				if(/*address.equals(IPAdressesList[i]) && port.equals(portList[i])&&*/line.contains("y"))
					game.p[i].nextMove = true;
				game.currentPlayer = i;
			}
		}}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){		
		GameSocket gs  = new GameSocket();
		gs.startServer();
		gs.startGame();
	}
}
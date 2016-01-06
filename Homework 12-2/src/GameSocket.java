import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GameSocket {

	TwoPlayerController controller;
	TwoPlayerView view;
	TwoPlayerModel game;
	DatagramSocket serverSocket;
	InetAddress IPAdressesList[] = new InetAddress[game.noOfPlayers];
	Integer portList[] = new Integer[game.noOfPlayers];
	String playerNames[] = new String[game.noOfPlayers];
	private int port = 9876;
	
	public GameSocket(){
		game = new TwoPlayerModel();
	}
	
	public void startServer(){
		Integer count =0;
		game.distributeCards(game.remainingCards);
		Thread[] t = new Thread[game.noOfPlayers]; 
		try {
			serverSocket = new DatagramSocket(port);
			System.out.println("MiniServer active " + port);
            while(count < (game.noOfPlayers)){
            	byte[] receiveData = new byte[64];
				byte[] sendData = new byte[1];
            	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            	serverSocket.receive(receivePacket); 
    			String playerName = new String( receivePacket.getData());
    			playerName = playerName.trim();
    			System.out.println(playerName + " Connected");
    			IPAdressesList[count] = receivePacket.getAddress();                   
    			portList[count] = receivePacket.getPort();
    			sendData = (count.toString()).getBytes();
    			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAdressesList[count], portList[count]);
				serverSocket.send(sendPacket);
            	t[count] = new Thread(game.p[count]);
            	t[count].start();
            	count++;}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view = new TwoPlayerView(serverSocket,IPAdressesList,portList );
		controller = new TwoPlayerController(game, view);
		for(int i = 0; i<game.noOfPlayers; i++){
           	game.p[i].addObserver(controller);  
		}		
	}
	
	public void startGame(){
		view.drawCard(game.p[0]);
		try {
		while(!game.getGameOver()){
			for(int i = 0; i < game.noOfPlayers; i++){				
				String line;
				byte[] receiveData = new byte[64];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				line = new String(receivePacket.getData());
				IPAdressesList[i] = receivePacket.getAddress(); 
				portList[i] = receivePacket.getPort();
				//System.out.println(address + " "+ port + " "+ line);
				System.out.println(IPAdressesList[i] + " "+portList[i]+" " );
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
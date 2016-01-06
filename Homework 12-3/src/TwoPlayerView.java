import java.io.BufferedWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TwoPlayerView{

	InetAddress[] out;
	Integer[] port; 
	DatagramSocket serverSocket;
	BufferedWriter[] outList;
	String mode;
	
	TwoPlayerView(DatagramSocket serverSocket, InetAddress[] iPAdressesList, Integer[] portList){
		this.out = iPAdressesList;
		this.port = portList;
		this.serverSocket = serverSocket;
		this.mode = "UDP";
	}
	
	TwoPlayerView(BufferedWriter[] out){
		this.outList = out;
		this.mode = "TCP";
	}
	
	public void displayMove(Player p, Card c){
		System.out.println("Player "+ p + " played " + c.card);
		view("Player "+ p + " played " + c.card);
	}
	
	public void declareWinner(String winner){
		System.out.println("Winner is "+ winner);
		view("Winner is "+ winner);
	}	
	
	public void drawCard(Player p){
		System.out.println(p + " press Y/y to draw next card");	
		view(""+p + " press Y/y to draw next card");
	}
	
	public void cardRedistributed(){
		System.out.println("Cards redistributed");
		view("Cards redistributed");
	}
	
	public void wishToContinue(){
		System.out.println("Do you want to continue? y/n");
		view("Do you want to continue? y/n");
	}
	
	public void wonMove(Player p){
		System.out.println("Player " + p + " got cards in stack, new stack started");
		view("Player " + p + " got cards in stack, new stack started");
	}
	
	public void gameOver(){
		System.out.println("Game is Over");
		view("Game is Over");
	}
	
	public void view(String op){
		try {
			if(mode == "UDP")
			for(int i=0; i < out.length; i++){
					byte[] sendData = op.getBytes();       
					DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, out[i], port[i]);
					
						serverSocket.send(sendPacket);
					}
		else
			for(int i=0; i < outList.length; i++){
					outList[i].write(op);
					outList[i].newLine();
					outList[i].flush();
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
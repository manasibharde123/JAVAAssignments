import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * A Simple Socket client that connects to our socket server
 *
 */
public class SocketClient{
    private String hostname;
    private int port;
    DatagramSocket clientSocket;
    DatagramPacket receivePacket;
    byte[] sendData = new byte[64];       
	byte[] receiveData = new byte[64];
	boolean gameOver = false;
	InetAddress IPAddress;
	boolean readReady = true;
	int playerNumber;

    public SocketClient(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() throws UnknownHostException, IOException{
        System.out.println("Attempting to connect to "+hostname+":"+port);
        clientSocket = new DatagramSocket();       
		IPAddress = InetAddress.getByName(hostname);  
        System.out.println("Connection Established");
    }

    public void readResponse() throws IOException{
    	receiveData = new byte[128];
    	receivePacket = new DatagramPacket(receiveData, receiveData.length);     
    	if(!readReady)
    		return;
    	clientSocket.receive(receivePacket);       
		String receivedData = new String(receivePacket.getData());  
		receivedData = receivedData.trim();
		System.out.println("FROM SERVER:" + receivedData);
		if(receivedData.equals("Game is Over"))
			gameOver = true;
		readReady = false;
    }
    
    public void writeToServer() throws IOException{
    	BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));       
		clientSocket = new DatagramSocket();       		       
		String sentence = inFromUser.readLine();       
		sendData = sentence.getBytes();       
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		readReady = true;
    }

    private void initWrite(String playerName) throws IOException {
    	sendData = playerName.getBytes();
    	System.out.println(sendData.length);
    	DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		receiveData = new byte[1];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		playerNumber = Integer.parseInt(new String(receivePacket.getData()));
		receiveData = new byte[64];
	}
    
    public static void main(String args[]){
    	String host = args.length > 0 ? args[0] : "localhost";
		int port = args.length > 1 ? Integer.parseInt(args[1]) : 9876;
		String playerName = args.length > 2 ? args[2] : "Player";
		
        SocketClient client = new SocketClient (host,port);
        
        try {
        	client.connect();
        	client.initWrite(playerName);
        	client.readResponse();
            while(!client.gameOver){
            	for(int i = 0; i < client.playerNumber*2;i++){
    	            client.readReady = true;
            		client.readResponse();
            	}
	            client.writeToServer();
	            for(int i=0; i < (4-client.playerNumber)*2; i++ ){
	            	client.readReady = true;
		            client.readResponse();
	            }
        	}
        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
        }
    }
}
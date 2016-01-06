import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A Simple Socket client that connects to our socket server
 *
 */
public class SocketClient implements Runnable{

    private String hostname;
    private int port;
    static Socket socketClient;

    public SocketClient(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() throws UnknownHostException, IOException{
        System.out.println("Attempting to connect to "+hostname+":"+port);
        socketClient = new Socket(hostname,port);
        System.out.println("Connection Established");
    }

    public void readResponse() throws IOException{
       String userInput;
       BufferedReader stdIn = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
      /* System.out.print ("RESPONSE FROM SERVER:");*/
       System.out.println(stdIn.readLine());
      /* String line = stdIn.readLine();
       do{
           System.out.println(line);
           line = stdIn.readLine();
       }while(line != null);*/
    }
    
    public void writeToServer(String line) throws IOException{
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
       writer.write(line);
       writer.newLine();
       writer.flush();
    }

    public static void main(String arg[]){
        //Creating a SocketClient object
        SocketClient client = new SocketClient ("localhost",9991);
 /*       Thread read = new Thread(client, "read");
        Thread write = new Thread(client, "write");*/
        
        try {
        	client.connect();
            //trying to establish connection to the server
            //waiting to read response from server
        	client.readResponse();
            while(true){
            BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
            String s = br.readLine();
            client.writeToServer(s);
            client.readResponse();
            client.readResponse();
        	}
        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
        }
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if( Thread.currentThread().getName()== "read"){
			while(true)
			try {
				readResponse();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		else{
			while(true)
				try {
					BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
		            String s = br.readLine();
		            writeToServer(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
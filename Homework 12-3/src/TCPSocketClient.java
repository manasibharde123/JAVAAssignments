import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Stream;

public class TCPSocketClient{

    private String hostname;
    private int port;
    static Socket socketClient;
    boolean readReady = true;

    public TCPSocketClient(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() throws UnknownHostException, IOException{
        System.out.println("Attempting to connect to "+hostname+":"+port);
        socketClient = new Socket(hostname,port);
        System.out.println("Connection Established");
    }

    public void readResponse() throws IOException{
       BufferedReader stdIn = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
       if(readReady)
        	  System.out.println(stdIn.readLine());
       readReady = false;
    }
    
    public void writeToServer(String line) throws IOException{
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
       writer.write(line);
       writer.newLine();
       writer.flush();
    }

    public static void main(String arg[]){
        TCPSocketClient client = new TCPSocketClient ("localhost",9991);
        int x = 1;// wait for 1 second to get input
         try {
        	client.connect();
        	client.readResponse();
        	BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
            while(true){
	            client.readReady = true;
	            client.readResponse();
	            client.readReady = true;
	            client.readResponse();    
	            client.writeToServer(br.readLine());
	            client.readReady = true;
	            client.readResponse();
	            client.readReady = true;
	            client.readResponse();
        	}
        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
        }
    }
}
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Stream;

/**
 * A Simple Socket client that connects to our socket server
 *
 */
public class SocketClient{

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
       int x = 1;
       BufferedReader stdIn = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
      /* System.out.print ("RESPONSE FROM SERVER:");*/
           long startTime = System.currentTimeMillis();
           while ((System.currentTimeMillis() - startTime) < x * 1000
                   && !stdIn.ready()) {
           }
           if(stdIn.ready())
        	   System.out.println(stdIn.readLine());
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
        int x = 1;// wait for 1 second to get input
         try {
        	client.connect();
        	client.readResponse();
            while(true){
            BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
            long startTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startTime) < x * 1000
                    && !br.ready()) {
            }
            if(br.ready())
            client.writeToServer(br.readLine());
            client.readResponse();
            client.readResponse();
        	}
        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
        }
    }
}
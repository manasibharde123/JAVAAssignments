import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Server{
	public void start(){
		try {
			 ServerSocket server = new ServerSocket(9991);
			 BufferedWriter bw;
			 System.out.println("MiniServer active " + 9991);
			 Socket client = server.accept();
			 bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			 BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			 Long before = System.currentTimeMillis();
			 for(int i=0; i<100; i++){			 
				 bw.write("Sending data using TCP connection");
				 bw.newLine();
				 bw.flush();
				 br.readLine();
			 }
			 long after = System.currentTimeMillis();
			 System.out.println("TCP ThroughPut = "+(after-before));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

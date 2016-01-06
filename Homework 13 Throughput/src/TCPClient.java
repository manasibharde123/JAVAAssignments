import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCPClient extends Client{
	public void connect(){
		try {
			Socket socketClient = new Socket("localhost",9991);
			System.out.println(socketClient.getLocalPort());
			System.out.println("connected to server");
			BufferedReader br = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
			String line;
			for(int i=0; i<100; i++){
				line = br.readLine();
				bw.write(line);
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

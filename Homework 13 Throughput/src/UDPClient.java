import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient extends Client{
	public void connect(){
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			byte[] sendData = "Sending data using UDP connection".getBytes();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacket = new DatagramPacket(sendData, sendData.length);      
			for(int i=0; i<100; i++){
				clientSocket.send(sendPacket);
				clientSocket.receive(receivePacket);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}

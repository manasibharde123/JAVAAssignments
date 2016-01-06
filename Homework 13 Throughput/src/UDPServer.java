import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer extends Server{

	public void start(){
		try {
			DatagramSocket serverSocket = new DatagramSocket(9876);
			byte[] receiveData = new byte[33];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			InetAddress IPAddress = receivePacket.getAddress();                   
			int port = receivePacket.getPort();                   
			DatagramPacket sendPacket = new DatagramPacket(receiveData, receiveData.length, IPAddress, port);            
			long before = System.currentTimeMillis();
			for(int i=0; i<100; i++){
				serverSocket.receive(receivePacket);
				serverSocket.send(sendPacket);
			}
        	long after = System.currentTimeMillis();
        	System.out.println("UDP ThroughPut = "+(after-before));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

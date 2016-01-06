import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class DatagramServer implements Runnable {

	private final DatagramSocket sock;
	private int last;
	
	public DatagramServer(int port) throws SocketException {
		sock = new DatagramSocket(port);
		last = 0;
	}
	
	@Override
	public void run() {
		//256 byte buffer
		byte[] buffer = new byte[256];
		boolean read = true;
		while(read) {
			try {
				DatagramPacket received = new DatagramPacket(buffer, buffer.length);
				//blocks until something is received
				sock.receive(received);
							
				int r = readInteger(buffer);
				if( r == -1 ) {
					// stop
					System.out.println("terminating packet received");
					read = false;
				}
				else
				if( r > (last+1)) {
					System.out.println("missing packet (received=" + r + ", last=" + last);
				}
				else 
				if( r <= last ) {
					System.out.println("out of order packet (received=" + r + ", last=" + last);
				}
				last = r;
				
				//echo the number back
				InetAddress client = received.getAddress();
				int port = received.getPort();
				DatagramPacket send = new DatagramPacket(buffer, buffer.length, client, port);
				sock.send(send);
			}
			catch(Exception e) {
				read = false;
			}
		}
	}
	
	public static void main(String[] args) throws SocketException, UnknownHostException {
		int port = args.length > 0 ? Integer.parseInt(args[0]) : 33075;
		
		DatagramServer server = new DatagramServer(port);
		new Thread(server).start();
	}
	
	private static int readInteger(byte[] data) throws IOException {
		try(InputStream in = new ByteArrayInputStream(data);
			DataInputStream din = new DataInputStream(in)) {
			return din.readInt();
		}
	}
}
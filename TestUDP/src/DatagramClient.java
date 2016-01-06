import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class DatagramClient implements Runnable {
	private DatagramSocket sock;
	private InetAddress serverAddress;
	private int port;
	private int limit;
	
	public DatagramClient(String hostname, int port, int limit) throws SocketException, UnknownHostException {
		sock = new DatagramSocket();
		serverAddress = InetAddress.getByName(hostname);
		this.port = port;
		this.limit = limit;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[256];
		for(int i=1; i<=limit; i++) {
			try {
				//write the response
				sendInteger(i, buffer);
				
				DatagramPacket receive = new DatagramPacket(buffer, buffer.length);
				sock.receive(receive);
				
				int r = readInteger(buffer);
				
				if(r != i) {
					System.out.println("received out of order response (sent=" + i + ", received=" + r + ")");
				}				
			} 
			catch (IOException e) {
				
			}
		}
		System.out.println("client done, sending terminate packet");
		try {
			sendInteger(-1, buffer);
		} 
		catch (IOException e) {
			
		}
	}
	
	public static void main(String[] args) throws SocketException, UnknownHostException {
		String host = args.length > 0 ? args[0] : "localhost";
		int port = args.length > 1 ? Integer.parseInt(args[1]) : 33075;
		
		DatagramClient client = new DatagramClient(host, port, 10000);
		new Thread(client).start();
	}
	
	private void sendInteger(int value, byte[] buffer) throws IOException {
		try(ByteArrayOutputStream bout = new ByteArrayOutputStream();
				DataOutputStream dout = new DataOutputStream(bout)) {
			dout.writeInt(value);
			dout.flush();
			
			byte[] buf = bout.toByteArray();
			System.arraycopy(buf, 0, buffer, 0, buf.length);
			
			DatagramPacket send = new DatagramPacket(buffer, 
					buffer.length, serverAddress, port);
			sock.send(send);
		}
	}
	
	private static int readInteger(byte[] data) throws IOException {
		try(InputStream in = new ByteArrayInputStream(data);
			DataInputStream din = new DataInputStream(in)) {
			return din.readInt();
		}
	}
	
}
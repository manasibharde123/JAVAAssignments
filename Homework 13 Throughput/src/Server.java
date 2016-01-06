/**
 * 
 * @author Manasi and Praneet
 *	
 *	When 33*100 Bytes of data is sent and received simultaneously across TCP, It took 18 ms.
 *	Whereas same data when sent using UDP, it took 8 ms. 
 *  Network Throughput of TCP = 33000/18 = 1833.33 bytes/ms
 *  Network Throughput of UDP = 33000/8 = 4125 bytes/ms
 *  Hence it is proved that additional handshaking that takes place in TCP leads to lower network throughput
 *  
 */

public class Server {

	public static void main(String[] args) {
		Server s;
		if(args[0].equals("TCP"))
			s = new TCPServer();
		else
			s = new UDPServer();
		s.start();
	}

	public void start(){
		
	}
}

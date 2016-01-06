
public class Client {

	public static void main(String[] args) {
		Client c;
		if(args[0].equals("TCP"))
			c = new TCPClient();
		else
			c = new UDPClient();
		c.connect();		
	}
	
	public void connect(){
		
	}
}

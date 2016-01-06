import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProxyCalculator implements Calculator {
	
	private final String host;
	private final int port;
	
	public ProxyCalculator(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public double add(double op1, double op2) {
		return sendCommand(1, op1, op2);
	}

	@Override
	public double subtract(double op1, double op2) {
		return sendCommand(2, op1, op2);
	}

	@Override
	public double divide(double op1, double op2) {
		return sendCommand(3, op1, op2);
	}

	@Override
	public double multiply(double op1, double op2) {
		return sendCommand(4, op1, op2);
	}
	
	private double sendCommand(int command, double op1, double op2) {
		double result;
		try( Socket sock = new Socket(host, port);
			InputStream in = sock.getInputStream();
			DataInputStream din = new DataInputStream(in);
			OutputStream out = sock.getOutputStream();
			DataOutputStream dout = new DataOutputStream(out)) {
			
			dout.writeInt(command);
			dout.writeDouble(op1);
			dout.writeDouble(op2);
			
			result = din.readDouble();
			
		}
		catch(IOException e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}
}
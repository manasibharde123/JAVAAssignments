import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class CalculatorServer implements Runnable {

	private final ServerSocket server;
	private final Calculator calculator;
	
	public CalculatorServer(Calculator calculator, int port) throws IOException {
		this.calculator = calculator;
		server = new ServerSocket(port);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				System.out.println("cs: waiting for client connections...");
				Socket sock = server.accept();
				System.out.println("  cs: connection established!");
				CalculatorClientThread client = new CalculatorClientThread(calculator, sock);
				new Thread(client).start();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		int port = args.length > 0 ? Integer.parseInt(args[0]) : 33075;
		Calculator calculator = new RealCalculator();
		CalculatorServer server = new CalculatorServer(calculator, port);
		new Thread(server).start();
	}
	
	private static class CalculatorClientThread implements Runnable {
		private final Calculator calculator;
		private final Socket sock;
		
		public CalculatorClientThread(Calculator calculator, Socket sock) {
			this.calculator = calculator;
			this.sock = sock;
		}
		
		@Override
		public void run() {
			System.out.println("cct: handling single request...");
			try (InputStream in = sock.getInputStream();
				DataInputStream din = new DataInputStream(in);
				OutputStream out = sock.getOutputStream();
				DataOutputStream dout = new DataOutputStream(out)) {
				int command = din.readInt();
				double op1 = din.readDouble();
				double op2 = din.readDouble();
				double result;
				System.out.println("  cct: command: " + command + ",op1: " + op1 + ",op2: " + op2);
				switch(command) {
				case 1:
					result = calculator.add(op1, op2);
					break;
				case 2:
					result = calculator.subtract(op1, op2);
					break;
				case 3:
					result = calculator.divide(op1, op2);
					break;
				case 4:
					result = calculator.multiply(op1, op2);
					break;
				default:
					result = 0;
					break;
				}
				System.out.println("  cct: calculated result: " + result);
				dout.writeDouble(result);
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			finally {
				try {
					sock.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
}
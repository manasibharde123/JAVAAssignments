public class Application {

	public static void main(String[] args) {
		String connectionType;
		GameSocket server;
		if(args.length == 0){
			System.out.println("No protocol type provided");
			return;
		}
		else
			connectionType = args[0];
		
		if(connectionType.equals("TCP"))
			server = new GameTCPSocket();
		else
			server = new GameUDPSocket();

		server.startServer();
		server.startGame();
	}
}

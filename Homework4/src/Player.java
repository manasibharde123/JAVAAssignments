import java.util.Scanner;

public class Player implements PlayerInterface{
	private char playerPiece;
	private String name;
	Connect4Field aConnect4Field;

	public Player(Connect4Field aConnect4Field, String string, char c) {
		this.aConnect4Field = aConnect4Field;
		name = string;
		playerPiece = c;
	}

	@Override
	public char getGamePiece() {
		// TODO Auto-generated method stub
		return playerPiece;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int nextMove() {
		if(aConnect4Field.checkIfPieceCanBeDroppedIn(aConnect4Field.lastMoveColumn))
			aConnect4Field.dropPieces(aConnect4Field.lastMoveColumn,playerPiece);
		else if(aConnect4Field.checkIfPieceCanBeDroppedIn(aConnect4Field.lastMoveColumn-1))
			aConnect4Field.dropPieces(aConnect4Field.lastMoveColumn-1,playerPiece);
		else 
			aConnect4Field.dropPieces(aConnect4Field.lastMoveColumn+1,playerPiece);
		Scanner s = new Scanner(System.in);
		System.out.println("Enter column number for next move");
		return Integer.parseInt(s.nextLine());
	}
}

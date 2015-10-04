import java.util.Scanner;

public class TestConnect4Field {

public Connect4Field aConnect4Field = new Connect4Field();
public Player aPlayer = new Player(aConnect4Field, "A", '+');
public Player bPlayer = new Player(aConnect4Field, "B", '*');

public void dropTest( int column ) {
	System.out.println("Can it be dropped in " +
			   column + ": " 	   +
			   aConnect4Field.checkIfPieceCanBeDroppedIn(column));
}

public void testIt() {
	aConnect4Field = new Connect4Field();
	System.out.println(aConnect4Field);
	dropTest(-1);
	dropTest(0);
	dropTest(1);
	Scanner s = new Scanner(System.in);
	while(true){
	int row = Integer.parseInt(s.nextLine());
	aConnect4Field.dropPieces(row, '+');
	System.out.println(aConnect4Field);
	if(aConnect4Field.didLastMoveWin())
		break;
	row = Integer.parseInt(s.nextLine());
	aConnect4Field.dropPieces(row, '*');
	System.out.println(aConnect4Field);
	if(aConnect4Field.didLastMoveWin())
		break;
	if(aConnect4Field.isItaDraw())
		break;
	}
	aConnect4Field.init(aPlayer, bPlayer);
}

public static void main( String[] args ) {
	new TestConnect4Field().testIt();
	}
}
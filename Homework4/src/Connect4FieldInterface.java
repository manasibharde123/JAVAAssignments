public interface Connect4FieldInterface {
	public boolean checkIfPieceCanBeDroppedIn(int column);
	public void dropPieces(int column, char gamePiece);
	boolean didLastMoveWin();
	public boolean isItaDraw();
	public void init( Player playerA, Player playerB );
	public String toString();
	public void playTheGame();
}
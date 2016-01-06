

public interface Connect4ModelInterface {
	public String[] getPattern();
	public String toString();
	public boolean checkIfPieceCanBeDroppedIn(int column);
	public boolean verifyColumn(int column);
	public void dropPieces(int i, char c);
	public boolean didLastMoveWin();
	public boolean isItaDraw();

}

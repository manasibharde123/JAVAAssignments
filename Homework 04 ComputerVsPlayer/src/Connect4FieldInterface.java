public interface Connect4FieldInterface {
	/**
	 * 
	 * @param column pass the column number which is next move
	 * @return returns true if piece can be dropped in passed column, false otherwise 
	 */
	public boolean checkIfPieceCanBeDroppedIn(int column);
	/**
	 * 
	 * @param column updates passed column in board 
	 * @param gamePiece with passed gamePiece
	 */
	public void dropPieces(int column, char gamePiece);
	
	/**
	 * Decides if due to last move last player has won the game
	 * @return and returns boolean result
	 */
	boolean didLastMoveWin();
	
	/**
	 * Decides if last move made board full
	 * @return boolean result if board is full or not
	 */
	public boolean isItaDraw();
	
	/**
	 * Initializes Connect4Field object with player objects
	 * Depending upon player mode user selects
	 * If mode is '1' Its both human player
	 * If mode is '2' player1 is human and player2 is computer
	 * @param playerA  player1
	 * @param playerB  player2
	 */
	public void init( Player playerA, Player playerB );
	
	/**
	 * 
	 * @return prints board
	 */
	public String toString();
	
	/**
	 * Starts the game
	 */
	public void playTheGame();
}
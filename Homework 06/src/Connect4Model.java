/**
		 * Model class which gets input data from controller and executes logic to update game board
		 * 
 6       *
 7       * @author1    Manasi Sunil Bharde
 8       * @author2    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */


public class Connect4Model implements Connect4ModelInterface{
	
	private String[] pattern={"OOOOOOOOOOOOOOOOOOOOOOOOO"," OOOOOOOOOOOOOOOOOOOOOOO ","  OOOOOOOOOOOOOOOOOOOOO  ","   OOOOOOOOOOOOOOOOOOO   ","    OOOOOOOOOOOOOOOOO    ","     OOOOOOOOOOOOOOO     ","      OOOOOOOOOOOOO      ","       OOOOOOOOOOO       ","        OOOOOOOOO        "};
	private int lastMoveRow, lastMoveColumn, moveNumber=0;
	private char lastMoveSymbol;
	
	public void resetPattern(){
		pattern=new String[]{"OOOOOOOOOOOOOOOOOOOOOOOOO"," OOOOOOOOOOOOOOOOOOOOOOO ","  OOOOOOOOOOOOOOOOOOOOO  ","   OOOOOOOOOOOOOOOOOOO   ","    OOOOOOOOOOOOOOOOO    ","     OOOOOOOOOOOOOOO     ","      OOOOOOOOOOOOO      ","       OOOOOOOOOOO       ","        OOOOOOOOO        "};
	}
	
	/**
	 * @return current board
	 */
	public String[] getPattern(){
		return pattern;
	}

	/**
	 * @return last player's game symbol
	 */
	public char getLastSymbol(){
		return lastMoveSymbol;
	}
	/**
	 * returns pattern
	 */
	public String toString(){
		String displayPattern = "";
		for(int i=0;i<9;i++)
			displayPattern = displayPattern+"\n"+pattern[i];
		return displayPattern;
	}
	
	/**
	 * @param column piece to drop in
	 * @return if column is not full
	 */
	public boolean checkIfPieceCanBeDroppedIn(int column) {
		//If column number entered by user is greater than board columns or less than zero, fail dropTest
		for(int i1 = 8; i1>=0;i1--)
			if(pattern[i1].charAt(column) == 'O'){
				lastMoveRow = i1;
				lastMoveColumn = column;
				return true;
			}
		return false;			
	}
	
	/**
	 * @param column Column number entered by user
	 * @return if column number is valid for game
	 */
	public boolean verifyColumn(int column){
		if(column<0||column>24)
			return false;
		else 
			return true;
	}
	
	/* Updates pattern as per column number
	 * @param i column number
	 * @param c Player piece
	 */
	public void dropPieces(int i, char c){
		if(checkIfPieceCanBeDroppedIn(i))		
		{
			pattern[lastMoveRow] = pattern[lastMoveRow].substring(0, i)+c+pattern[lastMoveRow].substring(i+1, 25);
			lastMoveSymbol = c;
			moveNumber++;
		}
	}
	
	/**
	 * Verifies if last player won due to last move
	 * @return if player won or not
	 */
	public boolean didLastMoveWin(){
		int score=0;
		
		//Verify if last player formed connectFour in row
		if(pattern[lastMoveRow].contains(""+lastMoveSymbol+lastMoveSymbol+lastMoveSymbol+lastMoveSymbol))
			return true;
		//Verify if last player formed connectFour in column
		score=0;
		for(int i = lastMoveRow - 3; (i<=(lastMoveRow+3))&&(i<9); i++){			
			if(i<0)
				continue;
			score = pattern[i].charAt(lastMoveColumn)==lastMoveSymbol?score+1:0;
			if(score==4)
				return true;
		}
		//Verify if last player formed connectFour diagonally left to right \
		score=0;
		for(int i = lastMoveRow-3, j = lastMoveColumn - 3;(i<=lastMoveRow+3)&&(j<=lastMoveColumn+3)&&(i<9)&&(j<25);i++,j++){
			if((i<0)||(j<0))
				continue;
			score = pattern[i].charAt(j)==lastMoveSymbol?score+1:0;
			if(score==4)
				return true;
		}
		//Verify if last player formed connectFour diagonally right to left /
		score=0;
		for(int i = lastMoveRow-3, j = lastMoveColumn+3;(i<=lastMoveRow+3)&&(j>=lastMoveColumn-3)&&(i<9)&&(j>0);i++,j--){
			if((i<0)||(j>24))
				continue;
			score = pattern[i].charAt(j)==lastMoveSymbol?score+1:0;			
			if(score==4)
				return true;
		}	
		return false;
	}

	/**
	 * Verifies if game is drawn due to last move
	 * @return if game is draw
	 */
	public boolean isItaDraw(){
		for(int i=0;i<9;i++){
			if(pattern[i].contains("O"));
			return false;
		}
		return true;		  
	}
}

/**
		 * Player Model class which gives next move of player to controller 
 6       *
 7       * @author1    Manasi Sunil Bharde
 8       * @author2    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */


import java.util.Scanner;

public class PlayerModel implements PlayerInterface{
	private int bestMove ;
	private int bestMoveScore;
	private char playerPiece;
	private int playMode;
	Connect4Model aConnect4Field;
	Connect4View aConnect4View = new Connect4View();
	private String name;
	
	public PlayerModel(Connect4Model aConnect4Field, String string, char c) {
		playerPiece = c;
		name = string;
		this.aConnect4Field = aConnect4Field;
	}
	
	public char getGamePiece() {
		return playerPiece;
	}

	/**
	 * @return returns name of player
	 */
	public String getName() {
		return name;
	}
	
	public void setPlayMode(int mode){
		playMode = mode;
	}
	
	
	public int nextMove(){
		if(playMode == 1)
			getUserMove();
		else
			bestMove();
		return bestMove;		
	}

	
	private void getUserMove(){		
		Scanner s = new Scanner(System.in);
		boolean validMove = false;
		while(!validMove){
			aConnect4View.askMove();
		while(verifyMove(s))
			aConnect4View.displayWarning();
		if(aConnect4Field.verifyColumn(bestMove))
			if(aConnect4Field.checkIfPieceCanBeDroppedIn(bestMove))
				validMove = true;
			else 
				aConnect4View.displayColumnWarning();
		else
			aConnect4View.wrongColumnError();
	}
	}
	/**
	 * 
	 * @param s
	 * @return
	 */
	private boolean verifyMove(Scanner s){
		String line;
		if((line=s.nextLine()).isEmpty()||!(line.substring(0,line.length()).matches("[0-9]+")))
			return true;
		else{
			bestMove = Integer.parseInt(line);
			return false;
		}				
	}
	
	/**
	 * Method scans rows and columns to check best next move of opponent 
	 * which in-turn is best move for computer. 
	 * @return returns best move column for computer
	 */
	private int bestMove(){
		String patternCopy[] = aConnect4Field.getPattern();
		String ifMoved;
		bestMove = 12;
		bestMoveScore= 0;
		// Scans horizontally
		for(int i = 8; i >= 0;i--){
			for(int j=i; j<25-i;j++){
				if(i<8)
					if(patternCopy[i+1].charAt(j)=='O')
						continue;
				if(patternCopy[i].charAt(j) == 'O'){
					if(j<22){
					ifMoved = patternCopy[i].substring(j+1, j+4);
					getBestScore(ifMoved,j);}
					if(j>=3){
					ifMoved = patternCopy[i].substring(j-3,j);
					getBestScore3(ifMoved,j);}					
					if(bestMoveScore==4){
						bestMove = j;
						return j;
						}
				}
				}			
			}
		// Scans vertically
		for(int j=0; j<19;j++)
		{for(int i = 5; i>=0 ;i--){
			if(patternCopy[i].charAt(j+i) == 'O'){
				ifMoved= (""+patternCopy[i+3].charAt(j+i)+patternCopy[i+2].charAt(j+i)+patternCopy[i+1].charAt(j+i));
				if(ifMoved.charAt(2)==playerPiece||ifMoved.charAt(1)==playerPiece)
				continue;
				getBestScore(ifMoved,j+i);
				if(bestMoveScore==4){
					bestMove = j+i;
					return j+i;	
				}
			}
		}
		}
		//Scans \
		for(int i = 8; i >= 0;i--){
			for(int j=i; j<25-i;j++){
				if((patternCopy[i].charAt(j) == 'O')){
					if(i<8)
						if(patternCopy[i+1].charAt(j) == 'O')
							continue;
					if(i>=3){
				ifMoved=""+patternCopy[i-3].charAt(j-3)+patternCopy[i-2].charAt(j-2)+patternCopy[i-1].charAt(j-1);
				getBestScore3(ifMoved,j);}
					if(i<=5&&j<22){
				ifMoved=""+patternCopy[i+1].charAt(j+1)+patternCopy[i+2].charAt(j+2)+patternCopy[i+3].charAt(j+3);
				getBestScore(ifMoved,j);}
				}
			}}
		//Scans /
		for(int i = 8; i >= 0;i--){
			for(int j=i; j<25-i;j++){
				if(patternCopy[i].charAt(j) == 'O'){
					if(i<8)
						if(patternCopy[i+1].charAt(j) == 'O')
							continue;
					if(i<=5&&j>=3){
				ifMoved=""+patternCopy[i+3].charAt(j-3)+patternCopy[i+2].charAt(j-2)+patternCopy[i+1].charAt(j-1);
				getBestScore3(ifMoved,j);}
					if(i>=3&&j<22){
				ifMoved=""+patternCopy[i-1].charAt(j+1)+patternCopy[i-2].charAt(j+2)+patternCopy[i-3].charAt(j+3);
				getBestScore(ifMoved,j);}
				}
			}}	
		return bestMove;		
	}
	/**
	 * Scans 3 positions to the right in row or down in column of current position 
	 * @param ifMoved  String to scan  
	 * @param column updates bestMove as column
	 */
	private void getBestScore(String ifMoved, int  column){
		char opponentSymbol = aConnect4Field.getLastSymbol();
		String desiredString1 = ""+opponentSymbol+opponentSymbol+opponentSymbol,desiredString2 = ""+opponentSymbol+opponentSymbol;
		if(ifMoved.equals(desiredString1)){
			bestMove = column;
			bestMoveScore = 4;
			}
		else if(ifMoved.substring(0,2).equals(desiredString2)){
			if(bestMoveScore<3){
			bestMove = column;
			bestMoveScore = 3;}
		}
		else if(ifMoved.charAt(0) == opponentSymbol){
				if(bestMoveScore<2){
					bestMove = column;
					bestMoveScore = 2;}}
	}
	
	/**
	 *  Scans 3 positions to the left in row from current position 
	 * @param ifMoved  String to scan 	
	 * @param column   updates bestMove as column
	 */
	private void getBestScore3(String ifMoved, int  column){
		char opponentSymbol = aConnect4Field.getLastSymbol();
		String desiredString1 = ""+opponentSymbol+opponentSymbol+opponentSymbol,desiredString2 = ""+opponentSymbol+opponentSymbol;

		if(ifMoved.equals(desiredString1)){
			bestMove = column;
			bestMoveScore = 4;
			}
		else if(ifMoved.charAt(2)==opponentSymbol){
			if(bestMoveScore<2){
				bestMove = column;
				bestMoveScore = 2;}
		}
		else if(ifMoved.substring(1,3).equals(desiredString2)){
			if(bestMoveScore<3){
				bestMove = column;
				bestMoveScore = 3;}
		}
	}
	/**
	 * @return returns player's symbol
	 */
	
}

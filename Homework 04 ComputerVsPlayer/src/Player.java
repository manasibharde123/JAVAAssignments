import java.util.Scanner;
	
public class Player implements PlayerInterface{

	public char playerPiece;
	public String name;
	public Connect4Field aConnect4Field;
	public int playMode;
	int bestMoveScore;
	int bestMove = 12;
	
	/**
	 * 
	 * @param aConnect4Field Object of Connect 4 Field which is shared between 2 players.
	 * @param string 		 Name of player
	 * @param c 			 Game piece of player
	 */
	public Player(Connect4Field aConnect4Field, String string, char c) {
		playerPiece = c;
		name = string;
		this.aConnect4Field = aConnect4Field;
	}

	/**
	 * Asks user for next move
	 * or
	 * Decides move of computer
	 */
	@Override
	public int nextMove(){
		int columnNumber;
		// Asks user for next move and validates input
		if(playMode == 1){
			Scanner s = new Scanner(System.in);
				while(true){
				System.out.println("Enter column number for next move");
				String line;
				while((line=s.nextLine()).isEmpty()||!(line.substring(0,line.length()).matches("[0-9]+")))
					System.out.println("Don't enter empty characters or invalid characters!");
				columnNumber = Integer.parseInt(line);
				System.out.println(columnNumber);
				if(aConnect4Field.checkIfPieceCanBeDroppedIn(columnNumber))
					return columnNumber;
				}
		}
		/* Decides computer's next move. If there is no best move depending on player's last move
		   Randomly plays a move about players last move
		   If there is no place about player's move drops in largest column: 12*/
		else{
			bestMove();
			if(bestMoveScore==0){				
			int i = (int) (3*Math.random());
			switch(i){
			case 0:{
			if(aConnect4Field.checkIfPieceCanBeDroppedIn(aConnect4Field.lastMoveColumn))
				return aConnect4Field.lastMoveColumn;}
			case 1:{ 
			if(aConnect4Field.checkIfPieceCanBeDroppedIn(aConnect4Field.lastMoveColumn-1))
				return aConnect4Field.lastMoveColumn;}
			case 2:{ 
				if(aConnect4Field.checkIfPieceCanBeDroppedIn(aConnect4Field.lastMoveColumn+1))
				return aConnect4Field.lastMoveColumn;}
			}
			}
		}			
		return bestMove;
	}
	
	/**
	 * Method scans rows and columns to check best next move of opponent 
	 * which in-turn is best move for computer. 
	 * @return returns best move column for computer
	 */
	public int bestMove(){
		String patternCopy[] = aConnect4Field.pattern;
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
					if(bestMoveScore==4)
						return j;
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
				if(bestMoveScore==4)
					return j+i;	}
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
	void getBestScore(String ifMoved, int  column){
		char opponentSymbol = aConnect4Field.lastMoveSymbol;
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
	public void getBestScore3(String ifMoved, int  column){
		char opponentSymbol = aConnect4Field.lastMoveSymbol;
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
	@Override
	public char getGamePiece() {
		// TODO Auto-generated method stub
		return playerPiece;
	}
	/**
	 * @return returns name of player
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
}
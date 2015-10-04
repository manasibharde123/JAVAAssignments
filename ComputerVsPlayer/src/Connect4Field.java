import java.util.Scanner;

public class Connect4Field implements Connect4FieldInterface{
	
	String[] pattern={"OOOOOOOOOOOOOOOOOOOOOOOOO"," OOOOOOOOOOOOOOOOOOOOOOO ","  OOOOOOOOOOOOOOOOOOOOO  ","   OOOOOOOOOOOOOOOOOOO   ","    OOOOOOOOOOOOOOOOO    ","     OOOOOOOOOOOOOOO     ","      OOOOOOOOOOOOO      ","       OOOOOOOOOOO       ","        OOOOOOOOO        "};
	int lastMoveRow, lastMoveColumn, moveNumber=0;
	char lastMoveSymbol;
	Player[] thePlayers = new Player[2];
	
	/*
	 * Resets the game board
	 */
	public void resetPattern(){
		pattern=new String[]{"OOOOOOOOOOOOOOOOOOOOOOOOO"," OOOOOOOOOOOOOOOOOOOOOOO ","  OOOOOOOOOOOOOOOOOOOOO  ","   OOOOOOOOOOOOOOOOOOO   ","    OOOOOOOOOOOOOOOOO    ","     OOOOOOOOOOOOOOO     ","      OOOOOOOOOOOOO      ","       OOOOOOOOOOO       ","        OOOOOOOOO        "};
	}
	
	public void init(Player aPlayer, Player bPlayer){
		int playMode;
		aPlayer.aConnect4Field = this;
		bPlayer.aConnect4Field = this;
		System.out.println("Choose Mode:\n1. Two player 2. Single player Vs Computer");
		Scanner s = new Scanner(System.in);
		playMode = Integer.parseInt(s.nextLine());
		if(playMode==1){
		aPlayer.playMode = playMode;
		bPlayer.playMode = playMode;
		}
		else{
		aPlayer.playMode = 1;
		bPlayer.playMode = 2;
		}	
			thePlayers[0]= aPlayer;
			thePlayers[1] =bPlayer;
		resetPattern();
		playTheGame();
	}
	

	public void dropPieces(int i, char c){
		if(checkIfPieceCanBeDroppedIn(i))		
		{
			pattern[lastMoveRow] = pattern[lastMoveRow].substring(0, i)+c+pattern[lastMoveRow].substring(i+1, 25);
			lastMoveSymbol = c;
			moveNumber++;
		}
	}

	public boolean didLastMoveWin(){
		int score=0;
		
		//Verify if last player formed connectFour in row
		if(pattern[lastMoveRow].contains(""+lastMoveSymbol+lastMoveSymbol+lastMoveSymbol+lastMoveSymbol))
			return true;
		//Verify if last player formed connectFour in column
		for(int i = lastMoveRow - 3; (i<=(lastMoveRow+3))&&(i<9); i++){
			score=0;
			if(i<0)
				continue;
			if(pattern[i].charAt(lastMoveColumn)==lastMoveSymbol)
				score++;
			else
				score=0;
			if(score==4)
				return true;
		}
		//Verify if last player formed connectFour diagonally left to right \
		for(int i = lastMoveRow-3, j = lastMoveColumn - 3;(i<=lastMoveRow+3)&&(j<=lastMoveColumn+3)&&(i<9)&&(j<25);i++,j++){
			score=0;
			if((i<0)||(j<0))
				continue;
			if(pattern[i].charAt(j)==lastMoveSymbol)
				score++;
			else
				score=0;
			if(score==4)
				return true;
		}
		//Verify if last player formed connectFour diagonally right to left /
		for(int i = lastMoveRow-3, j = lastMoveColumn+3;(i<=lastMoveRow+3)&&(j>=lastMoveColumn-3)&&(i<9)&&(j>0);i++,j--){
			score=0;
			if((i<0)||(j>24))
				continue;
			if(pattern[i].charAt(j)==lastMoveSymbol)
				score++;
			else
				score=0;
			if(score==4)
				return true;
		}			
		return false;
	}

	public boolean isItaDraw(){
		for(int i=0;i<9;i++){
			if(pattern[i].contains("O"));
			return false;
		}
		return true;		  
	}

	public String toString(){
		String displayPattern = "";
		for(int i=0;i<9;i++)
			displayPattern = displayPattern+"\n"+pattern[i];
		return displayPattern;
	}

	@Override
	public boolean checkIfPieceCanBeDroppedIn(int column){
		//If column number entered by user is greater than board columns or less than zero fail dropTest
		if(column<0||column>24){
			System.out.println("Wrong Column number!");
			return false;
		}
		for(int i1 = 8; i1>=0;i1--){
			if(pattern[i1].charAt(column) == 'O'){
				lastMoveRow = i1;
				lastMoveColumn = column;
				return true;
			}				
		}
		System.out.println("Column is Full!");
		return false; 
	}
	
	@Override
	 public void playTheGame(){
        System.out.println(this);
        boolean gameIsOver = false;
        do{
            for ( int index = 0; index < 2&&!gameIsOver; index ++ ){
                    if ( isItaDraw() )      {
                        System.out.println("Draw");
                        gameIsOver = true;
                    }else                        	
                        dropPieces(thePlayers[index].nextMove(), thePlayers[index].getGamePiece());
                     if(didLastMoveWin()){
                        gameIsOver = true;
                        System.out.println("The winner is: " + thePlayers[index].getName());}
                     	System.out.println(this);
                    }       
        }while(!gameIsOver);
}
}
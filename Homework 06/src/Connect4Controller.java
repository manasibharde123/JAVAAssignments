/**
		 * Controller class which acts between view and model class to execute logic and display 
		 * output of game
 6       *
 7       * @author1    Manasi Sunil Bharde
 8       * @author2    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */


import java.util.Scanner;

public class Connect4Controller {
	Connect4Model aConnect4Model = new Connect4Model(); 
	Connect4View connect4View = new Connect4View(); 
	PlayerModel[] thePlayers = new PlayerModel[2];
	
	/**
	 * Initializes players as per play mode
	 * @param aPlayer Player 1
	 * @param bPlayer Player 2
	 */
	public void init(PlayerModel aPlayer, PlayerModel bPlayer){
		int playMode;
		aPlayer.aConnect4Field = aConnect4Model;
		bPlayer.aConnect4Field = aConnect4Model;
		connect4View.menu();
		playMode = askPlayMode();
		if(playMode==1){
		aPlayer.setPlayMode(playMode); 
		bPlayer.setPlayMode(playMode); 
		}
		else{
		aPlayer.setPlayMode(1);
		bPlayer.setPlayMode(2);
		}	
			thePlayers[0] = aPlayer;
			thePlayers[1] = bPlayer;
	}
	
	/**
	 * gets Play mode from user
	 * @return playmode
	 */
	public int askPlayMode(){
		Scanner s = new Scanner(System.in);
		return Integer.parseInt(s.nextLine());	
	}
	
	/**
	 * Starts and continues the game until
	 * Game is drawn 
	 * Game is won by one of the participants
	 */
	public void playTheGame(){ 
		connect4View.displayPattern(aConnect4Model);
		boolean gameIsOver = false;
        do{
        	for ( int index = 0; index < 2&&!gameIsOver; index ++ ){
                    if ( aConnect4Model.isItaDraw() )      {
                    	connect4View.gameDrawMessage();
                        gameIsOver = true;
                    }else                        	
                    	aConnect4Model.dropPieces(thePlayers[index].nextMove(), thePlayers[index].getGamePiece());
                    	connect4View.displayPattern(aConnect4Model);
                     if(aConnect4Model.didLastMoveWin()){
                        gameIsOver = true;
                        connect4View.gameOverMessage(thePlayers[index].getName());
                     	connect4View.displayPattern(aConnect4Model);
                    }       }
            }while(!gameIsOver);
    }
	
	public static void main(String[] args){		
		Connect4Controller connect4Controller = new Connect4Controller();
		PlayerModel aPlayer = new PlayerModel(connect4Controller.aConnect4Model,"A",'+');
		PlayerModel bPlayer = new PlayerModel(connect4Controller.aConnect4Model,"B",'*');
		connect4Controller.init(aPlayer, bPlayer);
		connect4Controller.playTheGame();
	}
}

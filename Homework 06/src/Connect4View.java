/**
		 * View class which handles all the output messages during the game
 6       *
 7       * @author1    Manasi Sunil Bharde
 8       * @author2    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */


public class Connect4View {
	
	public void menu(){
		System.out.println("Choose Mode:\n1. Two player 2. Single player Vs Computer");
	}

	public void displayColumnWarning(){
		System.out.println("Column you selected is full!");
	}
	
	public void displayPattern(Connect4Model aConnect4Model){
		System.out.println(aConnect4Model);
	}
	
	public void askMove(){
		System.out.println("Enter column number for next move");
	}
	
	public void displayWarning(){
		System.out.println("Don't enter empty characters or invalid characters!");
	}
	
	public void wrongColumnError(){
		System.out.println("Wrong Column number!");
	}
	
	public void gameDrawMessage(){
		System.out.println("Game is draw!");
	}
	
	public void gameOverMessage(String winner){
		 System.out.println("The winner is: " + winner);
	}
}

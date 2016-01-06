/**
 2       * This is HangMan game. Use command line arguments to pass player names
 3       * You can have any number of correct guesses 
 4       * and 8 wrong guesses at most to guess a correct word.
 5       * @version   1
 6       *
 7       * @author1    Manasi Sunil Bharde
 8       * @author2    Sri Praneeth Iyyapu
 9       * Revisions:
10       *      $Log$
11       */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class HangMan{
	String fileName;
    ArrayList<String> wordlist  = new ArrayList<String>();
    ArrayList<Player> currentPlayers = new ArrayList<Player>();
    private String guessWord;    
    
    /**
     * 
     * @param args			   File name, Player 1 .. Player n 
     */
    public static void main(String[] args){
    	Scanner s = new Scanner(System.in);
    	HangMan game = new HangMan();
    	game.fileName = args[0];
    	int length = args.length;
    	for(int i = 1; i < length; i++)
    	game.currentPlayers.add(new Player(args[i]));
    	while(true){
        game.chooseWord();
        for(Player player:game.currentPlayers){
        	System.out.println("Player "+player.name);        	
        	player.playHangman(game.guessWord);
        }
        System.out.println("Do you want to play one more round? y/n");
        if(!s.nextLine().equals(""+'y'))
        	break;
    	}
        game.sortPlayers();
        game.displayTopScorers();
    }
    /**
     * Displays the top n/2 scorers of game
     */
    private void displayTopScorers(){
    	int length = currentPlayers.size() /2;
    	int lowerScore;
    	System.out.println("Top scorers are");
    	if(length <4){
    	lowerScore = currentPlayers.get(length).score;
		for(int i =0; i<currentPlayers.size() && currentPlayers.get(i).score>=lowerScore  ;i++)
			System.out.println(currentPlayers.get(i).name + " "+currentPlayers.get(i).score );
		}
    	else{
    		lowerScore = currentPlayers.get(3).score;
    		for(int i =0; i<currentPlayers.size() && currentPlayers.get(i).score>=lowerScore  ;i++)
    			System.out.println(currentPlayers.get(i).name + " "+currentPlayers.get(i).score );
    	}    		    	
	}
    /**
     * Sorts players list as per their scores
     */
	private void sortPlayers() {
		for(int i = 0; i<currentPlayers.size();i++){
			for(int j=i+1; j < currentPlayers.size(); j++)
				if(currentPlayers.get(i).compareTo(currentPlayers.get(j))<0){
					Player temp = new Player("temp");
					temp = currentPlayers.get(i);
					currentPlayers.remove(i);
					currentPlayers.add(i, currentPlayers.get(j-1));	
					currentPlayers.remove(j);
					currentPlayers.add(j, temp);
				}
		}		
	}
	/**
	 * Selects random word from word file
	 */
	public void chooseWord(){
    	Scanner br;
		try {
			br = new Scanner(new File(fileName));
			String line;
	        while(br.hasNext()){
	        	line = br.nextLine();
				wordlist.add(line);
			}
	        br.close();
	        Random rand = new Random();
	        int randomWordIndex = rand.nextInt(wordlist.size());
	        guessWord = wordlist.get(randomWordIndex).toLowerCase();
	        System.out.println(guessWord);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }
}
/**
 * This class defines player behavior
 */
class Player{
	String name;
	int score=0;
	String displayString;
	
	public Player(String name){
		this.name = name;
	}
	/**
	 * @param guessWord word to be guessed
	 */
	public void playHangman(String guessWord){
		int incorrectCount = 0;
		Scanner br  = new Scanner(System.in);
		displayString = "";
		for(int i = 0; i<guessWord.length(); i++)
			displayString = displayString + "_";
		char guessLetter;
		// Displays warning if character is not entered
		while(incorrectCount<8){
			String line;
			while((line=br.nextLine()).isEmpty())
				System.out.println("Don't enter empty characters!");
			// Gets character 
			guessLetter = line.charAt(0);
			//Updates string and score if character is in word to be guessed
			if(guessWord.contains(""+guessLetter)&&(!displayString.contains(""+guessLetter))){
				updateDisplayString(guessWord,guessLetter);
				score += 10;
				System.out.println(displayString);
				if(displayString.equals(guessWord))
					break;
			}
			//If character was already entered gives warning
			else if(displayString.contains(""+guessLetter))
				System.out.println("You already entered this character!");
			//If character is wrong, draws hangman and updates score
			else{
				System.out.println(displayString);
				incorrectCount++;
				drawHangman(incorrectCount);
				score -= 5;
			}		
		}
	}
	/**
	 * 
	 * @param guessWord		word to be guessed
	 * @param guessLetter	letter in guess word
	 */
	private void updateDisplayString(String guessWord, char guessLetter) {
		for(int i = 0; i<guessWord.length(); i++)
			if(guessWord.charAt(i) == guessLetter)
				displayString = displayString.substring(0, i) + guessLetter + displayString.substring(i+1,displayString.length());
	}
	/**
	 * 
	 * @param p player
	 * @return returns difference between score
	 */
	public int compareTo(Player p){
		return score-p.score;
	}
	/**
	 * draws hangman for incorrect guesses
	 * @param incorretGuess number of incorrect guess
	 */
	public void drawHangman(int incorretGuess)
	{
		switch(incorretGuess){
		case 8:
		{
		System.out.println("            "+"__________________________");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"            |");	
		System.out.println("            |"+"            "+"            O");
		System.out.println("            |"+"            "+"           "+(char)92+"|"+"/");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"           "+"/"+" "+(char)92);
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("   "+"-------------------");
		break;
		}
		case 7:
		{
		System.out.println("            "+"__________________________");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"            |");	
		System.out.println("            |"+"            "+"            O");
		System.out.println("            |"+"            "+"           "+(char)92+"|"+"/");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"           "+"/"+" ");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("   "+"-------------------");
		break;
		}
		case 6:
		{
		System.out.println("            "+"__________________________");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"            |");	
		System.out.println("            |"+"            "+"            O");
		System.out.println("            |"+"            "+"           "+(char)92+"|"+"/");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"           "+" ");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("   "+"-------------------");
		break;
		}
		case 5:
		{
		System.out.println("            "+"__________________________");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"            |");	
		System.out.println("            |"+"            "+"            O");
		System.out.println("            |"+"            "+"           "+(char)92+"|");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"           "+" ");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("   "+"-------------------");
		break;
		}
		case 4:
		{
		System.out.println("            "+"__________________________");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"            |");	
		System.out.println("            |"+"            "+"            O");
		System.out.println("            |"+"            "+"            "+"|");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"           "+" ");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("   "+"-------------------");
		break;
		}
		case 3:
		{
		System.out.println("            "+"__________________________");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"            |");	
		System.out.println("            |"+"            "+"            O");
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("   "+"-------------------");
		break;
		}
		case 2:
		{
		System.out.println("            "+"__________________________");
		System.out.println("            |"+"            "+"            |");
		System.out.println("            |"+"            "+"            |");	
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("   "+"-------------------");
		break;
		}
		case 1:
		{
		System.out.println("");
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");	
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");
		System.out.println("            |"+"            ");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("            |");
		System.out.println("   "+"-------------------");
		break;
		}
		}
	}	

}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class StringZipOutputStream {
	
	String inputString;
	int inputLength = 0;
	OutputStream out;
	public HashMap<Character,Integer> dataTable = new HashMap<Character,Integer>();
	public static HashMap<Character,Integer> frequencyTable = new HashMap<Character,Integer>();
	public static TreeSet<Entry<Character,Integer>>  huffmanTree= new TreeSet<Entry<Character,Integer>>();
	static{
		frequencyTable.put('', 0);
		frequencyTable.put('\t', 0);
		frequencyTable.put('\n', 0);
		frequencyTable.put(' ', 0);
		frequencyTable.put('!', 0);
		frequencyTable.put('"', 0);
		frequencyTable.put('#', 0);
		frequencyTable.put('$', 0);
		frequencyTable.put('%', 0);
		frequencyTable.put('&', 0);
		frequencyTable.put('\'', 0);
		frequencyTable.put('(', 0);
		frequencyTable.put(')', 0);
		frequencyTable.put('*', 0);
		frequencyTable.put('+', 0);
		frequencyTable.put(',', 0);
		frequencyTable.put('-', 0);
		frequencyTable.put('.', 0);
		frequencyTable.put('/', 0);
		frequencyTable.put('0', 0);
		frequencyTable.put('1', 0);
		frequencyTable.put('2', 0);
		frequencyTable.put('3', 0);
		frequencyTable.put('4', 0);
		frequencyTable.put('5', 0);
		frequencyTable.put('6', 0);
		frequencyTable.put('7', 0);
		frequencyTable.put('8', 0);
		frequencyTable.put('9', 0);
		frequencyTable.put(':', 0);
		frequencyTable.put(';', 0);
		frequencyTable.put('<', 0);
		frequencyTable.put('=', 0);
		frequencyTable.put('>', 0);
		frequencyTable.put('?', 0);
		frequencyTable.put('@', 0);
		frequencyTable.put('A', 0);
		frequencyTable.put('B', 0);
		frequencyTable.put('C', 0);
		frequencyTable.put('D', 0);
		frequencyTable.put('E', 0);
		frequencyTable.put('F', 0);
		frequencyTable.put('G', 0);
		frequencyTable.put('H', 0);
		frequencyTable.put('I', 0);
		frequencyTable.put('J', 0);
		frequencyTable.put('K', 0);
		frequencyTable.put('L', 0);
		frequencyTable.put('M', 0);
		frequencyTable.put('N', 0);
		frequencyTable.put('O', 0);
		frequencyTable.put('P', 0);
		frequencyTable.put('Q', 0);
		frequencyTable.put('R', 0);
		frequencyTable.put('S', 0);
		frequencyTable.put('T', 0);
		frequencyTable.put('U', 0);
		frequencyTable.put('V', 0);
		frequencyTable.put('W', 0);
		frequencyTable.put('X', 0);
		frequencyTable.put('Y', 0);
		frequencyTable.put('Z', 0);
		frequencyTable.put('[', 0);
		frequencyTable.put('\\', 0);
		frequencyTable.put(']', 0);
		frequencyTable.put('^', 0);
		frequencyTable.put('_', 0);
		frequencyTable.put('`', 0);
		frequencyTable.put('a', 0);
		frequencyTable.put('b', 0);
		frequencyTable.put('c', 0);
		frequencyTable.put('d', 0);
		frequencyTable.put('e', 0);
		frequencyTable.put('f', 0);
		frequencyTable.put('g', 0);
		frequencyTable.put('h', 0);
		frequencyTable.put('i', 0);
		frequencyTable.put('j', 0);
		frequencyTable.put('k', 0);
		frequencyTable.put('l', 0);
		frequencyTable.put('m', 0);
		frequencyTable.put('n', 0);
		frequencyTable.put('o', 0);
		frequencyTable.put('p', 0);
		frequencyTable.put('q', 0);
		frequencyTable.put('r', 0);
		frequencyTable.put('s', 0);
		frequencyTable.put('t', 0);
		frequencyTable.put('u', 0);
		frequencyTable.put('v', 0);
		frequencyTable.put('w', 0);
		frequencyTable.put('x', 0);
		frequencyTable.put('y', 0);
		frequencyTable.put('z', 0);
		frequencyTable.put('{', 0);
		frequencyTable.put('|', 0);
		frequencyTable.put('}', 0);
		frequencyTable.put('~', 0);		
	}
	
		// Creates a new output stream with a default buffer size.
		public StringZipOutputStream(OutputStream out)	{
			this.out = out;			
		}

		public StringZipOutputStream() {
		}
		
		// Writes aStrign compressed output stream. This method will block until all data is written.
		public void write(String aString) {
			inputString = aString;
			inputLength  = aString.length();
			getFrequencies();
	//		createTree();
		}
	
		private void createTree() {
			huffmanTree.addAll(frequencyTable.entrySet());
			System.out.println(huffmanTree.descendingSet());
		}
		
		private void getFrequencies() {
			int i =0 ;
			Character entry = null;
			try{
			while(i<inputLength){
				entry = inputString.charAt(i);
				int frequency = frequencyTable.get(entry);
				frequencyTable.put(entry, ++frequency);
				i++;
			}}
			catch(NullPointerException e){				
				System.err.println(":missing char "+entry.DECIMAL_DIGIT_NUMBER);
				System.exit(1);
			}
		}
		
		private void displayTable(){
			Set<Entry<Character, Integer>> entries =  frequencyTable.entrySet();
			for(Entry<Character, Integer> entry:entries){
				if(entry.getValue()!=0 )
					System.out.println(entry.getKey()+" "+entry.getValue());
				dataTable.put(entry.getKey(), entry.getValue());
			}			
		}
		// Writes remaining data to the output stream and closes the underlying stream.
		public void close() {
		}
		
		public static void main(String[] args){
			StringZipOutputStream newOut = new StringZipOutputStream();
			newOut.write("a");
			try {
				String aWord;

				BufferedReader input = new BufferedReader(new FileReader("words.txt"));
				StringZipOutputStream aStringZipOutputStream = new StringZipOutputStream();

				while (  ( aWord = input.readLine() )  != null ) {
						System.out.println("write:	" + aWord);
						aStringZipOutputStream.write(aWord);
				}
				aStringZipOutputStream.close();
				aStringZipOutputStream.displayTable();
				aStringZipOutputStream.sortMap();

				input.close();				
			} catch ( Exception e )	{
				e.printStackTrace();
				System.exit(1);
			}
		}	
		
		public void sortMap(){
			ArrayList<Character> characterList = new ArrayList<Character>(frequencyTable.keySet());			
			ArrayList<Integer> frequencies = new ArrayList<Integer>(frequencyTable.values());	
			int i = 0;
			for(Character ch: characterList){
				System.out.println(ch + " : "+ frequencies.get(i++));
			}			
		}
}

class HuffmanNode{
	Character character;
	HuffmanNode parent, left, right;
	
	
}
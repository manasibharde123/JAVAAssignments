import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;

public class StringZipOutputStream {
	
	BitOutputStream bs; 
	Node tree;
	StringBuilder sb = new StringBuilder(); 
	static LinkedList<Node> exaustingNodes;
	static Node leafNodes[];
	static char priorityChars[] = new char[256];
	static char truncatedChars[];
	String inputString = "";
	int inputLength = 0;
	OutputStream out;
	static int dataTable[] = new int[256] ;
	static int truncatedData[];
	char charArray[];
	BufferedOutputStream os ;

	// Creates a new output stream with a default buffer size.
	public StringZipOutputStream(OutputStream out)	{
		this.out = out;			
	}

	public StringZipOutputStream() {
	}
	
	/**
	 *  Writes aStrign compressed output stream. This method will block until all data is written.
	 * @param aString
	 */
	public void write(String aString) {
		inputString = inputString+aString + "\r\n";
		inputLength  = inputString.length();
	}

	/**
	 * Decides frequencies depending on input string buffered
	 */
	private void getFrequencies() {
		charArray = inputString.toCharArray();
		inputLength  = inputString.length();
		int i =0 ;
		while(i<inputLength){
			dataTable[charArray[i++]]++;
		}
	}
	
	/**
	 * Method used for debugging
	 * Displays table
	 */
	private void displayTable(){
		for(int i=0; i<256; i++)
			System.out.println(""+(char)(i)+" "+dataTable[i]);
	}
	
	/**
	 *  Writes data to the output stream and closes the underlying stream.
	 */
	public void close() {
		getFrequencies();
		initPriorityChars();
		createAllNodeArray();
		sortArray();
		buildHuffmanTree();
		assignCodes(tree, "");
		addPrefixTable();
		createOutputBitString();
	}
	
	/**
	 * Creates string consisting of huffman codes
	 */
	private void createOutputBitString() {
		for(int i = 0; i < inputLength; i++ ){
			sb.append(leafNodes[charArray[i]].prefix);			
		}	
		bs = new BitOutputStream(os);
		bs.write(sb.toString());
	}

	private void sortArray() {
		char swapChar;
		
		int swapInt;
		for(int i = 0; i<256; i++){
			for(int j = 0; j<256; j++){
				if(dataTable[i]<=dataTable[j]){
					swapInt = dataTable[i];
					swapChar = priorityChars[i];
					dataTable[i] = dataTable[j];
					priorityChars[i] = priorityChars[j];
					dataTable[j] = swapInt;
					priorityChars[j] = swapChar;					
				}
			}
		}
	}
	
	public void initPriorityChars(){
		for(int i=0; i<256; i++)
			priorityChars[i] = (char) i;
	}
	
	private void buildHuffmanTree(){
		truncateArrays();
        createNodeArray();
        tree = null;
        while (exaustingNodes.size() > 1)  {
            Node left = exaustingNodes.poll();
            Node right = exaustingNodes.poll();
            Node newNode = new Node(left.frequency + right.frequency, ' ', false);
            newNode.right = left;
            newNode.left = right;
            left.parent = newNode;
            right.parent = newNode;
            insertToNodeArray(newNode);
            tree = newNode;
        }
	}
	private static void displayArray(){
		for(int i = 0; i<exaustingNodes.size();i++)
			System.out.print(exaustingNodes.get(i).frequency+" ");
		
		System.out.println();
	}

	private void assignCodes(Node tree, String prefix){
		if(tree.isLeaf){
			tree.prefix = prefix;
			leafNodes[tree.character].prefix = prefix;
			}
		else{		
			prefix = prefix +'0';
			assignCodes(tree.left,prefix);			
			prefix = prefix.substring(0,prefix.length()-1);
			
			prefix = prefix+'1';
			assignCodes(tree.right,prefix);
			prefix = prefix.substring(0,prefix.length()-1);
		}
	}
	
	private void truncateArrays() {
		// TODO Auto-generated method stub
		int i = 0 ;
		for( i = 0; i < 256; i++ )
			if(dataTable[i]!=0)
				break;
		truncatedData = new int[256-i];
		truncatedChars = new char[256-i];
		System.arraycopy(dataTable, i, truncatedData, 0, 256-i);
		System.arraycopy(priorityChars, i, truncatedChars, 0, 256-i);
	}

	private static void createNodeArray(){
		exaustingNodes = new LinkedList<Node>();
		int i = 0;
		while( i < truncatedData.length ){
			exaustingNodes.add(new Node(truncatedData[i],truncatedChars[i], true));
			i++;
		}
	}
	
	private static void createAllNodeArray(){
		leafNodes = new Node[256];
		for(int i = 0; i < 256; i++){
			leafNodes[i] = new Node(dataTable[i],priorityChars[i],false);
		//	System.out.println(leafNodes[i].character+ " "+leafNodes[i].frequency);
		}
	}
	
	public void displayCodes(Node tree){
		if(tree.isLeaf)
			System.out.println(tree.character+" : "+tree.prefix);
		else{
			displayCodes(tree.left);
			displayCodes(tree.right);}
		}
	
	private static void insertToNodeArray(Node newNode){
		int i;
		for(i = 0; i < exaustingNodes.size(); i++){
			if(exaustingNodes.get(i).frequency>=newNode.frequency)
				break;
		}
		exaustingNodes.add(i,newNode);		
	}

	public void addPrefixTable(){
		try {
		os = new BufferedOutputStream(out);
			StringBuilder sb = new StringBuilder();
			for(int i= 0; i<256; i++)
				sb.append(leafNodes[i].prefix+",");
			byte[] b = sb.toString().getBytes();
			os.write(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class Node{
	int frequency;
	char character;
	boolean isLeaf;
	Node left, right, parent;
	boolean code;
	String prefix = "";
	Node(){
	}
	
	Node(int frequency,char letter, boolean isLeaf){
		this.frequency = frequency;
		this.character = letter;
		this.isLeaf = isLeaf;
	}	
	
	void setPrefix(String prefix){
		this.prefix = prefix;
	}
}

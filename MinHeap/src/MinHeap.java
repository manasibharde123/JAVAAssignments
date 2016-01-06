import java.util.ArrayList;
import java.util.LinkedList;

public class MinHeap {
	
	Node tree;
	static LinkedList<Node> leafNodes;
	static int truncatedData[] = {1,2,3,4,5,6};
	static char truncatedChars[] = {'A', 'C', 'D', 'G', 't','h'};
 
	public static Node makeHuffmanTree() {      
        createNodeArray();
        Node root = null;
        while (leafNodes.size() > 1)  {
        	displayArray();
            Node node1 = leafNodes.poll();
            Node node2 = leafNodes.poll();
            Node newNode = new Node(node1.frequency + node2.frequency, ' ', false);
            newNode.right = node1;
            newNode.left = node2;
            node1.parent = newNode;
            node2.parent = newNode;
            inserToNodeArray(newNode);
            // Keep track until we actually find the root
            root = newNode;
        }
        return root;
    }
	
	private void buildHuffmanTree(){
		createNodeArray();
		tree = new Node();
		tree.left = leafNodes.get(0);
		tree.right = leafNodes.get(1);
		tree.frequency = leafNodes.get(0).frequency + leafNodes.get(1).frequency;
		int i =2;
		while(i<leafNodes.size()-1){		
		System.out.println(tree.frequency+ "  "+ leafNodes.get(i).frequency);
		getMin(tree,leafNodes.get(i++));		
		System.out.println(tree.frequency);
		displayArray();
		}
		Node newNode = new Node();
		newNode.left = tree;
		newNode.right = leafNodes.get(leafNodes.size()-1);
		newNode.frequency = tree.frequency + leafNodes.get(leafNodes.size()-1).frequency;
		tree = newNode;
		System.out.println(tree.frequency);
		
	}
	
	private void getMin(Node node1, Node node2){
		Node newNode = new Node();
		if(tree.frequency<=node2.frequency){
			newNode.left = tree;
			newNode.right = node1;
			newNode.frequency = tree.frequency + node1.frequency;
			tree = newNode;
		}
		else{
			newNode.left = node1;
			newNode.right = node2;
			newNode.frequency = node1.frequency+node2.frequency;
			inserToNodeArray(newNode);
		}
	}


	private static void createNodeArray(){
		leafNodes = new LinkedList<Node>();
		int i = 0;
		while( i < truncatedData.length ){
			leafNodes.add( new Node(truncatedData[i],truncatedChars[i], true));
			System.out.println(leafNodes.get(i).character+" "+leafNodes.get(i).frequency);
			i++;
		}
	}
	
	private static void inserToNodeArray(Node newNode){
		int i;
		for(i = 0; i < leafNodes.size(); i++){
			if(leafNodes.get(i).frequency>=newNode.frequency)
				break;
		}
		leafNodes.add(i,newNode);		
	}
	
	private static void displayArray(){
		for(int i = 0; i<leafNodes.size();i++)
			System.out.print(leafNodes.get(i).frequency+" ");
		
		System.out.println();
	}
	

	public static void main(String args[]){
		MinHeap mh = new MinHeap();
		mh.makeHuffmanTree();
	}
}

class Node{
	int frequency;
	char character;
	boolean isLeaf = false;
	Node left, right, parent;
	
	Node(){
		
	}
	
	Node(int frequency,char letter, boolean isLeaf){
		this.frequency = frequency;
		this.character = letter;
		this.isLeaf = isLeaf;
	}	
}
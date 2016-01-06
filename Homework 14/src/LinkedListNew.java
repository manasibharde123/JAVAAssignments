import java.util.Iterator;

public class LinkedListNew implements Iterable{
	
	Node front;
	int size = 0;
	Node iter;
	
	public LinkedListNew(){
		front = new Node();
		iter = front;
	}
	
	public void add(Object nodeValue){
		
		if(size == 0){
			front.setValue(nodeValue);
		}
		else{
			Node temp = front;
			while(temp.next != null)
				temp = temp.next();
			temp.next = new Node(nodeValue);
		}
		size ++;
	}
	
	
	public void remove(Object node){
		Node temp = front;
		if(temp.equals(node))
			front = front.next();
		else
			while(temp.hasNext())
				if(temp.next().equals(node)){
					temp.next = temp.next.next();
					break;
				}
	}

	@Override
	public Iterator iterator() {
		iter = front;
		
		Iterator it = new Iterator(){
			Node prev = iter;
			@Override
			public boolean hasNext(){
				return iter!=null & size!=0 ;
			}

			@Override
			public Object next(){
				prev = iter; 
				if(iter != null)
					iter = iter.next();
				return prev;
			}
			
		};
		return it;
	}
	
	public static void main(String[] args){
		LinkedListNew ls = new LinkedListNew();
		for (int i =0; i<1; i++)
			ls.add(new String(""+i));
		Iterator it = ls.iterator();
		System.out.println("");
		while(it.hasNext()){
			System.out.println(((Node)it.next()).getValue());
		}
		
		for(Object s: ls){
			System.out.println(((Node)s).getValue());
		}
	}
}


class Node{
	Object value;
	Node next;
	
	public Node(){
		
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node(Object value){
		this.value = value;
	}
	
	public boolean hasNext(){
		if(this.next!=null)
			return true;
		else 
			return false;
	}
	
	public Node next(){
		return this.next;
	}
}


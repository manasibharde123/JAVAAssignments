/**
 * 
 * @author Manasi
 *
 * @param <T>
 */
public class Queue<T>{
	
	Node<T> front, rear;
	
	public Queue(){
		front = null;
		rear = null;
	}
	
	public void add(T element){
		if(front==null){
			front = new Node<T>(element);
			rear = new Node<T>();
			front.next = rear;
			front.hasNext = true;
		}
		else if(rear.element==null){			
			rear.element = element;
			rear.hasNext = false;
		}
		else{	
			Node<T> newNode = new Node<T>(element);
			rear.hasNext = true;
			rear.next = newNode;
			rear = newNode;
		}
	}
	
	public T remove(){
		T element = (T) front.element;
		front = front.next;		
		return element;
	}
	
	public T peek(){
		return (T) rear.element;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		
		Integer array[] = new Integer[20];
		Queue<Integer> newQ = new Queue<Integer>();
		newQ.add(10);
		newQ.add(12);
		newQ.add(30);
		newQ.add(23);
		newQ.add(45);
		Node<Integer> node = newQ.front;
		
		do{
			System.out.println();
			System.out.println(node.element);
			if(node.hasNext)
				node = node.next;
			else 
				break;
			
		}while(true);
	}
}


class Node<T>{
	T element = null;
	Node<T> next;
	boolean hasNext = false;
	
	public Node(){
		
	}
	
	public Node(T element){
		this.element = element;
		this.hasNext = false;
		this.next = null;
	}
	
	public T getElement(){
		return element;
	}
	
	public boolean hasNext(){
		return hasNext;
	}
	
	public Node<T> next(){
		return next;
	}
}
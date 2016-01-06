import java.util.EmptyStackException;

public class Stack<T> {
	public Object[] array;
	public int index;
	
	Stack(){
		array = new Object[100]; 
		index = 0;
	}

	@SuppressWarnings("unchecked")
	public T peek(){
		return (T) array[index-1];
	}
	
	@SuppressWarnings("unchecked")
	public T pop(){
		if(index==0)
			throw new EmptyStackException();
		return (T) array[--index];
	}
	
	public void push(T item ){
		array[index] = item;
		index++;
	}
	
	public boolean empty(){
		if(index==0)
			return true;
		else 
			return false;
	}
}

import java.util.EmptyStackException;

class Stack<T> {
	Object[] array;
	int index;
	
	Stack(){
		array = new Object[100]; 
		index = 0;
	}

	public T peek(){
		return (T) array[index-1];
	}
	
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

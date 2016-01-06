

public class ArrayListNew<T>{

	int MAX_SIZE = 1000;
	int index;
	Object[] array;
	
	public ArrayListNew(){
		array = new Object[MAX_SIZE];
		index = 0;
	}
	
	public int size(){
		return index;
	}
	
	public void add(T item){
		array[index++] = item;
	}
	
	public T get(int index){
		return (T) array[index];
	}
}
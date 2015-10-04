
class StorageFixed<E,V> implements Storage<E,V> {
	
	E[] a = (E[]) new Object[100];
	E e ;
	V v;
	int counter = 0;
	int idx = 0;
	int main_index = 0;
	public boolean	add(E e){
		this.e = e;
		if (counter<100){
		a[counter] = e;
		++counter;
		return true;
		}
		else return false;
	}
	public void add(int index, E element){
		
		idx = index;
		e = element;
		if (idx<100){
		a[idx] = e;
		++counter;
		System.out.println("true");
		}
		else System.out.println("false");
	}
	public void	addElement(E obj){
		
		e = obj;
		if (counter<100 && idx == 0){
		a[0] = e;
		++counter;}
		else if (counter < 100 && idx > 0){
			a[idx + 1] = e;
			++counter;
		}
		
	}
	public void	addElement(E obj, V elem){
		
		e = obj;
		if (counter<100 && idx == 0){
		a[0] = e;
		++counter;}
		else if (counter < 100 && idx > 0){
			a[idx + 1] = e;
			++counter;
		}
	}
	public int	capacity(){
		return 100-counter;
	}
	public E	firstElement(){
		return a[0];
	}
	public E	get(int index){
		idx = index;
		return a[idx];
	}
	public E	lastElement(){
		return a[a.length -1];
	}
	public void	clear(){
		for (int i =0; i<100; i++){
			a[i] = null;
		}
	}
	public Object   clone()
	{
		E[] a_clone = (E[]) new Object[100];
		for (int i =0; i<100; i++){
			a_clone[i] = a[i];
			
		}
		return a_clone;
		
	}
	
	
	
	
	
	public static void main(String[] args){
		StorageFixed<String, String> aStorageString = new StorageFixed<String, String>();
		System.out.println(aStorageString.add("1"));
		aStorageString.add(1, "2");
		aStorageString.addElement("1");
		aStorageString.addElement("1", "2");
		System.out.println(aStorageString.capacity());
		System.out.println(aStorageString.firstElement());
		System.out.println(aStorageString.get(2));
		System.out.println(aStorageString.lastElement());
		aStorageString.clear();
		System.out.println(aStorageString.get(2));
		System.out.println(aStorageString.clone());
		
		
		
	}
	
	
	

	
	
	
	
	
	
	
	
}

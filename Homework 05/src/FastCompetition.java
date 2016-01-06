/**
 2       * This program implements storage class:
 3       *  To minimize space usage it dynamically creates new blocks of arrays to store data
 4       *  as size of data increases. 
 5       *  Uses Insertion sort method to give sorted data  
 6       * @version   1
 7       * @author1    Manasi Bharde 
 8       * @author2    Praneet Iyyapu
 9       * Revisions:
10       *      $Log$
11       */
public class FastCompetition<E extends Comparable <E>> implements Competition<E> {
	
	int size = 0;
	Object[] fastComp = new Object[1024];	
	int MAX_SIZE = 1024;
	int MAX_LIMIT = 1000000;
	
	public FastCompetition(){
		
	}	

	public FastCompetition(int i) {		
		MAX_LIMIT = i;
	}

	@Override
	public boolean add(E e) {
		if(getMaxSize()){
			if(MAX_SIZE>MAX_LIMIT)
				return false;
		Object[] newObj = new Object[MAX_SIZE];
		System.arraycopy(fastComp, 0, newObj, 0, size);
		fastComp = newObj;
		}	
		fastComp[size++] = e;
		return true;
	}

	private boolean getMaxSize() {
		if(size>=MAX_SIZE){
			MAX_SIZE += 1024;
			return true;
		}		
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		int i;
	
		for(i=0; i<size&&((E)fastComp[i]).compareTo((E)o)!=0;i++);
		
		if(i!=size)
			return true;
		else
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		int i;
		
		for(i=0; i<size&&((E)fastComp[i]).compareTo((E)o)!=0;i++);
		if(i!=size){
			for(int j=i+1; j<size;j++)
			fastComp[i] = fastComp[j];
			size--;
			return true;
		}
		else
			return false;
   }

	@SuppressWarnings("unchecked")
	@Override
	public E elementAt(int index) {
		// TODO Auto-generated method stub
		return (E)fastComp[index];
	}

	@SuppressWarnings("unchecked")
	@Override
	public Competition<E> sort() {
		FastCompetition<E> fc = new FastCompetition<E>();
		Object[] objArr =  new Object[size];
		objArr = fastComp;
		for(int i=1; i<size;i++){
			for(int j=i; j>0 &&((E)fastComp[j]).compareTo((E)fastComp[j-1])<0;j--){
				Object temp = objArr[j-1];
				objArr[j-1] = objArr[j];
				objArr[j] = temp;
			}
		}
		fc.fastComp = objArr;
		return fc;
	}

	@Override
	public int size() {
		return size;
	}
}
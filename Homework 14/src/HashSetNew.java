/**
 * author1: Manasi Sunil Bharde
 * author2: Praneeth Iyyapu
 * 
 * This program constructs HashSetNew. HashSetNew is backed by HashMap.
 * HashMap is array of LinkedListNew.
 * Iterator is implemented in LinkedListNew. 
 * iterator() method returns Iterator i.e. list created in entrySet method of HashMap
 */


import java.util.*;

public class HashSetNew extends HashSet {
	
	private static final Object SIMPLEPLACEHOLDER  = new Object();
	HashMapNew hashMap = new HashMapNew();

	public boolean add(Object K) {
		if(hashMap.containsKey(K))
			return false;
		else
			hashMap.put(K, SIMPLEPLACEHOLDER);
		return true;
	}

	public boolean addAll(Collection arg0) {
		return false;
	}

	public void clear() {
		hashMap.clear();
	}

	public boolean contains(Object K) {
		return hashMap.containsKey(K);
	}

	public boolean containsAll(Collection arg0) {
		return false;
	}

	public boolean isEmpty() {
		return hashMap.size() == 0;
	}

	public Iterator iterator() {
		return hashMap.entrySet().iterator();
	}

	public boolean remove(Object K) {
		if(hashMap.remove(K) == null)
			return false;
		else
			return true;
	}

	public boolean removeAll(Collection arg0) {
		return false;
	}

	public boolean retainAll(Collection arg0) {
		return false;
	}

	public int size() {
		return hashMap.size();
	}

	public Object[] toArray() {
		return null;
	}

	public Object[] toArray(Object[] a) {
		return null;
	}
	
	public static void main(String[] args){
		HashSetNew hsn = new HashSetNew();
		for(int i =0; i < 684; i++)
			hsn.add(new Object());
	}
}
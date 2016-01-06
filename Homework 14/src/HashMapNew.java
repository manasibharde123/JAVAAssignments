
public class HashMapNew{

	private static int CAPACITY = 20000;
	private int size = 0;
	LinkedListNew[] hashTable = new LinkedListNew[CAPACITY];
	
	private static final double loadFactor = 0.1;
	
	public static void main(String[] args) {
		HashMapNew hs = new HashMapNew();
		Object[] objects = new Object[4];
		for(int i=0; i<4; i++){
			objects[i] = new String(""+i);
		}
		for(int i=0; i<4; i++){
			hs.put(objects[i], objects[i]);
		}
		for(int i=0; i<4; i++){
			System.out.println(hs.containsKey(objects[i]));
		}
		
		String t = "AA";
		StringBuffer s = new StringBuffer();
	}
	
	public HashMapNew(){
		createTable(hashTable);
	}

	public void clear() {
		CAPACITY = 20000;
		hashTable = new LinkedListNew[CAPACITY];
		size = 0;
		createTable(hashTable);
	}

	private void createTable(LinkedListNew[] table){
		for(int i = 0; i < CAPACITY; i++)
			table[i] = new LinkedListNew();
	}
	
	public boolean containsKey(Object K) {
		int hash  = K.hashCode();
		int index = hash % hashTable.length;
		index = index < 0 ? -index : index;
		if(hashTable[index].size == 0)
			return false;
		
		for(Object temp: hashTable[index] ){
			if(((EntryNode)((Node)temp).value).getKey().equals(K)){				
				return true;
			}
		}
		return false;
	}

	public boolean containsValue(Object arg0) {
		return false;
	}

	public LinkedListNew entrySet() {
		LinkedListNew entryList = new LinkedListNew();
		for(LinkedListNew list: hashTable){
			for(Object temp: list){
				entryList.add(temp);
			}
		}
		return entryList;
	}

	public Object get(Object arg0) {
		return null;
	}

	public boolean isEmpty() {
		return size==0;
	}


	public Object put(Object K, Object V) {
		float load = (float)size/(float)CAPACITY;
	//	if(load>loadFactor){
	//		rehash();
	//	}
		size++;
		int hash  = K.hashCode();
		int index = hash%hashTable.length;
		index = index < 0 ? -index : index;
		if(hashTable[index].size == 0){
			EntryNode e = new EntryNode(K, V);
			hashTable[index].add(e);
			return null;
		}
		Node temp = hashTable[index].front; 
		Object oldValue = null;
		while(temp.hasNext()){
			temp = temp.next();
			if(((EntryNode)temp.value).getKey().equals(K)){
				oldValue = ((EntryNode)(temp.value)).getValue(); 
				((EntryNode)(temp.value)).setValue(V);
				return oldValue;
			}			
		}
		Node node;
		/*if((node = getNode(K)) != null)
			node.setValue(V);
		else*/
		hashTable[index].add(new EntryNode(K, V));
		return oldValue;
	}

	private void rehash() {
		CAPACITY = CAPACITY*2;
		LinkedListNew[] newHash = new LinkedListNew[CAPACITY];
		LinkedListNew originalElements = entrySet();
		createTable(newHash);
		for(Object entry: originalElements){
			EntryNode e = (EntryNode)((Node)((Node)entry).getValue()).getValue();
			int hash  = (e.getKey()).hashCode();
			int index = hash%newHash.length;
			index = index < 0 ? -index : index;
			newHash[index].add(e);
		}
		hashTable = newHash;
	}

/*	public Node getNode(Object K){
		Node temp = iteratorList.front;
		for(Object entry: iteratorList){
			if(((EntryNode)((Node)entry).value).getKey().equals(K)){
				return (Node)entry;
			}
		}
		return null;
	}*/
	
	public Object remove(Object K) {
		size--;
		int hash = K.hashCode();
		int index = hash%hashTable.length;
		index = index < 0 ? -index : index;
		if(hashTable[index].size == 0)
			return null;
		Node temp = hashTable[index].front; 
		Object oldValue = null;
		while(temp.hasNext()){
			if(((EntryNode)temp.value).getKey().equals(K)){
				oldValue = ((EntryNode)(temp.value)).getValue(); 
				hashTable[index].remove(temp);
				
				return oldValue;
			}
			temp = temp.next();
		}
		if(((EntryNode)temp.value).getKey().equals(K)){
			oldValue = ((EntryNode)(temp.value)).getValue(); 
			hashTable[index].remove(temp);
		}
		return oldValue;
	}

	public int size() {
		return size;
	}

}


public class  ProfQ<T> {

	LinkedList<T> myList = new LinkedList<T>();
	int id;
	public void add(T element){
		myList.add(element);
		myList.remove(element);
	}
	
	ProfQ(){
		
		System.out.println("In constructor");
		id = 12;
	}
	static {
		System.out.println("Static 1");
	}
	
	static{
	System.out.println("static 2");	
	} 
	
	public int size(){
		final int myVar=0;
		return myList.size();
	}
	
	void method(){
		this.method2();
		method2();
	}
	
	private void method2() {
		// TODO Auto-generated method stub
		System.out.println(id);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProfQ<Integer> pq = new ProfQ<Integer>();
		pq.method();
	//	int x = 1/5.5;
		//ProfQ<Integer>.myClass<Integer> cs = new ProfQ<Integer>.myClass<Integer>();
	//	cs.var = 10;
//		System.out.println(cs.var);
		//cs = new ProfQ.myClass();
		//System.out.println(cs.var);
		String aString = "Temperature";
		System.out.println(1+aString.length()+1);
		aString = "abc";

		System.out.println("abc"==aString);
		System.out.println(aString.substring(aString.indexOf('a'), aString.indexOf('c')));
	}
	
	//static final 
	class myClass<T>{
		static final int var=0;
		 
		 int getvalue(){
			 return var;
		 }		 
	}
}

/*final static class myClass{
	int var;
}*/


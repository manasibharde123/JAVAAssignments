
public class Test123 {

	public static void main(String... args) {
		// TODO Auto-generated method stub
		
	
		Object o = new Object();
		int j =0 ;
		for(int i=0; i<5;i++)j = j++;
		System.out.println(j);
		
		try{
		int x;
		throw new NullPointerException();
		}catch(NullPointerException e){
			System.out.println("a");
		}catch(RuntimeException p){
			System.out.println("b");
		}
	}

}

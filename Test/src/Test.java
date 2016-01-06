class One{
	static{
		System.out.println("when is my turn");
	}
	
	public void aMthod(){
		
	}
}


public class Test extends Thread{

	public void run(){
		synchronized(One.class){
			One.class.notify();
			System.out.println("here");
			new One();
			try {
				One.class.wait();
				System.out.println("There");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
	}
	
	public static void main(String args[]){
		Thread t = new Test();
		t.start();
		Thread t1 = new Test();
		t1.start();
	}
}

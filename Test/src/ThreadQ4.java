
class T_4{
	static{
		System.out.println("T_4 initialized");
	}
}

public class ThreadQ4 {

	
	public static void main(String[] args) throws InterruptedException{
		synchronized(T_4.class){
			System.out.println("T_4 locked");
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new T_4();
				}
				
			}).start();
			Thread.sleep(2000);
			System.out.println("Unlocking T_4");
			
		}
	}
}

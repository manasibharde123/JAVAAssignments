import java.util.Arrays;

public class ZeroOne1 extends Thread	{
	private String info;
	static int i=0;
	static Object o = new Object();
	static Boolean[] isRunning = new Boolean[100];
	static{
		Arrays.fill(isRunning, false);
	}
			
	public ZeroOne1 (String info) {
		this.info    = info;
	}
	
	public void run () {
		while ( true )	{
			synchronized ( o ) {
				o.notify();
				System.out.print(info);
				try {
						sleep(50);
						o.wait(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
				}
		}}
	}
	public static void main (String args []) {
		Thread numbers[] = new ZeroOne1[99];
		new ZeroOne1("0").start();
		
		for(int i = 0; i<99; i++){
			numbers[i] = new ZeroOne1(""+i);
			numbers[i].start();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
/**
 		* 
 		* @version   1
        *
        * @author1    Manasi Sunil Bharde
        * @author2    Sri Praneeth Iyyapu
        * Revisions:
        *      $Log$
        *
 */
import java.util.Arrays;

public class ZeroOne extends Thread	{
	private String info;
	static Object o = new Object();
	static Boolean[] isRunning = new Boolean[99];
	static{
		Arrays.fill(isRunning, false);
	}
			
	public ZeroOne (String info) {
		this.info    = info;
	}
	
	public void run () {
		
		while ( true )	{
			synchronized ( o ) {
				o.notify();
				/*if(getId()-11<0)
					System.out.print(getId()-10);
				else
					System.out.print(getId());*/
				System.out.print(info);
				for(int i = 1; i<99; i++){
					if ( ! isRunning[i] )	{
						try {
							sleep(400);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						new ZeroOne(""+i).start();
						isRunning[i] = true;
					}
					try {
						o.wait(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}}
			}
		}
	}
	
	public static void main (String args []) {
		
		ZeroOne x = new ZeroOne("0");
		x.start();
		
	}
}


/** 
 * Program discusses how multi-threading can lead to different outputs
 * @author1    Manasi Sunil Bharde
 * @author2    Sri Praneeth Iyyapu
 * @version   1
 * Revisions: 1
 * $Log$
 * 
 * 
 * Besides Thread a's constructor call and Thread b's constructor 
 * call which are called in order by main thread, rest of the calls will take place at random in code. 
 * which are
 * Thread a run increment, Thread a fetch 1st x, Thread a fetch 2nd x, Thread a Print  
 * Thread b run increment, Thread b fetch 1st x, Thread b fetch 2nd x, Thread b Print
 * Possible outputs of the program are:
 * 3 a: 3 5 b: 5 
 * 5 b: 5 3 a: 3  
 * 3 a: 5 5 b: 5
 * 5 b: 5 3 a: 5   
 * 3 a: 4 5 b: 5
 * 5 b: 5 3 a: 4 
 * 4 a: 4 5 b: 5
 * 4 b: 4 5 a: 5
 * 4 a: 5 5 b: 5
 * 5 a: 5 5 b: 5
 * 5 b: 5 5 a: 5
 * 5 b: 5 4 a: 5
 * 5 b: 5 4 a: 4
 * Output varies for different runs because there are three thread executing 
 * simultaneously without any defined priority order or synchronization and using a shared resource x.
 * After T_1("a") is created, value of x is 2. Main thread creates the thread a, 
 * starts it and proceeds on to create thread b. So before thread a's run method 
 * is begun by JVM's thread scheduler, value of x may be 3 if x is already incremented in 
 * thread b's constructor call or it will be 2 if x is not still incremented in 2nd constructor call.
 * Also while creating output run method fetches value of x twice. 
 * For 1st fetch x takes value 3 if thread b's constructor has not been called yet
 * it is 4 if thread b's constructor is already called. 
 * It is 5 if both thread b's constructor and run method is called already. 
 * Between 1st fetch and 2nd fetch 
 * if an increment occurs in thread b constructor call 
 * or may be in both constructor call and run method of thread b 
 * or may be none of this has been called yet. 
 * So value of x for 2nd fetch in output line can be 3, 4 or 5. 
 * Similarly possible values of x at beginning of run method call of thread b 
 * are 3 if thread a's run method has not been called yet
 * 4 if thread a's run method is already called
 * It can never be 2 as main thread calls thread a constructor before 
 * thread b constructor and hence run.   
 * So 1st fetch of x in run output formation will give 4 if thread a's run method has not been called yet
 * or 5 if thread a's run method is already called
 * Hence all the possible outcomes.
 */
public class T_1 extends Thread	{
	static int x = 1;
	String info = "";

	public T_1 (String info) {
		this.info = info;
		x++;
	}

	public void run () {
		x++;
		String output = x + " " + info + ": " + x;
		System.out.println(output);
	}

	public static void main (String args []) {
		new T_1("a").start();
		new T_1("b").start();
	}
}
/**
        * @version   1
        *
        * @author1    Manasi Sunil Bharde
        * @author2    Sri Praneeth Iyyapu
        * Revisions:
        *      $Log$
        */

public class SieveOfEratosthenes  extends Thread{
	
    static int MAX=100;
    static int threadCount;
    static boolean[] numbers;
    int index;

    public SieveOfEratosthenes (){
    	
    	
    }
    
    
    public SieveOfEratosthenes (int index) {
    	this.index = index;
    }
    
    public void run(){
    	int counter = 2;
		while ( index * counter < MAX )	{		
			numbers[index * counter] = false;	
			counter++;				
		}						
    }
    
    public void determinePrimeNumbers()	{
	    numbers = new boolean[MAX];
		for (int index = 1; index < MAX; index ++ )	{
			numbers[index] = true;
		}
		int limit = (MAX > 10 ? (int)Math.sqrt(MAX) + 1 : 3);
		for (int index = 2; index < limit; index ++ ){
			if ( numbers[index] )	{				
				Thread newThread = new SieveOfEratosthenes (index);
				while(Thread.activeCount()==threadCount);
				newThread.start();
			}
		}    	
    }
    
    public void testForPrimeNumber()	{
	int[] test = { 2, 3, 4,5,6, 7, 13, 17, MAX - 1, MAX};
	for (int index = 0; index < test.length; index ++ )	{
		if ( test[index] < MAX )	{
			System.out.println(test[index] + " = " + numbers[test[index]]);
		}
	}
    }

    public static void main( String[] args ) {
    threadCount = Integer.parseInt(args[0]);
    SieveOfEratosthenes  soe = new SieveOfEratosthenes();
	soe.determinePrimeNumbers();
	soe.testForPrimeNumber();
	System.exit(0);
    }
}

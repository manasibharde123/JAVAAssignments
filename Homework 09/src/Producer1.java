public class Producer1 extends Thread{
	static String[] candies = new String[60];
	static String[] wrappers = new String[20];
	static String[] boxes = new String[120];
	static String[] candyStorage = new String[120];
	static String[] boxStorage = new String[360];
	static Integer candyCount=0, boxCount=0, wrapperCount=0, wrappedCandyCount=0, filledBoxCount=0;
	
	public void run(){
		while(true){
		synchronized(candyCount){
		if(candyCount==0){
			candyCount++;
			//myWait();
			System.out.println("Produced Candy" + candyCount);
		}
		else
		try {
			candyCount.notify();
			candyCount.wait();
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	//	System.out.println("candyCount"+candyCount+"wrapperCount"+wrapperCount+"boxCount"+boxCount+"wrappedCandyCount"+wrappedCandyCount+"filledBoxCount"+filledBoxCount);
		}}}
	
	
	public static void main(String args[]){
		Producer1 p = new Producer1();
		Thread Producer1 = p;
		Thread CandyBoxProducer1 = new CandyBoxProducer1();
		Thread CandyWrappingPaperProducer1 = new CandyWrappingPaperProducer1();
		Thread CandyWrapperConsumer = new CandyWrapperConsumer();
		Thread CandyFillConsumer = new CandyFillConsumer();
		Producer1.start();
		myWait();
		CandyWrappingPaperProducer1.start();
		myWait();
		CandyWrapperConsumer.start();
		myWait();
		CandyBoxProducer1.start();
		myWait();
		CandyFillConsumer.start();
//		while(true)
	//		System.out.println("candyCount"+candyCount+"wrapperCount"+wrapperCount+"boxCount"+boxCount+"wrappedCandyCount"+wrappedCandyCount+"filledBoxCount"+filledBoxCount);
		}
	
	public static void myWait(){
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class CandyBoxProducer1 extends Thread{
	public void run(){
		synchronized(Producer1.boxCount){
		while(true){
		if(Producer1.boxCount==0){
			Producer1.boxCount++;
		//	Producer1.myWait();
			System.out.println("Produced box"+Producer1.boxCount);
		} else{
			try {
				Producer1.boxCount.notify();
				Producer1.boxCount.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}}}}
}

class CandyWrappingPaperProducer1 extends Thread{
	public void run(){
		synchronized(Producer1.wrapperCount){
		while(true){
		if(Producer1.wrapperCount==0){
			Producer1.wrapperCount +=3;
			System.out.println("Prodced Wrapper"+Producer1.wrapperCount);
	//		Producer1.myWait();
		}else{
			try {
				Producer1.wrapperCount.notify();
				Producer1.wrapperCount.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}}}
}

class CandyWrapperConsumer extends Thread{
	public void run(){
	synchronized(Producer1.wrappedCandyCount){
		synchronized(Producer1.candyCount){
			synchronized(Producer1.wrapperCount){
		while(true){
		if(Producer1.wrappedCandyCount==0)
			if(Producer1.candyCount>0)
				if(Producer1.wrapperCount>0){
				Producer1.candyCount--;
				Producer1.wrapperCount--;
				Producer1.wrappedCandyCount++;
				System.out.println("Wrapped candy"+Producer1.wrappedCandyCount);
				//Producer1.myWait();
				}
				else{
					try {
						Producer1.wrapperCount.notify();
						Producer1.wrapperCount.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			else{
				try {
					Producer1.candyCount.notify();
					Producer1.candyCount.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		else{
			try {
				Producer1.wrappedCandyCount.notify();
				Producer1.wrappedCandyCount.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}}}}
}

class CandyFillConsumer extends Thread{
	public void run(){
		synchronized(Producer1.boxCount){
			synchronized(Producer1.wrappedCandyCount){
		while(true){
		if(Producer1.boxCount>0)
			if(Producer1.wrappedCandyCount>3){
				Producer1.boxCount--;
				Producer1.wrappedCandyCount-=4;
				Producer1.filledBoxCount++;
				Producer1.myWait();
				System.out.println("Filled box: "+Producer1.filledBoxCount);
			}
			else{
				try {
					Producer1.wrappedCandyCount.notify();
					Producer1.wrappedCandyCount.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		else{
			try {
				Producer1.boxCount.notify();
				Producer1.boxCount.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}}}
}
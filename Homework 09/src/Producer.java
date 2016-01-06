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
public class Producer extends Thread{
	static final Object sharedCandyObject = new Object();
	static final Object sharedWrappperObject = new Object();
	static final Object sharedBoxObject = new Object();
	static final Object sharedWrappedCandy = new Object();
	static Integer candyCount=0, boxCount=0, wrapperCount=0, wrappedCandyCount=0, filledBoxCount=0;
	static int totalCandy, totalBox, totalWrapper, totalWrappedCandy;
	public void run(){
		synchronized(sharedCandyObject){
			while(true){		
				if(candyCount==0){
					candyCount++;
					totalCandy++;
					//myWait();
					System.out.println("Produced Candy" + totalCandy);
				}
				else
				try {
					sharedCandyObject.notify();
					sharedCandyObject.wait();
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
		}}}
	
	
	public static void main(String args[]){
		Producer p = new Producer();
		Thread ProducerDemo = p;
		Thread CandyBoxProducer = new CandyBox();
		Thread CandyWrappingPaperProducer = new CandyWrappingPaper();
		Thread CandyWrapperConsumer = new CandyWrapper();
		Thread CandyFillConsumer = new CandyFill();
		ProducerDemo.start();
		myWait();
		CandyWrappingPaperProducer.start();
		myWait();
		CandyWrapperConsumer.start();
		myWait();
		CandyBoxProducer.start();
		myWait();
		CandyFillConsumer.start();
		//	while(true)
		//	System.out.println("candyCount"+candyCount+"wrapperCount"+wrapperCount+"boxCount"+boxCount+"wrappedCandyCount"+wrappedCandyCount+"filledBoxCount"+filledBoxCount);
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

class CandyBox extends Thread{
	public void run(){
		synchronized(Producer.sharedBoxObject){
		while(true){
		if(Producer.boxCount==0&&Producer.totalBox<120){
			Producer.boxCount++;
			Producer.totalBox++;
		//	Producer.myWait();
			System.out.println("Produced box"+Producer.totalBox);
		} else{
			try {
				Producer.sharedBoxObject.notify();
				Producer.sharedBoxObject.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}}}}
}

class CandyWrappingPaper extends Thread{
	public void run(){
		synchronized(Producer.sharedWrappperObject){
		while(true){
		if(Producer.wrapperCount==0){
			Producer.wrapperCount +=3;
			Producer.totalWrapper+=3;
			System.out.println("Prodced Wrapper"+Producer.totalWrapper);
	//		Producer.myWait();
		}else{
			try {
				Producer.sharedWrappperObject.notify();
				Producer.sharedWrappperObject.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}}}
}

class CandyWrapper extends Thread{
	public void run(){
		synchronized(Producer.sharedWrappedCandy){
			synchronized(Producer.sharedCandyObject){
				synchronized(Producer.sharedWrappperObject){
				while(true){
					if(Producer.wrappedCandyCount<4){
						if(Producer.candyCount>0){
							if(Producer.wrapperCount>0){
								Producer.candyCount--;
								Producer.wrapperCount--;
								Producer.wrappedCandyCount++;
								Producer.totalWrappedCandy++;
								System.out.println("Wrapped candy"+Producer.totalWrappedCandy);
								//Producer.myWait();
							}
							else{
								try {
									Producer.sharedWrappperObject.notify();
									Producer.sharedWrappperObject.wait();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}}
						else{
							try {
								Producer.sharedCandyObject.notify();
								Producer.sharedCandyObject.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}}
					else{
						try {
							Producer.sharedWrappedCandy.notify();
							Producer.sharedWrappedCandy.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
			}
		}
	}
	}
}

class CandyFill extends Thread{
	public void run(){
		synchronized(Producer.sharedWrappedCandy){
			synchronized(Producer.sharedBoxObject){
				while(true){
					if(Producer.wrappedCandyCount>3){
						if(Producer.boxCount>0){
							Producer.boxCount--;
							Producer.wrappedCandyCount-=4;
							Producer.filledBoxCount++;
							//Producer.myWait();
							System.out.println("Filled box: "+Producer.filledBoxCount);
						}
						else{
							try {
							Producer.sharedBoxObject.notify();
							Producer.sharedBoxObject.wait();
							} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							}
						}}
					else{
						try {
							Producer.sharedWrappedCandy.notify();
							Producer.sharedWrappedCandy.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}}
					}
				}
			}
	}
}
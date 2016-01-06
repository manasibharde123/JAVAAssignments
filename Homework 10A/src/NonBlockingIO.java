import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class NonBlockingIO extends Thread{

	long tStart;
	BufferedInputStream bi;
	BufferedOutputStream bs;
	byte[] buffer = new byte[10000];
	int noOfBytesRead = 0;
	static Object o = new Object();
	static boolean dataAvailable = true;

	public NonBlockingIO(BufferedInputStream bi){
		try {
			this.bi = bi;
			bs = new BufferedOutputStream(new FileOutputStream("newFile.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void processData(Object o){
		while(dataAvailable){
		synchronized(o){
		try {
			System.out.println("Processing data");
			Thread.sleep(20);
			o.notify();
			o.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}}
	}
	
	public void readOneK(){
		try {
			bi.read(buffer, noOfBytesRead,1000);
			noOfBytesRead+=1000;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void open(){
		Thread nonBlockingProcess = new Thread(this);
		nonBlockingProcess.start();
	}
	
	public void run(){
		synchronized(o){
		try {
			while(bi.available()!=0){
				readOneK();
				System.out.println("reading data");
				o.notify();
				o.wait();
			}
			dataAvailable = false;
			o.notify();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} }
		System.out.println("read completed in "+(System.currentTimeMillis() - tStart)+" ms time");
	}
	
	public void read(){
			Thread DataProcess = new Thread(){public void run(){
				processData(o);
			}};
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DataProcess.start();
	}
	
	public static void main(String[] args){
		FileInputStream fs;
		try {
			fs = new FileInputStream("words.txt");
			NonBlockingIO io = new NonBlockingIO(new BufferedInputStream(fs));
			io.tStart = System.currentTimeMillis();
			io.open();
			io.read();
			System.out.println("Back to main in "+ (System.currentTimeMillis() - io.tStart)+" ms time");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

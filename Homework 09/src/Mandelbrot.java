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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame implements Runnable{
	
	private Image theImage;
	PixelThread[] pixels;
	int maxThreadCount = 10;
	int i =0;
	static int defaultThreadCount;
	
	public Mandelbrot(){
        setBounds(100, 100, 800, 800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void run() {
		for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
            	synchronized(PixelThread.o[i%20]){
            	pixels[i%20].initPixelThread(x, y,i%20);
            	try {
					PixelThread.o[i%20].wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	i++;
            	}}
	        }
		System.out.println(PixelThread.pixelCount);
		theImage =  PixelThread.stopThreads();
        repaint();
	}
	
    public static void main(String[] args) {
	   Mandelbrot aMandelbrot = new Mandelbrot();
	   aMandelbrot.setVisible(true);
	   aMandelbrot.createThreads();
	   Thread manager = new Thread(aMandelbrot);
	   defaultThreadCount = Thread.activeCount();
	   manager.start();
	}

	private void createThreads() {
		pixels = new PixelThread[20];
		for(int  i=0; i<20; i++){
			pixels[i] = new PixelThread();
			pixels[i].i = i;
			pixels[i].start();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
	    g.drawImage(theImage, 0, 0, this);
	}
}

class PixelThread extends Thread{
	private static final int MAX 	= 5000;
    private static final int LENGTH   	= 800;
    private static final double ZOOM  	= 1000;
    private static int[] colors = new int[MAX];
    static int pixelCount =0;
	static BufferedImage theImage;
	int x,y,i;
	static Object o[];
	static boolean runThreads  = true;
	
	public void initPixelThread(int x, int y, int i){
		synchronized(o[i]){
			this.x = x;
			this.y = y;
			o[i].notifyAll();
		}
	}
	
	static{
		o = new Object[20];
		for(int i=0; i<20; i++){
			o[i] = new Object();
		}
	}
	
	static{
		theImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
		for (int index = 0; index < MAX; index++) {
	        colors[index] = Color.HSBtoRGB(index/256f, 1, index/(index+8f));
	    }
	}
	
	public static Image stopThreads(){
		runThreads = false;
		return theImage;
	}

	@Override
	public void run() {
	synchronized(o[i]){
		while(runThreads){
			try {
				o[i].wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		int iter = 0;
		double zx, zy, cX, cY;
		zx = zy = 0;
        cX = (x - LENGTH) / ZOOM;
        cY = (y - LENGTH) / ZOOM;
		double tmp;
        while ( (zx * zx + zy * zy < 10 ) && ( iter < MAX - 1 ) ) {	
            tmp = zx * zx - zy * zy + cX;				
            zy = 2.0 * zx * zy + cY;					
            zx = tmp;							
            iter++;							
        }	
		if ( iter > 0 )							
			theImage.setRGB(x, y, colors[iter]);			
		else								
			theImage.setRGB(x, y, iter | (iter << 8));
		synchronized((Object)pixelCount){
		pixelCount++;		
		}
		o[i].notify();
	}}}
}
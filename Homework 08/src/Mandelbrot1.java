import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot1 extends JFrame implements Runnable{
	
	private static final int MAX 	= 5000;
    private static final int LENGTH   	= 800;
    private static final double ZOOM  	= 1000;
    private static int[] colors = new int[MAX];
	private static BufferedImage theImage;
	private int x,y;
	
	public Mandelbrot1(){
		initColors();
        setBounds(100, 100, LENGTH, LENGTH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
		theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
	}
	
	public Mandelbrot1(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void run(){
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
	}
	
	public void createSet()	{
        theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        int maxThreadCount = Runtime.getRuntime().availableProcessors();
      //  System.out.println("ma t count"+maxThreadCount);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
            	while(Thread.activeCount()==maxThreadCount);
            	Thread nextPixel = new Thread(new Mandelbrot(x,y));
            	nextPixel.start();
            	}
        }
        repaint();
	}

	public void initColors() {
	    for (int index = 0; index < MAX; index++) {
	        colors[index] = Color.HSBtoRGB(index/256f, 1, index/(index+8f));
	    }
	}
	
	@Override
	public void paint(Graphics g) {
	    g.drawImage(theImage, 0, 0, this);
	}
	
    public static void main(String[] args) {
    Mandelbrot1 aMandelbrot = new Mandelbrot1();
	aMandelbrot.setVisible(true);
	long start = System.currentTimeMillis();			
	aMandelbrot.createSet();
	long end = System.currentTimeMillis();
	System.out.println(end-start);
    }
}
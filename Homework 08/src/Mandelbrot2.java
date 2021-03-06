/**
 * That is, a complex number c is part of the Mandelbrot set if, when starting with z0 = 0 and 
 * applying the iteration repeatedly, * the absolute value of zn remains bounded however large 
 * n gets.
 * 
 * ==Bailout test==
Instead of checking :

 <math> \sqrt{Z_x^2 + Z_y^2} < ER </math>

compute ER2 once and check :

 <math> Z_x^2 + Z_y^2 < ER^2 </math>

It gives the same result and is faster.

 */
// original from: http://rosettacode.org/wiki/Mandelbrot_set#Java
// modified for color

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
 
public class Mandelbrot2 extends JFrame {
 
    private final int MAX 	= 5000;
    private final int LENGTH   	= 800;
    private final double ZOOM  	= 1000;
    private BufferedImage theImage;
    private int[] colors = new int[MAX];
 
    public Mandelbrot2() {
        super("Mandelbrot Set");
	
	initColors();
        setBounds(100, 100, LENGTH, LENGTH);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void createSet()	{
        theImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
	double zx, zy, cX, cY;
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - LENGTH) / ZOOM;
		cY = (y - LENGTH) / ZOOM;
		int iter = 0;
		double tmp;
                while ( (zx * zx + zy * zy < 10 ) && ( iter < MAX - 1 ) ) {	// this is the part for the parallel part
                    tmp = zx * zx - zy * zy + cX;				// this is the part for the parallel part
                    zy = 2.0 * zx * zy + cY;					// this is the part for the parallel part
                    zx = tmp;							// this is the part for the parallel part
                    iter++;							// this is the part for the parallel part
                }								// this is the part for the parallel part
		if ( iter > 0 )							// this is the part for the parallel part
			theImage.setRGB(x, y, colors[iter]);			// this is the part for the parallel part
		else								// this is the part for the parallel part
			theImage.setRGB(x, y, iter | (iter << 8));		// this is the part for the parallel part
            }
	repaint();
        }
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
        Mandelbrot2 aMandelbrot = new Mandelbrot2();
	aMandelbrot.setVisible(true);
	long start = System.currentTimeMillis();			
	aMandelbrot.createSet();
	long end = System.currentTimeMillis();
	System.out.println(end-start);
    }
}
package julia;

import java.awt.Color;
import java.awt.Point;

import nip.Image;

public class Julia {
	
	private Complex ul, lr, upL, lowR;
	private int minw, minh, maxIters, wandh;
	private ComplexRaster cr;
	private Image image;
	private Point pt;

	/**
	 * Constructor:  prepare to be able to draw on the supplied image.
	 *   Retains the size of the image as the min of its width and height.
	 *   Initially, the displayed pixels correspond to:
	 *      -2 + 2i for the upper-left-hand corner
	 *       2 - 2i for the lower-right-hand corner
	 * @param image -- pixels to be set to show the Julia set
	 */
	public Julia(Image image) {
		this.image = image;
		ul = new Complex(-2, 2);
		lr = new Complex(2, -2);
		// due to the parameter Point ul in zoomTo, I decided to leave ul and lr alone, and make my own Complex numbers.
		upL = ul;
		lowR = lr;
		
		//should be same, but just in case
		minw = image.getWidth();
		minh = image.getHeight();
		
		//to store the upper left POINT
		pt = new Point(0,0);
		
		cr = new ComplexRaster(ul, lr, minh, minw);
	}
	
	/**
	 * Reset the raster to show the original complex space and
	 * reset the iterations to 100.
	 */
	public void reset() {
		maxIters = 100;
		zoomTo(new Point(0,0), minw);
	}
	
	/**
	 * Sets the complex coordinates based on the specified pixel location
	 * @param ul Upper-left corner in pixel coordinates of the zoom-to box
	 * @param widthAndHeight length and width of the zoom-to box, in pixels
	 */
	public void zoomTo(Point ul, int widthAndHeight) {
		pt = ul;
		double wide = Math.abs(upL.getReal() - lowR.getReal());
		upL = cr.getPoint(ul);
		//System.out.println(uppL);
		double perc = (widthAndHeight*1.0) / minh;
		double nwide = wide * perc;
		lowR = new Complex(upL.getReal() + nwide, upL.getImaginary() - nwide);
		cr = new ComplexRaster(upL, lowR, minw, minw);
		// next line was for debugging purposes, I was having issues with "wide" remaining at 4.0 so i moved that line to first and 
		// made it reference uppL and lowR before they are changed instead of using ul and lr which i dont ever change.
		//System.out.println (perc + " " + wide + " " + nwide + " " + minh + " " + widthAndHeight + " " + pt + " " + upL + " " + lowR);
		draw();
	}
	
	/**
	 * Zooms in, which counteracts a zoom out
	 */
	public void zoomIn() {
		wandh = minw / 2; //minw and minh should be equivalent wandh = Width and height
		int in = minw / 4;
		zoomTo(new Point(in, in), wandh);
	}
	
	/**
	 * Zooms out, which counteracts a zoom in
	 * could use some work still
	 */
	public void zoomOut() {		 
		double dx = Math.abs(upL.getReal() - lowR.getReal());
		double dy = Math.abs(upL.getImaginary() - lowR.getImaginary());
		upL = new Complex(upL.getReal() - .5*dx, upL.getImaginary() + .5*dy);
		lowR = new Complex(lowR.getReal() + .5*dx, lowR.getImaginary() - .5*dy);
		cr = new ComplexRaster(upL, lowR, wandh*2, wandh*2);
		draw();
		
	}
	
	/**
	 * Increase the maximum number of iterations by some amount (say, 50)
	 */
	public void bump() {
		maxIters += 50;
		draw();
		//System.out.println("bump");
	}
	
	/**
	 * Decrease the maximum number of iterations by some amount (say, 50)
	 */
	public void unbump() {
		maxIters -= 50;
		draw();
	}

	private int rigor(Complex c) {
		Complex z = new Complex(-.7795, .134);
		int iters = 0;
		while ((c.abs() < 2) && (iters < maxIters)) {
			c = c.times(c).plus(z);
			iters++;
		}
		return iters;
	}

	/**
	 * Call this method to draw or redraw the Julia set
	 */
	public void draw() {
		//System.out.println("saldfja");
		int iters;
		for (int x = 0 ; x < minw ; x++) {
			for (int y = 0 ; y < minh ; y++) {
				iters = rigor(cr.getPoint(x, y));
				Color color = Color.black;
				if (iters < maxIters) {
					color = Color.getHSBColor((iters%256)/255.0f, 1.0f, 1.0f);
				}
				image.setPixel(x, y, color);
			}
		}
	}

}

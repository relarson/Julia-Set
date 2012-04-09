package julia;
import java.awt.Point;

public class ComplexRaster {
	
	private Complex ul, lr;
	private int height, width, maxh, maxw;
	
	/**
	 * Constructs an immutable correspondence to the specified Complex space.
	 * In that space, the real component gets larger to the right, and
	 *    the imaginary component gets larger as you go up.
	 * @param ul is the Complex coordinate of the upper-left-hand corner
	 * @param lr is the Complex coordinate of the lower-right-hand corner
	 * @param height is the height of the displayed area in pixels
	 * @param width  is the width of the displayed area in pixels
	 */
	public ComplexRaster(Complex ul, Complex lr, int height, int width) {
		this.ul = ul;
		this.lr = lr;
		this.height = height;
		this.width = width;	
		maxh = height-1;
		maxw = width-1;
	}

	/**
	 * Based on how this ComplexRaster was constructed, returns the
	 *   Complex coordinate of the specified pixel location.
	 * @param p the pixel coordinate of interest
	 * @return the Complex value associated with the specified pixel
	 */
	public Complex getPoint(Point p) {
		double cw = Math.abs(ul.getReal() - lr.getReal());
		double ch = Math.abs(ul.getImaginary() - lr.getImaginary());
		double w = (cw/maxw)*p.getX();
		double h = (ch/maxh)*p.getY();
		return new Complex(ul.getReal() + w, ul.getImaginary() - h);
	}
	
	/**
	 * Same as getPoint(Point) but with x,y coordinates
	 * @param x horizontal coordinate of the pixel of interest
	 * @param y vertical coordinate of the pixel of interest
	 * @return
	 */
	public Complex getPoint(double x, double y) {
		double cw = Math.abs(ul.getReal() - lr.getReal());
		double ch = Math.abs(ul.getImaginary() - lr.getImaginary());
		double w = (cw/maxw)*x;
		double h = (ch/maxh)*y;
		return new Complex(ul.getReal() + w, ul.getImaginary() - h);
	}

	/**
	 * 
	 * Because this object is immutable, this method always returns
	 *   the same value (width of the raster in pixels) for a given ComplexRaster.
	 * @return the width of the ComplexRaster area, in pixels
	 */
	public int getWidth() {
		return width; 
	}

	/**
	 * Because this object is immutable, this method always returns
	 *    the same value (height of the raster in pixels) for a given ComplexRaster.
	 * @return the height of the ComplexRaster area, in pixels
	 */
	public int getHeight() {
		return height;
	}
	
	
}

package nip;

import java.awt.*;
import java.awt.geom.*;

/**
 * 
 * A Rectangle is an axis-aligned visual component defined by an upper left corner, a width, and a height.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jun 28, 2005
 */
public class Rectangle extends Shape {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a Rectangle with the given upper left corner and dimensions.
	 * @param x the x coordinate of the upper left corner, in pixels
	 * @param y the x coordinate of the upper left corner, in pixels
	 * @param width in pixels
	 * @param height in pixels
	 */
	public Rectangle(int x, int y, int width, int height) {
		super(new Rectangle2D.Double(x,y,width,height));
	}

	/**
	 * 
	 * Creates a Rectangle with the given upper left corner and dimensions, color, and fill option.
	 * @param x the x coordinate of the upper left corner, in pixels
	 * @param y the x coordinate of the upper left corner, in pixels
	 * @param width in pixels
	 * @param height in pixels
	 * @param color the color with which the shape should be painted
	 * @param filled true if the color should be filled, false if only the outline should be drawn
	 */
	public Rectangle(int x, int y, int width, int height, Color color, boolean filled) {
		super(new Rectangle2D.Double(x,y,width,height), color, filled);
	}

}

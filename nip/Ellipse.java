package nip;

import java.awt.Color;
import java.awt.geom.*;

/**
 * An Ellipse is a an axis-aligned visual component defined by a bounding box with a given
 * upper left corner, a width, and a height.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jun 28, 2005
 */
public class Ellipse extends Shape {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an Ellipse with the given upper left corner and dimensions.
	 * @param x the x coordinate of the upper left corner, in pixels
	 * @param y the x coordinate of the upper left corner, in pixels
	 * @param width in pixels
	 * @param height in pixels
	 */
	public Ellipse(int x, int y, int width, int height) {
		super(new Ellipse2D.Double(x,y,width,height));
	}

	/**
	 * Creates an Ellipse with the given upper left corner and dimensions, color, and fill option.
	 * @param x the x coordinate of the upper left corner, in pixels
	 * @param y the x coordinate of the upper left corner, in pixels
	 * @param width in pixels
	 * @param height in pixels
	 * @param color the color with which the shape should be painted
	 * @param filled true if the color should be filled, false if only the outline should be drawn
	 */
	public Ellipse(int x, int y, int width, int height, Color color, boolean filled) {
		super(new Ellipse2D.Double(x,y,width,height), color, filled);
	}

}



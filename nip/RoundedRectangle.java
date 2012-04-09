package nip;

import java.awt.*;
import java.awt.geom.*;

/**
 * 
 * A RoundedRectangle is an axis-aligned visual component defined by an upper left corner, a width, and a height, where
 * the corners are slightly rounded for a softer visual effect.  Three different constructor options are provided.
 * Note that all attributes except the curvature can be changed later by calling various "set" methods.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jun 28, 2005
 */
public class RoundedRectangle extends Shape {

	private static final long serialVersionUID = 1L;
	static final int DEFAULT_CURVE = 10;

	/**
	 * Creates a RoundedRectangle with the given upper left corner and dimensions.
	 * A default curvature is used to round the corners.
	 * @param x the x coordinate of the upper left corner, in pixels
	 * @param y the x coordinate of the upper left corner, in pixels
	 * @param width in pixels
	 * @param height in pixels
	 */
	public RoundedRectangle(int x, int y, int width, int height) {
		super(new RoundRectangle2D.Double(x,y,width,height,DEFAULT_CURVE,DEFAULT_CURVE));
	}

	/**
	 * 
	 * Creates a Rectangle with the given upper left corner, dimensions, and curvature.
	 * @param x the x coordinate of the upper left corner, in pixels
	 * @param y the x coordinate of the upper left corner, in pixels
	 * @param width in pixels
	 * @param height in pixels
	 * @param arcWidth the horizontal distance, in pixels from the corner to the straight part of the rectangle
	 * @param arcHeight the vertical distance, in pixels from the corner to the straight part of the rectangle
	 */
	public RoundedRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) {
		super(new RoundRectangle2D.Double(x,y,width,height,arcWidth,arcHeight));
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
	public RoundedRectangle(int x, int y, int width, int height, Color color, boolean filled) {
		super(new RoundRectangle2D.Double(x,y,width,height,DEFAULT_CURVE,DEFAULT_CURVE), color, filled);
	}

}

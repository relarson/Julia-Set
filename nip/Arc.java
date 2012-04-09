package nip;

import java.awt.*;
import java.awt.geom.*;

//TODO: Check that the documentation is correct about the start location and extent direction
/**
 * 
 * 
 * An Arc is visual component defined by a bounding box with a given
 * upper left corner, a width, and a height, plus a starting point and extent in degrees.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jul 5, 2005
 */
public class Arc extends Shape {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an Arc with the given upper left corner and dimensions.
	 * @param x the x coordinate of the upper left corner, in pixels
	 * @param y the x coordinate of the upper left corner, in pixels
	 * @param width in pixels
	 * @param height in pixels
	 * @param startDegrees the starting point for the arc, with 0 at 3:00.
	 * @param extentDegrees the extent of the arc in the counterclockwise direction
	 */
	public Arc(int x, int y, int width, int height, double startDegrees, double extentDegrees) {
		super(new Arc2D.Double(x,y,width,height,startDegrees,extentDegrees,Arc2D.Double.OPEN));
	}
	
	/**
	 * Creates an Arc with the given upper left corner and dimensions.
	 * @param x the x coordinate of the upper left corner, in pixels
	 * @param y the x coordinate of the upper left corner, in pixels
	 * @param width in pixels
	 * @param height in pixels
	 * @param startDegrees the starting point for the arc, with 0 at 3:00.
	 * @param extentDegrees the extent of the arc in the counterclockwise direction
	 * @param color the color in which the arc should be drawn
	 * @param filled true if the interior of the arc should be filled
	 */
	public Arc(int x, int y, int width, int height, double startDegrees, double extentDegrees, Color color, boolean filled) {
		super(new Arc2D.Double(x,y,width,height,startDegrees,extentDegrees,filled? Arc2D.Double.PIE : Arc2D.Double.CHORD));
		setForeground(color);
	}

	public void setFilled(boolean filled) {
		int closureType = filled? Arc2D.Double.PIE : Arc2D.Double.CHORD;
		Arc2D.Double arc = (Arc2D.Double) shape;
		arc.setArc(arc.getX(),arc.getY(),arc.getWidth(),arc.getHeight(),arc.getAngleStart(),arc.getAngleExtent(),closureType);
	}
	
	public boolean isFilled() {
		return ((Arc2D.Double) shape).getArcType() == Arc2D.Double.PIE;
	}

}

package nip;
import java.awt.*;
import java.awt.geom.RectangularShape;

/**
 * A Shape is a geometric visual component.  To create a Shape, call a constructor for one of its subclasses, listed above.
 * This class defines methods that are available on all Shape objects.  Specific shapes may provide additional methods.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jun 28, 2005
 */
public abstract class Shape extends Graphic {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6894384378397546742L;
	RectangularShape shape;
	boolean filled;
	
	Shape(RectangularShape shape) {
		this(shape, Color.GRAY, false);
	}

	Shape(RectangularShape shape, Color color, boolean filled) {
		if (shape == null)
			throw new IllegalArgumentException("A shape must be provided.");
		this.shape = shape;
		java.awt.Rectangle shapeBounds = shape.getBounds();
		if (shape.getBounds().width <= 0)
			throw new IllegalArgumentException("Shape width is " + shapeBounds.width + ", but should be positive.");
		if (shape.getBounds().height <= 0)
			throw new IllegalArgumentException("Shape height is " + shapeBounds.height + ", but should be positive.");
		setOpaque(false);
		setForeground(color);
		setFilled(filled);
		setLocation(shapeBounds.x, shapeBounds.y);
		setSize(shapeBounds.width+1, shapeBounds.height+1);
		shape.setFrame(0,0,shapeBounds.width,shapeBounds.height);
	}
	
	/**
	 * Returns true if the given (x,y) pixel coordinate is within the boundary of the shape, where (0,0)
	 * represents the upper left corner of the bounding box of the shape.
	 */
	public boolean contains(int x, int y) {
		return shape.contains(x,y);
	}
 	
	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	/**
	 * Sets the width and height of the Shape, in pixels.
	 */
	public void setSize(int width, int height) {
		shape.setFrame(0,0,width-1,height-1);
		super.setSize(width,height);
	}
	
	public void setCenter(int x, int y) {
		setLocation(x-getWidth()/2, y-getHeight()/2);
	}
	
	/**
	 * Sets bounding box of the Shape based on the coordinates of any two diagonally opposite corners.
	 * @param x1 the x coordinate of the first point
	 * @param y1 the y coordinate of the first point
	 * @param x2 the x coordinate of the second point
	 * @param y2 the y coordinate of the second point
	 */
	public void setCorners(int x1, int y1, int x2, int y2) {
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);
		int x = Math.min(x1,x2);
		int y = Math.min(y1,y2);
		setSize(width,height);
		setLocation(x,y);
		repaint();
	}
	
	public void setUpperLeft(int x, int y) {
		setLocation(x,y);
	}

  /**
   * Paints the Shape when requested by the Java's graphics system.  You should not need to
   * call this method.  If you want to specifically request that a component be repainted, call
   * {@link nip.Shape#repaint()}.  (If you do not see the shape, it may not have been added to
   * a visible container, it may be behind another graphic, its color may blend with the background, or
   * its coordinates may be outside the visible region of the container.)
   */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getForeground());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if (filled) {
			g2.setColor(getBackground());
			g2.fill(shape);
			g2.setColor(getForeground());
			g2.draw(shape);
		} else {
			g2.setColor(getForeground());
			g2.draw(shape);
		}
		if (selected) {
			g2.setStroke(THICK_STROKE);
			g2.draw(shape);
		}
	}

}

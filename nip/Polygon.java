package nip;
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * 
 * 
 * A Polygon is a closed shape specified by a series of (x,y) points.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jun 28, 2005
 */
public class Polygon extends Graphic {

	private static final long serialVersionUID = 1L;
	GeneralPath shape;
	boolean filled;
	
	Polygon(GeneralPath shape) {
		init(shape, Color.BLACK);
	}
	
	/**
	 * Creates a polygon with the given vertices.  This constructor takes a variable
	 * number of arguments to allow polygons to be created with arbitrariily many vertices.
	 * For example, calling the constructor with the parameters (x1, y1, x2, y2, x3, y3, x4, y4)
	 * would create a polygon with 4 sides.  By default, the polygon is black.
	 * 
	 * @param x1 the x coordinate of the first end point
	 * @param y1 the y coordinate of the first end point
	 * @param points the remaining coordinates x2, y2, x3, y3, etc.
	 */
	public Polygon(int x1, int y1, int... points) {
		GeneralPath path = new GeneralPath();
		path.moveTo(x1,y1);
		for (int i = 0; i < points.length; i+=2) {
			path.lineTo(points[i], points[i+1]);
		}
		path.closePath();
		init(path, Color.BLACK);
	}

	void init(GeneralPath shape, Color color) {
		if (shape == null)
			throw new IllegalArgumentException("A shape must be provided.");
		this.shape = shape;
		java.awt.Rectangle shapeBounds = shape.getBounds();
		if (shape.getBounds().width < 0)
			throw new IllegalArgumentException("Shape width is " + shapeBounds.width + ", but should be positive.");
		if (shape.getBounds().height < 0)
			throw new IllegalArgumentException("Shape height is " + shapeBounds.height + ", but should be positive.");
		setOpaque(false);

		setForeground(color);
		setLocation(shapeBounds.x, shapeBounds.y);
		setSize(shapeBounds.width+1, shapeBounds.height+1);
	}
	
	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	
	/**
	 * This contains method reports true if the given point is contained within the polygon..
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 */
	public boolean contains(int x, int y) {
		return shape.contains(x,y);
	}

	  /**
	   * Paints the Line when requested by the Java's graphics system.  You should not need to
	   * call this method.  If you want to specifically request that a component be repainted, call
	   * {@link nip.Polygon#repaint()}.  (If you do not see the polygon, it may not have been added to
	   * a visible container, it may be behind another graphic, its color may blend with the background, or
	   * its coordinates may be outside the visible region of the container.)
	   */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getForeground());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		java.awt.Rectangle bounds = shape.getBounds();
		g2.translate(-bounds.x, -bounds.y);
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
		if (selected)
			g2.setStroke(THICK_STROKE);
	}
	
	/**
	 * Returns a String representation of the polygon.
	 */
	public String toString() {
		return "Polygon["+shape.toString()+"]";
	}
}

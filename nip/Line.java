package nip;
import java.awt.*;
import java.awt.geom.Line2D;


/**
 * 
 * 
 * A Line is a visual display of a line segment defined by the coordinates of two pixels at its end points.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jun 28, 2005
 */
public class Line extends Graphic {

	private static final long serialVersionUID = 1L;
	Line2D shape;
	java.awt.Rectangle selectionBox = new java.awt.Rectangle(0,0,5,5);
	boolean filled;
	
	public Line(Line2D shape) {
		this(shape, Color.GRAY);
	}
	
	/**
	 * Creates a Line with the given end points.
	 * @param x1 the x coordinate of the first end point
	 * @param y1 the y coordinate of the first end point
	 * @param x2 the x coordinate of the second end point
	 * @param y2 the y coordinate of the second end point
	 */
	public Line(int x1, int y1, int x2, int y2) {
		this(new Line2D.Double(x1,y1,x2,y2));
	}

	Line(Line2D shape, Color color) {
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
		setFilled(filled);
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
	 * Changes the endpoints of the line, as well as the bounding box of the graphic based on any two diagonally opposite corners.
	 * @param x1 the x coordinate of the first point
	 * @param y1 the y coordinate of the first point
	 * @param x2 the x coordinate of the second point
	 * @param y2 the y coordinate of the second point
	 */
	public void setCorners(int x1, int y1, int x2, int y2) {
		shape.setLine(x1,y1,x2,y2);
		int width = Math.abs(x2-x1);
		int height = Math.abs(y2-y1);
		int x = Math.min(x1,x2);
		int y = Math.min(y1,y2);
		setSize(width,height);
		setLocation(x,y);
		repaint();
	}
	
	public void setSize(int x, int y) {
		if (shape.getX1() < shape.getX2() ^ shape.getY1() > shape.getY2())
			shape.setLine(0,0,x-1,y-1);
		else
			shape.setLine(x-1,0,0,y-1);
		super.setSize(x,y);
	}
	
	public void setCenter(int x, int y) {
		setLocation(x-getWidth()/2, y-getHeight()/2);
	}
	
	public void setUpperLeft(int x, int y) {
		setLocation(x,y);
	}
	
	/**
	 * This contains method reports true if the given point is on, or very near, the line segment.
	 * The reason for this inexactness is to support selection; it would be difficult for a user to position the cursor
	 * exactly on the thin region defined by the line.
	 */
	public boolean contains(int x, int y) {
		selectionBox.x = x-2;
		selectionBox.y = y-2;
		return selectionBox.intersectsLine(shape);
	}
	
	boolean selectAt(int x, int y) {
		return contains(x,y);
	}

	  /**
	   * Paints the Line when requested by the Java's graphics system.  You should not need to
	   * call this method.  If you want to specifically request that a component be repainted, call
	   * {@link nip.Line#repaint()}.  (If you do not see the line, it may not have been added to
	   * a visible container, it may be behind another graphic, its color may blend with the background, or
	   * its coordinates may be outside the visible region of the container.)
	   */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getForeground());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if (selected)
			g2.setStroke(THICK_STROKE);
		g2.draw(shape);
	}
	
	/**
	 * Returns a String representation of the Line, including the coordinates of its end points.
	 */
	public String toString() {
		return "Line[(" + shape.getX1() + "," + shape.getY1() +
			") to ( "+ shape.getX2() + "," + shape.getY2() +
			") at location " + getLocation() + "]";
	}
}

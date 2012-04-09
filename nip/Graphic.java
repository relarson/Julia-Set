package nip;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Stroke;

import javax.swing.JPanel;

/**
 * A Graphic is a single visual component with a location and size.
 * To be seen, a graphic must be added to a visible container, such as a
 * {@link nip.GraphicsPanel GraphicsPanel}.  Other components cannot be placed inside a Graphic.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jun 28, 2005
 */
public abstract class Graphic extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2618830072283902312L;
	protected static final Stroke THICK_STROKE = new BasicStroke(1);
	boolean selected;
	
	protected Graphic() {
	}
	
	/**
	 * Sets the location of this Graphic so that its center is at the given pixel coordinates
	 * within its container.
	 * @param x in pixels
	 * @param y in pixels
	 */
	public void setCenter(int x, int y) {
		setLocation(x-getWidth()/2, y-getHeight()/2);
	}
	

	/**
	 * @return the x coordinate of the center point
	 */
	public int getCenterX() {
		return getX()+getWidth()/2;
	}
	
	/**
	 * @return the y coordinate of the center point
	 */
	public int getCenterY() {
		return getY()+getHeight()/2;
	}
	
	/**
	 * Sets the location of this Graphic so that its upper left corner is at the given pixel coordinates
	 * within its container.  (This is the same as <tt>setLocation</tt>.)
	 * @param x in pixels
	 * @param y in pixels
	 */
	public void setUpperLeft(int x, int y) {
		setLocation(x,y);
	}
	
	/**
	 * Sets whether or not the graphic should be filled 
	 * (painted in a solid color).
	 * A Graphic will ignore this method if its filled status cannot be changed.
	 * @param filled whether or not the Graphic should be filled
	 */
	public void setFilled(boolean filled) {
	}
	
	/**I
	 * If a Graphic is filled, it will be painted in a solid color.
	 * @return true if the Graphic is filled.
	 */
	public boolean isFilled() {
		return false;
	}
	
	/**
	 * Sets the color that will be used to paint the outline of the Graphic.
	 * @param c the desired color
	 */
	public void setLineColor(java.awt.Color c) {
		setForeground(c);
	}
	
	/**
	 * Sets the color that will be used to fill the Graphic.
	 * The metod setFilled(true) is also called automatically.
	 * @param c the desired color
	 */
	public void setFillColor(java.awt.Color c) {
		setBackground(c);
		setFilled(true);
	}

	
	/**
	 * Sets whether or not the Graphic is selected.
	 * If a Graphic is selected, it may paint itself differently to inform the viewer.
	 * @param selected whether or not the Graphic should be selected
	 */
	void setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			repaint();
		}
	}

	/**
	 * If a Graphic is selected, it may paint itself differently to inform the viewer.
	 * @return whether or not the Graphic should be selected
	 */
	boolean isSelected() {
		return selected;
	}	
	
	/**
	 * A Graphic may not contain other components, so this method is <b>not supported</b>.
	 * Calling this method will cause an UnsupportedOperationException.
	 */
	public Component add(Component c) {
		throw new UnsupportedOperationException("Adding a component to a Graphic is not permitted.");
	}
	
	/**
	 * @return a String description of this graphic, including its class name, upper left corner, and pixel dimensions.
	 */
	public String toString() {
		return this.getClass().getName() + "[" + getBounds() + "]";
	}

}

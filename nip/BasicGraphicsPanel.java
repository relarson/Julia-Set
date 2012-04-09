package nip;

import javax.swing.*;
import java.awt.*;

/**
 * A GraphicsPanel is a container in which graphics components can be displayed.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jul 5, 2005
 */
public class BasicGraphicsPanel extends JLayeredPane {
	private static final long serialVersionUID = 1L;
	Image mainImage;
	ShapesPanelDisplay spd;

	/**
	 * Creates a GraphicsPanel with the given dimensions. 
	 * @param width in pixels
	 * @param height in pixels
	 */
	public BasicGraphicsPanel(int width, int height) {
		Dimension d = new Dimension(width,height);
		setPreferredSize(d);
		setMinimumSize(d);
		setMaximumSize(d);
		setLayout(null);
		mainImage = new Image(width,height);
		add(mainImage,DEFAULT_LAYER);
		add((spd = new ShapesPanelDisplay(width,height)), DRAG_LAYER);  // same coordinate system
	}

	/**
	 * Adds the given component to the GraphicsPanel for display.
	 */
	public Component add(Component c) {
		spd.add(c,DEFAULT_LAYER);
		repaint();
		return c;
	}

	/**
	 * Removes the given component from the GraphicsPanel.
	 */
	public void remove(Component c) {
		if (c != null && c.getParent() == spd) {
			spd.remove(c);
			repaint();
		} else {
			super.remove(c);
		}
	}

	/**
	 * Removes all components from the GraphicsPanel, except for the background image.
	 */
	public void clear() {
		spd.removeAll();
	}

	/**
	 * Places the given component behind all other components.
	 */
	public void moveToBack(Component arg0) {
		spd.moveToBack(arg0);
	}

	/**
	 * Places the given component in front of  all other components.
	 */
	public void moveToFront(Component arg0) {
		spd.moveToFront(arg0);
	}

	/**
	 * Gets the background image of this graphics panel.
	 * @return the background image
	 */
	public Image getMainImage() {
		return mainImage;
	}

}

class ShapesPanelDisplay extends JLayeredPane {

	private static final long serialVersionUID = 1L;

	ShapesPanelDisplay(int width, int height) {
		setSize(width, height);
	}
}

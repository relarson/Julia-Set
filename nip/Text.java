package nip;

import javax.swing.JLabel;

/**
 * A Text is a graphics component that displays a String value.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jul 5, 2005
 */
public class Text extends JLabel {

	private static final long serialVersionUID = 1L;
	JLabel label;
	/**
	 * Creates a Text object with the given text.
	 * @param text
	 */
	public Text(String text) {
		setOpaque(false);
		label = new JLabel();
		setText(text);
	}

	public void setText(String text) {
		super.setText(text);
		setSize(getPreferredSize());
		repaint();
	}
}

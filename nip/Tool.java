package nip;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.event.MouseInputListener;

/**
 * The Tool class is an abstract class which, when extended, provides NIP with the
 * information needed to communicate with a tool.  It does nothing on its own;
 * the methods here are merely a way for the tool to interact with a NIP instance.
 * The classes which extend it must make all the calls necessary to actually manipulate
 * the NIP images.
 * 
 * @author Jonathon Lundy
 * June 2009
 *
 */
public abstract class Tool implements ActionListener, MouseInputListener {
	
	protected NIP nip; //NIP instance currently associated with the tool
	
	/**
	 * Sets the NIP instance variable to point to the provided NIP window.
	 * This method is called whenever by the NIP window NIP.setTool is called, 
	 * so this is always correct (unless the same instance of a tool is being used
	 * in multiple NIP windows, which should be avoided at all costs), and there should
	 * be no reason to call this elsewhere.
	 * @param nip The NIP instance that this Tool is currently being used in.
	 */
	void setNIP(NIP nip) {
		this.nip = nip;
	}
	
	/**
	 * This method returns an array of Strings which correspond to the names of
	 * commands that will appear in the NIP Methods menu.  The names need not
	 * be related to method names, but should be as self-explanatory as possible.
	 * This method is most easily implemented as follows:
	 * return new String[] = {"Option 1", "Option 2", ...};
	 * 
	 * Note: To avoid errors, a method that wishes to display no methods in the
	 * Methods menu should return a new String[0]() instead of null.
	 * @return An array of String objects containing the names of commands.
	 */
	public abstract String[] getEventNames();
	
	/**
	 * When a command in the Method menu is pressed in the NIP instance, this
	 * method is called, with the parameter corresponding to the name of the 
	 * command clicked upon (this will be one of the Strings in the array returned
	 * by getEventNames).  The implementation of this class will determine how
	 * the tool should respond when a command is pressed.  It can be implemented by 
	 * a chain of if-else if branches, one corresponding to each possible command.
	 * @param name
	 */
	public abstract void actionNameCalled(String name);
	
	/**
	 * When a Method menu command is chosen, this method gets the name of that
	 * command and passes that string to the actionNameCalled method.  This works
	 * as is and should not be overridden unless there's a good reason to do so.
	 */
	public void actionPerformed(ActionEvent e) {
        JMenuItem source = (JMenuItem)e.getSource();
        actionNameCalled(source.getText());
        nip.repaint();
	}
	
	public void mouseClicked(MouseEvent e) {
		//By default, does nothing.  Must be overridden 
		//in subclasses to add functionality.
	}

	public void mouseEntered(MouseEvent e) {
		//By default, does nothing.  Must be overridden 
		//in subclasses to add functionality.
	}

	public void mouseExited(MouseEvent e) {
		//By default, does nothing.  Must be overridden 
		//in subclasses to add functionality.
	}

	public void mousePressed(MouseEvent e) {
		//By default, does nothing.  Must be overridden 
		//in subclasses to add functionality.
	}

	public void mouseReleased(MouseEvent e) {
		//By default, does nothing.  Must be overridden 
		//in subclasses to add functionality.
	}

	public void mouseDragged(MouseEvent arg0) {
		//By default, does nothing.  Must be overridden 
		//in subclasses to add functionality.
	}

	public void mouseMoved(MouseEvent arg0) {
		//By default, does nothing.  Must be overridden 
		//in subclasses to add functionality.
	}
	
}

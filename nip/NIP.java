package nip;

import javax.swing.*;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * NIP is an application skeleton that can be used as a starting point for
 * a variety of projects involving image manipulation and graphics.
 * 
 * <P><B>The NIP User Interface:</B>
 * <UL>
 * <LI><B>Panels:</B> Each NIP window contains one or more graphics panel in which images and graphics components (lines, rectangles, etc.)
 * can be displayed.
 * <LI><B>Loading Images:</B> The <em>browse</em> button above each panel can be used to select an image (gif or jpeg) to be loaded as the background
 * image (main image) of that panel.
 * Alternatively, a file name can be typed into the text field to load the image.
 * <LI><B>Adding Tools:</B> By itself, NIP is only a program skeleton, and so it does very little.
 * Programmers add to the functionality of NIP by writing <em>tools</em> and adding them to NIP using the
 * {@link nip.NIP#addTool(Object)} method.
 * <LI><B>Selecting Tools:</B> Each tool appears as a button in a list at the left
 * edge of the NIP window.  Pressing a button makes that tool the <em>current tool</em>.
 * NIP automatically adds the tool as a listener for the user's mouse events, as described below.
 * <LI><B>The Methods Menu:</B> When the user selects a tool, NIP fills the <em>methods</em> menu with
 * that tool's graphics methods, as described in the sections below.
 * <LI><B>Source/Target Buttons:</B> Certain methods create a new image by modifying the pixels of an old image.  
 * These buttons allow the user to select which panel is/are the source(s) for the method, and 
 * which panel is the target.
 * <LI><B>Status Panel:</B> The status panel at the bottom of the NIP can be used to display text messages to the user.
 * It can be updated by using the {@link nip.NIP#setStatusText(String)} method.
 * </UL>
 * <P>
 * <B>Creating Tools for NIP:</b>
 * <P>
 * One of the main purposes of NIP is to support image processing, but NIP doesn't have built-in functions for this.
 * Instead, your program will supply various image processing <em>tools</em> for NIP to use.
 * These tools will appear as a set of buttons on the left edge of the NIP window.
 * When the user selects a tool, NIP will call the tool's {@link nip.Tool#getEventNames()} method
 * to add a list of commands to the Methods menu in the NIP window.  When the user clicks one of these commands,
 * the {@link nip.Tool#actionNameCalled(String)} method in the Tool is triggered, with the name of the command clicked as the parameter.
 * The tool can then respond appropriately.  Tools can also react to mouse events which occur on the NIP GraphicsPanels by overriding
 * the mouse events in the Tool class, which do nothing on their own.  More information can be found in the Tool class.
 * 
 * <P>
 * Note that a NIP window is created with one or more {@link nip.GraphicsPanel GraphicsPanel} objects,
 * each with a certain width and height in pixels.
 * The panels are displayed left to right, and they are numbered from 0.
 * That is, the leftmost is panel 0, the next one is panel 1, and so on.
 * Users can load images into NIP as described above, but
 * NIP also has constructors that let you specify images to be loaded into the panels upon initialization, so that you don't have
 * to load them in manually each time you run NIP.
 * In addition, a tool can call methods on a {@link nip.GraphicsPanel GraphicsPanel} to load in a background image and to
 * display additional images, shapes, lines, etc.
 * There are methods for accessing the GraphicsPanels (and their associated {@link nip.Image Images}) that have been
 * marked as Source(s) or Targets by the user; also there is a method to access a specific GraphicsPanel (see below).
 * 
 * <P>
 * NIP is a modification of YOPS, an application created by Professor Ken Goldman to play the same purpose as NIP,
 * and provided much of the same functionality.  This main class has been changed a fair amount to remove the reflection
 * found in YOPS as well as to generally simplify the way it works.  Some of the other classes were slightly modified,
 * while many are unchanged.
 * 
 * @author Jonathon Lundy
 * June 2009
 * 
 * Adapted from the work of Professor Ken Goldman, July 2005
 */
public class NIP extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_RASTER_WIDTH = 200;
	private static final int DEFAULT_RASTER_HEIGHT = 300;

	private int numPanels;  // the number of graphics panels
	private GraphicsPanel[] panels; // the graphics panels, left (0) to right (numPanels-1)

	private Tool tool;  // the current tool
	private LinkedList<Tool> tools = new LinkedList<Tool>();  // list of available tools, added using addtool
	private ButtonGroup toolButtonGroup = new ButtonGroup();  // for selection of one tool at a time
	private Box toolChoices; // the panel of tool buttons
	private JMenu methodChoices; //drop-down menu of a tool's methods

	private static Applet applet; // when NIP is used in an applet, this variable refers to the containing Applet

	private StatusPanel status; //text panel at bottom of screen that can display messages

	private ButtonGroup sourceGroup1 = new ButtonGroup(); //primary source buttons
	private ButtonGroup sourceGroup2 = new ButtonGroup(); //secondary source buttons
	private ButtonGroup targetGroup = new ButtonGroup(); //target buttons

	/**
	 * Creates a NIP with one panel of a default width and height.
	 */
	public NIP() {
		this(null, DEFAULT_RASTER_WIDTH, DEFAULT_RASTER_HEIGHT, 1, (String[]) null);
	}

	/**
	 * Creates a NIP with one panel of the given width and height
	 * @param width in pixels
	 * @param height in pixels 
	 */
	public NIP(int width, int height) {
		this(null, width, height, 1, (String[]) null);
	}

	/**
	 * Creates a NIP with the given number of panels, each with the same given width and height.
	 * @param width in pixels
	 * @param height in pixels
	 * @param numPanels the number of panels to be created
	 */
	public NIP(int width, int height, int numPanels) {
		this(null, width, height, numPanels, (String[]) null);
	}

	/**
	 * Creates a NIP with the given number of panels, each with the same given width and height.
	 * @param width in pixels
	 * @param height in pixels
	 * @param numPanels the number of panels to be created
	 * 	@param imageFileNames - one or more Strings naming the images to be loaded into panels, left to right
	 */
	public NIP(int width, int height, int numPanels, String ... imageFileNames) {
		this(null, width, height, numPanels, imageFileNames);
	}

	/**
	 * Creates a NIP with one panel of a default width and height, and with the given tool.
	 * (See the NIP overview for details about tools.)
	 * @param tool the tool whose methods should be made available to the user 
	 */
	public NIP(Tool tool) {
		this(tool, DEFAULT_RASTER_WIDTH, DEFAULT_RASTER_HEIGHT, 1, (String[]) null);
	}

	/**
	 * Creates a NIP with the given tool and given number of panels, each of a default width and height.
	 * (See the NIP overview for details about tools.)
	 * @param tool the tool whose methods should be made available to the user
	 * @param numPanels the number of panels to be created
	 */
	public NIP(Tool tool, int numPanels) {
		this(tool, DEFAULT_RASTER_WIDTH, DEFAULT_RASTER_HEIGHT, numPanels, (String[]) null);
	}

	/**
	 * Creates a NIP with the given tool and one panel of the given width and height.
	 * (See the NIP overview for details about tools.)
	 * @param tool the tool whose methods should be made available to the user
	 * @param width in pixels
	 * @param height in pixels
	 */
	public NIP(Tool tool, int width, int height) {
		this(tool, width, height, 1, (String[]) null);
	}

	/**
	 * Creates a NIP with the given tool and the given number of panels, each of the given width and height.
	 * (See the NIP overview for details about tools.)
	 * @param tool the tool whose methods should be made available to the user
	 * @param width in pixels
	 * @param height in pixels
	 * @param numPanels the number of panels to be created
	 */
	public NIP(Tool tool, int width, int height, int numPanels) {
		this(tool, width, height, numPanels, (String[]) null);
	}


	/**
	 * Creates a NIP with the given tool and the given number of panels, each of the given width and height, and with
	 * one or more provided image files loaded as the background images of the panels, left to right.  If the number of
	 * image file names exceeds the number of panels, then the extra file names are ignored.
	 * (See the NIP overview for details about tools.)
	 * @param tool the tool whose methods should be made available to the user
	 * @param width in pixels
	 * @param height in pixels
	 * @param numPanels the number of panels to be created
	 * 	@param imageFileNames - one or more Strings naming the images to be loaded into panels, left to right
	 */
	public NIP(Tool tool, int width, int height, int numPanels, String... imageFileNames) {
		super("NIP");

		// Create the graphics panels
		this.numPanels = numPanels;
		panels = new GraphicsPanel[numPanels];

		// Create the mainPanel with the graphics panels and their browse buttons for image loading
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel browseBar = new JPanel(new GridLayout(0,numPanels));
		JPanel graphicsPanels = new JPanel(new GridLayout(0,numPanels));
		JPanel buttonPanel = new JPanel(new GridLayout(0,numPanels));
		mainPanel.add(browseBar, BorderLayout.NORTH);
		mainPanel.add(graphicsPanels, BorderLayout.CENTER);
		graphicsPanels.setDoubleBuffered(true);
		if (numPanels > 1) {
			mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		}
		for (int i = 0; i < numPanels; i++) {
			graphicsPanels.add(panels[i] = new GraphicsPanel(width, height, this));
			browseBar.add(panels[i].getFileNamePanel());
			JPanel panelButtons = new JPanel(new GridLayout(0,1));
			panelButtons.add(new PanelAssociatedRadioButton("Target", i == 0, panels[i], targetGroup));
			panelButtons.add(new PanelAssociatedRadioButton("Source 1", i == 1, panels[i], sourceGroup1));
			panelButtons.add(new PanelAssociatedRadioButton("Source 2", i == 2, panels[i], sourceGroup2));
			buttonPanel.add(panelButtons);
		}

		// Set up the menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(methodChoices = new JMenu("methods"));
		setJMenuBar(menuBar);

		// Create outerPanel, which contains everything except the toolbar
		JPanel outerPanel = new JPanel(new BorderLayout());
		toolChoices = new Box(BoxLayout.Y_AXIS);
		outerPanel.add(toolChoices,BorderLayout.WEST);
		outerPanel.add(mainPanel,BorderLayout.CENTER);

		// Add the outerPanel and the toolbar to the frame
		JPanel p = (JPanel) getContentPane();
		p.setLayout(new BorderLayout());
		p.add(outerPanel,BorderLayout.CENTER);
		p.add(status = new StatusPanel("NIP") ,BorderLayout.SOUTH);

		// Set the tool, if one was provided as a parameter.
		setTool(tool);

		// If image file names were provided as parameters, load the images.
		if (imageFileNames != null) {
			for (int i = 0; i < imageFileNames.length; i++)
				if (i < numPanels)
					panels[i].getMainImage().loadImage(imageFileNames[i]);
		}
		pack();
		setVisible(true);
		
		// When the NIP window is closed, end the program.
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (applet == null)
					System.exit(0);
			}
		});
	}

	/**
	 * Returns the GraphicsPanel for the given index, where 0 is the leftmost panel, 1 is the next panel, and so on.
	 * @param panelNumber the index of the desired GraphicsPanel within this NIP
	 * @return the GraphicsPanel for the given index
	 */
	public GraphicsPanel getGraphicsPanel(int panelNumber) {
		try {
			return panels[panelNumber];
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			throw new IllegalArgumentException("GraphicsPanel " + panelNumber + " doesn't exist.  Use a number from 0 to " + (panels.length-1));
		}
	}

	/**
	 * Gets the number of GraphicsPanels in this NIP object.
	 * @return Number of panels.
	 */
	public int getNumPanels() {
		return numPanels;
	}

	/**
	 * Returns the GraphicsPanel currently selected as Source 1 by the user.
	 * @return The primary source GraphicsPanel object.
	 */
	public GraphicsPanel getPrimarySourcePanel() {
		Enumeration<AbstractButton> e = sourceGroup1.getElements();
		while (e.hasMoreElements()) {
			AbstractButton ab = e.nextElement();
			PanelAssociatedRadioButton btn = (PanelAssociatedRadioButton)ab;
			if (btn.isSelected()) return btn.getPanel();
		}
		return null;
	}

	/**
	 * Returns the GraphicsPanel currently selected as Source 2 by the user.
	 * @return The secondary source GraphicsPanel object.
	 */
	public GraphicsPanel getSecondarySourcePanel() {
		Enumeration<AbstractButton> e = sourceGroup2.getElements();
		while (e.hasMoreElements()) {
			AbstractButton ab = e.nextElement();
			PanelAssociatedRadioButton btn = (PanelAssociatedRadioButton)ab;
			if (btn.isSelected()) return btn.getPanel();
		}
		return null;
	}

	/**
	 * Returns the GraphicsPanel currently selected as the target by the user.
	 * @return The target GraphicsPanel object.
	 */
	public GraphicsPanel getTargetPanel() {
		Enumeration<AbstractButton> e = targetGroup.getElements();
		while (e.hasMoreElements()) {
			AbstractButton ab = e.nextElement();
			PanelAssociatedRadioButton btn = (PanelAssociatedRadioButton)ab;
			if (btn.isSelected()) return btn.getPanel();
		}
		return null;
	}
	/**
	 * Returns the Image associated with the GraphicsPanel currently selected as Source 1 by the user.
	 * @return The primary source Image object.
	 */
	public Image getPrimarySourceImage() {
		GraphicsPanel p = getPrimarySourcePanel();
		if (p != null) return p.getMainImage();
		else return null;
	}
	/**
	 * Returns the Image associated with the GraphicsPanel currently selected as Source 2 by the user.
	 * @return The secondary source Image object.
	 */
	public Image getSecondarySourceImage() {
		GraphicsPanel p = getSecondarySourcePanel();
		if (p != null) return p.getMainImage();
		else return null;
	}

	/**
	 * Returns the Image associated with the GraphicsPanel currently selected as the target by the user.
	 * @return The target Image object.
	 */
	public Image getTargetImage() {
		GraphicsPanel p = getTargetPanel();
		if (p != null) return p.getMainImage();
		else return null;
	}


	/**
	 * Sets the current tool of this NIP to the provided object.
	 * If there is already a tool, then it is replaced with the new one.
	 * If the parameter is <code>null</code>, then no tool methods will be used.
	 * Note that the method setTool automatically calls addListener for the given tool,
	 * and calls removeListener for the old tool.
	 * @param tool the tool to be used
	 */
	public void setTool(Tool tool) {
		removeListener(this.tool);
		this.tool = tool;
		addTool(tool);
		updateFunctions();
		addListener(tool);
	}
	
	/**
	 * Stops a tool from listening for mouseEvents on all GraphicsPanels.
	 * @param t
	 */
	private void removeListener(Tool t) {
		for (GraphicsPanel gp : panels) {
			gp.removeMouseMotionListener(t);
			gp.removeMouseListener(t);
		}
	}
	
	/**
	 * Adds the specified tool as a listener for mouseEvents on all GraphicsPanels.
	 * @param t
	 */
	private void addListener(Tool t) {
		for (GraphicsPanel gp : panels) {
			gp.addMouseMotionListener(t);
			gp.addMouseListener(t);
		}
	}

	/**
	 * Gets the current tool.
	 * @return the current tool, or null if there isn't one
	 */
	public Tool getTool() {
		return tool;
	}

	/**
	 * Adds the given tool to a user-selectable list.  The toString method
	 * of the given tool is displayed to the user for selection purposes.
	 * When the tool is selected by the user, the NIP methods menu will list that tool's
	 * methods, as described in the NIP overview at the beginning of this file.
	 * In addition, the currently selected tool is automatically added as a
	 * listener for mouse events, also described in the NIP overview. 
	 * @param tool the tool to be added
	 */
	public void addTool(final Tool tool) {
		if (tool == null || tools.contains(tool)) return;
		tools.add(tool);
		JToggleButton button = new JToggleButton(new AbstractAction(tool.toString()) {
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent arg0) {
				setTool(tool);
			}	
		});
		toolButtonGroup.add(button);
		button.setSelected(tool == this.tool);
		toolChoices.add(button);
		pack();		
	}


	/**
	 * Adds the methods of the selected tool to the methods menu.
	 */
	private void updateFunctions() {
		methodChoices.removeAll();
		if (tool != null) {
			tool.setNIP(this); //adds the tool to be associated with this NIP instance
			for (String s : tool.getEventNames()) {
				JMenuItem mi = new JMenuItem();
				mi.setText(s);
				mi.addActionListener(tool);		
				methodChoices.add(mi);
			}
		}
	}	


	/**
	 * @return Returns the applet.
	 */
	public static Applet getApplet() {
		return applet;
	}

	/**
	 * When NIP is used within an Applet, this static method should be called before
	 * the first call to the NIP constructor.  This will enable NIP to use the applet
	 * to load images and sounds from the server.
	 * @param applet The Applet that NIP should use to load remote image and sound files.
	 */
	public static void setApplet(Applet applet) {
		NIP.applet = applet;
	}

	/**
	 * Updates the status text at the bottom of the NIP window to the provided text.
	 * @param str Text to be displayed.
	 */
	public void setStatusText(String str) {
		status.setText(str);
	}

	/**
	 * Returns the status text currently displayed at the bottom of the NIP window.
	 * @return Status text string.
	 */
	public String getStatusText() {
		return status.getText();
	}

	/**
	 * A JRadioButton that is associated with a given GraphicsPanel.
	 * @author Jonathon Lundy
	 * June 2, 2009
	 *
	 */
	private class PanelAssociatedRadioButton extends JRadioButton {
		GraphicsPanel panel; //panel associated with this button
		private static final long serialVersionUID = 1L;
		/**
		 * Creates a new JRadioButton that points to a given GraphicsPanel.
		 * @param text The text to be displayed next to the button.
		 * @param selected Whether the button should start as selected or not.
		 * @param panel The panel associated with this button.
		 * @param group The ButtonGroup this button will be part of, if any.
		 */
		PanelAssociatedRadioButton(String text, boolean selected, GraphicsPanel panel, ButtonGroup group) {
			super(text, selected);
			this.panel = panel;
			if (group != null) {
				group.add(this);
			}
		}

		/**
		 * Gets the GraphicsPanel associated with this button.
		 * @return The GraphicsPanel this button points to.
		 */
		GraphicsPanel getPanel() {
			return panel;
		}
	}

	/**
	 * A StatusPanel is a JPanel with a single Text object on it, allowing
	 * text to be displayed in a frame with a simple interface for retrieving
	 * and updating the displayed message.
	 * @author Jonathon Lundy
	 * Created June 2, 2009
	 *
	 */
	private class StatusPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private Text text; //the text area contained in this panel
		/**
		 * Creates a new StatusPanel with the specified initial message.
		 * @param str The string to be displayed initially.
		 */
		StatusPanel(String str) {
			super();
			text = new Text(str);
			text.setVisible(true);
			add(text);
			pack();
			setVisible(true);
		}
		/**
		 * Gets the text currently displayed on the StatusPanel.
		 * @return The current status message.
		 */
		public String getText() {
			return text.getText();
		}

		/**
		 * Sets the text currently displayed on the StatusPanel.
		 * @param str The string to be shown in the Panel.
		 */
		public void setText(String str) {
			text.setText(str);
		}
	}
}
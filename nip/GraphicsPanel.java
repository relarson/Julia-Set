package nip;

import javax.swing.*;

import sun.print.ProxyPrintGraphics;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;

/**
 * A GraphicsPanel is a container in which graphics components can be displayed.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jul 5, 2005
 * 
 * Modified by Jonathon Lundy, June 2009
 */
public class GraphicsPanel extends BasicGraphicsPanel{
	private static final long serialVersionUID = 1L;
	JTextField fileNameField;
	JPanel fileNamePanel;
	NIP mainContainer;

	/**
	 * Creates a GraphicsPanel with the given dimensions, and the given NIP container.
	 * If the NIP container is not null, then mouse events will be forwarded to the given NIP container. 
	 * @param width in pixels
	 * @param height in pixels
	 * @param nip the container in which this GraphicsPanel will be shown
	 */
	GraphicsPanel(int width, int height, NIP nip) {
		super(width,height);
		mainContainer = nip;
		
		fileNameField = new JTextField();
		fileNameField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mainImage.loadImage(fileNameField.getText());
				repaint();
			}
		});
		JButton browse = new JButton("browse");
		browse.addActionListener(new ActionListener() {
			java.io.File currentDirectory = null;
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
			    chooser.setFileFilter(new RestrictedFileFilter());
				if (currentDirectory != null)
					chooser.setCurrentDirectory(currentDirectory);
			    int returnVal = chooser.showOpenDialog(GraphicsPanel.this);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    		fileNameField.setText(chooser.getSelectedFile().getAbsolutePath());
			    		currentDirectory = chooser.getSelectedFile().getParentFile();
			    		mainImage.loadImage(fileNameField.getText());
			    		repaint();
			    }
			}
		});

		fileNamePanel = new JPanel(new BorderLayout());
		fileNamePanel.add(fileNameField,BorderLayout.CENTER);
		fileNamePanel.add(browse,BorderLayout.EAST);
	}
	
	JPanel getFileNamePanel() {
		return fileNamePanel;
	}

	/**
	 * Initiates a print job, scaling this graphics panel to fit, centered, on a single page.
	 * @param title the title of the image to appear on the page
	 */
	public void print(String title) {
		PrintJob pj = Toolkit.getDefaultToolkit().getPrintJob(mainContainer,title,null);
		if (pj != null) {
			Dimension pageSize = pj.getPageDimension();
			BufferedImage buffer = new BufferedImage(pageSize.width*4/5, pageSize.height*4/5, BufferedImage.TYPE_INT_ARGB);
			Graphics2D bufferedGraphics = (Graphics2D) buffer.getGraphics();
			bufferedGraphics.setColor(Color.WHITE);
			bufferedGraphics.fillRect(0,0,pageSize.width, pageSize.height);
			double scale = Math.min(((double) buffer.getWidth())/getWidth(), ((double) buffer.getHeight())/getHeight());
			bufferedGraphics.scale(scale,scale);
			paint(bufferedGraphics);
			
			Graphics g = pj.getGraphics();
			if (g instanceof ProxyPrintGraphics)
				g = ((ProxyPrintGraphics) g).getGraphics();
			g.drawString(title, pageSize.width/10, pageSize.height/14);
			g.translate((int) (pageSize.width-scale*getWidth())/2, (int) (pageSize.height-scale*getHeight())/2); // create 10% margin
			g.drawImage(buffer, 0, 0, null);
			pj.end();
		}
	}

	/**
	 * This method is called by the Java event loop when mouse events occur.
	 * You should not call this method directly.
	 */
	public void mouseEntered(MouseEvent me) {
		if (mainContainer == null) return;
		if (me.getComponent() instanceof Graphic)
			((Graphic) me.getComponent()).setSelected(true);
	}

	/**
	 * This method is called by the Java event loop when mouse events occur.
	 * You should not call this method directly.
	 */
	public void mouseExited(MouseEvent me) {
		if (mainContainer == null) return;
		if (me.getComponent() instanceof Graphic)
			((Graphic) me.getComponent()).setSelected(false);
	}
	
	/**
	 * Gets the NIP instance containing this GraphicsPanel.
	 * @return A NIP window.
	 */
	public NIP getNIPContainer() {
		return mainContainer;
	}
	
	
}


class RestrictedFileFilter extends javax.swing.filechooser.FileFilter {
    File[] classPaths;
    static HashSet<String> imageExtensions;
    RestrictedFileFilter() {
    		if (imageExtensions == null) {
    			imageExtensions = new HashSet<String>();
    			imageExtensions.add(".jpg");
    			imageExtensions.add(".jpeg");
    			imageExtensions.add(".gif");
    		}
    }
    public boolean accept(File f) {
      if (f.isDirectory())
         return true;
      String cand = f.getAbsolutePath().toLowerCase();
      int i = cand.lastIndexOf('.');
      if (i < 0)
      	return false;
      String suffix = cand.substring(i);
      return imageExtensions.contains(suffix);
    }

    public String getDescription() {
      return ("image files (*.gif, *.jpg, *.jpeg)");
    }
  }

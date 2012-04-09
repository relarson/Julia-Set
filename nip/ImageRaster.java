
/**
 * ImageRaster.java
 *
 *
 * Created: Tue Feb 18 10:45:13 2003
 *
 * @author Kenneth J. Goldman
 * 
 * Modified by Jonathon Lundy, June 2009
 */

package nip;
import java.util.HashMap;
import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

class ImageRaster extends BufferedImage {
	private static final int DEFAULT_SIZE = 128;
	int width, height;
	private MediaTracker tracker = new MediaTracker(new JPanel());
	private int imageId = 1;
	Dimension size;
	HashMap<String,java.awt.Image> imagesFromFiles = new HashMap<String,java.awt.Image>();
	
	public ImageRaster() {
		this(DEFAULT_SIZE, DEFAULT_SIZE);
	}

	public ImageRaster(int width, int height) {
		super(width, height, TYPE_INT_ARGB);
		this.width = width;
		this.height = height;
		size = new Dimension(width,height);
		//    Graphics g = getGraphics();
		//    g.setColor(Color.WHITE);
		//    g.fillRect(0,0,width,height);
	}

	public Dimension getSize() {
		return size;
	}

	java.awt.Image getImage(String imageFile) {
		if (!imagesFromFiles.containsKey(imageFile)) {
			try {
				// Use an image tracker to wait fot the image to load completely.
				Applet applet = NIP.getApplet();
				java.awt.Image img = null;
				if (applet != null) {
					try {
						img = applet.getImage(applet.getDocumentBase(),imageFile);
					} catch (Exception e) {
						//System.err.println("Image " + imageFile + " not loaded from server." + e);
					}
				}
				if (img == null)
					img = Toolkit.getDefaultToolkit().getImage(imageFile);
				if (img == null)
					throw new IllegalArgumentException("Image file " + imageFile + "not found");
				if (tracker == null) 
					tracker = new MediaTracker(new JPanel()); // use dummy component
				tracker.addImage(img,imageId++);
				tracker.waitForAll();
				imagesFromFiles.put(imageFile, img);
			} catch (InterruptedException ioe) {
				System.err.println("Interrupted while loading image from file " + imageFile);
			}
		}
		return imagesFromFiles.get(imageFile);
	}

	public void loadImage(String imageFile) {
		//fillRegion(0,0,width,height,Color.WHITE);
		//fillRegion(0,0,width,height,new Color(0,0,0,0));
		java.awt.Image img = getImage(imageFile);
		if (img == null) {
			System.err.println("Image file could not be loaded: " + imageFile);
			return;
		} 	
		// Calculate the maximum dimensions for displaying the image.
		int imgWidth = img.getWidth(null);
		int imgHeight = img.getHeight(null);
		double scale = ((double) width) / imgWidth;
		if (imgHeight * scale > height)
			scale = ((double) height) / imgHeight;
		int startX = (int) (width - scale*imgWidth) / 2;
		int startY = (int) (height - scale*imgHeight) / 2;

		Graphics g = getGraphics();
		//System.out.println("Drawing at: " + startX +","+startY+":" + ((int) (scale*imgWidth)) + "x" + ((int) (scale*imgHeight)));
		g.drawImage(img, startX, startY,(int) (scale*imgWidth), (int) (scale*imgHeight), null);
	}


	public void setPixel(int x, int y, Color c) {
		setRGB(x,y,c.getRGB());
	}

	public int getPixel(int x, int y) {
		return getRGB(x,y);
	}

	public void fillRegion(int x, int y, int width, int height, Color c) {
		fillRegion(x,y,width,height,c.getRGB());
	}

	public void fillRegion(int x, int y, int width, int height, int c) {
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				super.setRGB(x+i,y+j,c);
	}

	public void copy(ImageRaster destination) {
		copy(0, 0, width, height, destination);
	}

	public void copy(int srcX, int srcY, int width, int height, ImageRaster destination) {
		copy(srcX, srcY, width, height, destination, 0, 0);
	}

	public void copy(int srcX, int srcY, int width, int height, ImageRaster destination, int destX, int destY) {
		width = Math.min(Math.min(width, this.width-srcX), destination.width-destX); // adjust width to fit within bounds
		//System.out.println("width = " + width);
		height = Math.min(Math.min(height, this.height-srcY), destination.height-destY); // adjust height to fit within bounds
		//System.out.println("height = " + height);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				destination.setRGB(destX+i,destY+j,getRGB(srcX+i,srcY+j));
	}

	public void setPixel(int x, int y, int rgb) {
		setRGB(x,y,rgb);
	}

	public void setRGB(int x, int y, int rgb) {
		super.setRGB(x,y,rgb);
	}

	public void swapPixels(int x1, int y1, int x2, int y2) {
		int temp = getPixel(x1,y1);
		super.setRGB(x1,y1,getPixel(x2,y2));
		setRGB(x2,y2,temp);
	}

} // ImageRaster


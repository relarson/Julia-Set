/**
 * Image.java
 *
 *
 * Created: Sat Feb 08 10:50:06 2003
 *
 * @author Kenneth J. Goldman
 * @version 1.0
 */

//TODO:  make sure it resizes ok before delting the change listener code.

package nip;
import java.awt.*;

/**
 * An Image is a visual display of a picture represented as a raster of pixels in an (x,y)
 * coordinate system, with (0,0) at the upper left.  An image can be loaded from a gif or jpg
 * image file.  Also, the pixels of an image can be individually set.
 * 
 * @author Kenneth J. Goldman<BR>
 * Created Jun 28, 2005
 * 
 * Modified by Jonathon Lundy, June 2009
 */
public class Image extends Graphic {

	private static final long serialVersionUID = 1L;
	private ImageRaster raster;
	private static final int DEFAULT_SIZE = 128;
	private double rotation;
	private double scaleFactor = 1;
	private boolean copyAsDisplayed;

	/**
	 * Creates a new Image with a default width and height.
	 */
	public Image() {
		this(DEFAULT_SIZE, DEFAULT_SIZE);
	}

	/**
	 * Creates a new Image with the given width and height.
	 * @param width in pixels
	 * @param height in pixels
	 */
	public Image(int width, int height) {
		this(new ImageRaster(width,height));
	}

	Image(ImageRaster raster) {
		setRaster(raster);
		setSize(raster.getWidth(),raster.getHeight());
		setLineColor(Color.WHITE);
	}

	/**
	 * Creates a new image by copying a rectangular region from the main image of the given
	 * {@link nip.GraphicsPanel GraphicsPanel}.
	 * @param panel the GraphicsPanel whose image should be copied from
	 * @param x the left pixel coordinate of the region to be copied
	 * @param y the top pixel coordinate of the region to be copied
	 * @param width in pixels
	 * @param height in pixels
	 */
	public Image(GraphicsPanel panel, int x, int y, int width, int height) {
		this(panel.getMainImage(), x, y, width,height);
	}

	/**
	 * Creates a new Image by copying a rectangular region from the main image of the given
	 * Image.
	 * @param img the image that should be copied from
	 * @param x the left pixel coordinate of the region to be copied
	 * @param y the top pixel coordinate of the region to be copied
	 * @param width in pixels
	 * @param height in pixels
	 */
	public Image(Image img, int x, int y, int width, int height) {
		this(width,height);
		img.copy(x,y,width,height,this);
	}



	private void setRaster(ImageRaster raster) {
		if (raster == null)
			throw new IllegalArgumentException("The image mainImage must not be null.");
		this.raster = raster;
		repaint();
	}

	ImageRaster getRaster() {
		return raster;
	}

	/**
	 * This returns the size of the image, for use by Java's layout manager.  You should not need
	 * to call this method. 
	 */
	public Dimension getPreferredSize() {
		return raster.getSize();
	}
	
	/**
	 * Loads a gif or jpeg file into the raster.
	 * The loaded image is centered and scaled as large as possible to fit within the
	 * dimensions of this Image object without changing the image proportions.
	 * @param imageFile the full path name of the file to be loaded
	 */
	public void loadImage(String imageFile) {
		raster.loadImage(imageFile);
	}

	/**
	 * Rotates the image display about its center within its rectangular frame.
	 * This affects only the display of the image.  It does <b>not</b> change the values of the
	 * pixels in the raster.
	 * @param degrees the angle of rotation, in the counter clockwise direction
	 */
	public void setRotation(double degrees) {
		rotation = degrees/180 * Math.PI; // convert to radians
	}

	/**
	 * Scales the image display by the given percentage, keeping the center fixed within its rectangular frame.
	 * Values less than 100 make the image appear smaller, while values greater than 100 make it appear larger.
	 * This affects only the display of the image.  It does <b>not</b> change the values of the
	 * pixels in the raster.
	 * @param percent the zoom factor, as a percentage
	 */
	public void setZoom(double percent) {
		scaleFactor = percent/100;
	}

	/**
	 * When copyAsDisplayed is set to true, the current zoom and rotation are taken into account
	 * every time the image is copied.
	 * Otherwize, copies are taken directly from the unzoomed and unrotated raster.
	 * @param copyAsDisplayed whether or not zooming and rotation should be used during image copying
	 */
	public void setCopyAsDisplayed(boolean copyAsDisplayed) {
		this.copyAsDisplayed = copyAsDisplayed;
	}

	/**
	 * Copies this Image into the given destination image.  The image will be painted into the
	 * destination starting at the upper left corner of the destination.
	 * @param destination the image into which this image should be copied
	 */
	public void copy(Image destination) {
		copy(0, 0, raster.width, raster.height, destination);
	}

	/**
	 * Copies a rectangular potion of this image into the given destination image.
	 * The image will be painted into the destination starting at the upper left corner of the destination.
	 * @param srcX the left pixel coordiniate of the region to be copied
	 * @param srcY the top pixel coordiniate of the region to be copied
	 * @param width of the region to be copied, in pixels
	 * @param height of the region to be copied, in pixels
	 * @param destination the image into which this image should be copied
	 */
	public void copy(int srcX, int srcY, int width, int height, Image destination) {
		copy(srcX, srcY, width, height, destination, 0, 0);
	}

	/**
	 * Copies a rectangular potion of this image into the given destination image.
	 * The image will be painted into the destination starting at the given coordinates (destX,destY).
	 * @param srcX the left pixel coordiniate of the region to be copied
	 * @param srcY the top pixel coordiniate of the region to be copied
	 * @param width of the region to be copied, in pixels
	 * @param height of the region to be copied, in pixels
	 * @param destination the image into which this image should be copied
	 * @param destX the x coordinate of the upper left corner within the destination, where the image should be copied
	 * @param destY the y coordinate of the upper left corner within the destination, where the image should be copied
	 */
	public void copy(int srcX, int srcY, int width, int height,
			Image destination, int destX, int destY) {
		if (copyAsDisplayed)
			copyAsDisplayed(srcX, srcY, width, height, destination.raster, destX, destY);
		else
			raster.copy(srcX, srcY, width, height, destination.raster, destX, destY);
	}

	void copyAsDisplayed(int srcX, int srcY, int width, int height, ImageRaster destination, int destX, int destY) {
		width = Math.min(Math.min(width, raster.width-srcX), destination.width-destX); // adjust width to fit within bounds
		height = Math.min(Math.min(height, raster.height-srcY), destination.height-destY); // adjust height to fit within bounds
		Graphics2D g = (Graphics2D) destination.getGraphics();
		boolean selected = isSelected();
		setSelected(false);
		g.clipRect(destX,destY,width,height);
		g.translate(-srcX,-srcY);
		paint(g);
		setSelected(selected);
	}

	/**
	 * Fills a rectangular region of the image with the given color.
	 * @param x the left pixel coordinate of the region
	 * @param y the top pixel coordinate of the region
	 * @param width in pixels
	 * @param height in pixels
	 * @param color the color to be used for filling
	 */
	public void fillRegion(int x, int y, int width, int height, Color color) {
		raster.fillRegion(x, y, width, height, color);
	}

	/**
	 * Fills a rectangular region of the image with the given color.
	 * @param x the left pixel coordinate of the region
	 * @param y the top pixel coordinate of the region
	 * @param width in pixels
	 * @param height in pixels
	 * @param rgb the color to be used for filling, expressed as an int, with 8 bits each for red (bits 16-23), green (bits 8-15), and blue (bits 0-7).
	 */
	public void fillRegion(int x, int y, int width, int height, int rgb) {
		raster.fillRegion(x, y, width, height, rgb);
	}

	/**
	 * Returns a Color object for the color of the pixel with the given coordinates.
	 * @param x
	 * @param y
	 * @return the Color of the pixel
	 */
	public Color getPixelColor(int x, int y) {
		return new Color(getPixel(x,y));
	}

	/**
	 * Returns the value of the pixel with the given coordinates.
	 * The result given as an int, with 8 bits each for red (bits 16-23), green (bits 8-15), and blue (bits 0-7).
	 * @param x
	 * @param y
	 * @return the pixel value
	 */
	public int getPixel(int x, int y) {
		return raster.getPixel(x, y);
	}


	/**
	 * Sets the the pixel with the given coordinates to the given color.
	 * @param x
	 * @param y
	 * @param c the desired color for pixel (x,y)
	 */
	public void setPixel(int x, int y, Color c) {
		raster.setPixel(x, y, c);
	}


	/**
	 * Sets the the pixel with the given coordinates to the given integer value.
	 * @param x
	 * @param y
	 * @param rgb the desired color for pixel (x,y), expressed as an int, with 8 bits each for red (bits 16-23), green (bits 8-15), and blue (bits 0-7).
	 */
	public void setPixel(int x, int y, int rgb) {
		raster.setPixel(x, y, rgb);
	}

	/**
	 * Swaps the color values of the two pixels with coordinates (x1,y1) and (x2,y2).
	 * @param x1 x coordinate of the first pixel
	 * @param y1 y coordinate of the first pixel
	 * @param x2 x coordinate of the second pixel
	 * @param y2 y coordinate of the second pixel
	 */
	public void swapPixels(int x1, int y1, int x2, int y2) {
		raster.swapPixels(x1, y1, x2, y2);
	}

	/**
	 * Paints the Image when requested by the Java's graphics system, using the current zoom and
	 * rotation.  You should not need to
	 * call this method.  If you want to specifically request that a component be repainted, call
	 * {@link nip.Image#repaint()}.  (If you do not see this graphic, it may not have been added to
	 * a visible container, or it may be behind another graphic.)
	 */
	public void paint(Graphics g) {
		int w = getWidth();
		int h = getHeight();
		if (raster != null) {
			Graphics2D g2 = (Graphics2D) g;
			g2.rotate(rotation,getWidth()/2,getHeight()/2); 
			g.translate((int) ((w - w*scaleFactor)/2), (int) ((h - h*scaleFactor)/2));
			g2.scale(scaleFactor,scaleFactor);
			g.drawImage(raster,0,0,w,h,null);
			// undo the scaling, translating, and rotating
			g2.scale(1/scaleFactor,1/scaleFactor);
			g.translate((int) ((w*scaleFactor - w)/2), (int) ((h*scaleFactor - h)/2));
			g2.rotate(-rotation,getWidth()/2,getHeight()/2);
		}
		if (selected) {
			g.setColor(getForeground());
			g.drawRect(0,0,w-1,h-1);
		}
	}
}

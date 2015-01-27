package fabbroniko.environment;

/**
 * Represents a custom rectangle, that can intersects with points and other rectangles.
 * @author fabbroniko
 */
@SuppressWarnings("serial")
public class MyRectangle extends java.awt.Rectangle {

	private static final Dimension PIXEL_DIMENSION = new Dimension(1, 1);
	
	/**
	 * Constructs a new rectangle starting from x and y, with the given width and height.
	 * @param x Starting x position.
	 * @param y Starting y position.
	 * @param width Width.
	 * @param height Height.
	 */
	public MyRectangle(final int x, final int y, final int width, final int height) {
		super(x, y, width, height);
	}
	
	/**
	 * Checks if this rectangle intersects with the given point.
	 * @param point 
	 * @return Returns true if there is an intersection, false otherwise.
	 */
	public boolean intersects(final MyPoint point) {
		return super.intersects(new MyRectangle((int) point.getX(), (int) point.getY(), PIXEL_DIMENSION.getWidth(), PIXEL_DIMENSION.getHeight()));
	}

	/**
	 * Checks if this rectangle intersects with thw given one.
	 * @param rectangle 
	 * @return Returns true if there is an intersection, false otherwise.
	 */
	public boolean intersects(final MyRectangle rectangle) {
		return super.intersects(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
	}
}

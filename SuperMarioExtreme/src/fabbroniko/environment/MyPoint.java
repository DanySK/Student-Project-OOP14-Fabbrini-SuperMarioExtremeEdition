package fabbroniko.environment;

import java.awt.Point;

/**
 * Represent a {@link Point Point} with an offset.
 * @author fabbroniko
 */
@SuppressWarnings("serial")
public class MyPoint extends java.awt.Point {
	
	/**
	 * Constructs a simple point.
	 */
	public MyPoint() {
		super();
	}
	
	/**
	 * Constructs a new Point with the given offset.
	 * @param old A Simple Point.
	 * @param xOffset X Offset.
	 * @param yOffset Y Offset.
	 */
	public MyPoint(final MyPoint old, final int xOffset, final int yOffset) {
		super(old);
		setLocation(old.getX() + xOffset, old.getY() + yOffset);
	}
}

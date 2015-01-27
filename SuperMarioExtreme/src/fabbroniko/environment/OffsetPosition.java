package fabbroniko.environment;

import fabbroniko.gameobjects.AbstractGameObject;

/**
 * Represents a movement for an {@link AbstractGameObject AbstractGameObject}. It's used to hold the movement's offset related to an {@link AbstractGameObject AbstractGameObject}.
 * @author fabbroniko
 */
public class OffsetPosition {
	private int x;
	private int y;
	
	/**
	 * Constructs an OffsetPosition from the given x and y.
	 * @param xP X Coordinate.
	 * @param yP Y Coordinate.
	 */
	public OffsetPosition(final int xP, final int yP) {
		this.x = xP;
		this.y = yP;
	}
	
	/**
	 * Constructs a new OffsetPosition with default values.
	 */
	public OffsetPosition() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * Constructs an OffsetPosition starting from a Position.
	 * @param pos Position from which it will be constructed.
	 */
	public OffsetPosition(final Position pos) {
		this.x = pos.getX();
		this.y = pos.getY();
	}
	
	/**
	 * Sets the X Offset.
	 * @param xP 
	 */
	public void setX(final int xP) {
		this.x = xP;
	}
	
	/**
	 * Sets the Y Offset.
	 * @param yP 
	 */
	public void setY(final int yP) {
		this.y = yP;
	}
	
	/**
	 * Gets the X Offset.
	 * @return Returns the X Offset.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Gets the Y Offset.
	 * @return Returns the Y Offset
	 */
	public int getY() {
		return this.y;
	}
	
	@Override
	public String toString() {
		return "[X Offset = " + this.x + ". Y Offset = " + this.y + "]";
	}
}

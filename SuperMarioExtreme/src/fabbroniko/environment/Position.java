package fabbroniko.environment;

import java.awt.Point;

/**
 * Represents a position on the screen
 * @author fabbroniko
 *
 */
public class Position implements Cloneable{

	private int x;
	private int y;
	
	/**
	 * Constructs a new position.
	 * @param x
	 * @param y
	 */
	public Position(final int x, final int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructs a new position copying the one passed as parameter.
	 * @param cpy
	 */
	public Position(final Position cpy){
		this.x = cpy.getX();
		this.y = cpy.getY();
	}
	
	public Position(final Point point){
		this.x = (int)point.getX();
		this.y = (int)point.getY();
	}
	
	/**
	 * Change the current position
	 * @param x
	 * @param y
	 */
	public void setPosition(final int x, final int y){
		this.x = x;
		this.y = y;
	}
	
	public void copyPosition(final Position pos){
		this.x = pos.getX();
		this.y = pos.getY();
	}
	
	/**
	 * X Getter
	 * @return returns the x value of the actual position
	 */
	public int getX(){ return this.x; }
	
	/**
	 * Y Getter
	 * @return returns the y value of the actual position
	 */
	public int getY(){ return this.y; }
	
	/**
	 * Updates the x coord of the position
	 * @param x New X Position
	 */
	public void setX(final int x){ this.x = x; }
	
	/**
	 * Updates the y coord of the position
	 * @param y New Y Position
	 */
	public void setY(final int y){ this.y = y; }
	
	public String toString(){
		return "[X = " + this.x + ", Y = " + this.y + "]";
	}
	
	@Override
	public Position clone(){
		return new Position(this);
	}
	
	@Override
	public boolean equals(final Object pos){
		return pos instanceof Position && this.x == ((Position)pos).getX() && this.y == ((Position)pos).getY();
	}
	
	@Override
	public int hashCode(){
		return this.x * 10000 + this.y;
	}
}

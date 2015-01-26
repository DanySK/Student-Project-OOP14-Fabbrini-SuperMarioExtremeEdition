package fabbroniko.main;

import java.awt.Graphics2D;

/**
 * Represents a class that can be draw into the graphic context.
 * @author fabbroniko
 */
public interface Drawable {
	
	/**
	 *	Updates the image that should be displayed.
	 */
	void update();
	
	/**
	 * Draws the updated image into the specified graphic context.
	 * @param g Graphic Context
	 */
	void draw(Graphics2D g);
}

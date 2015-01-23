package fabbroniko.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Represents a class that can be controlled through the keyboard
 * @author fabbroniko
 *
 */
public interface KeyDependent {

	/**
	 * @see KeyListener#keyPressed(KeyEvent)
	 */
	void keyPressed(KeyEvent e);
	
	/**
	 * @see KeyListener#keyReleased(KeyEvent)
	 */
	void keyReleased(KeyEvent e);
}

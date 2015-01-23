package fabbroniko.gamestatemanager;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import fabbroniko.main.*;
/**
 * Represents a generic state
 * @author nicola.fabbrini
 *
 */
public abstract class AbstractGameState implements Drawable, KeyDependent{
	
	/**
	 * Initializes the current gameState
	 */
	public abstract void init();
	
	/**
	 * @see fabbroniko.main.Drawable#update()
	 */
	@Override
	public abstract void update();
	
	/**
	 * @see fabbroniko.main.Drawable#draw(Graphics2D)
	 */
	@Override
	public abstract void draw(Graphics2D g);
	
	/**
	 * @see fabbroniko.main.KeyDependent#keyPressed(KeyEvent)
	 */
	@Override
	public abstract void keyPressed(KeyEvent e);
	
	/**
	 * @see fabbroniko.main.KeyDependent#keyReleased(KeyEvent)
	 */
	@Override
	public abstract void keyReleased(KeyEvent e);
}

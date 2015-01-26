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
	public abstract void draw(final Graphics2D g);
	
	/**
	 * @see fabbroniko.main.KeyDependent#keyPressed(KeyEvent)
	 */
	@Override
	public void keyPressed(final KeyEvent e){
		if(!GamePanel.getInstance().isRunning()){ return; }
	}
	
	/**
	 * @see fabbroniko.main.KeyDependent#keyReleased(KeyEvent)
	 */
	@Override
	public void keyReleased(final KeyEvent e){
		if(!GamePanel.getInstance().isRunning()){ return; }
	}
}

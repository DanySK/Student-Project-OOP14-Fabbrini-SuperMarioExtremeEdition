package fabbroniko.gamestatemanager;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import fabbroniko.main.Drawable;
import fabbroniko.main.KeyDependent;
import fabbroniko.main.GamePanel;

/**
 * Represents a generic state.
 * @author nicola.fabbrini
 */
public abstract class AbstractGameState implements Drawable, KeyDependent {
	
	/**
	 * Initializes the current gameState.
	 */
	public abstract void init();
	
	@Override
	public void update() {
		if (isNotRunning()) {
			return;
		}
	}
	
	@Override
	public abstract void draw(final Graphics2D g);
	
	@Override
	public void keyPressed(final KeyEvent e) {
		if (isNotRunning()) {
			return;
		}
	}
	
	@Override
	public void keyReleased(final KeyEvent e) {
		if (isNotRunning()) {
			return;
		}
	}
	
	private boolean isNotRunning() {
		return !GamePanel.getInstance().isRunning();
	}
}

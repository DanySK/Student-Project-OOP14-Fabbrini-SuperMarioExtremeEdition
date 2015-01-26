package fabbroniko.environment;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import fabbroniko.error.ResourceNotFoundError;
import fabbroniko.main.*;

/**
 * Represents a background that can be placed in a GameState
 * @author nicola.fabbrini
 *
 */
public class Background implements Drawable{

	private BufferedImage bgImg;
	private Position bgPosition;
	
	/**
	 * Constructs a new Background
	 * @param bg Background Path
	 */
	public Background(final String bg){
		try{
			bgImg = ImageIO.read(getClass().getResourceAsStream(bg));
		}catch(Exception e){
			throw new ResourceNotFoundError(bg);
		}
		
		bgPosition = Game.ORIGIN.clone();
	}
	
	/**
	 * Position Setter 
	 * @param pos new position
	 */
	public void setPosition(final Position pos){ 
		this.bgPosition = pos; 
	}
	
	/**
	 * Position getter
	 * @return current position
	 */
	public Position getPosition(){ return this.bgPosition.clone(); }

	/**
	 * @see fabbroniko.main.Drawable#update()
	 */
	@Override
	public void update() {
		if(!GamePanel.getInstance().isRunning()){ return; }
	}

	/**
	 * @see fabbroniko.main.Drawable#draw(Graphics2D)
	 */
	@Override
	public void draw(final Graphics2D g) {
		g.drawImage(bgImg, bgPosition.getX(), bgPosition.getY(), null);
	}	
}

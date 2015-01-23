package fabbroniko.environment;

import java.awt.image.BufferedImage;

import fabbroniko.environment.EnvironmentStatics.TileTypes;

/**
 * Represents one tile of the map
 * @author nicola.fabbrini
 *
 */
public class Tile {

	private final BufferedImage tileImage;
	private final TileTypes type;
	
	/**
	 * Constructs a new tile.
	 * @param img Tile Image
	 * @param type Tile type (e.g. BLOCKED or UNBLOCKED)
	 */
	public Tile(final BufferedImage img, final TileTypes type)
	{
		this.tileImage = img;
		this.type = type;
	}
	
	/**
	 * Image Getter
	 * @return Returns the tile's image.
	 */
	public BufferedImage getImage(){
		return tileImage;
	}
	
	/**
	 * Type Getter
	 * @return Returns the tile's type.
	 */
	public TileTypes getType(){
		return this.type;
	}
}

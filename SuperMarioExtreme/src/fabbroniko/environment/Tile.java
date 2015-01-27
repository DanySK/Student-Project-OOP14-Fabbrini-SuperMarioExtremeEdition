package fabbroniko.environment;

import java.awt.image.BufferedImage;

import fabbroniko.environment.EnvironmentStatics.TileType;

/**
 * Represents one tile of the map.
 * @author nicola.fabbrini
 *
 */
public class Tile {

	private final BufferedImage tileImage;
	private final TileType type;
	
	/**
	 * Constructs a new tile.
	 * @param img Tile Image
	 * @param typeP Tile type (e.g. BLOCKED or UNBLOCKED)
	 */
	public Tile(final BufferedImage img, final TileType typeP) {
		this.tileImage = img;
		this.type = typeP;
	}
	
	/**
	 * Image Getter.
	 * @return Returns the tile's image.
	 */
	public BufferedImage getImage() {
		return tileImage;
	}
	
	/**
	 * Type Getter.
	 * @return Returns the tile's type.
	 */
	public TileType getType() {
		return this.type;
	}
}

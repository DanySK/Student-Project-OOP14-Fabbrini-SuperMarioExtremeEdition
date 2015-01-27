package fabbroniko.environment;

import fabbroniko.error.TileTypeError;
import fabbroniko.main.Game;

/**
 * Service Class used to perform environmental-operations.
 * @author fabbroniko
 */
public final class EnvironmentStatics {

	/**
	 * Basic tile dimension.
	 */
	public static final Dimension TILE_DIMENSION = new Dimension(30, 30);
	
	/**
	 * Represents the lower dimension that can be used.
	 */
	public static final Dimension BASE_DIMENSION = new Dimension(0, 0);
	
	private EnvironmentStatics() { }
	
	/**
	 * Gets a new centered position (X Coord) starting from the BASE_SIZE of the game and the dimension of the view.
	 * @param viewDimension dimension of the view that has to be centered
	 * @return A new Centered Position
	 */
	public static Position getXCentredPosition(final Dimension viewDimension) {
		final Position centredPosition = Game.ORIGIN.clone();
		centredPosition.setX((Game.BASE_WINDOW_SIZE.getWidth() - viewDimension.getWidth()) / 2);
		return centredPosition;
	}
	
	/**
	 * Represents the type of a tile.
	 * @author fabbroniko
	 */
	public enum TileType {
		
		/**
		 * Represents a non-blocking tile.
		 */
		TILE_FREE(0),
		
		/**
		 * Represents a bloking tile.
		 */
		TILE_BLOCK(1);
		
		private int tileType;
		
		private TileType(final int i) {
			this.tileType = i;
		}
		
		private int getType() {
			return this.tileType;
		}
		
		/**
		 * Gets the TileType associated with the given index.
		 * @param index Value associated with each TileType.
		 * @return Returns the corresponding TileType.	
		 */
		public static TileType getTileType(final int index) {
			for (final TileType i:TileType.values()) {
				if (i.getType() == index) {
					return i;
				}
			}
			
			throw new TileTypeError(index);
		}
	}
}

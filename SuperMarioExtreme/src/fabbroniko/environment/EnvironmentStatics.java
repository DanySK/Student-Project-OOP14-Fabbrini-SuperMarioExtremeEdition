package fabbroniko.environment;

import fabbroniko.error.TileTypeError;
import fabbroniko.main.Game;

public final class EnvironmentStatics {

	/**
	 * Basic tile dimension
	 */
	public static final Dimension TILE_DIMENSION = new Dimension(30, 30);
	
	public static final Dimension BASE_DIMENSION = new Dimension(0, 0);
	
	private EnvironmentStatics(){}
	
	/**
	 * Gets a new centered position (X Coord) starting from the BASE_SIZE of the game and the dimension of the view.
	 * @param viewDimension dimension of the view that has to be centered
	 * @return A new Centered Position
	 */
	public static Position getXCentredPosition(final Dimension viewDimension){
		final Position centredPosition = Game.ORIGIN.clone();
		centredPosition.setX((Game.BASE_WINDOW_SIZE.getWidth() - viewDimension.getWidth()) / 2);
		return centredPosition;
	}
	
	public enum TileTypes{
		TILE_FREE(0), TILE_BLOCK(1);
		
		private int tileType;
		
		private TileTypes(final int i){
			this.tileType = i;
		}
		
		private int getType(){
			return this.tileType;
		}
		
		public static TileTypes getTileType(final int index){
			for(final TileTypes i:TileTypes.values()){
				if(i.getType() == index){ return i; }
			}
			
			throw new TileTypeError(index);
		}
	}
}

package fabbroniko.gameobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import fabbroniko.environment.Dimension;
import fabbroniko.environment.TileMap;
import fabbroniko.error.ResourceNotFoundError;
import fabbroniko.gamestatemanager.AbstractGenericLevel;

public class Castle extends AbstractGameObject{

	private static final String RES_CASTLE_SPRITES = "/fabbroniko/Blocks/Castle.png";
	private static final Dimension SPRITE_DIMENSION = new Dimension(170, 175);
	private static final int CASTLE_INDEX = 0;
	
	public Castle(final TileMap tileMap, final AbstractGenericLevel level) {
		super(tileMap, level);
		this.objectType = ObjectType.TYPE_CASTLE;
	}

	@Override
	protected void loadSprites() {
		List<List<BufferedImage>> loadedImages = null;
		spriteDimension = SPRITE_DIMENSION.clone();
		imageMap = new int[] {1};
		
		try {
			loadedImages = loadSpritesFromFile(imageMap, RES_CASTLE_SPRITES);
		} catch (IOException e) {
			throw new IllegalArgumentException();
		}
		
		if(loadedImages == null){ throw new ResourceNotFoundError(RES_CASTLE_SPRITES); }
		
		Animation.getInstance(Animations.CASTLE).setImages(loadedImages.get(CASTLE_INDEX));
		Animation.getInstance(Animations.CASTLE).setTimes(1000, REPEAT);
		currentAnimation = Animation.getInstance(Animations.CASTLE);
	}

}

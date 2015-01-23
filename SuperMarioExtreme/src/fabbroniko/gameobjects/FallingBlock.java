package fabbroniko.gameobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import fabbroniko.environment.Dimension;
import fabbroniko.environment.TileMap;
import fabbroniko.error.ResourceNotFoundError;
import fabbroniko.gamestatemanager.GenericLevel;

public class FallingBlock extends AbstractGameObject{
	
	private static final String RES_FALLINGBLOCK_SPRITES = "/fabbroniko/Blocks/FallingBlockSprites.png";
	private static final Dimension SPRITE_DIMENSION = new Dimension(90, 30);
	private static final int FALLING_BLOCK_INDEX = 0;
	
	public FallingBlock(final TileMap tileMap, final GenericLevel level) {
		super(tileMap, level);
		this.objectType = ObjectType.TYPE_FALLING_BLOCK;
	}
	
	@Override
	public void handleObjectCollisions(final CollisionDirection direction, final ObjectType objectType) {
		super.handleObjectCollisions(direction, objectType);
		
		if(objectType.equals(ObjectType.TYPE_PLAYER) && direction.equals(CollisionDirection.TOP_COLLISION)){
			falling = true;
		}
	}

	@Override
	protected void loadSprites() {
		List<List<BufferedImage>> loadedImages = null;
		imageMap = new int[] {1};
		spriteDimension = SPRITE_DIMENSION.clone();
		
		try {
			loadedImages = loadSpritesFromFile(imageMap, RES_FALLINGBLOCK_SPRITES);
		} catch (IOException e) {
			throw new ResourceNotFoundError(RES_FALLINGBLOCK_SPRITES);
		}
		
		if(loadedImages == null){ throw new ResourceNotFoundError(RES_FALLINGBLOCK_SPRITES); }
		
		Animation.getInstance(Animations.FALLING_BLOCK).setImages(loadedImages.get(FALLING_BLOCK_INDEX));
		Animation.getInstance(Animations.FALLING_BLOCK).setTimes(1000, REPEAT);
		currentAnimation = Animation.getInstance(Animations.FALLING_BLOCK);
	}

}

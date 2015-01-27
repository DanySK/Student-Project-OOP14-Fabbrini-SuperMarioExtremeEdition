package fabbroniko.gameobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import fabbroniko.environment.AudioManager;
import fabbroniko.environment.Dimension;
import fabbroniko.environment.TileMap;
import fabbroniko.error.ResourceNotFoundError;
import fabbroniko.gamestatemanager.AbstractGenericLevel;

/**
 * Represents the simplest block in the game.
 * It can be break if hit from the bottom.
 * @author fabbroniko
 */
public class Block extends AbstractGameObject {

	private static final String RES_BLOCK_SPRITES = "/fabbroniko/Blocks/BlockSprites.png";
	private static final Dimension SPRITE_DIMENSION = new Dimension(30, 30);
	private static final int BLOCK_NORMAL_INDEX = 0;
	private static final int BLOCK_BREAKING_INDEX = 1;
	
	private static final int N_ROWS = 1;
	private static final int N_COLS = 6;
	
	/**
	 * Constructs a new Block.
	 * @param tileMap Reference of the {@link TileMap TileMap} on which it should be placed.
	 * @param level Reference of the {@link AbstractGenericLevel AbstractGenericLevel} on which it should be placed.
	 */
	public Block(final TileMap tileMap, final AbstractGenericLevel level) {
		super(tileMap, level);
		this.objectType = ObjectType.TYPE_BLOCK;
	}
	
	@Override
	public void handleObjectCollisions(final CollisionDirection direction, final ObjectType objectType) {
		super.handleObjectCollisions(direction, objectType);
		
		if (objectType == ObjectType.TYPE_PLAYER && direction == CollisionDirection.BOTTOM_COLLISION && currentAnimation != Animation.getInstance(Animations.BLOCK_BREAKING)) {
			currentAnimation = Animation.getInstance(Animations.BLOCK_BREAKING);
			currentAnimation.reset();
			AudioManager.getInstance().setEffect(AudioManager.Sound.BREAKING_BLOCK_EFFECT);
		}
	}
	
	@Override
	public void update() {
		super.update();
		if (currentAnimation == Animation.getInstance(Animations.BLOCK_BREAKING) && Animation.getInstance(Animations.BLOCK_BREAKING).hasBeenRepeatedOnce()) {
			death = true;
		}
	}
	
	@Override
	protected void loadSprites() {
		List<List<BufferedImage>> loadedImages = null;
		spriteDimension = SPRITE_DIMENSION.clone();
		imageMap = new int[] {N_ROWS, N_COLS};
		
		try {
			loadedImages = loadSpritesFromFile(imageMap, RES_BLOCK_SPRITES);
		} catch (IOException e) {
			throw new ResourceNotFoundError(RES_BLOCK_SPRITES);
		}
		
		if (loadedImages == null) {
			throw new ResourceNotFoundError(RES_BLOCK_SPRITES);
		}
		
		Animation.getInstance(Animations.BLOCK_NORMAL).setImages(loadedImages.get(BLOCK_NORMAL_INDEX));
		Animation.getInstance(Animations.BLOCK_NORMAL).setTimes(Player.ANIMATION_TIMES_1000, REPEAT);

		Animation.getInstance(Animations.BLOCK_BREAKING).setImages(loadedImages.get(BLOCK_BREAKING_INDEX));
		Animation.getInstance(Animations.BLOCK_BREAKING).setTimes(Player.ANIMATION_TIMES_2, NO_REPEAT);
		
		currentAnimation = Animation.getInstance(Animations.BLOCK_NORMAL);
	}

}

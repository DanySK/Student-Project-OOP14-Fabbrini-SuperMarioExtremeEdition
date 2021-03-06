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
 * Represents an invisible block. If visible, it follows the same rules of a normal block. Otherwise it can only be hit from below.
 * @author fabbroniko
 */
public class InvisibleBlock extends AbstractGameObject {
	
	private static final Dimension SPRITE_DIMENSION = new Dimension(30, 30);
	private static final String RES_INVISIBLEBLOCK_SPRITES = "/fabbroniko/Blocks/InvisibleBlockSprites.png";
	
	/**
	 * Constructs a new InvisibleBlock.
	 * @param tileMap Reference of the {@link TileMap TileMap} on which it should be placed.
	 * @param level Reference of the {@link AbstractGenericLevel AbstractGenericLevel} on which it should be placed.
	 */
	public InvisibleBlock(final TileMap tileMap, final AbstractGenericLevel level) {
		super(tileMap, level);
		this.objectType = ObjectType.TYPE_INVISIBLE_BLOCK;
	}
	
	@Override
	public void handleObjectCollisions(final CollisionDirection direction, final ObjectType objectType) 
	{
		if (objectType.equals(ObjectType.TYPE_PLAYER) && direction.equals(CollisionDirection.BOTTOM_COLLISION)) {
			currentAnimation = Animation.getInstance(Animations.INVISIBLEBLOCK_VISIBLE);
			this.objectType = ObjectType.TYPE_BLOCK;
			AudioManager.getInstance().setEffect(AudioManager.Sound.HIT_EFFECT);
		}
	}

	@Override
	protected void loadSprites() {
		List<List<BufferedImage>> loadedImages = null;
		spriteDimension = SPRITE_DIMENSION.clone();
		imageMap = new int[] {1, 1};
		
		try {
			loadedImages = loadSpritesFromFile(imageMap, RES_INVISIBLEBLOCK_SPRITES);
		} catch (IOException e) {
			throw new ResourceNotFoundError(RES_INVISIBLEBLOCK_SPRITES);
		}

		if (loadedImages == null) {
			throw new ResourceNotFoundError(RES_INVISIBLEBLOCK_SPRITES);
		}
		
		Animation.getInstance(Animations.INVISIBLEBLOCK_INVISIBLE).setImages(loadedImages.get(0));
		Animation.getInstance(Animations.INVISIBLEBLOCK_INVISIBLE).setTimes(Player.ANIMATION_TIMES_1000, REPEAT);
		
		Animation.getInstance(Animations.INVISIBLEBLOCK_VISIBLE).setImages(loadedImages.get(1));
		Animation.getInstance(Animations.INVISIBLEBLOCK_VISIBLE).setTimes(Player.ANIMATION_TIMES_1000, REPEAT);
		currentAnimation = Animation.getInstance(Animations.INVISIBLEBLOCK_INVISIBLE);
	}

}

package fabbroniko.gameobjects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import fabbroniko.environment.AudioManager;
import fabbroniko.environment.Dimension;
import fabbroniko.environment.TileMap;
import fabbroniko.error.ResourceNotFoundError;
import fabbroniko.gamestatemanager.GenericLevel;

public class Enemy extends AbstractGameObject{
	
	private boolean init;
	
	private static final String RES_ENEMY_SPRITES = "/fabbroniko/Enemy/GhostSprites.png";
	private static final Dimension SPRITE_DIMENSION = new Dimension(27,48);

	public Enemy(final TileMap tileMap, final GenericLevel level) {
		super(tileMap, level); 
		spriteDimension = SPRITE_DIMENSION.clone();
		falling = true;
		leftOffset = -1;
		rightOffset = 1;
		this.objectType = ObjectType.TYPE_ENEMY;
	}
	
	@Override
	public void handleMapCollisions(final CollisionDirection direction){
		super.handleMapCollisions(direction);
		
		if(direction.equals(CollisionDirection.BOTTOM_COLLISION) && !init){
			right = true;
			facingRight = true;
			init = true;
		}
		if(direction.equals(CollisionDirection.LEFT_COLLISION)){
			left = false;
			facingRight = false;
			right = true;
		}
		if(direction.equals(CollisionDirection.RIGHT_COLLISION)){
			right = false;
			facingRight = true;
			left = true;
		}
		
	}
	
	@Override
	public void handleObjectCollisions(final CollisionDirection direction, final ObjectType objectType) {
		super.handleObjectCollisions(direction, objectType);
		
		if(direction == CollisionDirection.TOP_COLLISION && objectType == ObjectType.TYPE_PLAYER && currentAnimation != Animation.getInstance(Animations.ENEMY_DEAD)){
			currentAnimation = Animation.getInstance(Animations.ENEMY_DEAD);
			currentAnimation.reset();
			AudioManager.getInstance().setEffect(AudioManager.Sounds.HIT_EFFECT);
		}
	}
	
	@Override
	public void update(){
		super.update();
		
		if(currentAnimation == Animation.getInstance(Animations.ENEMY_DEAD) && Animation.getInstance(Animations.ENEMY_DEAD).hasBeenRepeatedOnce()){ death = true; }
	}

	@Override
	protected void loadSprites() {
		List<List<BufferedImage>> loadedImages = null;
		spriteDimension = SPRITE_DIMENSION.clone();
		imageMap = new int[] {2, 1};
		
		try {
			loadedImages = loadSpritesFromFile(imageMap, RES_ENEMY_SPRITES);
		} catch (IOException e) {
			throw new ResourceNotFoundError(RES_ENEMY_SPRITES);
		}
		
		if(loadedImages == null){ throw new ResourceNotFoundError(RES_ENEMY_SPRITES); }
		
		Animation.getInstance(Animations.ENEMY_WALK).setImages(loadedImages.get(0));
		Animation.getInstance(Animations.ENEMY_WALK).setTimes(10, REPEAT);
		
		Animation.getInstance(Animations.ENEMY_DEAD).setImages(loadedImages.get(1));
		Animation.getInstance(Animations.ENEMY_DEAD).setTimes(2, NO_REPEAT);
		
		currentAnimation = Animation.getInstance(Animations.ENEMY_WALK);
	}

}

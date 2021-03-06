package fabbroniko.gameobjects;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import fabbroniko.environment.AudioManager;
import fabbroniko.environment.AudioManager.Sound;
import fabbroniko.environment.Dimension;
import fabbroniko.environment.TileMap;
import fabbroniko.error.ResourceNotFoundError;
import fabbroniko.gamestatemanager.GameStateManager;
import fabbroniko.gamestatemanager.AbstractGenericLevel;
import fabbroniko.gamestatemanager.GameStateManager.GameStates;
import fabbroniko.gamestatemanager.gamestates.DeathState;
import fabbroniko.gamestatemanager.gamestates.SettingsState;
import fabbroniko.main.Game;

/**
 * Represents the player's character.
 * @author fabbroniko
 */
public class Player extends AbstractGameObject {
	
	private boolean animationJump;
	private boolean animationMove;
	private final AbstractGenericLevel currentLevel;
	
	private static final String RES_MARIO_SPRITES = "/fabbroniko/Mario/MarioSprites.png";
	private static final int STILL_INDEX = 0;
	private static final int JUMP_INDEX = 1;
	private static final int WALK_INDEX = 2;
	
	private static final Dimension SPRITE_DIMENSION = new Dimension(28, 26);
	
	/**
	 * Indicates the amount of time that an Ananimation has to be used for.
	 */
	public static final int ANIMATION_TIMES_1000 = 1000;
	
	/**
	 * Indicates the amount of time that an Ananimation has to be used for.
	 */
	public static final int ANIMATION_TIMES_5 = 5;
	
	/**
	 * Indicates the amount of time that an Ananimation has to be used for.
	 */
	public static final int ANIMATION_TIMES_2 = 2;
	
	/**
	 * Constructs the player instance.
	 * @param tileMap Reference of the {@link TileMap TileMap} on which it should be placed.
	 * @param level Reference of the {@link AbstractGenericLevel AbstractGenericLevel} on which it should be placed.
	 */
	public Player(final TileMap tileMap, final AbstractGenericLevel level) {
		super(tileMap, level);
		falling = true;
		animationJump = true;
		facingRight = true;
		this.objectType = ObjectType.TYPE_PLAYER;
		this.currentLevel = level;
	}
	
	@Override
	public void update() {
		super.update();
		tileMap.setPosition(myPosition.getX() - (int) (Game.BASE_WINDOW_SIZE.getWidth() / 2), myPosition.getY() - (int) (Game.BASE_WINDOW_SIZE.getHeight() / 2));
		if (death) {
			DeathState.getInstance().incDeath();
			GameStateManager.getInstance().setState(GameStates.DEATH_STATE);
		}
		if (animationJump) {
			currentAnimation = Animation.getInstance(Animations.PLAYER_JUMP);
		} else if (animationMove) {
			currentAnimation = Animation.getInstance(Animations.PLAYER_WALK);
		} else {
			currentAnimation = Animation.getInstance(Animations.PLAYER_STILL);
		}
	}
	
	@Override
	public void handleMapCollisions(final CollisionDirection direction) {
		super.handleMapCollisions(direction);
		
		if (direction.equals(CollisionDirection.BOTTOM_COLLISION)) {
			animationJump = false;
		}
	}
	
	@Override
	public void handleObjectCollisions(final CollisionDirection direction, final ObjectType objectType) {
		if (!objectType.equals(ObjectType.TYPE_INVISIBLE_BLOCK) || objectType.equals(ObjectType.TYPE_INVISIBLE_BLOCK) && direction.equals(CollisionDirection.TOP_COLLISION)) { 
			super.handleObjectCollisions(direction, objectType);
		}
		
		if (objectType.equals(ObjectType.TYPE_ENEMY)) {
			if (direction.equals(CollisionDirection.BOTTOM_COLLISION)) {
				jumping = true;
			} else {
				death = true;
			}
		} else if (objectType.equals(ObjectType.TYPE_CASTLE)) {
			this.currentLevel.levelFinished();
		} else {
			if (direction.equals(CollisionDirection.BOTTOM_COLLISION)) {
				animationJump = false;
				groundHit = true;
			}
		}
	}
	
	@Override
	public void keyPressed(final KeyEvent e) {
		if (e.getKeyCode() == SettingsState.getInstance().getLeftKeyCode()) {
			left = true;
			animationMove = true;
			facingRight = false;
		}
		if (e.getKeyCode() == SettingsState.getInstance().getRightKey()) {
			right = true;
			animationMove = true;
			facingRight = true;
		}
		if (e.getKeyCode() == SettingsState.getInstance().getJumpKey() && !jumping && groundHit) {
			jumping = true;
			groundHit = false;
			currentJump = 0;
			animationJump = true;
			AudioManager.getInstance().setEffect(Sound.JUMP_EFFECT);
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) {
		if (e.getKeyCode() == SettingsState.getInstance().getLeftKeyCode()) {
			left = false; 
			animationMove = false;
		}
		if (e.getKeyCode() == SettingsState.getInstance().getRightKey()) {
			right = false; 	
			animationMove = false;
		}
		if (e.getKeyCode() == SettingsState.getInstance().getJumpKey()) {
			jumping = false;
		}
	}

	@Override
	protected void loadSprites() {
		List<List<BufferedImage>> loadedImages = null;
		imageMap = new int[]{1, 1, 3};
		spriteDimension = SPRITE_DIMENSION.clone();
		
		try {
			loadedImages = loadSpritesFromFile(imageMap, RES_MARIO_SPRITES);
		} catch (IOException e) {
			throw new ResourceNotFoundError(RES_MARIO_SPRITES);
		}
		
		if (loadedImages == null) {
			throw new ResourceNotFoundError(RES_MARIO_SPRITES);
		}
		
		Animation.getInstance(Animations.PLAYER_STILL).setImages(loadedImages.get(STILL_INDEX));
		Animation.getInstance(Animations.PLAYER_STILL).setTimes(ANIMATION_TIMES_1000, REPEAT);
		
		Animation.getInstance(Animations.PLAYER_WALK).setImages(loadedImages.get(WALK_INDEX));
		Animation.getInstance(Animations.PLAYER_WALK).setTimes(ANIMATION_TIMES_5, REPEAT);
		
		Animation.getInstance(Animations.PLAYER_JUMP).setImages(loadedImages.get(JUMP_INDEX));
		Animation.getInstance(Animations.PLAYER_JUMP).setTimes(ANIMATION_TIMES_1000, REPEAT);
		
		currentAnimation = Animation.getInstance(Animations.PLAYER_JUMP);
	}
}

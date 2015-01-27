package fabbroniko.gameobjects;

/**
 * Represents the available animations.
 * @author fabbroniko
 */
public enum Animations {
	
	/**
	 * Used when the player is walking.
	 */
	PLAYER_WALK,
	
	/**
	 * Used when the player is jumping.
	 */
	PLAYER_JUMP,
	
	/**
	 * Used when the player is still.
	 */
	PLAYER_STILL,
	
	/**
	 * Used for normal blocks.
	 */
	BLOCK_NORMAL,
	
	/**
	 * Used when a normal block is going to be broken.
	 */
	BLOCK_BREAKING,
	
	/**
	 * Used when an enemy is walking.
	 */
	ENEMY_WALK,
	
	/**
	 * Used when an enemy is dying.
	 */
	ENEMY_DEAD,
	
	/**
	 * Used when an invisible block is visible.
	 */
	INVISIBLEBLOCK_VISIBLE,
	
	/**
	 * Used when an invisible block is invisible.
	 */
	INVISIBLEBLOCK_INVISIBLE,
	
	/**
	 * Used when a falling block is falling.
	 */
	FALLING_BLOCK,
	
	/**
	 * Used for the static castle.
	 */
	CASTLE;
}

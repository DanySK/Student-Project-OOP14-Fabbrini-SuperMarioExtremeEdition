package fabbroniko.gameobjects;

/**
 * Represents the type of an {@link AbstractGameObject AbstractGameObject}.
 * @author fabbroniko
 */
public enum ObjectType {
	
	/**
	 * Player's type.
	 */
	TYPE_PLAYER,
	
	/**
	 * Enemy's type.
	 */
	TYPE_ENEMY,
	
	/**
	 * Block's type.
	 */
	TYPE_BLOCK,
	
	/**
	 * Invisible Block's type.
	 */
	TYPE_INVISIBLE_BLOCK,
	
	/**
	 * Falling Block's type.
	 */
	TYPE_FALLING_BLOCK,
	
	/**
	 * Castle's type.
	 */
	TYPE_CASTLE,
	
	/**
	 * Unknown Type.
	 */
	TYPE_NONE;
}

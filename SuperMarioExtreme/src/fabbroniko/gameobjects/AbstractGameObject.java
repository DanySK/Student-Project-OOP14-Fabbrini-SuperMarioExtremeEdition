package fabbroniko.gameobjects;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fabbroniko.environment.Dimension;
import fabbroniko.environment.MyPoint;
import fabbroniko.environment.MyRectangle;
import fabbroniko.environment.OffsetPosition;
import fabbroniko.environment.Position;
import fabbroniko.environment.TileMap;
import fabbroniko.environment.EnvironmentStatics.TileType;
import fabbroniko.gamestatemanager.AbstractGenericLevel;
import fabbroniko.main.Drawable;
import fabbroniko.main.Game;
import fabbroniko.main.GamePanel;
import fabbroniko.main.KeyDependent;

/**
 * Abstract Class representing a generic GameObject.
 * @author fabbroniko
 */
public abstract class AbstractGameObject implements Drawable, KeyDependent{
 
	/**
	 * Object's position, relative to the map.
	 */
	protected Position myPosition;
	
	/**
	 * Map's Position.
	 */
	protected Position mapPosition;
	
	/**
	 * Object's type.
	 */
	protected ObjectType objectType = ObjectType.TYPE_NONE;
	
	/**
	 * Sprite's dimensions.
	 */
	protected Dimension spriteDimension;
	
	/**
	 * Object's current animation.
	 */
	protected Animation currentAnimation;
	
	/**
	 * Map used to load its prites.
	 */
	protected int[] imageMap;
	
	/**
	 * TileMap on which it has to be placed.
	 */
	protected TileMap tileMap;
	
	/**
	 * Level on which it has to be placed.
	 */
	protected AbstractGenericLevel level;
	
	/**
	 * Represents whether it's jumping or not.
	 */
	protected boolean jumping;
	
	/**
	 * Represents whether it's falling or not.
	 */
	protected boolean falling;
	
	/**
	 * Represents it's going in the left direction.
	 */
	protected boolean left;
	
	/**
	 * Represents it's going in the right direction.
	 */
	protected boolean right;
	
	/**
	 * Represents whether it's facing right or not.
	 */
	protected boolean facingRight;
	
	/**
	 * Represents whether it hit the ground or not.
	 */
	protected boolean groundHit;
	
	/**
	 * Represents the current jumping height.
	 */
	protected int currentJump;
	
	/**
	 * Represents whether it's dead or not.
	 */
	protected boolean death;
	
	/**
	 * Represents the default offset for movements.
	 */
	protected int upOffset = -5;
	
	/**
	 * Represents the default offset for movements.
	 */
	protected int downOffset = 3;
	
	/**
	 * Represents the default offset for movements.
	 */
	protected int leftOffset = -3;
	
	/**
	 * Represents the default offset for movements.
	 */
	protected int rightOffset = 3;
	
	/**
	 * Represents the max jump.
	 */
	protected int maxJump = 20;
	
	// Collision rectangle
	/**
	 * Movement's offset.
	 */
	protected OffsetPosition offset;
	
	/**
	 * Top Left Corner.
	 */
	protected MyPoint topLeft;
	
	/**
	 * Top Right Corner.
	 */
	protected MyPoint topRight;
	
	/**
	 * Bottom Left Corner.
	 */
	protected MyPoint bottomLeft;
	
	/**
	 * Bottom Right Corner.
	 */
	protected MyPoint bottomRight;
	
	/**
	 * Indicates that the animation has to be repeated indefinitely.
	 */
	protected static final boolean REPEAT = false;
	
	/**
	 * Indicates that the animation has to be repeated once.
	 */
	protected static final boolean NO_REPEAT = true;
	
	/**
	 * Constructs a new AbstractGameObject.
	 * @param tileMapP TileMap on which it has to be placed.
	 * @param levelP GenericLevel on which it has to be placed.
	 */
	protected AbstractGameObject(final TileMap tileMapP, final AbstractGenericLevel levelP) {
		this.tileMap = tileMapP;
		this.level = levelP;
		this.loadSprites();
		this.death = false;
		
		topLeft = new MyPoint();
		topRight = new MyPoint();
		bottomLeft = new MyPoint();
		bottomRight = new MyPoint();
		
		offset = new OffsetPosition();
		mapPosition = Game.ORIGIN.clone();
	}
	
	// Deve migliorare in modo che i vari movimenti siano separati.
	private void checkForTileCollisions() {
		try {
			if (tileMap.getTileType(new MyPoint(topLeft, 0, offset.getY())) == TileType.TILE_BLOCK || tileMap.getTileType(new MyPoint(topRight, 0, offset.getY())) == TileType.TILE_BLOCK) {
				handleMapCollisions(CollisionDirection.TOP_COLLISION);
			}
			if (tileMap.getTileType(new MyPoint(topLeft, offset.getX(), 0)) == TileType.TILE_BLOCK || tileMap.getTileType(new MyPoint(bottomLeft, offset.getX(), 0)) == TileType.TILE_BLOCK) {
				handleMapCollisions(CollisionDirection.LEFT_COLLISION);
			}
			if (tileMap.getTileType(new MyPoint(topRight, offset.getX(), 0)) == TileType.TILE_BLOCK || tileMap.getTileType(new MyPoint(bottomRight, offset.getX(), 0)) == TileType.TILE_BLOCK) {
				handleMapCollisions(CollisionDirection.RIGHT_COLLISION);
			}		
			if (tileMap.getTileType(new MyPoint(bottomLeft, 0, offset.getY())) == TileType.TILE_BLOCK || tileMap.getTileType(new MyPoint(bottomRight, 0, offset.getY())) == TileType.TILE_BLOCK) {
				handleMapCollisions(CollisionDirection.BOTTOM_COLLISION);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			death = true;
		}
	}
	
	private void checkForObjectCollisions() {
		for (final AbstractGameObject i:level.getGameObjects()) {
			if (!i.equals(this)) {
				if ((i.getRectangle().intersects(new MyPoint(topLeft, 0, offset.getY())) || i.getRectangle().intersects(new MyPoint(topRight, 0, offset.getY()))) && !i.getRectangle().intersects(this.getRectangle())) {
					handleObjectCollisions(CollisionDirection.TOP_COLLISION, i.objectType);
					i.handleObjectCollisions(CollisionDirection.BOTTOM_COLLISION, this.objectType);
				}
				if ((i.getRectangle().intersects(new MyPoint(topLeft, offset.getX(), 0)) || i.getRectangle().intersects(new MyPoint(bottomLeft, offset.getX(), 0))) && !i.getRectangle().intersects(this.getRectangle())) {
					handleObjectCollisions(CollisionDirection.LEFT_COLLISION, i.objectType);
					i.handleObjectCollisions(CollisionDirection.RIGHT_COLLISION, this.objectType);
				}
				
				if ((i.getRectangle().intersects(new MyPoint(topRight, offset.getX(), 0)) || i.getRectangle().intersects(new MyPoint(bottomRight, offset.getX(), 0))) && !i.getRectangle().intersects(this.getRectangle())) {
					handleObjectCollisions(CollisionDirection.RIGHT_COLLISION, i.objectType);
					i.handleObjectCollisions(CollisionDirection.LEFT_COLLISION, this.objectType);
				}
				
				if ((i.getRectangle().intersects(new MyPoint(bottomLeft, 0, offset.getY())) || i.getRectangle().intersects(new MyPoint(bottomRight, 0, offset.getY()))) && !i.getRectangle().intersects(this.getRectangle())) {
					handleObjectCollisions(CollisionDirection.BOTTOM_COLLISION, i.objectType);
					i.handleObjectCollisions(CollisionDirection.TOP_COLLISION, this.objectType);
				}
			}
		}
	}
	
	/**
	 * Handles collisions with the map.
	 * @param direction Collision's direction.
	 */
	protected void handleMapCollisions(final CollisionDirection direction) {
		if (direction.equals(CollisionDirection.BOTTOM_COLLISION)) {
			groundHit = true;
			offset.setY(0);
		}
		if (direction.equals(CollisionDirection.TOP_COLLISION)) {
			jumping = false;
			offset.setY(0);
		}
		if (direction.equals(CollisionDirection.LEFT_COLLISION) || direction.equals(CollisionDirection.RIGHT_COLLISION)) {
			offset.setX(0);
		}
	}
	
	/**
	 * Handles collisions with other objects.
	 * @param direction Collision's Direction.
	 * @param objectTypeP Type of the object.
	 */
	protected void handleObjectCollisions(final CollisionDirection direction, final ObjectType objectTypeP) {
		handleMapCollisions(direction);
	}
	
	/**
	 * Loads the sprites.
	 */
	protected abstract void loadSprites();
	
	/**
	 * Loads the sprites from a file.
	 * @param columns Map of the sprites.
	 * @param source Image's path.
	 * @return Returns a list of list of subimages.
	 * @throws IOException Thrown if there isn't the specified file.
	 */
	protected List<List<BufferedImage>> loadSpritesFromFile(final int[] columns, final String source) throws IOException {
		final List<List<BufferedImage>> images = new ArrayList<>();
		final BufferedImage sprites = ImageIO.read(getClass().getResourceAsStream(source));
		final Position currentSubImagePosition = Game.ORIGIN.clone();
		
		for (int r = 0; r < columns.length; r++) {
			images.add(new ArrayList<>());
			currentSubImagePosition.setX(Game.ORIGIN.getX());
			for (int c = 0; c < columns[r]; c++) {
				images.get(images.size() - 1).add(sprites.getSubimage(currentSubImagePosition.getX(), currentSubImagePosition.getY(), spriteDimension.getWidth(), spriteDimension.getHeight()));
				currentSubImagePosition.setX(currentSubImagePosition.getX() + spriteDimension.getWidth());
			}
			currentSubImagePosition.setY(currentSubImagePosition.getY() + spriteDimension.getHeight());
		}
		
		return images;
	}
	
	/**
	 * Checks if the object is dead.
	 * @return Return true if it's dead, fale otherwise.
	 */
	public boolean isDead() {
		return death;
	}
	
	/**
	 * Gets the object's position.
	 * @return The Object's position.
	 */
	public Position getObjectPosition() {
		return this.myPosition;
	}
	
	/**
	 * Sets the object's position.
	 * @param position The new object's position.
	 */
	public void setObjectPosition(final Position position) {
		this.myPosition = position.clone();
	}
	
	/**
	 * Gets the rectangle corresponding to this object.
	 * @return Returns the rectangle corresponding to this object.
	 */
	public MyRectangle getRectangle() {
		return new MyRectangle(myPosition.getX(), myPosition.getY(), spriteDimension.getWidth(), spriteDimension.getHeight());
	}
	
	/**
	 * Gets the object's type.
	 * @return Returns the object's type.
	 */
	public ObjectType getObjectType() {
		return this.objectType;
	}
	
	private void buildCorners() {
		topLeft.setLocation(myPosition.getX(), myPosition.getY());
		topRight.setLocation(myPosition.getX() + spriteDimension.getWidth() - 1, myPosition.getY());
		bottomLeft.setLocation(myPosition.getX(), myPosition.getY() + spriteDimension.getHeight() - 1);
		bottomRight.setLocation(myPosition.getX() + spriteDimension.getWidth() - 1, myPosition.getY() + spriteDimension.getHeight() - 1);
	}
	
	/**
	 * Updates the object's position.
	 * @param pos
	 */
	public void updateObjectPosition() {
		buildCorners();
		checkForTileCollisions();
		checkForObjectCollisions();
		myPosition.setPosition(myPosition.getX() + offset.getX(), myPosition.getY() + offset.getY());
	}
	
	@Override
	public boolean equals(final Object gameObject) {
		return gameObject instanceof AbstractGameObject && myPosition.equals(((AbstractGameObject) gameObject).getObjectPosition());
	}
	
	@Override
	public int hashCode() {
		return this.getObjectPosition().hashCode();
	}
	
	@Override
	public void update() {
		int xOffset = 0;
		int yOffset = 0;
		
		mapPosition.copyPosition(tileMap.getPosition());
		
		if (jumping) {
			yOffset += upOffset;
			currentJump++;
			if (currentJump > maxJump) {
				jumping = false;
			}
		}
		
		yOffset += falling && !jumping ? downOffset : 0;
		xOffset += left ? leftOffset : 0;
		xOffset += right ? rightOffset : 0;
		
		if (xOffset != 0 || yOffset != 0) {
			offset.setX(xOffset);
			offset.setY(yOffset);
			updateObjectPosition();
		}
	}
	
	@Override
	public void draw(final Graphics2D g) {
		if (facingRight) {
			g.drawImage(currentAnimation.getImage(), myPosition.getX() - mapPosition.getX(), myPosition.getY() - mapPosition.getY(), spriteDimension.getWidth(), spriteDimension.getHeight(), null);
		} else {
			g.drawImage(currentAnimation.getImage(), myPosition.getX() - mapPosition.getX() + spriteDimension.getWidth(), myPosition.getY() - mapPosition.getY(),  -spriteDimension.getWidth(), spriteDimension.getHeight(), null);
		}
	}
	
	@Override
	public void keyPressed(final KeyEvent e) {
		if (isNotRunning()) {
			return;
		}
	}
	
	@Override
	public void keyReleased(final KeyEvent e) {
		if (isNotRunning()) {
			return;
		}
	}
	
	private boolean isNotRunning() {
		return !GamePanel.getInstance().isRunning();
	}
}

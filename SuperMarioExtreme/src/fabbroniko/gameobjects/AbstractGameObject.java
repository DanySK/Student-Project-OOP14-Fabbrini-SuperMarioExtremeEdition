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
import fabbroniko.environment.EnvironmentStatics.TileTypes;
import fabbroniko.gamestatemanager.GenericLevel;
import fabbroniko.main.Drawable;
import fabbroniko.main.Game;
import fabbroniko.main.GamePanel;
import fabbroniko.main.KeyDependent;

public abstract class AbstractGameObject implements Drawable, KeyDependent{
 
	// Posizione corrente dell'oggetto de della mappa
	protected Position myPosition;
	protected Position mapPosition;
	
	// Tipo oggetto, dimensioni e animazione
	protected ObjectType objectType = ObjectType.TYPE_NONE; // Tipo indefinito per evitare 
	protected Dimension spriteDimension;
	protected Animation currentAnimation;
	protected int[] imageMap;
	
	// TileMap => Usato per controllare collisioni con gli elementi della mappa
	protected TileMap tileMap;
	protected GenericLevel level;
	
	// Gestione movimenti e stati dell'oggetto
	protected boolean jumping;
	protected boolean falling;
	protected boolean left;
	protected boolean right;
	protected boolean facingRight;
	protected boolean groundHit;
	protected int currentJump;
	protected boolean death;
	
	// Velocità dei movimenti standard, possono essere modificati dall'oggetto che lo estende per modificare la propria velocità
	protected int upOffset = -5;
	protected int downOffset = 3;
	protected int leftOffset = -3;
	protected int rightOffset = 3;
	protected int maxJump = 20;
	
	// Collision rectangle
	protected OffsetPosition offset;
	protected MyPoint topLeft;
	protected MyPoint topRight;
	protected MyPoint bottomLeft;
	protected MyPoint bottomRight;
	
	// Costanti per determinare se un'animazione deve essere ripetuta piu volte o solo una volta.
	protected static final boolean REPEAT = false;
	protected static final boolean NO_REPEAT = true;
	
	protected AbstractGameObject(final TileMap tileMap, final GenericLevel level){
		this.tileMap = tileMap;
		this.level = level;
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
	private void checkForTileCollisions(){
		try {
			if(tileMap.getTileType(new MyPoint(topLeft, 0, offset.getY())) == TileTypes.TILE_BLOCK || tileMap.getTileType(new MyPoint(topRight, 0, offset.getY())) == TileTypes.TILE_BLOCK){
				handleMapCollisions(CollisionDirection.TOP_COLLISION);
			}
			if(tileMap.getTileType(new MyPoint(topLeft, offset.getX(), 0)) == TileTypes.TILE_BLOCK || tileMap.getTileType(new MyPoint(bottomLeft, offset.getX(), 0)) == TileTypes.TILE_BLOCK){
				handleMapCollisions(CollisionDirection.LEFT_COLLISION);
			}
			if(tileMap.getTileType(new MyPoint(topRight, offset.getX(), 0)) == TileTypes.TILE_BLOCK || tileMap.getTileType(new MyPoint(bottomRight, offset.getX(), 0)) == TileTypes.TILE_BLOCK){
				handleMapCollisions(CollisionDirection.RIGHT_COLLISION);
			}		
			if(tileMap.getTileType(new MyPoint(bottomLeft, 0, offset.getY())) == TileTypes.TILE_BLOCK || tileMap.getTileType(new MyPoint(bottomRight, 0, offset.getY())) == TileTypes.TILE_BLOCK){
				handleMapCollisions(CollisionDirection.BOTTOM_COLLISION);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			death = true;
		}
	}
	
	private void checkForObjectCollisions(){
		for(final AbstractGameObject i:level.getGameObjects()){
			if(!i.equals(this)){
				if((i.getRectangle().intersects(new MyPoint(topLeft, 0, offset.getY())) || i.getRectangle().intersects(new MyPoint(topRight, 0, offset.getY()))) && !i.getRectangle().intersects(this.getRectangle())){
					handleObjectCollisions(CollisionDirection.TOP_COLLISION, i.objectType);
					i.handleObjectCollisions(CollisionDirection.BOTTOM_COLLISION, this.objectType);
				}
				if((i.getRectangle().intersects(new MyPoint(topLeft, offset.getX(), 0)) || i.getRectangle().intersects(new MyPoint(bottomLeft, offset.getX(), 0))) && !i.getRectangle().intersects(this.getRectangle())){
					handleObjectCollisions(CollisionDirection.LEFT_COLLISION, i.objectType);
					i.handleObjectCollisions(CollisionDirection.RIGHT_COLLISION, this.objectType);
				}
				
				if((i.getRectangle().intersects(new MyPoint(topRight, offset.getX(), 0)) || i.getRectangle().intersects(new MyPoint(bottomRight, offset.getX(), 0))) && !i.getRectangle().intersects(this.getRectangle())){
					handleObjectCollisions(CollisionDirection.RIGHT_COLLISION, i.objectType);
					i.handleObjectCollisions(CollisionDirection.LEFT_COLLISION, this.objectType);
				}
				
				if((i.getRectangle().intersects(new MyPoint(bottomLeft, 0, offset.getY())) || i.getRectangle().intersects(new MyPoint(bottomRight, 0, offset.getY()))) && !i.getRectangle().intersects(this.getRectangle())){
					handleObjectCollisions(CollisionDirection.BOTTOM_COLLISION, i.objectType);
					i.handleObjectCollisions(CollisionDirection.TOP_COLLISION, this.objectType);
				}
			}
		}
	}
	
	protected void handleMapCollisions(final CollisionDirection direction) {
		if(direction.equals(CollisionDirection.BOTTOM_COLLISION)){
			groundHit = true;
			offset.setY(0);
		}
		if(direction.equals(CollisionDirection.TOP_COLLISION)){
			jumping = false;
			offset.setY(0);
		}
		if(direction.equals(CollisionDirection.LEFT_COLLISION) || direction.equals(CollisionDirection.RIGHT_COLLISION)){
			offset.setX(0);
		}
	}
	
	protected void handleObjectCollisions(final CollisionDirection direction, final ObjectType objectType) {
		handleMapCollisions(direction);
	}
	
	protected abstract void loadSprites();
	
	protected List<List<BufferedImage>> loadSpritesFromFile(final int[] columns, final String source) throws IOException{
		final List<List<BufferedImage>> images = new ArrayList<>();
		final BufferedImage sprites = ImageIO.read(getClass().getResourceAsStream(source));
		final Position currentSubImagePosition = Game.ORIGIN.clone();
		
		for(int r = 0; r < columns.length; r++){
			images.add(new ArrayList<>());
			currentSubImagePosition.setX(Game.ORIGIN.getX());
			for(int c = 0; c < columns[r]; c++){
				images.get(images.size() - 1).add(sprites.getSubimage(currentSubImagePosition.getX(), currentSubImagePosition.getY(), spriteDimension.getWidth(), spriteDimension.getHeight()));
				currentSubImagePosition.setX(currentSubImagePosition.getX() + spriteDimension.getWidth());
			}
			currentSubImagePosition.setY(currentSubImagePosition.getY() + spriteDimension.getHeight());
		}
		
		return images;
	}
	
	public boolean isDead(){
		return death;
	}
	
	public Position getObjectPosition(){
		return this.myPosition;
	}
	
	public void setObjectPosition(final Position position){
		this.myPosition = position.clone();
	}
	
	public MyRectangle getRectangle(){
		return new MyRectangle(myPosition.getX(), myPosition.getY(), spriteDimension.getWidth(), spriteDimension.getHeight());
	}
	
	public ObjectType getObjectType(){ return this.objectType; }
	
	private void buildCorners(){
		topLeft.setLocation(myPosition.getX(), myPosition.getY());
		topRight.setLocation(myPosition.getX() + spriteDimension.getWidth() - 1, myPosition.getY());
		bottomLeft.setLocation(myPosition.getX(), myPosition.getY() + spriteDimension.getHeight() - 1);
		bottomRight.setLocation(myPosition.getX() + spriteDimension.getWidth() - 1, myPosition.getY() + spriteDimension.getHeight() - 1);
	}
	
	/**
	 * Setta la posizione dell'oggetto a partire dalla destinationPosition
	 * @param pos
	 */
	public void updateObjectPosition(){
		buildCorners();
		checkForTileCollisions();
		checkForObjectCollisions();
		myPosition.setPosition(myPosition.getX() + offset.getX(), myPosition.getY() + offset.getY());
	}
	
	@Override
	public boolean equals(final Object gameObject){
		return gameObject instanceof AbstractGameObject && myPosition.equals(((AbstractGameObject)gameObject).getObjectPosition());
	}
	
	@Override
	public int hashCode(){
		return this.getObjectPosition().hashCode();
	}
	
	@Override
	public void update() {
		int xOffset = 0;
		int yOffset = 0;
		
		mapPosition.copyPosition(tileMap.getPosition());
		
		if(jumping){
			yOffset += upOffset;
			currentJump++;
			if(currentJump > maxJump){
				jumping = false;
			}
		}
		
		yOffset += falling && !jumping ? downOffset : 0;
		xOffset += left ? leftOffset : 0;
		xOffset += right ? rightOffset : 0;
		
		if(xOffset != 0 || yOffset != 0){
			offset.setX(xOffset);
			offset.setY(yOffset);
			updateObjectPosition();
		}
	}
	
	@Override
	public void draw(final Graphics2D g) {
		if(facingRight){
			g.drawImage(currentAnimation.getImage(), myPosition.getX() - mapPosition.getX(), myPosition.getY() - mapPosition.getY(), spriteDimension.getWidth(), spriteDimension.getHeight(), null);
		}else{
			g.drawImage(currentAnimation.getImage(), myPosition.getX() - mapPosition.getX() + spriteDimension.getWidth(), myPosition.getY() - mapPosition.getY(),  - spriteDimension.getWidth(), spriteDimension.getHeight(), null);
		}
	}
	
	@Override
	public void keyPressed(final KeyEvent e){
		if(isNotRunning()){ return; }
	}
	
	@Override
	public void keyReleased(final KeyEvent e){
		if(isNotRunning()){ return; }
	}
	
	private boolean isNotRunning(){
		return !GamePanel.getInstance().isRunning();
	}
}

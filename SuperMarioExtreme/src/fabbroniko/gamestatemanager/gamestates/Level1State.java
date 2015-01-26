package fabbroniko.gamestatemanager.gamestates;

import java.util.ArrayList;

import fabbroniko.environment.Position;
import fabbroniko.gameobjects.Block;
import fabbroniko.gameobjects.Castle;
import fabbroniko.gameobjects.Enemy;
import fabbroniko.gameobjects.FallingBlock;
import fabbroniko.gameobjects.AbstractGameObject;
import fabbroniko.gameobjects.GameObjectBuilder;
import fabbroniko.gameobjects.InvisibleBlock;
import fabbroniko.gameobjects.Player;
import fabbroniko.gamestatemanager.AbstractGenericLevel;
import fabbroniko.gamestatemanager.GameStateManager;
import fabbroniko.gamestatemanager.GameStateManager.GameStates;

/**
 * First Level.
 * @author fabbroniko
 */
public final class Level1State extends AbstractGenericLevel {
	
	private static final Level1State MY_INSTANCE = new Level1State();
	
	private static final String RES_BACKGROUND_IMAGE = "/fabbroniko/Levels/LevelsBG.png";
	private static final String RES_TILESET_IMAGE = "/fabbroniko/Levels/TileMap.png";
	private static final String RES_MAP_FILE = "/fabbroniko/Levels/Level1.txt";
	
	private static final Position BLOCK1_POSITION = new Position(60, 250);
	private static final Position BLOCK2_POSITION = new Position(90, 250);
	private static final Position ENEMY1_POSITION = new Position(1315, 270);
	private static final Position ENEMY2_POSITION = new Position(2000, 250);
	private static final Position ENEMY3_POSITION = new Position(2350, 250);
	private static final Position FALLING1_POSITION = new Position(270, 270);
	private static final Position FALLING2_POSITION = new Position(390, 190);
	private static final Position FALLING3_POSITION = new Position(480, 150);
	private static final Position FALLING4_POSITION = new Position(610, 300);
	private static final Position FALLING5_POSITION = new Position(700, 260);
	private static final Position FALLING6_POSITION = new Position(1470, 330);
	private static final Position FALLING7_POSITION = new Position(1560, 330);
	private static final Position INVISIBLE1_POSITION = new Position(330, 230);
	private static final Position INVISIBLE2_POSITION = new Position(760, 210);
	private static final Position INVISIBLE3_POSITION = new Position(990, 250);
	private static final Position INVISIBLE4_POSITION = new Position(1020, 170);
	private static final Position INVISIBLE5_POSITION = new Position(1170, 140);
	private static final Position INVISIBLE6_POSITION = new Position(1770, 170);
	private static final Position INVISIBLE7_POSITION = new Position(1740, 240);
	private static final Position INVISIBLE8_POSITION = new Position(1770, 240);
	private static final Position INVISIBLE9_POSITION = new Position(1800, 240);
	private static final Position INVISIBLE10_POSITION = new Position(1830, 240);
	private static final Position CASTLE_POSITION = new Position(2750, 170);
	
	private static final int POSITION_OFFSET = 10;
	
	private Level1State() {
		super(RES_BACKGROUND_IMAGE, RES_TILESET_IMAGE, RES_MAP_FILE);
	}
	
	/**
	 * Gets the single instance of this class.
	 * @return The single instance of this class.
	 */
	public static Level1State getInstance() {
		return MY_INSTANCE;
	}

	@Override
	public void init() {
		super.init();
		
		final GameObjectBuilder gameObjectBuilder = new GameObjectBuilder(tileMap, this);
		gameObjects = new ArrayList<AbstractGameObject>();
		
		gameObjects.add(gameObjectBuilder.newInstance(Player.class).setPosition(getPreferredStartPosition()).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Block.class).setPosition(BLOCK1_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Block.class).setPosition(BLOCK2_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Enemy.class).setPosition(ENEMY1_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Enemy.class).setPosition(ENEMY2_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Enemy.class).setPosition(ENEMY3_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(FALLING1_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(FALLING2_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(FALLING3_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(FALLING4_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(FALLING5_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(FALLING6_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(FALLING7_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE1_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE2_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE3_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE4_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE5_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE6_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE7_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE8_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE9_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(INVISIBLE10_POSITION).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Castle.class).setPosition(CASTLE_POSITION).getInstance());
	}
	
	@Override
	protected Position getPreferredStartPosition() {
		return new Position(tileMap.getTileSize().getWidth() + POSITION_OFFSET, tileMap.getTileSize().getHeight() + POSITION_OFFSET);
	}
	
	@Override
	public void levelFinished() {
		GameStateManager.getInstance().setState(GameStates.WIN_STATE);
	}
}

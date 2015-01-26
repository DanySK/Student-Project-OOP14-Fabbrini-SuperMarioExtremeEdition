package fabbroniko.gamestatemanager.gamestates;

import java.util.ArrayList;

import fabbroniko.environment.*;
import fabbroniko.gameobjects.Block;
import fabbroniko.gameobjects.Castle;
import fabbroniko.gameobjects.Enemy;
import fabbroniko.gameobjects.FallingBlock;
import fabbroniko.gameobjects.AbstractGameObject;
import fabbroniko.gameobjects.GameObjectBuilder;
import fabbroniko.gameobjects.InvisibleBlock;
import fabbroniko.gameobjects.Player;
import fabbroniko.gamestatemanager.*;
import fabbroniko.gamestatemanager.GameStateManager.GameStates;

public final class Level1State extends GenericLevel{
	
	private static final Level1State MY_INSTANCE = new Level1State();
	
	private static final String RES_BACKGROUND_IMAGE = "/fabbroniko/Levels/LevelsBG.png";
	private static final String RES_TILESET_IMAGE = "/fabbroniko/Levels/TileMap.png";
	private static final String RES_MAP_FILE = "/fabbroniko/Levels/Level1.txt";
	
	private static final int POSITION_OFFSET = 10;
	
	private Level1State() {
		super(RES_BACKGROUND_IMAGE, RES_TILESET_IMAGE, RES_MAP_FILE);
	}
	
	public static Level1State getInstance(){
		return MY_INSTANCE;
	}

	@Override
	public void init() {
		super.init();
		
		final GameObjectBuilder gameObjectBuilder = new GameObjectBuilder(tileMap, this);
		gameObjects = new ArrayList<AbstractGameObject>();
		
		gameObjects.add(gameObjectBuilder.newInstance(Player.class).setPosition(getPreferredStartPosition()).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Block.class).setPosition(new Position(60, 250)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Block.class).setPosition(new Position(90, 250)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Enemy.class).setPosition(new Position(1315, 270)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Enemy.class).setPosition(new Position(2000, 250)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Enemy.class).setPosition(new Position(2350, 250)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(330, 230)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(new Position(270, 270)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(new Position(390, 190)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(new Position(480, 150)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(new Position(610, 300)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(new Position(700, 260)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(new Position(1470, 330)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(FallingBlock.class).setPosition(new Position(1560, 330)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(760, 210)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(990, 250)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(1020, 170)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(1170, 140)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(1770, 170)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(1740, 240)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(1770, 240)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(1800, 240)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(InvisibleBlock.class).setPosition(new Position(1830, 240)).getInstance());
		gameObjects.add(gameObjectBuilder.newInstance(Castle.class).setPosition(new Position(2750, 170)).getInstance());
	}
	
	@Override
	protected Position getPreferredStartPosition(){
		return new Position(tileMap.getTileSize().getWidth() + POSITION_OFFSET, tileMap.getTileSize().getHeight() + POSITION_OFFSET);
	}
	
	@Override
	public void levelFinished(){
		GameStateManager.getInstance().setState(GameStates.WIN_STATE);
	}
}

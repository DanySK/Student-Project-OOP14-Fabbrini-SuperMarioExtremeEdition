package fabbroniko.gamestatemanager.gamestates;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

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
import fabbroniko.main.Game;

public final class Level1State extends AbstractGameState implements GenericLevel{

	private Background bg;
	private TileMap tileMap;
	private Position startPosition;
	private List<AbstractGameObject> gameObjects;
	
	private static final Level1State MY_INSTANCE = new Level1State();
	
	private static final String RES_BACKGROUND_IMAGE = "/fabbroniko/Levels/LevelsBG.png";
	private static final String RES_TILESET_IMAGE = "/fabbroniko/Levels/TileMap.png";
	private static final String RES_MAP_FILE = "/fabbroniko/Levels/Level1.txt";
	
	private static final int POSITION_OFFSET = 10;
	
	private Level1State() {
		super();
	}
	
	public static Level1State getInstance(){
		return MY_INSTANCE;
	}
	
	private Position getPreferredStartPosition(){
		return startPosition;
	}

	@Override
	public void init() {
		bg = new Background(RES_BACKGROUND_IMAGE);
		tileMap = new TileMap(RES_TILESET_IMAGE, RES_MAP_FILE);
		tileMap.setPosition(Game.ORIGIN.clone());
		
		startPosition = new Position(tileMap.getTileSize().getWidth() + POSITION_OFFSET, tileMap.getTileSize().getHeight() + POSITION_OFFSET);
		gameObjects = new ArrayList<AbstractGameObject>();
		final GameObjectBuilder gameObjectBuilder = new GameObjectBuilder(tileMap, this);
		
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
		
		AudioManager.getInstance().setMusic(AudioManager.Sounds.BACKGROUND_SOUND, true);
	}

	@Override
	public void update() {
		super.update();
		
		for(int i = 0; i < gameObjects.size(); i++){
			gameObjects.get(i).update();
			if(gameObjects.get(i).isDead()){
				gameObjects.remove(i);
			}
		}
	}

	@Override
	public void draw(final Graphics2D g) {
		bg.draw(g);
		
		for(final AbstractGameObject i:gameObjects){
			if(!i.isDead()){
				i.draw(g);	
			}
		}
		
		tileMap.draw(g);
	}

	@Override
	public void keyPressed(final KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			GameStateManager.getInstance().setState(GameStates.MENU_STATE);
			return;
		}
		for(final AbstractGameObject i:gameObjects){
			i.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) { 
		for(final AbstractGameObject i:gameObjects){
			i.keyReleased(e);
		} 
	}

	@Override
	public void addNewObject(final AbstractGameObject obj) {
		gameObjects.add(obj);
	}
	
	@Override
	public List<AbstractGameObject> getGameObjects(){
		return this.gameObjects;
	}
}

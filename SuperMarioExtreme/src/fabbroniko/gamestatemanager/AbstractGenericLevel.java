package fabbroniko.gamestatemanager;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

import fabbroniko.environment.AudioManager;
import fabbroniko.environment.Background;
import fabbroniko.environment.Position;
import fabbroniko.environment.TileMap;
import fabbroniko.gameobjects.AbstractGameObject;
import fabbroniko.gamestatemanager.GameStateManager.GameStates;
import fabbroniko.main.Game;

/**
 * Represents a generic level.
 * @author fabbroniko
 *
 */
public abstract class AbstractGenericLevel extends AbstractGameState {
	
	protected Background bg;
	protected TileMap tileMap;
	protected List<AbstractGameObject> gameObjects;
	
	private final String resBgImage;
	private final String resTileSet;
	private final String resMapFile;
	
	public AbstractGenericLevel(final String bgImage, final String tileSet, final String mapFile){
		this.resBgImage = bgImage;
		this.resTileSet = tileSet;
		this.resMapFile = mapFile;
	}
	
	@Override
	public void init() {
		bg = new Background(resBgImage);
		tileMap = new TileMap(resTileSet, resMapFile);
		tileMap.setPosition(Game.ORIGIN.clone());
		
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
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameStateManager.getInstance().setState(GameStates.MENU_STATE);
			return;
		}
		for (final AbstractGameObject i:gameObjects) {
			i.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(final KeyEvent e) { 
		for (final AbstractGameObject i:gameObjects) {
			i.keyReleased(e);
		} 
	}
	
	public void addNewObject(final AbstractGameObject obj) {
		gameObjects.add(obj);
	}
	
	public List<AbstractGameObject> getGameObjects() {
		return this.gameObjects;
	}
	
	protected abstract Position getPreferredStartPosition();
	
	public abstract void levelFinished();
}

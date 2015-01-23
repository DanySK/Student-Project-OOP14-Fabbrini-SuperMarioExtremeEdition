package fabbroniko.gamestatemanager;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import fabbroniko.gamestatemanager.gamestates.DeathState;
import fabbroniko.gamestatemanager.gamestates.Level1State;
import fabbroniko.gamestatemanager.gamestates.MenuState;
import fabbroniko.gamestatemanager.gamestates.SettingsState;
import fabbroniko.gamestatemanager.gamestates.WinState;
import fabbroniko.main.*;

/**
 * Handles the current state of the game (e.g. Menus, Levels, etc.)
 * @author nicola.fabbrini
 *
 */
public final class GameStateManager implements Drawable, KeyDependent{
	
	private final Map<GameStates, AbstractGameState> gameStates;
	private GameStates currentState;
	private GameStates previousState;
	private static Object synchronize;
	
	private static boolean initialized;
	private static GameStateManager myInstance;
	
	/**
	 * Constructs a new GameStateManager
	 */
	private GameStateManager(){
		currentState = GameStates.NO_STATE;
		previousState = GameStates.NO_STATE;
		gameStates = new HashMap<>();
		gameStates.put(GameStates.MENU_STATE, MenuState.getInstance());
		gameStates.put(GameStates.SETTINGS_STATE, SettingsState.getInstance());
		gameStates.put(GameStates.LEVEL1_STATE, Level1State.getInstance());
		gameStates.put(GameStates.DEATH_STATE, DeathState.getInstance());
		gameStates.put(GameStates.WIN_STATE, WinState.getInstance());
		this.setState(GameStates.MENU_STATE);
		
		initialized = true;
	}
	
	public static GameStateManager getInstance(){
		if(!initialized){
			synchronize = new Object();
			myInstance = new GameStateManager();
		}
		return myInstance;
	}
	
	/**
	 * Sets the specified state that has to be displayed on the screen.
	 * @param selectedState State that has to be draw on the screen
	 */
	public void setState(final GameStates selectedState){
		synchronized(synchronize){
			if(this.currentState != GameStates.NO_STATE){
				this.previousState = currentState;
			}
			this.gameStates.get(selectedState).init();
			this.currentState = selectedState;
		}
	}
	
	public void setPreviousState(){
		this.setState(previousState);
	}
	
	/**
	 * @see fabbroniko.main.Drawable#update()
	 */
	public void update(){
		synchronized(synchronize){
			gameStates.get(this.currentState).update();
		}
	}
	
	/**
	 * @see fabbroniko.main.Drawable#draw(Graphics2D)
	 */
	public void draw(final Graphics2D g){
		synchronized(synchronize){
			gameStates.get(this.currentState).draw(g);
		}
	}

	/**
	 * @see fabbroniko.main.KeyDependent#keyReleased(KeyEvent)
	 */
	public void keyPressed(final KeyEvent e) {
		gameStates.get(this.currentState).keyPressed(e);
	}

	/**
	 * @see fabbroniko.main.KeyDependent#keyTyped(KeyEvent)
	 */
	public void keyReleased(final KeyEvent e) {
		gameStates.get(this.currentState).keyReleased(e);
	}
	
	public enum GameStates{
		MENU_STATE, SETTINGS_STATE, LEVEL1_STATE, DEATH_STATE, WIN_STATE, NO_STATE;
	}
}

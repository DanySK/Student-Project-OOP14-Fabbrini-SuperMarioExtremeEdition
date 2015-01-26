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
import fabbroniko.main.Drawable;
import fabbroniko.main.KeyDependent;

/**
 * Handles the current state of the game (e.g. Menus, Levels, etc.)
 * @author nicola.fabbrini
 *
 */
public final class GameStateManager implements Drawable, KeyDependent {
	
	private final Map<GameStates, AbstractGameState> gameStates;
	private GameStates currentState;
	private GameStates previousState;
	private final Object synchronize;
	
	private static final GameStateManager MY_INSTANCE = new GameStateManager();
	
	/**
	 * Constructs a new GameStateManager
	 */
	private GameStateManager() {
		synchronize = new Object();
		
		currentState = GameStates.NO_STATE;
		previousState = GameStates.NO_STATE;
		gameStates = new HashMap<>();
		gameStates.put(GameStates.MENU_STATE, MenuState.getInstance());
		gameStates.put(GameStates.SETTINGS_STATE, SettingsState.getInstance());
		gameStates.put(GameStates.LEVEL1_STATE, Level1State.getInstance());
		gameStates.put(GameStates.DEATH_STATE, DeathState.getInstance());
		gameStates.put(GameStates.WIN_STATE, WinState.getInstance());
		this.setState(GameStates.MENU_STATE);
	}
	
	/**
	 * Gets the single instance of this class.
	 * @return The single instance of this class.
	 */
	public static GameStateManager getInstance() {
		return MY_INSTANCE;
	}
	
	/**
	 * Sets the specified state that has to be displayed on the screen.
	 * @param selectedState State that has to be draw on the screen
	 */
	public void setState(final GameStates selectedState) {
		synchronized (synchronize) {
			if (this.currentState != GameStates.NO_STATE) {
				this.previousState = currentState;
			}
			this.gameStates.get(selectedState).init();
			this.currentState = selectedState;
		}
	}
	
	/**
	 * Sets the previous state.
	 */
	public void setPreviousState() {
		this.setState(previousState);
	}
	
	/**	Updates the image that should be displayed.
	 * 	@see fabbroniko.main.Drawable#update()
	 */
	public void update() {
		synchronized (synchronize) {
			gameStates.get(this.currentState).update();
		}
	}
	
	/**	Draws the current state.
	 * 	@param g Graphic Context
	 * 	@see fabbroniko.main.Drawable#draw(Graphics2D)
	 */
	public void draw(final Graphics2D g) {
		synchronized (synchronize) {
			gameStates.get(this.currentState).draw(g);
		}
	}

	/**	Notifies, to the current state, that a key has been pressed.
	 * 	@param e Contains details about the event.
	 * 	@see fabbroniko.main.KeyDependent#keyReleased(KeyEvent)
	 */
	public void keyPressed(final KeyEvent e) {
		gameStates.get(this.currentState).keyPressed(e);
	}

	/**	Notifies, to the current state, that a key has been released.
	 * 	@param e Contains details about the event.
	 * 	@see fabbroniko.main.KeyDependent#keyReleased(KeyEvent)
	 */
	public void keyReleased(final KeyEvent e) {
		gameStates.get(this.currentState).keyReleased(e);
	}
	
	/**
	 * Represents a GameState.
	 * @author fabbroniko
	 */
	public enum GameStates {
		
		/**
		 * Represents the Menu.
		 */
		MENU_STATE,
		
		/**
		 * Represents the Settings Menu.
		 */
		SETTINGS_STATE,
		
		/**
		 * Represents the Level 1.
		 */
		LEVEL1_STATE, 
		
		/**
		 * Represents the Death Window.
		 */
		DEATH_STATE,
		
		/**
		 * Represents the Win Window.
		 */
		WIN_STATE, 
		
		/**
		 * Represents an empty state.
		 */
		NO_STATE;
	}
}

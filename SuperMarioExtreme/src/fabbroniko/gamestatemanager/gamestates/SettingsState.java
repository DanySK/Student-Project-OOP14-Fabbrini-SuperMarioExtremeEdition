package fabbroniko.gamestatemanager.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import fabbroniko.environment.Background;
import fabbroniko.gamestatemanager.*;
import fabbroniko.gamestatemanager.GameStateManager.GameStates;
import fabbroniko.main.Drawable;

/**
 * Handles and Draws the Settings State
 * @author fabbroniko
 *
 */
public final class SettingsState extends AbstractGameState{

	private Background bg;
	
	private static boolean initialized;
	private static SettingsState myInstance;
	private int currentSelection;
	private boolean keyListening;
	private boolean leftKeyListening;
	private boolean rightKeyListening;
	private boolean jumpKeyListening;
	
	private int jumpKeyCode = KeyEvent.VK_SPACE;
	private int rightKeyCode = KeyEvent.VK_RIGHT;
	private int leftKeyCode = KeyEvent.VK_LEFT;
	private boolean music = true;
	private boolean effects = true;
	
	private static final Color WHITE = new Color(0xffffff);
	private static final Color RED = new Color(0xff0000);
	private static final int MAX_SELECTION = 4;
	private static final String RES_BACKGROUND_IMAGE = "/fabbroniko/BaseBG.png";
	
	/**
	 * Constructs a new SettingsState
	 * @param gsm Reference of the GameStateManager
	 */
	private SettingsState(){
		super();
		initialized = true;
	}
	
	public static SettingsState getInstance(){
		if(!initialized){
			myInstance = new SettingsState();
		}
		return myInstance;
	}

	/**
	 * @see AbstractGameState#init()
	 */
	@Override
	public void init() {
		bg = new Background(RES_BACKGROUND_IMAGE);
	}

	/**
	 * @see Drawable#update()
	 */
	@Override
	public void update() {}

	/**
	 * @see Drawable#draw(Graphics2D)
	 */
	@Override
	public void draw(final Graphics2D g) {
		bg.draw(g);
		g.setColor((currentSelection == 0) ? RED : WHITE);
		g.drawString("Left Key: ", 30,30);
		g.drawString("<" + getKeyString(leftKeyCode) + ">", 200, 30);
		
		g.setColor((currentSelection == 1) ? RED : WHITE);
		g.drawString("Right Key: ", 30, 60);
		g.drawString("<" + getKeyString(rightKeyCode) + ">", 200, 60);
		
		g.setColor((currentSelection == 2) ? RED : WHITE);
		g.drawString("Jump Key: ", 30, 90);
		g.drawString("<" + getKeyString(jumpKeyCode) + ">", 200, 90);
		
		g.setColor((currentSelection == 3) ? RED : WHITE);
		g.drawString("Music: ", 30, 120);
		g.drawString("<" + (music ? "ON" : "OFF") + ">", 200, 120);
		
		g.setColor((currentSelection == 4) ? RED : WHITE);
		g.drawString("Effects: ", 30, 150);
		g.drawString("<" + (effects ? "ON" : "OFF") + ">", 200, 150);
	}
	
	private String getKeyString(final int keyCode){
		String tmp;
		
		if(keyCode == KeyEvent.VK_UP){ 
			tmp = "UP"; 
		}else if(keyCode == KeyEvent.VK_LEFT){ 
			tmp = "LEFT";
		}else if(keyCode == KeyEvent.VK_RIGHT){
			tmp = "RIGHT";
		}else if(keyCode == KeyEvent.VK_DOWN){
			tmp = "DOWN";
		}else if(keyCode == KeyEvent.VK_SPACE){ 
			tmp = "SPACE";
		}else{
			tmp = "" + (char)keyCode;
		}
		
		return tmp;
	}
	
	public int getJumpKey(){ return this.jumpKeyCode; }
	
	public int getRightKey(){ return this.rightKeyCode; }
	
	public int getLeftKeyCode(){ return leftKeyCode; }
	
	public boolean musicIsActive(){ return music; }
	
	public boolean effectIsActive(){ return effects; }

	/**
	 * @see KeyListener#keyPressed(KeyEvent)
	 */
	@Override
	public void keyPressed(final KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			GameStateManager.getInstance().setState(GameStates.MENU_STATE);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP && !keyListening){
			currentSelection--;
			currentSelection = currentSelection < 0 ? MAX_SELECTION : currentSelection;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN && !keyListening){
			currentSelection++;
			currentSelection = currentSelection > MAX_SELECTION ? 0 : currentSelection;
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			switch(currentSelection){
			case 0:
				keyListening ^= true;
				leftKeyListening ^= true;
				break;
			case 1: 
				keyListening ^= true;
				rightKeyListening ^= true;
				break;
			case 2:
				keyListening ^= true;
				jumpKeyListening ^= true;
				break;
			case 3:
				music ^= true;
				break;
			case 4:
				effects ^= true;
				break;
			default:
					break;
			}
		}
		
		if(leftKeyListening){
			leftKeyCode = e.getKeyCode();
		}
		if(rightKeyListening){
			rightKeyCode = e.getKeyCode();
		}
		if(jumpKeyListening){
			jumpKeyCode = e.getKeyCode();
		}
	}

	/**
	 * @see KeyListener#keyReleased(KeyEvent)
	 */
	@Override
	public void keyReleased(final KeyEvent e) {}
}

package fabbroniko.gamestatemanager.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import fabbroniko.environment.AudioManager;
import fabbroniko.environment.Dimension;
import fabbroniko.environment.EnvironmentStatics;
import fabbroniko.error.ResourceNotFoundError;
import fabbroniko.gamestatemanager.AbstractGameState;
import fabbroniko.gamestatemanager.GameStateManager;
import fabbroniko.main.Game;

public class DeathState extends AbstractGameState{
	
	private static boolean instance;
	private static DeathState myInstance;
	
	private int death;
	private BufferedImage gameOver;
	private int currentDelayCount;
	
	private static final String RES_GAMEOVER_IMAGE = "/fabbroniko/Menu/GameOver.png";
	private static final int DELAY_MAX_COUNT = 2000 / Game.FPS_MILLIS;
	private static final int GAME_OVER_OFFSET = 50;
	private static final Color black = new Color(0x00000000);
	private static final Color white = new Color(0xffffffff);

	private DeathState() {
		super();
		instance = true;
	}

	public static DeathState getInstance(){
		if(!instance){
			myInstance = new DeathState();
		}
		return myInstance;
	}

	@Override
	public void init() {
		try {
			gameOver = ImageIO.read(getClass().getResourceAsStream(RES_GAMEOVER_IMAGE));
		} catch (IOException e) {
			throw new ResourceNotFoundError(RES_GAMEOVER_IMAGE);
		}
		
		AudioManager.getInstance().setMusic(AudioManager.Sounds.GAME_OVER_SOUND, false);
		currentDelayCount = 0;
	}

	@Override
	public void update() {
		if(SettingsState.getInstance().musicIsActive() && !AudioManager.getInstance().isRunning() || !SettingsState.getInstance().musicIsActive() && currentDelayCount++ > DELAY_MAX_COUNT){
			GameStateManager.getInstance().setPreviousState();
		}
	}
	
	public void incDeath(){
		death++;
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(black);
		g.fillRect(Game.ORIGIN.getX(), Game.ORIGIN.getY(), Game.BASE_WINDOW_SIZE.getWidth(), Game.BASE_WINDOW_SIZE.getHeight());
		g.setColor(white);
		g.drawString("X " + death, Game.BASE_WINDOW_SIZE.getWidth() / 2, Game.BASE_WINDOW_SIZE.getHeight() / 2);
		g.drawImage(gameOver, EnvironmentStatics.getXCentredPosition(new Dimension(gameOver.getWidth(), gameOver.getHeight())).getX(), ((int)Game.BASE_WINDOW_SIZE.getHeight() / 2) - GAME_OVER_OFFSET, null);
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}

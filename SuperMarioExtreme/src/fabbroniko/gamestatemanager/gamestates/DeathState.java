package fabbroniko.gamestatemanager.gamestates;

import java.awt.Color;
import java.awt.Graphics2D;
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

/**
 * Death Window, it should be shown when the player dies.
 * @author fabbroniko
 */
public final class DeathState extends AbstractGameState {
	
	private static final DeathState MY_INSTANCE = new DeathState();
	
	private int death;
	private BufferedImage gameOver;
	private int currentDelayCount;
	
	private static final String RES_GAMEOVER_IMAGE = "/fabbroniko/Menu/GameOver.png";
	private static final int TWO_SECONDS = 2000;
	private static final int DELAY_MAX_COUNT = TWO_SECONDS / Game.FPS_MILLIS;
	private static final int GAME_OVER_OFFSET = 50;
	private static final Color BLACK = new Color(0x00000000);
	private static final Color WHITE = new Color(0xffffffff);

	private DeathState() {
		super();
	}

	/**
	 * Gets the single instance of this class.
	 * @return The single instance of this class.
	 */
	public static DeathState getInstance() {
		return MY_INSTANCE;
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
		if (SettingsState.getInstance().musicIsActive() && !AudioManager.getInstance().isRunning() || !SettingsState.getInstance().musicIsActive() && currentDelayCount > DELAY_MAX_COUNT) {
			GameStateManager.getInstance().setPreviousState();
		}
		currentDelayCount++;
	}
	
	/**
	 * Increments the number of deaths.
	 */
	public void incDeath() {
		death++;
	}

	@Override
	public void draw(final Graphics2D g) {
		g.setColor(BLACK);
		g.fillRect(Game.ORIGIN.getX(), Game.ORIGIN.getY(), Game.BASE_WINDOW_SIZE.getWidth(), Game.BASE_WINDOW_SIZE.getHeight());
		g.setColor(WHITE);
		g.drawString("X " + death, Game.BASE_WINDOW_SIZE.getWidth() / 2, Game.BASE_WINDOW_SIZE.getHeight() / 2);
		g.drawImage(gameOver, EnvironmentStatics.getXCentredPosition(new Dimension(gameOver.getWidth(), gameOver.getHeight())).getX(), Game.BASE_WINDOW_SIZE.getHeight() / 2 - GAME_OVER_OFFSET, null);
	}
}

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
import fabbroniko.gamestatemanager.GameStateManager.GameStates;
import fabbroniko.main.Game;

public final class WinState extends AbstractGameState{

	private BufferedImage win;
	
	private static final WinState MY_INSTANCE = new WinState();
	private int currentDelayCount;
	
	private static final String RES_WIN_IMAGE = "/fabbroniko/Menu/WinString.png";
	private static final int DELAY_MAX_COUNT = 2000 / Game.FPS_MILLIS;
	private static final Color BLACK = new Color(0x00000000);
	private static final int WIN_OFFSET = 50;
	
	private WinState(){
		super();
	}
	
	public static WinState getInstance(){
		return MY_INSTANCE;
	}
	
	@Override
	public void init() {
		try {
			win = ImageIO.read(getClass().getResourceAsStream(RES_WIN_IMAGE));
		} catch (IOException e) {
			throw new ResourceNotFoundError(RES_WIN_IMAGE);
		}
		
		AudioManager.getInstance().setMusic(AudioManager.Sounds.WIN_SOUND, false);
		currentDelayCount = 0;
	}

	@Override
	public void update() {
		if(SettingsState.getInstance().musicIsActive() && !AudioManager.getInstance().isRunning() || !SettingsState.getInstance().musicIsActive() && currentDelayCount > DELAY_MAX_COUNT){
			GameStateManager.getInstance().setState(GameStates.MENU_STATE);
		}
		currentDelayCount++;
	}

	@Override
	public void draw(final Graphics2D g) {
		g.setColor(BLACK);
		g.fillRect(Game.ORIGIN.getX(), Game.ORIGIN.getY(), Game.BASE_WINDOW_SIZE.getWidth(), Game.BASE_WINDOW_SIZE.getHeight());
		g.drawImage(win, EnvironmentStatics.getXCentredPosition(new Dimension(win.getWidth(), win.getHeight())).getX(), Game.BASE_WINDOW_SIZE.getHeight() / 2 - WIN_OFFSET, null);
	}
}

package fabbroniko.main;

import fabbroniko.environment.Dimension;
import fabbroniko.environment.Position;

import java.awt.Toolkit;

import javax.swing.JFrame;


/**
 * Starts a new game.
 * @author fabbroniko 
 *
 */
public final class Game extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final double SCALE_DIVISION = 2;
	private static final String GAME_NAME = "Super Mario Bros Extreme Edition";
	private static final int FPS = 50;
	
	/**
	 * Starting dimensions of the window. It will be scaled to determine which is the final dimension of the screen
	 */
	public static final Dimension BASE_WINDOW_SIZE = new Dimension(320, 240);
	
	/**
	 * Base Position.
	 */
	public static final Position ORIGIN = new Position(0, 0);
	
	/**
	 * Represents the actual screen dimensions.
	 */
	public static final Dimension SCREEN_SIZE = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
	
	/**
	 * Represents the x scale of the whole game starting from the BASE_SIZE.
	 */
	public static final double X_SCALE = (double) (SCREEN_SIZE.getWidth() / BASE_WINDOW_SIZE.getWidth() / SCALE_DIVISION);
	
	/**
	 * Represents the y scale of the whole game starting from the BASE_SIZE.
	 */
	public static final double Y_SCALE = (double) (SCREEN_SIZE.getHeight() / BASE_WINDOW_SIZE.getHeight() / SCALE_DIVISION);
	
	/** 
	 * Represents the game's window dimensions.
	 */
	public static final Dimension WINDOW_SIZE = new Dimension((int) (BASE_WINDOW_SIZE.getWidth() * X_SCALE), (int) (BASE_WINDOW_SIZE.getHeight() * Y_SCALE));
	
	/**
	 * Time for which every frame should stay on the screen.
	 */
	public static final int FPS_MILLIS = 1000 / FPS;
	
	/**
	 * Contructs the whole game
	 */
	private Game() {
		this.setTitle(GAME_NAME);
		this.setContentPane(GamePanel.getInstance());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * Starts the game.
	 * @param arg main's arguments
	 */
	public static void main(final String[] arg) {
		new Game();
	}
}

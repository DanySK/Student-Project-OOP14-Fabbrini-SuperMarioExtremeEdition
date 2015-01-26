package fabbroniko.main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import fabbroniko.environment.Dimension;
import fabbroniko.gamestatemanager.GameStateManager;

/**
 * Panel where the game will be drawn.
 * @author nicola.fabbrini
 */
public final class GamePanel extends JPanel implements Runnable, KeyListener {
	
	private Thread gameThread;							// Thread che conterrà la gestione di ogni parte del gioco.
	private boolean running;					// Campo booleano che serve ad uscire dal game loop.
	private BufferedImage image;						// Buffer dell'immagine finale che dovrà essere visualizzata sullo schermo
	
	private static final GamePanel MY_INSTANCE = new GamePanel();
	private boolean threadInitialized;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Initializes the game panel.
	 */
	private GamePanel() {
		super();
		this.setPreferredSize(Game.WINDOW_SIZE);
		this.setFocusable(true);
		this.requestFocus();
	}
	
	/**
	 * Gets the single instance of this class.
	 * @return The single instance of this class.
	 */
	public static GamePanel getInstance() {
		return MY_INSTANCE;
	}
	
	private void setPreferredSize(final Dimension dim) {
		setPreferredSize(new java.awt.Dimension(dim.getWidth(), dim.getHeight()));
	}
	
	/**
	 * @see JPanel#addNotify()
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		if (!threadInitialized) {
			gameThread = new Thread(this);
			this.addKeyListener(this);
			gameThread.start();
			threadInitialized = true;
		}
	}

	/**
	 * @see Thread#run()
	 */
	@Override
	public void run() {
		long currentTime;
		long wait;

		running = true;
		image = new BufferedImage(Game.BASE_WINDOW_SIZE.getWidth(), Game.BASE_WINDOW_SIZE.getHeight(), BufferedImage.TYPE_INT_RGB);
		final Graphics2D imgGraphics = (Graphics2D) image.getGraphics();

		// Game Loop
		while (running) {

			currentTime = System.currentTimeMillis();

			this.update();
			this.draw(imgGraphics);
			this.repaint();

			wait = Game.FPS_MILLIS - (System.currentTimeMillis() - currentTime);
			if (wait < 0) { 
				wait = 0; 
			}

			try {
				Thread.sleep(wait);
			} catch (Exception e) {
				System.out.println("Error occurred trying to call Thread.sleep.");
			}
		}
		System.exit(0);
	}

	/**
	 * @see Drawable#update()
	 */
	private void update() {
		GameStateManager.getInstance().update();
	}
	
	/**
	 * Closes the game.
	 */
	public void exit() {
		running = false;
	}

	/**
	 * @see Drawable#draw(Graphics2D)
	 */
	private void draw(final Graphics2D g) {
		GameStateManager.getInstance().draw(g);
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(Graphics)
	 */
	@Override
	public void paintComponent(final Graphics cGraphics) {
		super.paintComponent(cGraphics);
		cGraphics.drawImage(image, Game.ORIGIN.getX(), Game.ORIGIN.getY(), Game.WINDOW_SIZE.getWidth(), Game.WINDOW_SIZE.getHeight(), null);
	}

	/**
	 * @see KeyListener#keyPressed(KeyEvent)
	 */
	@Override
	public void keyPressed(final KeyEvent e) {
		GameStateManager.getInstance().keyPressed(e);
	}

	/**
	 * @see KeyListener#keyReleased(KeyEvent)
	 */
	@Override
	public void keyReleased(final KeyEvent e) {
		GameStateManager.getInstance().keyReleased(e);
	}

	@Override
	public void keyTyped(final KeyEvent e) { 
		if (!isRunning()) { 
			return; 
		} 
	}
	
	/**
	 * Checks if the game is still running.
	 * @return The current state of the game. True if running. Otherwise, false.
	 */
	public boolean isRunning() {
		return running;
	}
}

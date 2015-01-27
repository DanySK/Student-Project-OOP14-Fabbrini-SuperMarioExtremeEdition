package fabbroniko.environment;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import fabbroniko.gamestatemanager.gamestates.SettingsState;

/**
 * Controls the Audio.
 * @author fabbroniko
 */
public final class AudioManager {

	private final Music myMusic;
	private static AudioManager myInstance;
	private static boolean instance;
	
	private static final int DELAY_TIME = 50;
	
	private AudioManager() {
		myMusic = new Music();
	}
	
	/**
	 * Gets the single instance of the AudioManager.
	 * @return Returns the only instance.
	 */
	public static AudioManager getInstance() {
		if (!instance) {
			myInstance = new AudioManager();
			instance = true;
		}
		return myInstance;
	}
	
	/**
	 * Plays a music.
	 * @param music Sound to play.
	 * @param setLoop Whether loop it or not.
	 */
	public void setMusic(final Sound music, final boolean setLoop) {
		if (!SettingsState.getInstance().musicIsActive()) {
			return;
		}
		
		try {
			myMusic.setMusic(music, setLoop);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			System.out.println("Something went wrong trying to start " + music.filePath);
		}
	}
	
	/**
	 * Plays an effect.
	 * @param effect Sound to play.
	 */
	public void setEffect(final Sound effect) {
		if (!SettingsState.getInstance().effectIsActive()) { 
			return; 
		}
		
		new Thread(new Runnable() {
			private Clip tmpClip;
			private boolean exit;
			
			@Override
			public void run() {
				try {
					tmpClip = AudioSystem.getClip();
					tmpClip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(effect.getFilePath())));
					tmpClip.addLineListener(new LineListener() {	
						@Override
						public void update(final LineEvent event) {
							if (event.getType().equals(LineEvent.Type.STOP)) {
								tmpClip.close();
								exit = true;
							}
						}
					});
					tmpClip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					System.out.println("Something went wrong trying to start " + effect.filePath);
				}
				
				while (!exit) {
					try {
						Thread.sleep(DELAY_TIME);
					} catch (Exception e) {
						System.out.println("Thread sleep exception: " + e.getMessage());
					}
				}
			}
		}).start();
	}
	
	/**
	 * Stops the current music.
	 */
	public void stopCurrent() {
		try {
			myMusic.stopCurrent();
		} catch (IOException e) {
			System.out.println("Something went wrong trying to stop the current Music.");
		}
	}
	
	/**
	 * Checks the state of the current music.
	 * @return Returns true if running, false otherwise.
	 */
	public boolean isRunning() {
		return myMusic.isRunning();
	}
	
	private final class Music {
		
		private AudioInputStream audioInputStream;
		private Clip clip;
		private boolean isMusicRunning;
		
		private Music() {
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
				System.out.println("Something went wrong trying to initialize the music manager.");
			}
		}
		
		private void setMusic(final Sound music, final boolean setLoop) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
			this.stopCurrent();
			
			audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(music.getFilePath()));
			clip.open(audioInputStream);
			if (setLoop) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} else {
				clip.addLineListener(new LineListener() {
					
					@Override
					public void update(final LineEvent event) {
						if (event.getType().equals(LineEvent.Type.START)) {
							isMusicRunning = true;
						} else {
							isMusicRunning = false;
							clip.removeLineListener(this);
						}
					}
				});
				clip.start();
			}
		}
		
		private boolean isRunning() {
			return this.isMusicRunning;
		}
		
		private void stopCurrent() throws IOException {
			if (audioInputStream != null && clip != null) {
				clip.stop();
				clip.close();
				audioInputStream.close();
			}
		}
	}
	
	/**
	 * Represents a playable sound.
	 * @author fabbroniko
	 */
	public enum Sound {
		
		/**
		 * Background Music.
		 */
		BACKGROUND_SOUND("/fabbroniko/Sounds/bg.wav"),
		
		/**
		 * Game Over Music.
		 */
		GAME_OVER_SOUND("/fabbroniko/Sounds/GameOver.wav"),
		
		/**
		 * Win Music.
		 */
		WIN_SOUND("/fabbroniko/Sounds/Win.wav"),
		
		/**
		 * Jump Effect.
		 */
		JUMP_EFFECT("/fabbroniko/Sounds/Jump.wav"),
		
		/**
		 * Hit Effect.
		 */
		HIT_EFFECT("/fabbroniko/Sounds/Hit.wav"),
		
		/**
		 * Breaking Block Effect.
		 */
		BREAKING_BLOCK_EFFECT("/fabbroniko/Sounds/BreakingBlock.wav"),
		
		/**
		 * No Selections.
		 */
		NO_SELECTION("");
		
		private String filePath;
		
		private Sound(final String filePathP) {
			this.filePath = filePathP;
		}
		
		/**
		 * Gets the path of the associated Sound.
		 * @return Audio File's path.
		 */
		public String getFilePath() {
			return this.filePath;
		}
	}
}

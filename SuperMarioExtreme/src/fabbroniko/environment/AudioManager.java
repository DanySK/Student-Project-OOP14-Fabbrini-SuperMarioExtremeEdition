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

public class AudioManager{

	private final Music myMusic;
	private static AudioManager myInstance;
	private static boolean instance;
	
	private AudioManager(){
		myMusic = new Music();
	}
	
	public static AudioManager getInstance(){
		if(!instance){
			myInstance = new AudioManager();
			instance = true;
		}
		return myInstance;
	}
	
	public void setMusic(final Sounds music, final boolean setLoop){
		if(!SettingsState.getInstance().musicIsActive()) { return; }
		
		try {
			myMusic.setMusic(music, setLoop);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			System.out.println("Something went wrong trying to start " + music.filePath);
		}
	}
	
	public void setEffect(final Sounds effect){
		if(!SettingsState.getInstance().effectIsActive()) { return; }
		
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
							if(event.getType().equals(LineEvent.Type.STOP)){
								tmpClip.close();
								exit = true;
							}
						}
					});
					tmpClip.start();
				} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
					System.out.println("Something went wrong trying to start " + effect.filePath);
				}
				
				while(!exit){
					try{
						Thread.sleep(50);
					}catch(Exception e){
						System.out.println("Thread sleep exception: " + e.getMessage());
					}
				}
			}
		}).start();
	}
	
	public void stopCurrent(){
		try {
			myMusic.stopCurrent();
		} catch (IOException e) {
			System.out.println("Something went wrong trying to stop the current Music.");
		}
	}
	
	public boolean isRunning(){ return myMusic.isRunning(); }
	
	private class Music{
		
		private AudioInputStream audioInputStream;
		private Clip clip;
		private boolean isMusicRunning;
		
		private Music(){
			try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
				System.out.println("Something went wrong trying to initialize the music manager.");
			}
		}
		
		private void setMusic(final Sounds music, final boolean setLoop) throws IOException, UnsupportedAudioFileException, LineUnavailableException{
			this.stopCurrent();
			
			audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(music.getFilePath()));
			clip.open(audioInputStream);
			if(setLoop){
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}else{
				clip.addLineListener(new LineListener() {
					
					@Override
					public void update(final LineEvent event) {
						if(event.getType().equals(LineEvent.Type.START)){
							isMusicRunning = true;
						}else{
							isMusicRunning = false;
							clip.removeLineListener(this);
						}
					}
				});
				clip.start();
			}
		}
		
		private boolean isRunning(){ return this.isMusicRunning; }
		
		private void stopCurrent() throws IOException{
			if(audioInputStream != null && clip != null){
				clip.stop();
				clip.close();
				audioInputStream.close();
			}
		}
	}
	
	public enum Sounds{
		
		BACKGROUND_SOUND("/fabbroniko/Sounds/bg.wav"),
		GAME_OVER_SOUND("/fabbroniko/Sounds/GameOver.wav"),
		WIN_SOUND("/fabbroniko/Sounds/Win.wav"),
		JUMP_EFFECT("/fabbroniko/Sounds/Jump.wav"),
		HIT_EFFECT("/fabbroniko/Sounds/Hit.wav"),
		BREAKING_BLOCK_EFFECT("/fabbroniko/Sounds/BreakingBlock.wav"),
		NO_SELECTION("");
		
		private String filePath;
		
		private Sounds(final String filePath){
			this.filePath = filePath;
		}
		
		public String getFilePath(){
			return this.filePath;
		}
	}
}

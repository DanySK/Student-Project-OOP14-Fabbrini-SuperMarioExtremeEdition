package fabbroniko.gameobjects;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Animation{

	private List<BufferedImage> frames;
	private long maxTimes;
	private long currentTimes;
	private int currentFrame;
	private boolean repeatedOnce;
	private boolean repeatOnce;
	private final Animations myAnimation;
	
	private static Map<Animations, Animation> myInstance = new HashMap<>();
	
	private static final int START_INDEX = 0;
	
	private Animation(final Animations myAnimation){
		this.currentTimes = 0;
		this.currentFrame = START_INDEX;
		this.myAnimation = myAnimation;
	}
	
	public static Animation getInstance(final Animations type){
		if(!myInstance.containsKey(type)){
			myInstance.put(type, new Animation(type));
		}
		
		return myInstance.get(type);
	}
	
	public void setImages(final List<BufferedImage> frames){
		if(frames == null || frames.isEmpty()){
			throw new IllegalArgumentException();
		}
		this.frames = frames;
	}
	
	public void setTimes(final long times, final boolean repeatOnce){
		this.maxTimes = times;
		this.repeatOnce = repeatOnce;
	}
	
	private void checkIndex(){
		if(currentFrame >= frames.size()){
			currentFrame = START_INDEX;
			if(repeatOnce){ repeatedOnce = true; }
		}
	}
	
	public Animations getMyAnimation(){ return myAnimation; }

	public BufferedImage getImage(){
		final BufferedImage tmp = this.frames.get(currentFrame);
		
		currentTimes++;
		if(currentTimes > maxTimes){
			currentTimes = 0;
			currentFrame++;
			checkIndex();
		}
		 
		return tmp;
	}
	
	public void reset(){
		currentFrame = 0;
		currentTimes = 0;
		repeatedOnce = false;
	}
	
	public boolean hasBeenRepeatedOnce(){
		return repeatedOnce;
	}

}

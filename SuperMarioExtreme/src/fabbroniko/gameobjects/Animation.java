package fabbroniko.gameobjects;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an animation for an AbstractGameObject.
 * @author fabbroniko
 */
public final class Animation {

	private List<BufferedImage> frames;
	private long maxTimes;
	private long currentTimes;
	private int currentFrame;
	private boolean repeatedOnce;
	private boolean repeatOnce;
	private final Animations myAnimation;
	
	private static Map<Animations, Animation> myInstance = new HashMap<>();
	
	private static final int START_INDEX = 0;
	
	private Animation(final Animations myAnimationP) {
		this.currentTimes = 0;
		this.currentFrame = START_INDEX;
		this.myAnimation = myAnimationP;
	}
	
	/**
	 * Gets the instance of the specified animation.
	 * @param type Type of the animation.
	 * @return Returns the instance associated with the given type.
	 */
	public static Animation getInstance(final Animations type) {
		if (!myInstance.containsKey(type)) {
			myInstance.put(type, new Animation(type));
		}
		
		return myInstance.get(type);
	}
	
	/**
	 * Sets the animation's frames.
	 * @param framesP List of images that compose the animation.
	 */
	public void setImages(final List<BufferedImage> framesP) {
		if (frames == null || frames.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.frames = framesP;
	}
	
	/**
	 * Sets how many times the getImage has to return the same image.
	 * @param times Num of times.
	 * @param repeatOnceP Whether the animation has to be repeated once or not.
	 */
	public void setTimes(final long times, final boolean repeatOnceP) {
		this.maxTimes = times;
		this.repeatOnce = repeatOnceP;
	}
	
	private void checkIndex() {
		if (currentFrame >= frames.size()) {
			currentFrame = START_INDEX;
			if (repeatOnce) {
				repeatedOnce = true;
			}
		}
	}
	
	/**
	 * Gets the animation type.
	 * @return Returns the animation type.
	 */
	public Animations getMyAnimation() {
		return myAnimation;
	}

	/**
	 * Get's the current frame.
	 * @return The current frame.
	 */
	public BufferedImage getImage() {
		final BufferedImage tmp = this.frames.get(currentFrame);
		
		currentTimes++;
		if (currentTimes > maxTimes) {
			currentTimes = 0;
			currentFrame++;
			checkIndex();
		}
		 
		return tmp;
	}
	
	/**
	 * Resets this animation.
	 */
	public void reset() {
		currentFrame = 0;
		currentTimes = 0;
		repeatedOnce = false;
	}
	
	/**
	 * Checks if the animation has been repeated once.
	 * @return Returns true if it has been repeated once, false otherwise.
	 */
	public boolean hasBeenRepeatedOnce() {
		return repeatedOnce;
	}

}

package fabbroniko.environment;

@SuppressWarnings("serial")
public class MyPoint extends java.awt.Point{
	
	public MyPoint(){ super(); }
	
	public MyPoint(final MyPoint old, final int xOffset, final int yOffset){
		super(old);
		setLocation(getX() + xOffset, getY() + yOffset);
	}
}

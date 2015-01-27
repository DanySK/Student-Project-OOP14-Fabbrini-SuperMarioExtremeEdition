package fabbroniko.environment;

public class Dimension implements Cloneable{
	private int width;
	private int height;
	
	public Dimension(){
		this(EnvironmentStatics.BASE_DIMENSION.clone());
	}
	
	public Dimension(final Dimension dim){
		this.width = dim.getWidth();
		this.height = dim.getHeight();
	}
	
	public Dimension(final int width, final int height){
		this.width = width;
		this.height = height;
	}
	
	public Dimension(final java.awt.Dimension dim){
		this.width = (int)dim.getWidth();
		this.height = (int)dim.getHeight();
	}
	
	public int getWidth() { 
		return this.width; 
	}
	
	public int getHeight() { 
		return this.height; 
	}
	
	public void setWidth(final int width) { 
		this.width = width;
	}
	
	public void setHeight(final int height) { 
		this.height = height; 
	}
	
	public void setDimension(final Dimension dim) {
		this.width = dim.getWidth();
		this.height = dim.getHeight();
	}
	
	
	public void setDimension(final int width, final int height){
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Dimension clone(){
		return new Dimension(this);
	}
}

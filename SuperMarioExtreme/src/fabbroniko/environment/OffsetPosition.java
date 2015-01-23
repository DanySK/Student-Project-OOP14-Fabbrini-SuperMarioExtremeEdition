package fabbroniko.environment;

public class OffsetPosition {
	private int x;
	private int y;
	
	public OffsetPosition(final int x, final int y){
		this.x = x;
		this.y = y;
	}
	
	public OffsetPosition(){
		this.x = 0;
		this.y = 0;
	}
	
	public OffsetPosition(final Position pos){
		this.x = pos.getX();
		this.y = pos.getY();
	}
	
	public void setX(final int x){
		this.x = x;
	}
	
	public void setY(final int y){
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public String toString(){
		return "[X Offset = " + this.x + ". Y Offset = " + this.y + "]";
	}
}

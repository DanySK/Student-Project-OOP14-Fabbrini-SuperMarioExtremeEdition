package fabbroniko.error;

public class TileTypeError extends RuntimeException{

	private final int selectedIndex;
	
	private static final long serialVersionUID = 1L;
	
	public TileTypeError(final int selectedIndex){
		super();
		this.selectedIndex = selectedIndex;
	}
	
	@Override
	public String toString(){
		return "No such TileType with " + selectedIndex + " as index.";
	}
}

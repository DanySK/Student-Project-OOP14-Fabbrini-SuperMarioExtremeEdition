package fabbroniko.environment;

@SuppressWarnings("serial")
public class MyRectangle extends java.awt.Rectangle{

	private static final Dimension PIXEL_DIMENSION = new Dimension(1, 1);
	public MyRectangle(final int x, final int y, final int width, final int height) {
		super(x, y, width, height);
	}
	
	public boolean intersects(final MyPoint point){
		return super.intersects(new MyRectangle((int)point.getX(), (int)point.getY(), PIXEL_DIMENSION.getWidth(), PIXEL_DIMENSION.getHeight()));
	}

	public boolean intersects(final MyRectangle rectangle){
		return super.intersects(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
	}
}

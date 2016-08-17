import java.awt.Color;
import java.util.ArrayList;

public abstract class Shape {

public static int index=0;

protected ArrayList<MyPoint> list = new ArrayList<MyPoint>();
protected int size = 20;
protected int topX;
protected int topY;
protected Color color;



public Shape(){
index++;	
}
	
	protected abstract void setShape();
	
	protected abstract void setLocation(int newtopX, int newtopY);
	
	protected abstract MyPoint getRightExtremity();
	
	
	public Color getColor(){
	return color;
	
	}
	public ArrayList<MyPoint> getShape() {
		return list;
	}

	public int getSize() {
		return size;
	}
	


	public int getTopX() {
		return topX;
	}
	
	public int getTopY() {
		return topY;
	}

}

import java.awt.Color;
import java.awt.Point;

public class VertSnake extends Shape {

	public VertSnake(int newtopX, int newtopY) {
		setLocation(newtopX, newtopY);
		color=Color.MAGENTA;
	}

	public void setLocation(int newtopX, int newtopY) {
		this.topX = newtopX;
		this.topY = newtopY;
		setShape();
	}
	protected void setShape() {

		MyPoint point1 = new MyPoint(topX+size, topY);
		MyPoint point2 = new MyPoint(topX+size, topY +size );
		MyPoint point3 = new MyPoint(topX , topY + size);
		MyPoint point4 = new MyPoint(topX , topY + 2 *size);

		list.clear();
		list.add(point1);
		list.add(point2);
		list.add(point3);
		list.add(point4);
		
		for(int i=0;i<list.size();i++){
			list.get(i).setColor(color);
		}
	}
	
	protected MyPoint getRightExtremity() {
		return new MyPoint(topX+ size, topY);
	}



}

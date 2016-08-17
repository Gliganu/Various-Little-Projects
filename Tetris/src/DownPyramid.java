import java.awt.Color;


public class DownPyramid extends Shape {

	public DownPyramid(int newtopX, int newtopY) {
		setLocation(newtopX, newtopY);
		color = Color.GREEN;
	}

	public void setLocation(int newtopX, int newtopY) {
		this.topX = newtopX;
		this.topY = newtopY;
		setShape();
	}

	protected void setShape() {

		MyPoint point1 = new MyPoint(topX, topY);
		MyPoint point2 = new MyPoint(topX + size, topY);
		MyPoint point3 = new MyPoint(topX + 2 * size, topY);
		MyPoint point4 = new MyPoint(topX + size, topY + size);

		list.clear();
		list.add(point1);
		list.add(point2);
		list.add(point3);
		list.add(point4);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setColor(color);
		}
	}
	
	protected MyPoint getRightExtremity() {
		return new MyPoint(topX+ 2*size, topY);
	}



}

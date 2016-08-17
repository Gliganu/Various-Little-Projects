import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Block {

	private ArrayList<Point> pointList;
	private int topX;
	private int topY;
	public static int radius = 20;
	public Color color;

	public boolean hasCoin;
	public Coin coin;
	public boolean coinTaken;

	public Block(int newX, int newY) {

		pointList = new ArrayList<Point>();
		
		setLocation(newX, newY);
		
		for (int i = 0; i < 5; i++) {
			pointList.add(new Point(topX + i * radius, topY));
		}

		color = Color.BLACK;

		Random generator = new Random();
		int randValue = generator.nextInt(2);
		coinTaken=false;
	
		if (randValue == 1) {
			hasCoin = true;
			coin = new Coin(newX+2*radius, newY- 35);
		} else {
			coin = null;
			hasCoin = false;
		}

	
	}

	
	public void takeCoin(){
		hasCoin=false;
	}
	
	public boolean hasCoin(){
		return hasCoin;
	}
	
	public Coin getCoin(){
		return coin;
	}
	
	
	
	public void setColor(Color newColor) {
		color = newColor;
	}

	public Color getColor() {
		return color;
	}


	public void moveDown() {
		
		for (Point point : pointList) {
			point.setLocation((int) point.getX(), (int) point.getY() + 1);

		}
		
		topY = topY + 1;
		if(hasCoin)
			
		coin.setLocation((int)coin.getLocation().getX(), topY-35);
	}

	public int getMaxX() {
		return topX + 5 * radius;

	}

	public Point getLocation() {
		Point point = new Point(topX, topY);
		return point;
	}

	
	public void setLocation(int newX, int newY) {
		topX = newX;
		topY = newY;
	}

	
	public ArrayList<Point> getList() {
		return pointList;
	}
}

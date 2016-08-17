import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class MyCircle {

	private ArrayList<Ellipse2D.Double> circleList;
	private int topX;
	private int topY;
	private int width = 200;
	private int height = 200;
	private Color color;
	public MyCircle(int topX, int topY,Color newColor) {

		this.topX = topX;
		this.topY = topY;
		this.color=newColor;
		circleList = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			
			circleList.add(new Ellipse2D.Double(topX+i*5, topY+i*5, width-i*10, height-i*10));
		}

	}

	public Color getColor(){
		return color;
	}
	
	public int getTopX(){
		return topX;
	}
	
	public int getTopY(){
		return topY;
	}
	
	public void moveRight(int distance){
		topX=topX+distance;
		
		for(Ellipse2D.Double ellipse: circleList){
			ellipse.setFrame(ellipse.x+distance, ellipse.y, ellipse.width, ellipse.height);
		}
		
	}
	
	public void moveLeft(int distance){
		
		for(Ellipse2D.Double ellipse: circleList){
			ellipse.setFrame(ellipse.x-distance, ellipse.y, ellipse.width, ellipse.height);
		}
		
	}
	
   public void moveDown(int distance){
		
		for(Ellipse2D.Double ellipse: circleList){
			ellipse.setFrame(ellipse.x, ellipse.y+distance, ellipse.width, ellipse.height);
		}
		
	}
   
   public void moveUp(int distance){
		
		for(Ellipse2D.Double ellipse: circleList){
			ellipse.setFrame(ellipse.x, ellipse.y-distance, ellipse.width, ellipse.height);
		}
		
	}

	public ArrayList<Ellipse2D.Double> getList() {
		return circleList;
	}

	
	
}

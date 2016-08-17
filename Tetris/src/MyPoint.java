import java.awt.Color;
import java.awt.Point;


public class MyPoint extends Point{
	private Color color=Color.BLACK;


	public MyPoint(){
		super();
		
	}
	public MyPoint(int x, int y){
		super(x,y);
		
	}
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	

}

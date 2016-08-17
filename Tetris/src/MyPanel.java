import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private ArrayList<MyPoint> bottomPointList = new ArrayList<MyPoint>();
	private Shape movingShape;
	
	private final int radius=20;

	public MyPanel(Shape startingShape) {
		int width = 300;
		int height = 450;
		this.setMinimumSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		setFocusable( true );
		movingShape = startingShape;
	}

	public void updateMovingShape(Shape newShape) {
		movingShape = newShape;
		repaint();

	}

	public void addBottomList(MyPoint newPoint){
		bottomPointList.add(newPoint);
	}
	
	public void updateBottomList(ArrayList<MyPoint> newList){
		bottomPointList=newList;
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

	

		for (Point point : movingShape.getShape()) {
			g.setColor(Color.BLACK);
			g.drawRect((int) point.getX(), (int) point.getY(),
					radius, radius);
			
			g.setColor(movingShape.getColor());
			g.fillRect((int) point.getX()+1, (int) point.getY()+1,
					radius-1, radius-1);
		}
		
		
		for( MyPoint currentPoint: bottomPointList){
			   
			g.setColor(Color.BLACK);
			g.drawRect((int) currentPoint.getX(), (int) currentPoint.getY(),
					radius, radius);
			
			
			g.setColor(currentPoint.getColor());
				g.fillRect((int) currentPoint.getX()+1, (int) currentPoint.getY()+1,
						radius-1,radius-1);
			}
			
		}
		

}

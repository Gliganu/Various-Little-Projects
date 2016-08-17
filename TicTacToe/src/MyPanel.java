import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private String whatToShow;
	private int position;
	private boolean marked;
	private boolean markedAgainst;
	
	public MyPanel(int newPosition) {

		position=newPosition;
		marked=false;
		markedAgainst=false;
		whatToShow = "-";
		
		int width = 30;
		int height = 30;
		this.setMinimumSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));

		
		
	}
	
	public void reset(){
		marked=false;
		markedAgainst=false;
		whatToShow = "-";
		repaint();
	}
	public void mark(){
		marked=true;
	}
	
	public boolean getState(){
		return marked;
	}
	
	public boolean isEmpty(){
		
		if(!marked && !markedAgainst)
			return true;
		
		return false;
	}
	
	public void markAgainst(){
		markedAgainst=true;
	}
	
	
	public void showX() {
		whatToShow = "X";
		repaint();
	}

	public void showO() {
		whatToShow = "O";
		repaint();
	}

	
	public int getPosition(){
		return position;
	}
	
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.GREEN);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if (whatToShow.equals("X")) {
			g.setColor(Color.RED);
			g.drawLine(10, 10, this.getWidth() - 10, this.getHeight() - 10);
			g.drawLine(this.getWidth() - 10, 10, 10, this.getHeight() - 10);

		} else if (whatToShow.equals("O")) {
			g.setColor(Color.BLUE);
			g.drawOval(10, 10, this.getWidth() - 20, this.getHeight() - 20);
		}
		

	}

}

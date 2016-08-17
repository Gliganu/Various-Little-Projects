import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;
import javax.swing.Timer;


public class MyPanel extends JPanel implements ActionListener{
	
private MyCircle first;
private MyCircle second;
private MyCircle third;
private MyCircle fourth;
private Timer timer;
private int index;
public MyPanel() {
	first=new MyCircle(0, 200,Color.black);
	second=new MyCircle(400, 200,Color.black);
	third=new MyCircle(200,0,Color.black);
	fourth=new MyCircle(200,400,Color.black);
	index=0;
	timer = new Timer(50, this);
    timer.start();
}




public void paintComponent(Graphics g) {
	super.paintComponent(g);
	
	g.setColor(Color.white);
	g.fillRect(0, 0, this.getWidth(), this.getHeight());
	
	Graphics2D g2d = (Graphics2D) g;
	
	g2d.setColor(Color.black);
	 
	g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
	  
	for(Ellipse2D ellipse: first.getList()){
		g2d.setColor(first.getColor());
		g2d.draw(ellipse);
			
	}
	
	for(Ellipse2D ellipse: second.getList()){
		g2d.setColor(second.getColor());
		g2d.draw(ellipse);
	}
		
	for(Ellipse2D ellipse: third.getList())	{
		g2d.setColor(third.getColor());
		g2d.draw(ellipse);
	}
	
	for(Ellipse2D ellipse: fourth.getList())	{
		g2d.setColor(fourth.getColor());
		g2d.draw(ellipse);
	}
	
}




@Override
public void actionPerformed(ActionEvent arg0) {
	int distance=1;

		first.moveRight(distance);
		second.moveLeft(distance);
		third.moveDown(distance);
		fourth.moveUp(distance);
		repaint();
	
	
}

}

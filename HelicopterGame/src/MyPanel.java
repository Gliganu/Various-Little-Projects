import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JPanel;


public class MyPanel extends JPanel{

	private Helicopter copter;
	private ArrayBlockingQueue<Column> columnList;
	private ArrayBlockingQueue<Rocket> rocketList;
	private MonsterRow monsterRow;
	
	public MyPanel(Helicopter newCopter,ArrayBlockingQueue<Column> newList,ArrayBlockingQueue<Rocket> rocketList,MonsterRow monsterRow){
	this.columnList=newList;
	this.copter=newCopter;
	this.rocketList=rocketList;
	this.monsterRow=monsterRow;
	
	}
	

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g.setColor(Color.black);
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g2d.drawImage(copter.getImage(), copter.getX(), copter.getY(), this);
		
		
		g2d.setColor(Color.white);
		for(Column col: columnList){
			for(Rectangle rect:col.getList()){
				g2d.fillRect(rect.x,rect.y,rect.width-1,rect.height-1);
			}
		}
		
		for(Rocket rocket:rocketList){
			g2d.drawImage(rocket.getRocketImage(), rocket.getX(), rocket.getY(), this);
		}
		
		
		for(Monster monster:monsterRow.getList()){
			if(!monster.isKilled())
			g2d.drawImage(monster.getImage(), monster.getX(), monster.getY(), this);
		}
		
					
		
		
	}



}

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Block extends Rectangle {

	private Color color; 
	private BufferedImage leftFoot;
	private BufferedImage rightFoot;
	private boolean leftPressed;
	private boolean rightPressed;
	
	public Block(int x, int y, int width, int height,Color newColor) {
		super(x, y, width, height);

		color = newColor;
		leftPressed=false;
		rightPressed=false;
		try {
			leftFoot = ImageIO.read(new File("resources/leftFoot.png"));
			rightFoot = ImageIO.read(new File("resources/rightFoot.png"));
//			  URL url = this.getClass().getResource("/resources/leftFoot.png");  
//			  leftFoot = ImageIO.read(url);  
//			 url = this.getClass().getResource("/resources/rightFoot.png");  
//			  rightFoot = ImageIO.read(url);  
//			  
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}


	public void setYellow(){
		color=Color.YELLOW;
	}
	public void pressLeft(){
		leftPressed=true;
	}
	
	public void pressRight(){
		rightPressed=true;
	}
	
	
	public boolean isLeftPressed(){
		return leftPressed;
		
	}
	
	public boolean isRightPressed(){
		return rightPressed;
		
	}
	
	
	
	public void setBlack() {
		color = Color.black;
	}

	public Color getColor() {
		return color;
	}
	
	public BufferedImage getLeftFoot(){
		return leftFoot;
	}
	
	public BufferedImage getRightFoot(){
		return rightFoot;
	}
	
	 
}

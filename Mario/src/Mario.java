import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Mario {

	private ArrayList<BufferedImage> leftList;
	private ArrayList<BufferedImage> rightList;
	private int currentIndex;
	private int topX;
	private int topY;
	private String direction;
	private boolean isJumping;
    private Block currentBlock;
	
    
    public Mario() {

		isJumping = false;
		direction = "left";
		topX = 200;
		topY = 400;
		currentIndex = 0;
		currentBlock=null;
		leftList = new ArrayList<BufferedImage>();

		rightList = new ArrayList<BufferedImage>();

		try {
			leftList.add(ImageIO.read(new File("resources/left0.png")));
			leftList.add(ImageIO.read(new File("resources/left1.png")));
			leftList.add(ImageIO.read(new File("resources/left2.png")));
			leftList.add(ImageIO.read(new File("resources/left3.png")));
			leftList.add(ImageIO.read(new File("resources/left4.png")));
			leftList.add(ImageIO.read(new File("resources/left5.png")));
			leftList.add(ImageIO.read(new File("resources/left6.png")));

			rightList.add(ImageIO.read(new File("resources/right0.png")));
			rightList.add(ImageIO.read(new File("resources/right1.png")));
			rightList.add(ImageIO.read(new File("resources/right2.png")));
			rightList.add(ImageIO.read(new File("resources/right3.png")));
			rightList.add(ImageIO.read(new File("resources/right4.png")));
			rightList.add(ImageIO.read(new File("resources/right5.png")));
			rightList.add(ImageIO.read(new File("resources/right6.png")));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		;

	}

	
	public Block getCurrentBlock(){
		return currentBlock;
	}
	
	public void setCurrentBlock(Block newBlock){
		currentBlock=newBlock;
	}
	
	public Point getLocation() {
		return new Point(topX, topY);
	}

	public void moveLeft() {
		if (isJumping) {

		} else {
			direction = "left";
			topX -= 5;
			currentIndex = (currentIndex + 1) % 7;

		}

	}

	public void moveRight() {
		if (isJumping) {

		} else {
			direction = "right";
			topX += 3;
			currentIndex = (currentIndex + 1) % 7;
		}

	}

	public ArrayList<Point> jump() {

		int height=200;
		int step=5;
			currentIndex=2;
		
		ArrayList<Point> list = new ArrayList<Point>();
		
		int i=0;
		while(i<=height){
			list.add(new Point(topX, topY - i));	
			i+=step;
		}
		
		i=0;
		
		while(i<=height){
			list.add(new Point(topX, topY - height + i));	
			i+=step;
		}

		return list;

	}
	
	public void fall(){
		
		currentIndex=2;
		topY+=5;
		
	}

	public BufferedImage getCurrentImage() {

		if (direction.equals("left")) {
			return leftList.get(currentIndex);
		} else
			return rightList.get(currentIndex);

	}

	public void setLocation(int newX, int newY) {
		topX = newX;
		topY = newY;
	}

	public void resetPhoto(){
		currentIndex=0;
	}
	
	public String getDirecton() {
		return direction;
	}
	
	

}

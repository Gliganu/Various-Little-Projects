import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Coin{

	private ArrayList<BufferedImage> picList;
	private int currentIndex;
	private int topX;
	private int topY;
	

	
	
    public Coin(int newX, int newY) {

    	
    	setLocation(newX, newY);
    	
    	currentIndex = 0;
		picList = new ArrayList<BufferedImage>();


		try {
			picList.add(ImageIO.read(new File("resources/coin0.png")));
			picList.add(ImageIO.read(new File("resources/coin1.png")));
			picList.add(ImageIO.read(new File("resources/coin2.png")));
			picList.add(ImageIO.read(new File("resources/coin3.png")));
			picList.add(ImageIO.read(new File("resources/coin4.png")));
			picList.add(ImageIO.read(new File("resources/coin5.png")));
			picList.add(ImageIO.read(new File("resources/coin6.png")));
			picList.add(ImageIO.read(new File("resources/coin7.png")));
			picList.add(ImageIO.read(new File("resources/coin8.png")));
			picList.add(ImageIO.read(new File("resources/coin9.png")));
			

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		;

	}

 
	
	public Point getLocation() {
		return new Point(topX, topY);
	}



	public BufferedImage getCurrentImage() {
		
		currentIndex = (currentIndex + 1) % 10;
			
		return picList.get(currentIndex);

	}
	
	public void setLocation(int newX, int newY) {
		topX = newX;
		topY = newY;
	}

	

}

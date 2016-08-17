import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Helicopter {

private int x;
private int y;
private ArrayList<BufferedImage> imageList;
private int currentIndex;

public Helicopter(int x, int y) {
	this.x = x;
	this.y = y;
	currentIndex=0;
	imageList=new ArrayList<BufferedImage>();
	
	try {

		imageList.add(ImageIO.read(new File("resources/Untitled.png")));
		imageList.add(ImageIO.read(new File("resources/Untitled1.png")));
		imageList.add(ImageIO.read(new File("resources/Untitled2.png")));
		imageList.add(ImageIO.read(new File("resources/Untitled3.png")));
		imageList.add(ImageIO.read(new File("resources/Untitled5.png")));
		
	} catch (IOException ex) {
		ex.printStackTrace();
	}

}


public int getX() {
	return x;
}

public int getY() {
	return y;
}

public void setPosition(int newX, int newY){
	this.x=newX;
	this.y=newY;
}

public BufferedImage getImage(){
	
	if(currentIndex==5)
		currentIndex=0;
	
	BufferedImage image= imageList.get(currentIndex); 
	
	currentIndex++;
	
	return image;
}
public void moveUp(int distance){
	y-=distance;
}

public void moveDown(int distance){
	y+=distance;
}

}

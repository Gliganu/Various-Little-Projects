import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;


public class Monster {


private int topX;
private int topY;
private ArrayList<BufferedImage> imageList;
private BufferedImage image;
private boolean dead;


public Monster(int topX,int topY){
	this.topX=topX;
	this.topY=topY;
	dead=false;
	imageList=new ArrayList<BufferedImage>();
	
	try {
		imageList.add(ImageIO.read(new File("resources/allien1.png")));
		imageList.add(ImageIO.read(new File("resources/allien2.png")));
		imageList.add(ImageIO.read(new File("resources/allien3.png")));
		imageList.add(ImageIO.read(new File("resources/allien4.png")));
		imageList.add(ImageIO.read(new File("resources/allien5.png")));
		imageList.add(ImageIO.read(new File("resources/allien6.png")));
		imageList.add(ImageIO.read(new File("resources/allien7.png")));
		imageList.add(ImageIO.read(new File("resources/allien8.png")));
		imageList.add(ImageIO.read(new File("resources/allien9.png")));
		
	} catch (IOException ex) {
		ex.printStackTrace();
	}
	
	setImage();
}

public void kill(){
	dead=true;
}

public boolean isKilled(){
	return dead;
}

private void setImage(){
	Random generator=new Random();
	image=imageList.get(generator.nextInt(9));
	
}

public void move(int distance){
	topX-=distance;
}

public BufferedImage getImage(){
	return image;
}

public int getX() {
	return topX;
}

public int getY() {
	return topY;
}



}

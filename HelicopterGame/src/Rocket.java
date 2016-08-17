import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rocket {

	private int x;
	private int y;
	private BufferedImage rocketImage;
	private BufferedImage explosionImage;
	private boolean exploded;
	public Rocket(int newX, int newY) {

		this.x = newX;
		this.y = newY;
		try {
			rocketImage = ImageIO.read(new File("resources/rocket.png"));
			explosionImage = ImageIO.read(new File("resources/explosion.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	public void explode(){
		exploded=true;
	}
	
	public boolean isExploded(){
		return exploded;
	}
	public BufferedImage getRocketImage(){
		
		if(exploded){
			return explosionImage;
		}
		return rocketImage;
	}
	
	public void move(int distance){
	
		if(!exploded)
		x+=distance;
	
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	
	
}

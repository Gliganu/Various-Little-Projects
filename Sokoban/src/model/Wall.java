package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class which represents hard wall on the map
 *
 */
public class Wall extends AbstractPiece {
	
	/**
	 * Constructor for the wall class with specified coordinates
	 * @param x
	 * @param y
	 */
	public Wall(int x, int y) {
		super(x,y, false);
		personalizeImage();
	}
	
	/**
	 * String representaion of Wall Objects
	 */
	@Override
	public String toString() {
		return ""+4;
	}


	/**
	 * Initialize wall image from file
	 */
	@Override
	public void personalizeImage() {
		BufferedImage wallImage;
		try {
			wallImage = ImageIO.read(getClass().getResourceAsStream("/imageFolder/wall.png"));
					
			setImage(wallImage);		
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	

}

package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * 
 * Class representing the player on the map
 *
 */
public class Player extends AbstractPiece {

	private Map<String,BufferedImage> imageMap;
	private static final String LEFT = "left";
	private static final String RIGHT = "right";
	private static final String DOWN = "down";
	private static final String UP = "up";
	private static final String LEFT_PUSH = "leftPush";
	private static final String RIGHT_PUSH = "rightPush";
	private static final String DOWN_PUSH = "downPush";
	private static final String UP_PUSH = "upPush";
	
	/**
	 * Constructor for Player class
	 * @param x
	 * @param y
	 */
	public Player(int x, int y) {
		super(x,y,false);
		imageMap = new HashMap<>();
		personalizeImage();
	}
	
	
	/**
	 * Initialize the images depending on what position the user is in
	 */
	@Override
	public void personalizeImage() {
		try {
			imageMap.put(LEFT, ImageIO.read(getClass().getResourceAsStream("/imageFolder/leftPlayer.png")));
			imageMap.put(RIGHT, ImageIO.read(getClass().getResourceAsStream("/imageFolder/rightPlayer.png")));
			imageMap.put(UP, ImageIO.read(getClass().getResourceAsStream("/imageFolder/upPlayer.png")));
			imageMap.put(DOWN, ImageIO.read(getClass().getResourceAsStream("/imageFolder/downPlayer.png")));
			imageMap.put(LEFT_PUSH, ImageIO.read(getClass().getResourceAsStream("/imageFolder/leftPlayerPush.png")));
			imageMap.put(RIGHT_PUSH, ImageIO.read(getClass().getResourceAsStream("/imageFolder/rightPlayerPush.png")));
			imageMap.put(UP_PUSH, ImageIO.read(getClass().getResourceAsStream("/imageFolder/upPlayerPush.png")));
			imageMap.put(DOWN_PUSH, ImageIO.read(getClass().getResourceAsStream("/imageFolder/downPlayerPush.png")));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		setImage(imageMap.get(LEFT));		
	}
	
	/**
	 * Move the player on the box and adjacent blocks
	 * @param dir the direction he is moving
	 * @param boxesMoved if he moved any moxes or not
	 */
	public void move(Direction dir, boolean boxesMoved){
		switch (dir) {
		case UP:
			y-=1;
			if(boxesMoved){
				setImage(imageMap.get(UP));
			}else{
				setImage(imageMap.get(UP_PUSH));
			}
			break;
		case DOWN:
			y+=1;
			if(boxesMoved){
				setImage(imageMap.get(DOWN));
			}else{
				setImage(imageMap.get(DOWN_PUSH));
			}
			break;
		case LEFT:
			x-=1;
			if(boxesMoved){
				setImage(imageMap.get(LEFT));
			}else{
				setImage(imageMap.get(LEFT_PUSH));
			}
			break;
		case RIGHT:
			x+=1;
			if(boxesMoved){
				setImage(imageMap.get(RIGHT));
			}else{
				setImage(imageMap.get(RIGHT_PUSH));
			}
			break;

		}
	}
	
	/**
	 * To string representation of the Player class
	 */
	@Override
	public String toString() {
		return ""+3;
	}

	
}

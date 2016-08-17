package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class defining blocks, which will be the main structures on the map
 *
 */
public class Block extends AbstractPiece {

	private boolean crucial;
	private boolean occupied;
	private BufferedImage fullImage; 
	private BufferedImage crucialImage; 
	private BufferedImage freeImage ;
	private BufferedImage occupiedImage;

	/**
	 * Creating a Block image with the specified parameters
	 * @param x x coordinate of the piece
	 * @param y y coordinate of the piece
	 * @param crucial if the block is required to have a box on it at the end of the game
	 * @param free if the player can step on that block or not
	 */
	public Block(int x, int y, boolean crucial, boolean free) {
		super(x,y, free);
		this.crucial = crucial;
		this.free = free;
		this.occupied = false;
		initializeBlockStateImages();
		personalizeImage();
	}
	
	/**
	 * Initializing the images for the different types of blocks
	 */
	public void initializeBlockStateImages() {
		try {
		 	freeImage = ImageIO.read(getClass().getResourceAsStream("/imageFolder/freeBlock.png"));
			fullImage =ImageIO.read(getClass().getResourceAsStream("/imageFolder/fullBlock.png"));
			crucialImage = ImageIO.read(getClass().getResourceAsStream("/imageFolder/crucialBlock.png"));
			occupiedImage = ImageIO.read(getClass().getResourceAsStream("/imageFolder/occupiedBlock.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Set the main image for the block depending on its state
	 */
	@Override
	public void personalizeImage() {
		if (free) {
			setImage(freeImage);
		} else {
			setImage(fullImage);
		}

		if (crucial) {
			setImage(crucialImage);
		}

	}
	
	/**
	 * To string representation of the Block
	 */
	@Override
	public String toString() {
		if (isCrucial()) {
			return "" + 2;
		}

		if (isFree()) {
			return "" + 0;
		}

		return "" + 1;

	}
	
	/**
	 * Check if block is occupied
	 * @return if it is occupied
	 */
	public boolean isOccupied() {
		return occupied;
	}

	/**
	 * Set the occupied field 
	 * @param occupied if occupied or not
	 */
	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
	
	/**
	 * Check if the block is crucial
	 * @return
	 */
	public boolean isCrucial() {
		return crucial;
	}

	/**
	 * Set the crucial field
	 * @param crucial
	 */
	public void setCrucial(boolean crucial) {
		this.crucial = crucial;
	}

	/**
	 * Get the image for the occupied state
	 * @return
	 */
	public BufferedImage getOccupiedImage() {
		return occupiedImage;
	}
	
	/**
	 * Get the image for the full state
	 * @return
	 */
	public BufferedImage getFullImage() {
		return fullImage;
	}
	
	/**
	 * Set the image for the full state
	 * @param fullImage
	 */
	public void setFullImage(BufferedImage fullImage) {
		this.fullImage = fullImage;
	}

	

}

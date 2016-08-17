package model;

import java.awt.image.BufferedImage;

/**
 * 
 * Class serving as a base for object on the map
 *
 */
public abstract class AbstractPiece {
	
	protected int x;
	protected int y;
	protected boolean free;
	protected BufferedImage image;

	/**
	 * Constructor for AbstractPiece class 
	 * @param x	X-Coordinate in map
	 * @param y Y-Coordinate in map
	 * @param free Can the user step on this block
	 */
	public AbstractPiece(int x, int y,boolean free) {
		this.x = x;
		this.y = y;
		this.free = free;
	}
	
	
	/**
	 * Return the X-Coordinate
	 * @return
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set the X-Coordinate
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get the Y-Coordinate
	 * @return y y coordinate of the object
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the Y-Coordinate
	 * @param y y coordinate of the object
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Abstact method which defines which image to display
	 */
	public abstract void personalizeImage();

	/**
	 * Check if the piece is free
	 * @return
	 */
	public boolean isFree() {
		return free;
	}
	
	/**
	 * Set the free field
	 * @param free
	 */
	public void setFree(boolean free) {
		this.free = free;
	}

	/**
	 * Get the image for the piece
	 * @return
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * Set the image for the piece
	 * @param image
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	
}

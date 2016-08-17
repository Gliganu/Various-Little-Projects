package model;

import java.io.InputStream;
import java.util.Scanner;

/**
 * 
 *	Class representing different game levels for the user to play
 */
public class GameLevel {

	private InputStream inputStream;
	private int level;
	private int[][] map;
	
	
	/**
	 * Constructor for game level class
	 * @param inputStream the input stream to the map file
	 * @param level the level which will be represented
	 */
	public GameLevel(InputStream inputStream, int level) {
		super();
		this.inputStream = inputStream;
		this.level = level;
		readMap();
	}

	
	private void readMap() {
		Scanner scanner = new Scanner(inputStream);
		int nrCol = scanner.nextInt();
		int nrRow = scanner.nextInt();
		
		map = new int[nrRow][nrCol];
		
		for (int i = 0; i < nrRow; i++) {
			for (int j = 0; j < nrCol; j++) {
				
				map[i][j] = scanner.nextInt();
			}
		}
		
		//scanner.close();
		
	}

	/**
	 * Getter for the map
	 * @return
	 */
	
	public int[][] getMap() {
		return map;
	}

	/**
	 * Setter for the map
	 * @param map map to be set 
	 */
	public void setMap(int[][] map) {
		this.map = map;
	}

	/**
	 * Getter for the input stream
	 * @return  Input stream connected to the map file
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Setter for the input stream
	 * @param inputStream input stream connected to the map file
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Getter for the map level
	 * @return map level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Setter for the map level
	 * @param level the level linked to this object
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	
	

	
}

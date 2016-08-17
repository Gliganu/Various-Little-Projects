package controller;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.AbstractPiece;
import model.Block;
import model.Direction;
import model.GameLevel;
import model.Observer;
import model.Player;
import model.Wall;

/**
 * Class for controlling the game dynamics
 * @author user
 *
 */
public class GameController {

	private AbstractPiece[][] gameMap;
	private int mapIndex;
	private List<Point> crucialBlockList;
	private Player player;
	private Observer observer;
	private List<GameLevel> gameLevelList;
	
	public static final int MAX_LEVEL = 4;
	
	/**
	 * Constructor for class
	 */
	public GameController() {
		initializeMapList();
		mapIndex = 0;
		startGame();
	}
	
	/**
	 * Start the game from the beginning
	 */
	public void startGame(){
		crucialBlockList = new ArrayList<>();
		try {
			readMap(gameLevelList.get(mapIndex).getInputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * Start the game from a custom level
	 * @param customLevel level from which the game will start
	 */
	public void startGame(int customLevel){
		crucialBlockList = new ArrayList<>();
		mapIndex= customLevel;
		try {
			readMap(gameLevelList.get(mapIndex).getInputStream());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Read the map from the file
	 * @param inputStream the input stream connected to the map file
	 * @throws IOException 
	 */
	public void readMap(InputStream inputStream) throws IOException {
		int[][] templateMap = getCurrentGameLevel().getMap();
		
		int nrRow = templateMap.length;
		int nrCol = templateMap[0].length;
		
		gameMap = new AbstractPiece[nrRow][nrCol];

		for (int i = 0; i < nrRow; i++) {
			for (int j = 0; j < nrCol; j++) {
				addToMap(i, j, templateMap[i][j]);
			}
		}
	}

		
	/**
	 * Do the necessary operations to prepare the next map for the user to play
	 */
	public void setUpNextMap() {
		mapIndex++;
		startGame();
		observer.reset(true);
	}
	
	/**
	 * Replay game with current map
	 */
	public void resetCurrentMap() {
		startGame();
		observer.reset(false);
	}
	
	/**
	 * Set game with a custom level
	 * @param mapLevel from which level to start the game
	 */
	public void setUpCustomLevelMap(int mapLevel){
		startGame(mapLevel);
	}
	
	/**
	 * Initialize the gameLevelList with the maps from the files
	 */
	private void initializeMapList() {
		gameLevelList = new ArrayList<>();
		gameLevelList.add(new GameLevel(getClass().getResourceAsStream("/mapFolder/map1.txt"),0));
		gameLevelList.add(new GameLevel(getClass().getResourceAsStream("/mapFolder/map2.txt"),1));
		gameLevelList.add(new GameLevel(getClass().getResourceAsStream("/mapFolder/map3.txt"),2));
		gameLevelList.add(new GameLevel(getClass().getResourceAsStream("/mapFolder/map4.txt"),3));
		gameLevelList.add(new GameLevel(getClass().getResourceAsStream("/mapFolder/map5.txt"),4));
	}
	
	/**
	 * Get the current game level
	 * @return what game level you are currently on
	 */
	public GameLevel getCurrentGameLevel(){
		return gameLevelList.get(mapIndex);
	}
	
	/**
	 * Add a specific element to the map at the specified coordinates
	 * @param xCoord the x coordinate of the piece to be added
	 * @param yCoord the y coordinate of the piece to be added
	 * @param pieceType the type of the piece to be added
	 */
	public void addToMap(int xCoord, int yCoord, int pieceType) {
		switch (pieceType) {

		case 0:
			gameMap[xCoord][yCoord] = new Block(xCoord, yCoord, false, true); // FreeBlock
			break;

		case 1:
			gameMap[xCoord][yCoord] = new Block(xCoord, yCoord, false, false);// BoxBlock
			break;

		case 2:
			gameMap[xCoord][yCoord] = new Block(xCoord, yCoord, true, true); // CrucialBlock
			crucialBlockList.add(new Point(xCoord, yCoord));
			break;

		case 3:
			gameMap[xCoord][yCoord] = new Player(xCoord, yCoord);
			this.player = (Player) gameMap[xCoord][yCoord];
			break;

		case 4:
			gameMap[xCoord][yCoord] = new Wall(xCoord, yCoord);
			break;

		}
	}
	
	/**
	 * Update the game map based on the direction the player moved
	 * @param dir waht dirrection the player moved
	 */
	public void updateGameMap(Direction dir) {

		int currentXCoord = player.getX();
		int currentYCoord = player.getY();
		AbstractPiece pieceToBeMoved = getNeighbourPieceinDirection(dir,
				player.getX(), player.getY()); // Current piece at future player
												// position

		if (invalidMove(dir, pieceToBeMoved)) {
			return;
		}

		boolean boxesMoved = moveBoxes(dir, pieceToBeMoved);
		player.move(dir, boxesMoved);

		gameMap[currentXCoord][currentYCoord] = new Block(currentXCoord,
				currentYCoord, false, true); // Put freeblock at previous player
												// position

		gameMap[player.getX()][player.getY()] = player; // Update player
														// position

		restoreCrucialPoints();

		observer.updateMap();

		if (gameEnded()) {
			observer.endGame();
		}

	}
	
	/**
	 * End game
	 * @return
	 */
	private boolean gameEnded() {
		boolean gameEnded = true;

		for (Point point : crucialBlockList) {
			int x = point.x;
			int y = point.y;
			if (gameMap[x][y].isFree() || gameMap[x][y] instanceof Player) {
				gameEnded = false;
			}

		}

		return gameEnded;
	}
	
	/**
	 * After the player moved, restore all the points which were crucial, in case any of them got deleted 
	 */
	private void restoreCrucialPoints() {
		for (Point point : crucialBlockList) {
			int x = point.x;
			int y = point.y;
			if (gameMap[x][y].isFree()) {
				gameMap[x][y] = new Block(x, y, true, true);
			}

			if (!gameMap[x][y].isFree() && !(gameMap[x][y] instanceof Player)) {
				Block piece = (Block) gameMap[x][y];
				piece.setImage(piece.getOccupiedImage());
				piece.setOccupied(true);
			}
		}
	}
	
	/**
	 * Move the boxes coordinates if the player pushed them
	 * @param dir what direction the player is moving
	 * @param pieceToBeMoved what piece the player is pushing currently
	 * @return
	 */
	private boolean moveBoxes(Direction dir, AbstractPiece pieceToBeMoved) {

		AbstractPiece neighbourPiece = getNeighbourPieceinDirection(dir,
				pieceToBeMoved.getX(), pieceToBeMoved.getY());

		if (isFullBlock(pieceToBeMoved) && isFreeBlock(neighbourPiece)) {
			pieceToBeMoved.setX(neighbourPiece.getX());
			pieceToBeMoved.setY(neighbourPiece.getY());

			Block pieceBlock = (Block) pieceToBeMoved;
			if (pieceBlock.isOccupied()) {
				pieceBlock.setOccupied(false);
				pieceBlock.setImage(pieceBlock.getFullImage());
			}

			gameMap[neighbourPiece.getX()][neighbourPiece.getY()] = pieceToBeMoved;
			return false;
		}

		return true;

	}
	
	/**
	 * Check if a block is free or not
	 * @param piece piece to check if it's free
	 * @return
	 */
	private boolean isFreeBlock(AbstractPiece piece) {
		return (piece instanceof Block && piece.isFree() == true);
	}

	private boolean isFullBlock(AbstractPiece piece) {
		return (piece instanceof Block && piece.isFree() == false);
	}

	
	/**
	 * Check if a moved is valid or not
	 * @param dir what direction the player is moving
	 * @param pieceToBeMoved what piece he is moving
	 * @return 
	 */
	private boolean invalidMove(Direction dir, AbstractPiece pieceToBeMoved) {

		switch (dir) {
		case UP:
			if (pieceToBeMoved.getY() == 0)
				return true;
			break;
		case DOWN:
			if (pieceToBeMoved.getY() == gameMap.length - 1)
				return true;
			break;
		case LEFT:
			if (pieceToBeMoved.getX() == 0)
				return true;
			break;
		case RIGHT:
			if (pieceToBeMoved.getX() == gameMap[0].length - 1)
				return true;
			break;
		}

		AbstractPiece neighbourPiece = getNeighbourPieceinDirection(dir,
				pieceToBeMoved.getX(), pieceToBeMoved.getY());

		if(pieceToBeMoved instanceof Wall){
			return true;
		}
		if (pieceToBeMoved.isFree() == false
				&& neighbourPiece.isFree() == false) {
			return true;
		}

		switch (dir) {
		case UP:
			if (player.getY() == 0)
				return true;
			break;
		case DOWN:
			if (player.getY() == gameMap.length - 1)
				return true;
			break;
		case LEFT:
			if (player.getX() == 0)
				return true;
			break;
		case RIGHT:
			if (player.getX() == gameMap[0].length - 1)
				return true;
			break;
		}

		return false;

	}

	/**
	 * Print the game map
	 */
	public void printGameMap() {
		int nrRow = gameMap.length;
		int nrCol = gameMap[0].length;

		for (int i = 0; i < nrRow; i++) {
			System.out.println();
			for (int j = 0; j < nrCol; j++) {
				System.out.print(gameMap[j][i] + " ");
			}
		}
	}
	
	/**
	 * Print the map which was read from the file
	 * @param templateMap the string representation of the map
	 */
	public void printReadMap(int[][] templateMap) {
		int rowSize = templateMap[0].length;
		int colSize = templateMap.length;

		for (int i = 0; i < rowSize; i++) {
			System.out.println();
			for (int j = 0; j < colSize; j++) {
				System.out.print(templateMap[i][j] + " ");
			}
		}
	}
	
	/**
	 * Get the piece adiacent with the player's direction
	 * @param dir the direction the player is moving
	 * @param x  x coordinate of the piece
	 * @param y  y coordinate of the piece
	 * @return
	 */
	public AbstractPiece getNeighbourPieceinDirection(Direction dir, int x,
			int y) {
		AbstractPiece piece = null;
		switch (dir) {
		case UP:
			piece = gameMap[x][y - 1];
			break;
		case DOWN:
			piece = gameMap[x][y + 1];
			break;
		case LEFT:
			piece = gameMap[x - 1][y];
			break;
		case RIGHT:
			piece = gameMap[x + 1][y];
			break;
		}

		return piece;
	}
	
	/**
	 * Set observer field
	 * @param observer the class which wants to observe
	 */
	public void setObserver(Observer observer) {
		this.observer = observer;
	}

	/**
	 * Get game map
	 * @return
	 */
	public AbstractPiece[][] getGameMap() {
		return gameMap;
	}

}

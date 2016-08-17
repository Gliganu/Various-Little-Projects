package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.AbstractPiece;

/**
 * Panel used to display the game elements
 *
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private AbstractPiece[][] gameMap;
	
	/**
	 * Constructor for the panel
	 * @param gameMap the map containing the elements which will be displayer
	 */
	public GamePanel(AbstractPiece[][] gameMap) {
		this.gameMap = gameMap;
	}
	
	/**
	 * Repaint the panel components
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Graphics2D g2d = (Graphics2D) g;
		
		int rowSize = gameMap[0].length;
		int colSize = gameMap.length;

		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				AbstractPiece currentPiece = gameMap[i][j];
				g2d.drawImage(currentPiece.getImage(),currentPiece.getX()*GameDisplay.PICTURE_SIZE,currentPiece.getY()*GameDisplay.PICTURE_SIZE,this);
			}
		}
	}


}

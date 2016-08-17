package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel used for displaying the menu elements
 * @author user
 *
 */
public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	
	/**
	 * Constructor for the menu panel class
	 */
	public MenuPanel() {
		try {
			image =  ImageIO.read(getClass().getResourceAsStream("/imageFolder/background.png"))
					;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Paint the menu components
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	
	}


}

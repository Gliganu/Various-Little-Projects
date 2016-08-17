import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private Mario mario;
	private ArrayList<Block> blockList;
	private BufferedImage background;
	private Lava lava;
	public MyPanel(Mario mario, ArrayList<Block> newBlockList, Lava newLava) {
		int width = 300;
		int height = 450;
		this.setMinimumSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		this.mario = mario;
		blockList = newBlockList;
		lava=newLava;
		
		
		try {
			background = ImageIO.read(new File("resources/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public Color getRandomColor(String colorName){
		Random generator=new Random();
		int red=0,green=0,blue=0;
		switch(colorName){
		
		case "Yellow":
			red=255;
			green=255;
			blue=generator.nextInt(255);
			break;
		
		case "Orange":
			red=200+ generator.nextInt(50);
			green=100+generator.nextInt(50);
			blue=generator.nextInt(50);
			
			break;
		
		case "Red":
			red=255;
			green=generator.nextInt(50);
			blue=generator.nextInt(50);
			
			break;
		}
		
		
		return new Color(red,green,blue);
			
		}
			

	

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.white);
	
		g.drawImage(background, 0, 0, this);

		Iterator<Block> it = blockList.iterator();

		while (it.hasNext()) {

			Block block = it.next();

			g.setColor(block.getColor());

			int radius = Block.radius;

			for (Point currentPoint : block.getList()) {
				g.fillRect((int) currentPoint.getX(),
						(int) currentPoint.getY(), radius - 1, radius - 1);
			}

			if (block.hasCoin) {

				Coin coin = block.getCoin();
				BufferedImage currentImage = coin.getCurrentImage();
				g.drawImage(currentImage, (int) coin.getLocation().getX(),
						(int) coin.getLocation().getY(), this);

			}

		}

		BufferedImage currentImage = mario.getCurrentImage();
		g.drawImage(currentImage, (int) mario.getLocation().getX(), (int) mario
				.getLocation().getY(), this);
		
		
		
		for(int i=0;i<=lava.getLevel();i++){
			
			for(int j=0;j<=15;j++){
				Color color;
				
				if(i==lava.getLevel()) {
					color=getRandomColor("Yellow");
				}
					else {
						if( lava.getLevel()-i < 3){
							color=getRandomColor("Orange");
							}
						else color=getRandomColor("Red");
						 }
				
			
				g.setColor(color);
				g.fillRect(j*20,450-i*20,20,20);
			}
			
		}
		
	}

}

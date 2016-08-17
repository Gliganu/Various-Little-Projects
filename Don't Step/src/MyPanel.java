import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	private ArrayBlockingQueue<Row> rowList;

	public MyPanel(ArrayBlockingQueue<Row> newRowList) {
		int width = 400;
		int height = 800;
		this.setMinimumSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		setLayout(new GridLayout(8, 4, 1, 0));
		rowList = new ArrayBlockingQueue<Row>(100);

		rowList = newRowList;

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage image;

		for (Row row : rowList) {
			for (Block block : row.getBlocks()) {

				g2d.setColor(block.getColor());

				g2d.fill(block);

				g2d.setColor(Color.white);
				g2d.drawRect((int) block.getX(), (int) block.getY(),
						(int) block.getWidth(), (int) block.getHeight());

				if (block.isLeftPressed()) {
					image = block.getLeftFoot();
					g.drawImage(image, (int) block.getX() + 10,
							(int) block.getY(), this);

				}

				if (block.isRightPressed()) {
					image = block.getRightFoot();
					g.drawImage(image, (int) block.getX() + 10,
							(int) block.getY(), this);

				}

			}
		}

	}

}

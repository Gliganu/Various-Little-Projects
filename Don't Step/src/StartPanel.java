
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StartPanel extends JPanel {

	private int secondsLeft;
	private JLabel timeLabel;

	public StartPanel() {
		 
		this.setLayout(new BorderLayout());
		timeLabel = new JLabel("" + secondsLeft);
		secondsLeft = 3;
		Font bigFont = new Font("sanserif", Font.BOLD, 130);
		timeLabel.setFont(bigFont);
		timeLabel.setVerticalAlignment(SwingConstants.CENTER);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		 
		this.add(timeLabel);
	

		beginCountdown();
	}

	
	public int getSecondsLeft(){
		return secondsLeft;
	}
	
	
	
	public void beginCountdown() {
		secondsLeft=3;
		Thread timeThread = new Thread(new Runnable() {
			public void run() {
				while (secondsLeft >= 0) {
					try {
						Thread.sleep(1000);
						repaint();
						secondsLeft--;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		});

	timeThread.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		timeLabel.setForeground(Color.white);
		timeLabel.setText("" + secondsLeft);

	}

}

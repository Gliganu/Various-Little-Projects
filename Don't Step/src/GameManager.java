import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameManager {

	private JFrame frame;
	private JLabel timeLabel;
	private JLabel scoreLabel;
	private MyPanel myPanel;
	private volatile ArrayBlockingQueue<Row> rowList;
	private int totalScore;
	private ExecutorService executor;
	private int secondsLeft;
	private boolean whichFootToPress;
	private boolean gameEnded;
	private int miliSec;
	private boolean alreadyStopped;
	private JFrame startFrame;
	private StartPanel startPanel;

	public GameManager() {
		totalScore = 0;
		miliSec = 99;
		executor = Executors.newCachedThreadPool();
		whichFootToPress = false;
		rowList = new ArrayBlockingQueue<Row>(100);
		secondsLeft = 5;
		gameEnded = false;
		alreadyStopped = false;

		setUpGUI();
		initializeRows();
		startTime();
	}

	private void setUpGUI() {
		frame = new JFrame("Don't Step on White Block");
		startFrame = new JFrame("Don't Step on White Block");

		Container container = frame.getContentPane();
		Container startContainer = startFrame.getContentPane();

		JPanel labelPanel = new JPanel(new GridLayout(2, 1, 10, 0));
		startPanel = new StartPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 16);

		timeLabel = new JLabel("Time left: ");
		scoreLabel = new JLabel("Score: " + totalScore);
		timeLabel.setFont(bigFont);
		scoreLabel.setFont(bigFont);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		labelPanel.add(scoreLabel);
		labelPanel.add(timeLabel);

		myPanel = new MyPanel(rowList);
		myPanel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {

				if (e.getButton() == 1 && !gameEnded) {
					boolean pressed = false;
					for (Row row : rowList) {
						if (!pressed) {
							for (Block block : row.getBlocks()) {
								if (block.contains(e.getX(), e.getY())) {

									if (block.getColor() == Color.black) {

										totalScore++;
										scoreLabel.setText("Score: "
												+ totalScore);

										if (whichFootToPress) {
											block.pressLeft();
											whichFootToPress = false;
										} else {
											block.pressRight();
											whichFootToPress = true;
										}
										addTime(9);

										addRow();
										move();
										pressed = true;

									} else {

										if (whichFootToPress) {
											block.pressLeft();
											whichFootToPress = false;
										} else {
											block.pressRight();

											whichFootToPress = true;
										}

										block.setYellow();

										myPanel.repaint();

										alreadyStopped = true;
										endGame();
									}
								}
							}

						}

					}

				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

		});

		container.add(BorderLayout.NORTH, labelPanel);
		container.add(BorderLayout.CENTER, myPanel);

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 880);
		frame.setLocationRelativeTo(null);

		startContainer.add(BorderLayout.CENTER, startPanel);
		startFrame.setResizable(false);
		startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startFrame.setSize(400, 880);
		startFrame.setLocationRelativeTo(null);

		startFrame.setVisible(true);

		while (startPanel.getSecondsLeft() > 0) {
			System.out.println(startPanel.getSecondsLeft());
		}
		startFrame.setVisible(false);
		frame.setVisible(true);
	}

	private void initializeRows() {
		rowList.clear();
		for (int i = 0; i < 9; i++)
			rowList.add(new Row(i));
		myPanel.repaint();
	}

	private void endGame() {

		alreadyStopped = false;
		miliSec = 0;
		addTime(0);
		System.out.println("End Game");

		Object[] options = { "Yes.Let's play again", "No. I want to exit!" };
		int n = JOptionPane.showOptionDialog(frame, "Your final score is: "
				+ totalScore + ". Do you want to play again?", "End Game",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // do
				options, // the titles of buttons
				options[0]); // default button title

		totalScore = 0;
		scoreLabel.setText("Score: " + totalScore);

		if (n == JOptionPane.YES_OPTION) {
			addTime(9);

			initializeRows();
			startTime();

		}

		else {
			System.exit(-1);
		}
	}

	private void move() {

		for (Row row : rowList)
			row.moveDown(100);

		myPanel.repaint();

	}

	private void addRow() {
		rowList.add(new Row(-1));
		Iterator<Row> it = rowList.iterator();
		while (it.hasNext()) {
			if (!it.next().validRow()) {
				it.remove();

			}
		}
	}

	private void startTime() {

		Thread timeThread = new Thread(new Runnable() {

			public void run() {
				miliSec = 99;

				while (secondsLeft > 0) {

					if (miliSec == 0) {
						miliSec = 99;
						secondsLeft--;
					} else
						miliSec--;

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					timeLabel.setText("Time left: " + secondsLeft + "."
							+ miliSec);

				}
				if (alreadyStopped)
					endGame();

			}

		});

		executor.execute(timeThread);
	}

	private void addTime(int timeAdded) {
		secondsLeft = timeAdded;
	}

}

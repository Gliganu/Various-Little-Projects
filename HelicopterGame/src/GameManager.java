import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class GameManager {

	private JFrame frame;
	private JLabel scoreLabel;
	private JPanel scorePanel;
	private MyPanel myPanel;
	private int totalScore;
	private boolean gameEnded;
	private Helicopter copter;
	private ArrayBlockingQueue<Column> columnList;
	private int nrColumns;
	private int nrMonsters;
	private boolean heliUp;
	private ArrayBlockingQueue<Rocket> rocketList;
	private MonsterRow monsterRow;

	public GameManager() {

		monsterRow = new MonsterRow();
		heliUp = false;
		totalScore = 0;
		nrMonsters = 0;
		nrColumns = 200;
		columnList = new ArrayBlockingQueue<>(200);
		rocketList = new ArrayBlockingQueue<>(20);
		gameEnded = false;
		copter = new Helicopter(100, 200);
		myPanel = new MyPanel(copter, columnList, rocketList, monsterRow);
		scorePanel = new JPanel();
		scoreLabel = new JLabel("Monsters killed: " + nrMonsters
				+ "      Total Score: " + totalScore);
		setUpGUI();
		startGame();
		startTime();
	}

	private void setUpGUI() {
		frame = new JFrame("Helicopter Game");

		Container container = frame.getContentPane();

		container.add(BorderLayout.CENTER, myPanel);

		scorePanel.add(scoreLabel);
		scoreLabel.setBackground(Color.WHITE);
		container.add(BorderLayout.NORTH, scorePanel);

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

				if (e.getButton() == MouseEvent.BUTTON3) {
					rocketList.add(new Rocket(copter.getX(), copter.getY()));
				}

				if (e.getButton() == MouseEvent.BUTTON1) {

					heliUp = true;

					Thread upThread = new Thread(new Runnable() {
						public void run() {

							while (heliUp && !gameEnded) {
								copter.moveUp(1);
								if (hitCopter()) {
									gameEnded = true;
									endGame();
								}

								try {
									Thread.sleep(5);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					});

					upThread.start();

				}

			}

			public void mouseReleased(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					heliUp = false;

					Thread downThread = new Thread(new Runnable() {
						public void run() {

							while (!heliUp && !gameEnded) {
								copter.moveDown(1);
								if (hitCopter()) {
									gameEnded = true;
									endGame();
								}

								try {
									Thread.sleep(5);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					});

					downThread.start();

				}

			}

		});

		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private boolean hitCopter() {

		for (Column col : columnList) {

			if (Math.abs(col.getX() - copter.getX()) < 50) {
				if (copter.getY() < col.getUpperBound()) {
					return true;
				}
				if (copter.getY() > col.getLowerBound()) {
					return true;
				}

			}

		}
		
		for(Monster monster: monsterRow.getList()){
			
			if (Math.abs(monster.getX() - copter.getX()) < 50 && !monster.isKilled()) {
				if (Math.abs(monster.getY() - copter.getY())<10) {
					return true;
				}
				

			}
		}

		return false;
	}

	private void checkRocketsStatus() {

		for (Column col : columnList) {
			for (Rocket rocket : rocketList) {

				if (Math.abs(col.getX() - rocket.getX()) < 50) {
					if (rocket.getY() < col.getUpperBound()
							|| rocket.getY() > col.getLowerBound()) {
						rocket.explode();
					}
				}

			}

		}

	}

	private void startGame() {

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			protected Void doInBackground() throws Exception {

				while (!gameEnded) {
					Thread.sleep(50);

					for (Column column : columnList) {
						column.move();
					}
					nrColumns++;

					for (Rocket rocket : rocketList) {
						rocket.move(10);
					}

					addUpdateComponents();

					checkRocketsStatus();

					updateMonsters();

					publish();

				}
				return null;
			}

			protected void process(List<Void> chunks) {

				myPanel.repaint();

			}

			protected void done() {
				myPanel.repaint();
			}

		};

		worker.execute();
	}

	private void endGame() {
		gameEnded = true;

		final int total = totalScore;
		final int monsters = nrMonsters;

		totalScore = 0;
		nrMonsters = 0;
		nrColumns = 0;
		scoreLabel.setText("Monsters killed: " + nrMonsters
				+ "      Total Score: " + totalScore);

		Thread downThread = new Thread(new Runnable() {
			public void run() {

				while (copter.getY() < 500) {
					copter.moveDown(1);
					myPanel.repaint();
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				Object[] options = { "Yes.Let's play again",
						"No. I want to exit!" };
				int n = JOptionPane.showOptionDialog(frame, "You killed: "
						+ monsters + " monsters. Your final score is: " + total
						+ "\n Do you want to play again?", "End Game",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, // the
																		// titles
																		// of
																		// buttons
						options[0]); // default button title

				if (n == JOptionPane.YES_OPTION) {

					rocketList.clear();
					columnList.clear();
					monsterRow.getList().clear();

					gameEnded = false;
					startGame();
					startTime();
					copter.setPosition(100, 200);

				}

				else {
					System.exit(-1);
				}
			}
		});

		downThread.start();

	}

	private void addUpdateComponents() {

		Column col = new Column(600, 440, nrColumns / 150);
		columnList.add(col);
		if (columnList.size() > 61)
			columnList.poll();

		if (rocketList.size() != 0
				&& (rocketList.peek().getX() > 700 || rocketList.peek()
						.isExploded())) {
			rocketList.poll();
		}

		monsterRow.updateKillStatus();

		int upperBound = ((nrColumns / 100) % 5 + 1) * 3 * 10;
		int lowerBound = 440 - upperBound;

		if (nrColumns % 150 == 0 && monsterRow.areAllDead()) {
			monsterRow.getNewMonsters(500, upperBound, lowerBound);
		}

	}

	private void updateMonsters() {

		for (Monster monster : monsterRow.getList()) {

			if (monster.getX() < 0)
				monster.kill();

			for (Rocket rocket : rocketList) {

				if (Math.abs(monster.getX() - rocket.getX()) < 10) {
					if (rocket.getY() > monster.getY()
							&& rocket.getY() < monster.getY() + 30
							&& !monster.isKilled()) {
						rocket.explode();
						monster.kill();
						nrMonsters++;
						scoreLabel.setText("Monsters killed: " + nrMonsters
								+ "      Total Score: " + totalScore);
					}
				}

			}

			monster.move(5);
		}

	}

	private void startTime() {

		Thread timeThread = new Thread(new Runnable() {

			public void run() {

				while (!gameEnded) {

					totalScore++;
					scoreLabel.setText("Monsters killed: " + nrMonsters
							+ "      Total Score: " + totalScore);

					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

			}

		});

		timeThread.start();
	}

}

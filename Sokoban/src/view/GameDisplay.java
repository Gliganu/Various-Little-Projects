package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.Direction;
import model.Observer;
import model.UpdateObserver;
import model.User;
import controller.GameController;

/**
 * 
 * Class for displaying the game 
 *
 */
public class GameDisplay implements Observer {

	private GameController gameController;
	private UpdateObserver updateObserver;
	private JFrame mainFrame;
	private JLabel timeLabel;
	private boolean gameEnded;
	private User user;
	private GamePanel mainPanel;
	private int timeLeft;
	public static int ROW_SIZE;
	public static int COL_SIZE;
	public static final int PICTURE_SIZE = 30;

	/**
	 * Constructor for GameDisplay class 
	 * @param gameController the controller handling the business logic
	 * @param user the user which is currently playing the game
	 */
	public GameDisplay(GameController gameController, User user) {
		this.gameController = gameController;
		this.user = user;
		initializeGameDisplay();
	}
	
	/**
	 * Reset the game and update the score if necessary
	 */
	@Override
	public void reset(boolean shouldUpdateScore) {
		gameEnded = true;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (shouldUpdateScore) {
			user.addToScore(timeLeft);
			user.setLevelReached(gameController.getCurrentGameLevel()
					.getLevel());
		}

		mainFrame.setVisible(false);
		initializeGameDisplay();
		setUpGameGui(user.getLevelReached());
	}

	/**
	 * Initialize the game parameter values
	 */
	public void initializeGameDisplay() {
		timeLeft = 100;
		gameEnded = false;
		ROW_SIZE = gameController.getGameMap()[0].length;
		COL_SIZE = gameController.getGameMap().length;
	}

	/**
	 * Create all the GUI elements and display them
	 * @param mapLevel the map level to be displayed
	 */
	public void setUpGameGui(int mapLevel) {

		mainFrame = new JFrame("Sokoban Game");

		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);
		JMenuItem saveAndExitItem = new JMenuItem("Save and Exit");
		JMenuItem resetItem = new JMenuItem("Reset");

		
		saveAndExitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateObserver.saveToDatabase(user);
				mainFrame.setVisible(false);
			}
		});

		resetItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameController.resetCurrentMap();
			}
		});

		menu.add(saveAndExitItem);
		menu.add(resetItem);
		mainFrame.setJMenuBar(menuBar);

		gameController.setUpCustomLevelMap(mapLevel);

		mainPanel = new GamePanel(gameController.getGameMap());
		mainPanel.setLayout(new BorderLayout());
		timeLabel = new JLabel("User : " + user.getName() + " || Score: "
				+ user.getScore() + "  ||  Time Left : " + timeLeft);
		Container container = mainFrame.getContentPane();

		container.add(mainPanel, BorderLayout.CENTER);
		container.add(timeLabel, BorderLayout.NORTH);

		mainPanel.setFocusable(true);
		addKeyListener();

		startTime();

		mainFrame.setSize((ROW_SIZE) * PICTURE_SIZE, (COL_SIZE + 2)
				* PICTURE_SIZE);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);

	}
	
	/**
	 * Start the timer for the user
	 */
	private void startTime() {
		Thread timeThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!gameEnded) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timeLeft--;
					timeLabel.setText("User : " + user.getName()
							+ " || Score: " + user.getScore()
							+ "  ||  Time Left : " + timeLeft);
				}
			}
		});

		timeThread.start();
	}
	
	/**
	 * Add key listener to move the player on the map according to the user input
	 */
	private void addKeyListener() { 
		
		mainPanel.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_LEFT) {
					gameController.updateGameMap(Direction.LEFT);
				}

				if (key == KeyEvent.VK_RIGHT) {
					gameController.updateGameMap(Direction.RIGHT);
				}

				if (key == KeyEvent.VK_UP) {
					gameController.updateGameMap(Direction.UP);
				}

				if (key == KeyEvent.VK_DOWN) {
					gameController.updateGameMap(Direction.DOWN);
				}
			}
		});
	}
	
	/**
	 * Repaint the map
	 */
	@Override
	public void updateMap() {
		mainPanel.repaint();
	}
	
	/**
	 * End game
	 */
	@Override
	public void endGame() {
		gameEnded = true;

		if (user.getLevelReached() == GameController.MAX_LEVEL) {
			userFinishedAllGame();
			return;
		}

		Object[] options = { "Yes.Let's try the next map",
				"No. I want to save and exit!" };
		int n = JOptionPane.showOptionDialog(mainFrame,
				"Congratulations. Do you want to try the next map ?",
				"Level Solved", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (n == JOptionPane.YES_OPTION) {
			gameController.setUpNextMap();
		}

		else {
			updateObserver.saveToDatabase(user);
			mainFrame.setVisible(false);
		}
	}
	
	/**
	 * Prompt user he has finished the game and save it's score
	 */
	private void userFinishedAllGame() {
		JOptionPane
				.showMessageDialog(mainFrame,
						"Congratulations! You finished the game and entered the Hall of Fame");

		updateObserver.saveToDatabase(user);
		mainFrame.setVisible(false);

	}
	
	/**
	 * Get user 
	 * @return
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Set user
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Set update observer for the game display
	 * @param updateObserver
	 */
	public void setUpdateObserver(UpdateObserver updateObserver) {
		this.updateObserver = updateObserver;
	}

}

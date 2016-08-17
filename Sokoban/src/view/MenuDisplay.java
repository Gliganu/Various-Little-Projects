package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.UpdateObserver;
import model.User;
import controller.UserDAO;
import controller.UserDaoHibernate;

/**
 * Class for displaying the menu for initially interacting with the user
 * @author user
 *
 */
public class MenuDisplay implements UpdateObserver {

	private static final int FRAME_SIZE = 300;
	private GameDisplay gameDisplay;
	private User user;
	private UserDAO userDAO;

	/**
	 * Constructor for class 
	 * @param gameDisplay
	 */
	public MenuDisplay(GameDisplay gameDisplay) {
		this.gameDisplay = gameDisplay;
		this.userDAO = new UserDaoHibernate();
		setUpMenuGui();
	}

	/**
	 * Create all the GUI elements and display them
	 */
	private void setUpMenuGui() {
		JFrame menuFrame = new JFrame("Sokoban Game");
		MenuPanel menuPanel = new MenuPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

		JButton newGameButton = new JButton("New Game");
		JButton continueGameButton = new JButton("Continue Game");
		JButton highScoresButton = new JButton("HighScores");
		JLabel titleLabel = new JLabel("Sokoban Game");
		Font myFont = new Font("TimesRoman", Font.BOLD, 25);
		titleLabel.setFont(myFont);
		titleLabel.setForeground(Color.black);

		titleLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		newGameButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		highScoresButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		continueGameButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		Container container = menuFrame.getContentPane();

		container.add(menuPanel);
		menuPanel.add(Box.createVerticalStrut(70));
		menuPanel.add(titleLabel);
		menuPanel.add(Box.createVerticalStrut(20));
		menuPanel.add(newGameButton);
		menuPanel.add(Box.createVerticalStrut(20));
		menuPanel.add(continueGameButton);
		menuPanel.add(Box.createVerticalStrut(20));
		menuPanel.add(highScoresButton);

		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createUser();
				gameDisplay.setUpGameGui(0);
			}

		});

		highScoresButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showHighScores();
			}
		});

		continueGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reconnectUser();
				gameDisplay.setUpGameGui(user.getLevelReached());

			}

		});

		menuFrame.setSize(FRAME_SIZE, FRAME_SIZE);
		menuFrame.setResizable(false);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.setVisible(true);
	}
	
	/**
	 * Create a new user
	 */
	private void createUser() {

		user = null;
		String name = JOptionPane.showInputDialog("Enter your username");
		user = new User(name);
		gameDisplay.setUser(user);
		userDAO.saveUser(user);
	}
	
	/**
	 * Reconnect a user with a previously created one to allow him to continue his game
	 */
	private void reconnectUser() {
		String username = null;
		String outputMessage = null;
		user = null;
		boolean firstPass = true;
		do {
			if (firstPass) {
				outputMessage = "Enter your previous username: ";
			} else {
				outputMessage = " Invalid username. Enter your previous username again: ";
			}
			username = JOptionPane.showInputDialog(outputMessage);
			firstPass = false;

		} while (userDAO.getUser(username) == null);

		user = userDAO.getUser(username);
		gameDisplay.setUser(user);

	}
	
	/**
	 * Save user to database
	 */
	@Override
	public void saveToDatabase(User user) {
		userDAO.saveUser(user);
	}
	
	/**
	 * Show high scores frame
	 */
	public void showHighScores() {
		final JFrame scoresFrame = new JFrame("High Scores");
		JButton backButton = new JButton("Back to menu");
		JPanel scoresPanel = new JPanel();
		scoresPanel.setLayout(new BoxLayout(scoresPanel, BoxLayout.Y_AXIS));
		Container container = scoresFrame.getContentPane();

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getViewport().add( scoresPanel );
		
		container.add(scrollPane);
		container.add(backButton, BorderLayout.SOUTH);

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scoresFrame.setVisible(false);
			}

		});

		int index=1;
		for (User user: userDAO.getAllUsers()) {
			JLabel nameLabel =  new JLabel(index+". "+user.getName());
			nameLabel.setForeground(Color.red);
			JLabel scoreLabel =  new JLabel("Score: "+user.getScore());
			JLabel levelReachedLabel =  new JLabel("Level Reached: "+user.getLevelReached());
			scoresPanel.add(nameLabel);
			scoresPanel.add(scoreLabel);
			scoresPanel.add(levelReachedLabel);
			scoresPanel.add(Box.createVerticalStrut(20));
			
			index++;
		}


			scoresFrame.setSize(FRAME_SIZE, FRAME_SIZE);
			scoresFrame.setResizable(false);
			scoresFrame.setLocationRelativeTo(null);
			scoresFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			scoresFrame.setVisible(true);

	}

}

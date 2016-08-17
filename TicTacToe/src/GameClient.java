import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

public class GameClient {

	private JFrame frame;
	private String name;
	private boolean myTurn;
	private ArrayList<MyPanel> panelList;
	JTextArea incoming;
	JTextField outgoing;
	ObjectOutputStream writer;
	ObjectInputStream reader;
	Socket socket;

	public GameClient(String newName) {

		this.name = newName;
		panelList = new ArrayList<>();

		setUpGUI();
		setUpNetworking();
		startGame();

	}

	public GameClient() {

		this.name = JOptionPane.showInputDialog("Enter your name");

		panelList = new ArrayList<>();
		setUpGUI();
		setUpNetworking();
		startGame();

	}

	public void startGame() {

		Thread readerThread = new Thread(new Runnable() {

			public void run() {

				Object newMessage;

				while (true) {

					try {

						while ((newMessage = reader.readObject()) != null) {

							if (newMessage instanceof MyMessage) {

								MyMessage currentMessage = (MyMessage) newMessage;

								incoming.append(currentMessage.getSender()
										+ ": " + currentMessage.getMessage()
										+ "\n");

							}

							if (newMessage instanceof MyAction) {
								MyAction action = (MyAction) newMessage;

								if (action.getType().equals("X")) {

									panelList.get(action.getIndex()).showX();

								}

								else {
									panelList.get(action.getIndex()).showO();

								}

								if (!action.getPlayerName().equals(name)) {
									System.out.println(panelList.get(
											action.getIndex()).getPosition()
											+ " was marked against " + name);
									panelList.get(action.getIndex())
											.markAgainst();

								}

							}

							if (newMessage instanceof String) {

								String currentMessage = (String) newMessage;
								String whatToShow=null;
								
								if (currentMessage.equals("Winner")) {
									whatToShow="You Win.Congratulations!";
								}

								else if(currentMessage.equals("Tie")) {
									whatToShow="It's a draw!";
								}
								else {
									
									whatToShow="You Lose. Better luck next time!";
		
								}
								
								
								JOptionPane.showMessageDialog(frame,
										whatToShow);
							
								restartGame();
							}

						}
					} catch (IOException | ClassNotFoundException e) {
						System.exit(-1);
					}

				}

			}

		});

		readerThread.start();
	}

	public void setUpNetworking() {

		try {
			socket = new Socket("127.0.0.1", 2100);

			reader = new ObjectInputStream(socket.getInputStream());

			writer = new ObjectOutputStream(socket.getOutputStream());

			System.out.println("Network established");

		} catch (UnknownHostException e) {
			System.out.println("cacat dublu");
			e.printStackTrace();
		} catch (IOException e) {

			System.out.println("cacat dublu");
			e.printStackTrace();
		}

	}

	public boolean iWon() {
		if (panelList.get(0).getState() && panelList.get(1).getState()
				&& panelList.get(2).getState()) {
			return true;
		}
		if (panelList.get(3).getState() && panelList.get(4).getState()
				&& panelList.get(5).getState()) {
			return true;
		}
		if (panelList.get(6).getState() && panelList.get(7).getState()
				&& panelList.get(8).getState()) {
			return true;
		}
		if (panelList.get(0).getState() && panelList.get(3).getState()
				&& panelList.get(6).getState()) {
			return true;
		}
		if (panelList.get(1).getState() && panelList.get(4).getState()
				&& panelList.get(7).getState()) {
			return true;
		}
		if (panelList.get(2).getState() && panelList.get(5).getState()
				&& panelList.get(8).getState()) {
			return true;
		}
		if (panelList.get(0).getState() && panelList.get(4).getState()
				&& panelList.get(8).getState()) {
			return true;
		}
		if (panelList.get(2).getState() && panelList.get(4).getState()
				&& panelList.get(6).getState()) {
			return true;
		}

		return false;
	}

	public boolean endedAsTie() {
		boolean tie = true;

		for (MyPanel panel : panelList) {
			if (panel.isEmpty()) {
				tie = false;
			}

		}

	return tie;
	
	}

	public void restartGame() {
		for (MyPanel panel : panelList) {
			panel.reset();
		}
	}

	public void setUpGUI() {

		frame = new JFrame("Simple Chat: " + name);
		Container container = frame.getContentPane();

		JPanel mainPanel = new JPanel(new GridLayout(3, 3, 10, 10));

		for (int i = 0; i < 9; i++) {
			final MyPanel temp = new MyPanel(i);

			temp.addMouseListener(new MouseAdapter() {

				public void mousePressed(MouseEvent me) {

					if (temp.isEmpty()) {

						try {

							writer.writeObject(new MyAction(name, temp
									.getPosition()));

							temp.mark();
							System.out.println(temp.getPosition()
									+ " was marked for " + name);
							if (iWon()) {
								System.out.println("I just won the game");
								writer.writeObject(new String("WON"));
							}
							
							if(endedAsTie()){
								System.out.println("I just won the game");
								writer.writeObject(new String("TIE"));
							}

						} catch (IOException e) {
							e.printStackTrace();
						}

					} else
						System.out.println("This panel is occupied");

				}
			});

			panelList.add(temp);
			mainPanel.add(temp);
		}

		JPanel chatPanel = new JPanel();

		JButton button = new JButton("Send");

		outgoing = new JTextField(20);

		incoming = new JTextArea(5, 20);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		DefaultCaret caret = (DefaultCaret) incoming.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {

				try {

					writer.writeObject(new MyMessage(outgoing.getText(), name));

				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					writer.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}

				outgoing.setText("");
				outgoing.requestFocus();

			}
		});

		chatPanel.add(BorderLayout.NORTH, qScroller);
		chatPanel.add(BorderLayout.CENTER, outgoing);
		chatPanel.add(BorderLayout.SOUTH, button);

		container.add(BorderLayout.CENTER, mainPanel);
		container.add(BorderLayout.SOUTH, chatPanel);

		frame.setSize(600, 600);
		frame.setLocation(400, 100);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		GameClient bogdan = new GameClient("Bogdan");
		 GameClient mircea = new GameClient("Mircea");

		//GameClient bogdan = new GameClient();
		//GameClient mircea = new GameClient();
	}

}

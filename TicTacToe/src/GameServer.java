import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class GameServer {

	private int nrPlayers;
	private ObjectOutputStream streamClient1;
	private ObjectOutputStream streamClient2;
	private ArrayList<MyPanel> panelList;
	private ServerSocket serverSocket;

	public GameServer() {
		nrPlayers = 0;
		panelList = new ArrayList<>();
		streamClient1 = null;
		streamClient2 = null;

		try {
			serverSocket = new ServerSocket(2100);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setUpGUI();
	}

	public void setUpGUI() {
		JFrame frame = new JFrame("Server");
		JButton button = new JButton("Close server");
		JLabel label = new JLabel("Server is active. Click button to close it");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {

				try {
					serverSocket.close();

					System.exit(-1);

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		label.setHorizontalAlignment(SwingConstants.CENTER);

		frame.getContentPane().add(BorderLayout.CENTER, label);
		frame.getContentPane().add(BorderLayout.SOUTH, button);

		frame.setSize(300, 100);
		frame.setLocation(00, 100);
		frame.setVisible(true);

	}

	public void go() {

		try {

			// while (nrPlayers <= 2) {

			while (true) {

				Socket clientSocket = serverSocket.accept();
				nrPlayers++;

				if (streamClient1 == null) {
					streamClient1 = new ObjectOutputStream(
							clientSocket.getOutputStream());
					streamClient1.writeObject(new MyMessage(
							"Waiting for Player 2...", "Server"));
				} else {

					streamClient2 = new ObjectOutputStream(
							clientSocket.getOutputStream());
					tellEveryone(new MyMessage(
							"Player 2 connected. Let the game begin...",
							"Server"));

				}

				Thread t1;

				if (streamClient2 == null)
					t1 = new Thread(new ClientHandler(clientSocket, "X"));
				else
					t1 = new Thread(new ClientHandler(clientSocket, "O"));

				t1.start();

				System.out.println("Server: Got a connection");

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void endGame(int playerNumber) {

		try {
			if (playerNumber == 1) {

				streamClient1.writeObject("Winner");
				streamClient2.writeObject("Losser");

			} else if(playerNumber==2){
				streamClient2.writeObject("Winner");
				streamClient1.writeObject("Losser");

			} else {
				streamClient2.writeObject("Tie");
				streamClient1.writeObject("Tie");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void updatePanels(MyAction action) {
		try {
			streamClient1.writeObject(action);

			streamClient2.writeObject(action);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void tellEveryone(MyMessage message) {
		try {
			streamClient1.writeObject(message);

			streamClient2.writeObject(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		GameServer server = new GameServer();
		server.go();
	}

	// ////////////////-----------ClientHandler------\\\\\\\\

	public class ClientHandler implements Runnable {

		private Socket socket;
		private ObjectInputStream reader;
		private String identity;

		public ClientHandler(Socket newSocket, String newIdentity) {
			this.socket = newSocket;
			this.identity = newIdentity;

			try {
				reader = new ObjectInputStream(socket.getInputStream());

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void run() {

			Object message;
			try {
				while ((message = reader.readObject()) != null) {

					if (message instanceof MyMessage) {
						MyMessage currentMessage = (MyMessage) message;
						tellEveryone(currentMessage);
					}

					if (message instanceof MyAction) {
						
						MyAction action=(MyAction)message;
						
						int i = action.getIndex();
						
						String name= action.getPlayerName();
						
						
						updatePanels(new MyAction(name,identity, i));
					}

					if (message instanceof String) {
						String currentMessage=(String)message;
						
						if(currentMessage.equals("WON")){
							
							if (identity.equals("X")) {
								endGame(1);
							} else
								endGame(2);

							
						}
						else {
							
							endGame(0);
						}

					}

				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

		}

	}

}

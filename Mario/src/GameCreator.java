import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class GameCreator {

	private MyPanel myPanel;
	private volatile Mario mario;
	private ArrayList<Block> blockList;

	private volatile boolean jumpInterrupted;
	private volatile boolean onEdge;
	private boolean isJumping;
	private boolean runGame;

	private JLabel scoreLabel;
	private int totalScore;

	private Lava lava;
	private int bottomLevel;

	public GameCreator() {
		mario = new Mario();
		runGame = true;
		blockList = new ArrayList<Block>();
		lava=new Lava();
		
		myPanel = new MyPanel(mario, blockList,lava);
		
		jumpInterrupted = false;
		isJumping = false;
		onEdge = false;
		totalScore = 0;
		start();

	
		scoreLabel = new JLabel("Score: " + totalScore);

	}

	public void setJumping(boolean newState) {
		isJumping = newState;
	}

	public void updateBlockList() {

		Iterator<Block> it = blockList.iterator();

		while (it.hasNext()) {
			Block block = it.next();

			if (block.getLocation().getY() > 450) {
				it.remove();
			}
		}

	}

	public Block getCurrentBottom() {

		Block highestBlock = new Block(10000, 10000);

		for (Block block : blockList) {

			double minX = block.getLocation().getX();
			double maxX = block.getMaxX();
			double marioX = mario.getLocation().getX();
			if (minX < marioX
					&& marioX < maxX
					&& mario.getLocation().getY() + 35 < block.getLocation()
							.getY()
					&& block.getLocation().getY() < highestBlock.getLocation()
							.getY()) {

				highestBlock = block;

			}

		}

		if (highestBlock.getLocation().getX() != 10000) {

			jumpInterrupted = true;
			return highestBlock;

		}

		return null;

	}

	public void updateMarioPosition(Block block) {

		if (block != null) {

			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			Color color = new Color(red, green, blue);
			block.setColor(color);

			onEdge = true;

			mario.setLocation((int) mario.getLocation().getX(), (int) block
					.getLocation().getY() - 35);

			mario.setCurrentBlock(block);

		}

	}

	public void wasCoinTaken() {

		if (mario.getCurrentBlock() != null
				&& mario.getCurrentBlock().hasCoin()) {

			double coinPoint = mario.getCurrentBlock().getLocation().getX() + 2 * 20;

			double marioPoint = mario.getLocation().getX();

			if (Math.abs(marioPoint - coinPoint) < 10) {
				mario.getCurrentBlock().takeCoin();
				totalScore++;
				scoreLabel.setText("Score: " + totalScore);
			}

		}

	}

	public void reset(){
		mario.setLocation(200, 400);
		totalScore=0;
		lava.setLevel(0);
		blockList.clear();
		scoreLabel.setText("Score: " + totalScore);
	}
	
	
	public void marioDied(){
		if(lava.getHeight()>0 && mario.getLocation().getY() > 401-lava.getHeight()){
			 
			
			 JOptionPane.showMessageDialog( null, 
		               " Game Over! Your final score is: "+ totalScore,
		               "End of Game", JOptionPane.INFORMATION_MESSAGE );
		      reset();
		      
		}
	}
	public void checkIfMarioFell() {
		int currentX=(int)mario.getLocation().getX();
		
		if (onEdge
				&& ( currentX < mario.getCurrentBlock()
						.getLocation().getX() || currentX> mario.getCurrentBlock().getMaxX()) ) {
			
				Block block= getCurrentBottom();
				
				if(block!=null){
					updateMarioPosition(block);
				}
			
				else{
					
					Thread t1 = new Thread(new Runnable() {
						public void run() {

		
							while(mario.getLocation().getY()<400){
						
								mario.fall();
								myPanel.repaint();

								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
								}
							}
							
							mario.resetPhoto();
							
						}
					});

					t1.start();
					
					

					
					
					
				}
				
		
		}
	
	}

	public void updateLavaLevel(){
		
	lava.setLevel(totalScore/2);
		
	}
	
	public void start() {

		SwingWorker<Void, ArrayList<Block>> worker = new SwingWorker<Void, ArrayList<Block>>() {

			protected Void doInBackground() throws Exception {

				int timePassed = 0;
				Random generator = new Random();

				while (runGame) {

					Thread.sleep(15);
					timePassed += 50;

					for (Block block : blockList) {
						block.moveDown();

					}

					if (timePassed >= 5000) {
						timePassed = 0;
						int randomX = generator.nextInt(250);
						blockList.add(new Block(randomX, 0));

					}

					updateBlockList();

					wasCoinTaken();
					
					updateLavaLevel();
					
					checkIfMarioFell();
					
					marioDied();
					
					updateMarioPosition(getCurrentBottom());

					publish(blockList);

				}

				return null;

			}

			protected void process(List<ArrayList<Block>> chunks) {
				ArrayList<Block> list = chunks.get(chunks.size() - 1);

				myPanel.repaint();

				chunks.clear();
			}

			protected void done() {
				myPanel.repaint();
			}

		};

		worker.execute();

	}

	public boolean isOnBottom() {

		Block block = getCurrentBottom();

		int limit;

		if (block == null) {
			limit = 400;
		} else {
			limit = (int) block.getLocation().getY();
		}

		if (mario.getLocation().getY()< limit) {
			jumpInterrupted = true;
			return false;
		}

		else {
			jumpInterrupted = false;
			return true;

		}

	}

	public void create() {

		JFrame frame = new JFrame();
		Container content = frame.getContentPane();
		JLabel intructionsLabel = new JLabel("A-Left    D-Right    W- Jump");

		JPanel totalPanel = new JPanel();
		JPanel random1 = new JPanel();
		JPanel random2 = new JPanel();
		JPanel wrapperPanel = new JPanel();

		Font bigFont = new Font("sanserif", Font.BOLD, 16);
		intructionsLabel.setFont(bigFont);
		intructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scoreLabel.setFont(bigFont);

		frame.setSize(410, 625);
		frame.setLocation(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		totalPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		totalPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		wrapperPanel
				.setLayout(new BoxLayout(wrapperPanel, BoxLayout.PAGE_AXIS));
		wrapperPanel.setBackground(Color.LIGHT_GRAY);

		wrapperPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		wrapperPanel.add(myPanel);
		wrapperPanel.add(Box.createRigidArea(new Dimension(0, 50)));

		random1.setLayout(new BoxLayout(random1, BoxLayout.LINE_AXIS));
		random1.add(Box.createRigidArea(new Dimension(50, 0)));
		random1.setBackground(Color.LIGHT_GRAY);

		random2.setLayout(new BoxLayout(random2, BoxLayout.LINE_AXIS));
		random2.add(Box.createRigidArea(new Dimension(50, 0)));
		random2.setBackground(Color.LIGHT_GRAY);

		content.add(wrapperPanel, BorderLayout.CENTER);

		content.add(BorderLayout.SOUTH, intructionsLabel);

		content.add(BorderLayout.EAST, random1);
		content.add(BorderLayout.WEST, random2);
		content.add(BorderLayout.NORTH, scoreLabel);

		myPanel.addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();
				if (keyCode == event.VK_A) {
					mario.moveLeft();
					myPanel.repaint();

				}
				if (keyCode == event.VK_D) {
					mario.moveRight();
					myPanel.repaint();
				}

				if (keyCode == event.VK_S) {
					mario.fall();
					myPanel.repaint();
				}

				if (keyCode == event.VK_W) {

					if (onEdge) {
						blockList.remove(mario.getCurrentBlock());
						jumpInterrupted = false;
						mario.setCurrentBlock(null);
					}

					onEdge = false;

					if (!isJumping) {

						Thread t1 = new Thread(new Runnable() {
							public void run() {

								ArrayList<Point> list = mario.jump();

								for (Point point : list) {

									if (!jumpInterrupted) {

										mario.setLocation((int) point.getX(),
												(int) point.getY());

										myPanel.repaint();
										

										try {
											Thread.sleep(10);
										} catch (InterruptedException e) {
										}

									} else {
										break;
									}

									isJumping = true;
								}

								
								if(!onEdge){
									
								
									while(!isOnBottom()){
										mario.fall();
										try {
											Thread.sleep(10);
										} catch (InterruptedException e) {
										}
										
									}
									
								}
								
							
								
								isJumping = false;

								mario.resetPhoto();
								myPanel.repaint();

							}
						});

						t1.start();

					}

				}
			}

		});
		frame.setVisible(true);

	}
}

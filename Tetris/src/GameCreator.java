import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class GameCreator {

	private int totalScore;

	private ArrayList<Shape> shapeList;
	private ArrayList<MyPoint> bottomPointList;

	private MyPanel myPanel;

	private Shape movingShape;

	private boolean runGame;

	private MyPoint highestPoint;

	private final int radius = 20;

	private JLabel scoreLabel;

	public GameCreator() {

		scoreLabel = new JLabel("Current score is: " + totalScore);
		shapeList = new ArrayList<Shape>();
		bottomPointList = new ArrayList<MyPoint>();
		runGame = true;
		movingShape = getRandomShape();

		myPanel = new MyPanel(movingShape);

		totalScore = 0;

		highestPoint = new MyPoint(1000, 1000);
		
		start();
	}

	public void setRunGame(boolean newState) {
		runGame = newState;
	}

	public boolean shapeIntersect(Shape shape, Point bottomPoint,
			String direction) {

		ArrayList<MyPoint> setPoint1 = shape.getShape();

		switch (direction) {

		case "Null":
			for (Point point1 : setPoint1) {
				if (point1.getX() == bottomPoint.getX()
						&& point1.getY() == bottomPoint.getY()) {

					return true;
					
				}
				
			}
			
			return false;
			
		case "Down":

			for (Point point1 : setPoint1) {
				if (point1.getX() == bottomPoint.getX()
						&& point1.getY() + radius == bottomPoint.getY()) {
					return true;
				}

			}

			return false;

		case "Left":

			for (Point point1 : setPoint1) {
				if (point1.getX() - radius == bottomPoint.getX()
						&& point1.getY() == bottomPoint.getY()) {
					return true;
				}

				return false;
			}

		case "Right":

			for (Point point1 : setPoint1) {
				if (point1.getX() + radius == bottomPoint.getX()
						&& point1.getY() == bottomPoint.getY()) {
					return true;
				}

			}
			return false;

		}
		return false;

	}

	public Shape getRandomShape() {

		Random random = new Random();

		int topX = random.nextInt(15) * 20;

		int newInt = random.nextInt(9);

		Shape newShape;
		
		switch (newInt) {

		case 0:
			newShape = new Square(Math.min(topX, 300 - 2 * radius), 0);
			break;
		case 1:

			newShape = new UpPyramid(Math.min(topX, 300 - 3 * radius), 0);
			break;
		case 2:

			newShape = new HorrizLShape(Math.min(topX, 300 - 3 * radius), 0);
			break;
		case 3:

			newShape = new HorrizSnake(Math.min(topX, 300 - 3 * radius), 0);
			break;

		case 4:
			newShape = new VertSnake(Math.min(topX, 300 - 2 * radius), 0);
			break;

		case 5:
			newShape = new VertLShape(Math.min(topX, 300 - 2 * radius), 0);
			break;

		case 6:
			newShape = new DownPyramid(Math.min(topX, 300 - 3 * radius), 0);
			break;

		case 7:
			newShape = new VertLine(Math.min(topX, 300 -  radius), 0);
			break;

		default:

			newShape = new HorrizLine(Math.min(topX, 300 - 4 * radius), 0);
			break;

		}

		return newShape;

	}

	public boolean isBottom(Shape currentShape) {

		int limit;

		for (Point bottomPoint : bottomPointList) {
			if (shapeIntersect(currentShape, bottomPoint, "Down")) {
				return true;
			}

		}

		if (currentShape instanceof HorrizLine)
			limit = 410;
		else if (currentShape instanceof VertLShape
				|| currentShape instanceof VertSnake) {
			limit = 380;
			
		} else if (currentShape instanceof VertLine) {
			limit=360;
		} else
			limit = 400;

		if (currentShape.getTopY() >= limit)
			return true;
		else
			return false;

	}

	public void moveShape() {

		movingShape.setLocation(movingShape.getTopX(), movingShape.getTopY()
				+ radius);
	}

	public boolean endGame() {

		for (MyPoint point : bottomPointList) {
			if (point.getY() == 0)
				return true;
		}

		return false;
	}

	public void start() {

		SwingWorker<Void, Shape> worker = new SwingWorker<Void, Shape>() {
			protected Void doInBackground() throws Exception {

				while (runGame) {
					Thread.sleep(150);

					if (!isBottom(movingShape)) {
						moveShape();
					} else {

						for (MyPoint point : movingShape.getShape()) {
							myPanel.addBottomList(point);
							bottomPointList.add(point);
						}

						for (MyPoint point : bottomPointList) {

							if (point.getY() < highestPoint.getY()) {
								highestPoint = point;
							}

						}
						updateLines();
						movingShape = getRandomShape();

						if (endGame()) {
							runGame = false;
							JOptionPane.showMessageDialog(null,
									" Game Over :(", "Game Over",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}

					publish(movingShape);

				}
				return null;

			}

			protected void process(List<Shape> chunks) {
				Shape currentShape = chunks.get(chunks.size() - 1);

				myPanel.updateMovingShape(currentShape);
				chunks.clear();
			}

			protected void done() {
				myPanel.repaint();
			}

		};

		worker.execute();

	}

	public void updateLines() {
		int maxLine = 450 - (int) highestPoint.getY();
		maxLine = maxLine / radius;

		HashSet<MyPoint> set = new HashSet<MyPoint>();

		for (int i = 0; i < maxLine; i++) {
			for (MyPoint point : bottomPointList) {
				if (point.getY() == 420 - i * radius) {
					set.add(point);
				}
			}

			if (set.size() + 1 > 15) {
				bottomPointList.removeAll(set);
				myPanel.updateBottomList(bottomPointList);
				totalScore++;
				scoreLabel.setText("Current score is: " + totalScore);

				for (MyPoint point : bottomPointList) {
					if (point.getY() < 420 - i * radius) {
						point.setLocation(point.getX(), point.getY() + radius);
					}
				}

			}

			set.clear();
		}

	}

	public int getRightEdge() {

		if (movingShape instanceof Square) {
			return 300 - radius;
		}

		if (movingShape instanceof HorrizLine) {
			return 300 - 3 * radius;
		}

		if (movingShape instanceof HorrizLShape) {
			return 300 - 2 * radius;
		}

		if (movingShape instanceof HorrizSnake) {
			return 300 - 2 * radius;
		}

		if (movingShape instanceof UpPyramid) {
			return 300 - 2 * radius;
		}
		
		
		if (movingShape instanceof DownPyramid) {
			return 300 - 2* radius;
		}

		if (movingShape instanceof VertLine) {
			return 300 ;
		}

		if (movingShape instanceof VertLShape) {
			return 300 - radius;
		}

		if (movingShape instanceof VertSnake) {
			return 300 - radius;
		}

		
		
		
		

		return 300;

	}

	public Shape getRotatedShape() {

		if (movingShape instanceof DownPyramid)
			return new UpPyramid(movingShape.getTopX(), movingShape.getTopY());

		if (movingShape instanceof UpPyramid)
			return new DownPyramid(movingShape.getTopX(), movingShape.getTopY());

		if (movingShape instanceof HorrizLine)
			return new VertLine(movingShape.getTopX(), movingShape.getTopY());

		if (movingShape instanceof VertLine)
			return new HorrizLine(movingShape.getTopX(), movingShape.getTopY());

		if (movingShape instanceof HorrizLShape)
			return new VertLShape(movingShape.getTopX(), movingShape.getTopY());

		if (movingShape instanceof VertLShape)
			return new HorrizLShape(movingShape.getTopX(),
					movingShape.getTopY());

		if (movingShape instanceof HorrizSnake)
			return new VertSnake(movingShape.getTopX(), movingShape.getTopY());

		if (movingShape instanceof VertSnake)
			return new HorrizSnake(movingShape.getTopX(), movingShape.getTopY());

		return new Square(movingShape.getTopX(), movingShape.getTopY());
	}

	
	public void moveRight(){
		if (movingShape.getTopX() + radius < getRightEdge()) {

			boolean notIntersect = true;

			for (MyPoint point : bottomPointList) {
				if (shapeIntersect(movingShape, point, "Right")) {
					notIntersect = false;
					break;
				}
			}

			if (notIntersect)
				movingShape.setLocation(movingShape.getTopX() + radius,
						movingShape.getTopY());
			
		}
	}
	
	public void moveLeft(){

		if (movingShape.getTopX() - radius > -20) {
			boolean notIntersect = true;

			for (MyPoint point : bottomPointList) {
				if (shapeIntersect(movingShape, point, "Left")) {
					notIntersect = false;
					break;
				}
			}

			if (notIntersect)
				movingShape.setLocation(movingShape.getTopX() - radius,
						movingShape.getTopY());
		}
	}
	
	public void rotateMovingShape(){
		
		
		boolean notIntersect = true;

		Shape futureShape=getRotatedShape();
		futureShape.setLocation(futureShape.getTopX(), futureShape.getTopY()
				+ radius);
		
		for (MyPoint point : bottomPointList) {
			
			if (shapeIntersect(futureShape, point, "Null")) {
				notIntersect = false;
				break;
			}
		}

		if (notIntersect && !isBottom(futureShape) && futureShape.getRightExtremity().getX() < getRightEdge())
			movingShape=futureShape;
		
	}
	
	public void create() {

		JFrame frame = new JFrame();
		Container content = frame.getContentPane();
		JButton leftButton = new JButton("Left");
		JButton rightButton = new JButton("Right");
		JButton rotateButton = new JButton("Rotate");
		JPanel buttonPanel = new JPanel();
		JPanel totalPanel = new JPanel();
		JPanel random1 = new JPanel();
		JPanel random2 = new JPanel();
		JPanel wrapperPanel = new JPanel();

		Font bigFont = new Font("sanserif", Font.BOLD, 16);
		scoreLabel.setFont(bigFont);
		leftButton.setFont(bigFont);
		rightButton.setFont(bigFont);
		rotateButton.setFont(bigFont);

		frame.setSize(410, 625);
		frame.setLocation(300, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		buttonPanel.add(leftButton);
		buttonPanel.add(rightButton);
		buttonPanel.add(rotateButton);

		totalPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		totalPanel.add(buttonPanel);
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

		content.add(BorderLayout.SOUTH, totalPanel);

		content.add(BorderLayout.EAST, random1);
		content.add(BorderLayout.WEST, random2);

		content.add(BorderLayout.NORTH, scoreLabel);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

		leftButton.setFocusable(false);
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
					
				moveLeft();
			

			}
		});
		rightButton.setFocusable(false);

		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {

				moveRight();

			
			}
		});
		rotateButton.setFocusable(false);
		rotateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E) {
			
				
				rotateMovingShape();
				
			}
			
		});

		
		myPanel.addKeyListener(new KeyAdapter(){
		
			
			public void keyPressed(KeyEvent event) {
		            int keyCode = event.getKeyCode();
		            if (keyCode == event.VK_A)
		            {
		              moveLeft();
		            
		            }
		            if (keyCode == event.VK_D)
		            {
		               moveRight();
		            }
		            if (keyCode == event.VK_S)
		            {
		            	rotateMovingShape();
		            }
		        }
			
			
			
		});
		frame.setVisible(true);
	}

}

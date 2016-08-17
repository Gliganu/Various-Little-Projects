package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class Game extends JComponent {


    private Ellipse2D.Double ball = new Ellipse2D.Double(100, 100, 15, 15);
    private RoundRectangle2D.Double bat = new RoundRectangle2D.Double(200, 200, 100, 10, 20, 20);

    private double speed = 10.0;

    private double batSpeed = 15.0;

    private int xDirectionBall = 1;
    private int yDirectionBall = 1;

    private boolean checkIntersection = true;

    private BufferedImage buffer;


    public Game() {

        configureGui();

    }

    private void configureGui() {

        Cursor hiddenCursor = getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(1, 1), "");

        setCursor(hiddenCursor);

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent event) {

                bat.x = event.getX() - bat.getWidth() / 2;
                bat.y = event.getY() - bat.getHeight() / 2;
                repaint();
            }
        });


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                ball.x = event.getX();
                ball.y = event.getY();
                repaint();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                buffer = null;
            }
        });


        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent event) {

                int key = event.getKeyCode();

                switch (key) {
                    case KeyEvent.VK_UP:
                        bat.y -= batSpeed;
                        break;
                    case KeyEvent.VK_DOWN:
                        bat.y += batSpeed;
                        break;
                    case KeyEvent.VK_LEFT:
                        bat.x -= batSpeed;
                        break;
                    case KeyEvent.VK_RIGHT:
                        bat.x += batSpeed;
                        break;

                }

                return false;
            }
        });


    }

    @Override
    protected void paintComponent(Graphics g) {

        if (buffer == null) {

            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }


        Graphics2D g2d = (Graphics2D) buffer.getGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.red);
        g2d.fill(ball);


        g2d.setColor(Color.blue);
        g2d.fill(bat);


        g.drawImage(buffer, 0, 0, null);

    }

    public void update() {

        ball.x += xDirectionBall * speed;
        ball.y += yDirectionBall * speed;

        if (ball.x < 0) {
            xDirectionBall = 1;
            ball.x = 0;
        }

        if (ball.x > getWidth() - ball.getBounds().width) {
            xDirectionBall = -1;
            ball.x = getWidth() - ball.getBounds().width;
        }


        if (ball.y < 0) {
            yDirectionBall = 1;
            ball.y = 0;
        }

        if (ball.y > getHeight() - ball.getBounds().height) {
            yDirectionBall = -1;
            ball.y = getHeight() - ball.getBounds().height;
        }

        if (ball.intersects(bat.getBounds2D())) {

            if (checkIntersection) {
                xDirectionBall *= -1;
                yDirectionBall *= -1;
                checkIntersection = false;
            }
        }else{
            checkIntersection = true;
        }

        repaint();
    }
}

package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JApplet implements ActionListener{

    private Timer timer;
    private Game game;

    private StartPanel startPanel;

    private CardLayout cards;

    @Override
    public void init() {

        timer = new Timer(20,this);
        game = new Game();
        startPanel = new StartPanel();
        cards = new CardLayout();

        startPanel.setListener(new StartPanelListener() {
            @Override
            public void startGame() {
                cards.show(Main.this.getContentPane(),"game");
            }
        });

        setSize(600, 500);
        setLayout(cards);
        add(startPanel, "start");
        add(game,"game");
    }

    @Override
    public void start() {
        timer.start();
    }

    @Override
    public void stop() {
        timer.stop();
    }

    @Override
    public void destroy() {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        game.update();
    }
}

package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {

    private JButton startButton;
    private JLabel label;
    private StartPanelListener listener;

    public StartPanel() {

        configureGui();
    }

    private void configureGui() {

        startButton = new JButton("Start");
        label = new JLabel("Animation Game");

        label.setFont(new Font("Serif",Font.PLAIN,20));
        configureLayout();


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireStartGame();
            }
        });



    }

    private void fireStartGame() {

        if(listener!= null){
            listener.startGame();
        }
    }

    private void configureLayout() {

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();


        //First Row//////////////////////////////////////////
        gc.gridy = 0;
        gc.gridx = 0;

        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.SOUTH;
        add(label, gc);

        ///////////////Next Row///////////////////////////
        gc.gridy++;

        gc.weightx = 0;
        gc.weighty = 0.1;

        gc.anchor = GridBagConstraints.CENTER;
        add(startButton, gc);

    }

    public void setListener(StartPanelListener listener) {
        this.listener = listener;
    }
}

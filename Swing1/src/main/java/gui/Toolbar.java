package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class Toolbar extends JToolBar implements ActionListener {

    private JButton saveButton;
    private JButton refreshButton;

    private ToolbarListener toolbarListener;

    public Toolbar() {

        configureGui();

    }

    private void configureGui() {

        saveButton = new JButton("Save");
        refreshButton = new JButton("Refresh");

        saveButton.setToolTipText("Save");
        refreshButton.setToolTipText("Refresh");


        saveButton.addActionListener(this);
        refreshButton.addActionListener(this);

        add(saveButton);
        addSeparator();
        add(refreshButton);

        setBorder(BorderFactory.createEtchedBorder());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JButton clicked = (JButton) event.getSource();
        if (clicked == saveButton) {
            if (toolbarListener != null) {
                toolbarListener.saveEventOccured();
            }
        }
        if (clicked == refreshButton) {
            if (toolbarListener != null) {
                toolbarListener.refreshEventOcrurred();
            }
        }
    }


    public void setToolbarListener(ToolbarListener toolbarListener) {
        this.toolbarListener = toolbarListener;
    }
}

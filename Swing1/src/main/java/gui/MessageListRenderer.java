package gui;

import controller.Utils;
import model.Message;

import javax.swing.*;
import java.awt.*;

public class MessageListRenderer implements ListCellRenderer<Message> {

    private JPanel panel;
    private JLabel label;

    private Color selectedColor;
    private Color normalColor;

    public MessageListRenderer() {

        panel = new JPanel();
        label  = new JLabel();
        label.setIcon(Utils.createIcon("/Information24.gif"));

        selectedColor = new Color(100,100,255);
        normalColor =  Color.white;

        panel.setBackground(Color.white);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);

        label.setOpaque(true);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Message> list, Message value, int index, boolean isSelected, boolean cellHasFocus) {

        Message message = value;

        label.setText(message.getTitle());

        if(cellHasFocus){
            label.setBackground(selectedColor);
            label.setForeground(Color.WHITE);
        }else{
            label.setBackground(normalColor);
            label.setForeground(Color.black);
        }

        return panel;
    }
}

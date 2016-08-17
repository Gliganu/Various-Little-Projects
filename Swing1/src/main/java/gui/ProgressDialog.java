package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgressDialog extends JDialog {

    private JButton cancelButton;
    private JProgressBar progressBar;
    private ProgressDialogListener listener;


    public ProgressDialog(Window parent, String title) {
        super(parent, title, ModalityType.APPLICATION_MODAL);

        cancelButton = new JButton("Cancel");
        progressBar = new JProgressBar();
        setLocationRelativeTo(parent);
        configureGui();

    }

    private void configureGui() {

        progressBar.setStringPainted(true);
        progressBar.setString("Retrieving messages...");
        progressBar.setMaximum(10);

        setLayout(new FlowLayout());

        add(progressBar);
        add(cancelButton);

        Dimension size = cancelButton.getPreferredSize();
        size.width = 400;
        progressBar.setPreferredSize(size);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listener != null){
                    listener.progressDialogCanceled();
                }
            }
        });

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(listener != null){
                    listener.progressDialogCanceled();
                }
            }
        });


        pack();

    }

    public void setMaximum(int value){

        progressBar.setMaximum(value);

    }

    public void setValue(int value){

        int progress = 100* value/progressBar.getMaximum();

        progressBar.setString(String.format("%d%%", progress));

        progressBar.setValue(value);

    }

    public void setListener(ProgressDialogListener listener) {
        this.listener = listener;
    }


    @Override
    public void setVisible(final boolean visible) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                if(visible == false){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    setValue(0);
                }

                if(visible){
                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                }else{
                    setCursor(Cursor.getDefaultCursor());
                }

               ProgressDialog.super.setVisible(visible);
            }
        });

    }
}

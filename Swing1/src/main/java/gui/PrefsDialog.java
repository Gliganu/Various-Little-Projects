package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PrefsDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel  spinnerModel;
    private JTextField  userField;
    private JPasswordField passwordField;
    private PrefsListener prefsListener;


    public PrefsDialog(JFrame parent) {
        super(parent, "Preferences", false);
        setLocationRelativeTo(parent);
        configureGui();
    }

    private void configureGui() {

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        spinnerModel = new SpinnerNumberModel(3306,0,9999,1);
        portSpinner = new JSpinner(spinnerModel);
        userField = new JTextField(10);
        passwordField = new JPasswordField(10);

        passwordField.setEchoChar('*');

        configureGridBagLayout();
        setSize(350, 200);
        setResizable(false);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String user = userField.getText();
                char[] password = passwordField.getPassword();


                Integer portNumber = (Integer) spinnerModel.getValue();
                setVisible(false);

                if(prefsListener != null){
                    prefsListener.preferencesSet(user,String.copyValueOf(password),portNumber);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

    }

    private void configureGridBagLayout() {

        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel  = new JPanel();

        int space = 15;
        Border spaceBorder = BorderFactory.createEmptyBorder(space,space,space,space);
        Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");


        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,titleBorder));



        controlsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        Insets rightPadding = new Insets(0,0,0,15);
        Insets noPadding = new Insets(0,0,0,15);

        gc.gridy=0;
        ///////////First Row///////////////

        gc.weightx=1;
        gc.weighty=1;
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.NONE;
        gc.insets = rightPadding;
        gc.gridx=0;

        controlsPanel.add(new JLabel("User: "), gc);

        gc.gridx++;
        gc.insets = noPadding;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel. add(userField, gc);


        ///////////Next Row..............
        gc.gridy ++;

        gc.weightx=1;
        gc.weighty=1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        gc.gridx=0;

        controlsPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;
        gc.insets = noPadding;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel. add(passwordField, gc);

        ///////////Next Row..............
        gc.gridy ++;

        gc.weightx=1;
        gc.weighty=1;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        gc.gridx=0;

        controlsPanel.add(new JLabel("Port: "), gc);

        gc.gridx++;
        gc.insets = noPadding;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(portSpinner, gc);

        ///////////Buttons Panel//////////////
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        Dimension buttonSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(buttonSize);

        //Add sub panels to dialog

        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel,BorderLayout.SOUTH);

    }

    public void setPrefsListener(PrefsListener prefsListener) {
        this.prefsListener = prefsListener;
    }

    public void setDefault(String user, String password, int port){
        userField.setText(user);
        passwordField.setText(password);
        portSpinner.setValue(port);
    }
}

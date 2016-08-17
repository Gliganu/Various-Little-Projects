package gui;


import controller.Controller;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private Toolbar toolbar;
    private FormPanel formPanel;
    private JFileChooser fileChooser;
    private Controller controller;
    private TablePanel tablePanel;
    private PrefsDialog prefsDialog;
    private Preferences prefs;
    private JSplitPane splitPane;
    private JTabbedPane tabPane;
    private MessagePanel messagePanel;

    public MainFrame() {
        super("Hello World");
        configureGui();
        initializeTableData();

    }

    private void initializeTableData() {

        try {
            controller.loadFromFile(new File("C:\\Users\\user\\Desktop\\Work\\Computer Science\\Java\\Swing\\Person Database\\finalList.per"));
            tablePanel.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void configureGui() {


        try {
            for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
                if("Nimbus".equals(info.getName())){
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        formPanel = new FormPanel();
        toolbar = new Toolbar();
        controller = new Controller();
        tablePanel = new TablePanel();
        prefsDialog = new PrefsDialog(this);
        prefs = Preferences.userRoot().node("db");
        tabPane = new JTabbedPane();
        messagePanel = new MessagePanel(this);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,formPanel,tabPane);


        tabPane.addTab("Person Database",tablePanel);
        tabPane.addTab("Messages",messagePanel);


        splitPane.setOneTouchExpandable(true);


        tablePanel.setData(controller.getPersonList());

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        setJMenuBar(createMenuBar());

        tablePanel.addPersonTableListener(new PersonTableListener() {
            @Override
            public void rowDeleted(int row) {
                controller.removePerson(row);
            }

        });
        toolbar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOccured() {
                connect();

                try {
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Cannot save to database", "Database connection error", JOptionPane.ERROR_MESSAGE);
                }


            }

            @Override
            public void refreshEventOcrurred() {
                connect();

                try {
                    controller.load();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to load to database", "Database connection error", JOptionPane.ERROR_MESSAGE);
                }

                tablePanel.refresh();
            }
        });

        formPanel.setFormListener(new FormListener() {
            @Override
            public void formEventOccured(FormEvent event) {

                controller.addPerson(event);
                tablePanel.refresh();

            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                controller.disconnect();
                dispose();
                System.gc();
            }
        });

        tabPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                    int tabIndex = tabPane.getSelectedIndex();

                if(tabIndex == 1){
                    messagePanel.refresh();
                }
                    System.out.println("tabIndex = " + tabIndex);
            }
        });

        add(toolbar, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);


        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setLocation(100, 50);
        this.setSize(1250, 500);
      //  this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(500, 400));
        this.setVisible(true);
    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database", "Database connection error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu windowMenu = new JMenu("Window");

        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);


        JMenu showMenu = new JMenu("Show");
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        final JMenuItem prefsItem = new JMenuItem("Prefences...");

        showFormItem.setSelected(true);

        showMenu.add(showFormItem);

        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);


        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) event.getSource();

                if(menuItem.isSelected()){
                    splitPane.setDividerLocation((int) formPanel.getMinimumSize().getWidth());
                }else{
                    splitPane.setDividerLocation(0);
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit the application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

                if (action == JOptionPane.OK_OPTION) {
                    for (WindowListener windowListener : getWindowListeners()) {
                        windowListener.windowClosing(new WindowEvent(MainFrame.this, 0));
                    }
                }
            }
        });

        importDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        controller.loadFromFile(fileChooser.getSelectedFile());
                        tablePanel.refresh();
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });

        exportDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {

                    try {
                        controller.saveToFile(fileChooser.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });


        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prefsDialog.setVisible(true);
            }
        });

        prefsDialog.setPrefsListener(new PrefsListener() {
            @Override
            public void preferencesSet(String user, String password, int port) {
                prefs.put("user", user);
                prefs.put("password", password);
                prefs.putInt("port", port);
            }

        });

        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        Integer port = prefs.getInt("port", 3306);

        prefsDialog.setDefault(user, password, port);

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);

        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
        exportDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));

        return menuBar;

    }

}

package gui;

import model.EmploymentCategory;
import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablePanel extends JPanel {

    private JTable table;
    private PersonTableModel tableModel;
    private JPopupMenu  popUpMenu;
    private PersonTableListener personTableListener;

    public TablePanel() {

        configureGui();
    }

    private void configureGui() {

        tableModel = new PersonTableModel();
        table = new JTable(tableModel);
        popUpMenu = new JPopupMenu();


        table.setDefaultRenderer(EmploymentCategory.class, new  EmploymentCategoryRenderer());
        table.setDefaultEditor(EmploymentCategory.class, new  EmploymentCategoryEditor());


        table.setRowHeight(25);

        JMenuItem removeItem = new JMenuItem("Delete");
        popUpMenu.add(removeItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {

                int row = table.rowAtPoint(event.getPoint());

                table.getSelectionModel().setSelectionInterval(row,row);

                if(event.getButton()== MouseEvent.BUTTON3){
                    popUpMenu.show(table, event.getX(),event.getY());
                }
            }
        });

        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=table.getSelectedRow();

                if(personTableListener != null){
                    personTableListener.rowDeleted(row);
                    tableModel.fireTableRowsDeleted(row,row);
                }
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(table),BorderLayout.CENTER);
    }

    public void setData(List<Person> personList){
        tableModel.setData(personList);

    }

    public void refresh() {
        tableModel.fireTableDataChanged();

    }

    public void addPersonTableListener(PersonTableListener personTableListener) {
        this. personTableListener = personTableListener;

    }
}

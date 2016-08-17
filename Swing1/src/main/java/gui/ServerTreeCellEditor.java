package gui;

import model.ServerInfo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

    private ServerTreeCellRenderer renderer;
    private JCheckBox checkBox;
    private ServerInfo serverInfo;

    public ServerTreeCellEditor() {
        renderer = new ServerTreeCellRenderer();
    }

    @Override
    public boolean isCellEditable(EventObject event) {

        if (!(event instanceof MouseEvent)) {
            return false;
        }

        MouseEvent mouseEvent = (MouseEvent) event;
        JTree tree = (JTree) event.getSource();

        TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());

        if (path == null) {
            return false;
        }

        Object lastPathComponent = path.getLastPathComponent();

        if (lastPathComponent == null) {
            return false;
        }

        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) lastPathComponent;

        return treeNode.isLeaf();

    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {

        Component component = renderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);

        if (leaf) {
            checkBox = (JCheckBox) component;

            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            serverInfo = (ServerInfo) treeNode.getUserObject();

            checkBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    fireEditingStopped();
                    checkBox.removeItemListener(this);
                }
            });

        }

        return component;
    }

    @Override
    public Object getCellEditorValue(){

        serverInfo.setChecked(checkBox.isSelected());
        return serverInfo;

    }

}

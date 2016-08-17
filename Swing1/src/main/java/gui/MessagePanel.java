package gui;

import controller.MessageServer;
import model.Message;
import model.ServerInfo;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessagePanel extends JPanel implements ProgressDialogListener {

    private JFrame parentFrame;
    private JTree serverTree;
    private ServerTreeCellRenderer treeCellRenderer;
    private ServerTreeCellEditor treeCellEditor;
    private ProgressDialog progressDialog;
    private SwingWorker<List<Message>, Integer> swingWorker;

    private TextPanel textPanel;
    private JList<Message> messageList;
    private DefaultListModel<Message> messageListModel;

    private JSplitPane upperPane;
    private JSplitPane lowerPane;

    private Set<Integer> selectedServers;
    private MessageServer messageServer;

    public MessagePanel(JFrame parentFrame) {

        this.parentFrame = parentFrame;
        messageServer = new MessageServer();
        selectedServers = new TreeSet<Integer>();
        progressDialog = new ProgressDialog(parentFrame, "Messages Downloading...");
        messageListModel = new DefaultListModel<Message>();

        progressDialog.setListener(this);

        selectedServers.add(0);
        selectedServers.add(1);
        selectedServers.add(4);


        configureGui();
    }

    private void configureGui() {
        configureTree();


        setLayout(new BorderLayout());

        textPanel = new TextPanel();
        messageList = new JList<Message>(messageListModel);
        messageList.setCellRenderer(new MessageListRenderer());


        lowerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,  new JScrollPane(messageList), textPanel);
        upperPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(serverTree), lowerPane);

        textPanel.setMinimumSize(new Dimension(10, 100));

        messageList.setMinimumSize(new Dimension(10, 100));

        upperPane.setResizeWeight(0.5);
        lowerPane.setResizeWeight(0.5);


        messageList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Message message = messageList.getSelectedValue();
                textPanel.setText(message.getContent());

            }
        });


        add(upperPane, BorderLayout.CENTER);

    }

    private void configureTree() {

        treeCellRenderer = new ServerTreeCellRenderer();
        treeCellEditor = new ServerTreeCellEditor();

        serverTree = new JTree(createTree());

        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.setEditable(true);
        serverTree.setCellEditor(treeCellEditor);

        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);


        messageServer.setSelectedServers(selectedServers);

        treeCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent event) {

                ServerInfo serverInfo = (ServerInfo) treeCellEditor.getCellEditorValue();

                int serverId = serverInfo.getId();

                if (serverInfo.isChecked()) {
                    selectedServers.add(serverId);
                } else {
                    selectedServers.remove(serverId);
                }

                messageServer.setSelectedServers(selectedServers);

                retrieveMessages();

            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });
    }

    public void refresh() {
        retrieveMessages();
    }


    private void retrieveMessages() {

        progressDialog.setMaximum(messageServer.getMessageCount());

        progressDialog.setVisible(true);


        swingWorker = new SwingWorker<List<Message>, Integer>() {
            @Override
            protected List<Message> doInBackground() throws Exception {

                List<Message> retrievedMessages = new ArrayList<Message>();
                int count = 0;

                for (Message message : messageServer) {

                    if(isCancelled()){
                        break;
                    }

                    System.out.println(message.getTitle());
                    retrievedMessages.add(message);
                    count++;

                    publish(count);
                }

                return retrievedMessages;

            }

            @Override
            protected void process(List<Integer> countList) {

                int retrieved = countList.get(countList.size() - 1);

                progressDialog.setValue(retrieved);

            }

            @Override
            protected void done() {
                try {
                    if(isCancelled()){
                        progressDialog.setVisible(false);
                        return;
                    }

                    List<Message> retrievedMessages = get();

                    messageListModel.removeAllElements();

                    for(Message message: retrievedMessages){
                        messageListModel.addElement(message);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                progressDialog.setVisible(false);

            }
        };

        swingWorker.execute();

    }

    private DefaultMutableTreeNode createTree() {


        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");

        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo(0, "New York", selectedServers.contains(0)));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo(1, "Boston", selectedServers.contains(1)));
        DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo(2, "Los Angeles", selectedServers.contains(2)));

        branch1.add(server1);
        branch1.add(server2);
        branch1.add(server3);

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo(3, "London", selectedServers.contains(3)));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo(4, "Edinburgh", selectedServers.contains(4)));

        branch2.add(server4);
        branch2.add(server5);

        top.add(branch1);
        top.add(branch2);

        return top;
    }

    @Override
    public void progressDialogCanceled() {
        if(swingWorker!=null){
            swingWorker.cancel(true);
        }
    }
}

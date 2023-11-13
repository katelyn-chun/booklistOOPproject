package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

// A GUI for a bookkeeping app
// Citation: Used ListDemo as reference from https://docs.oracle.com/javase/tutorial/uiswing/components/list.html

public class GUI extends JPanel
        implements ListSelectionListener {

    private JList list;
    private DefaultListModel listModel;
    private JButton addButton;
    private JButton removeButton;
    private JTextField bookName;

    public GUI() throws
            UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(new BorderLayout());
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        listModel = new DefaultListModel();
        listModel.addElement("<<Sample Book>>");
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(500, 200));

        addButton = new JButton("Add Book");
        removeButton = new JButton("Remove Book");
        AddListener addListener = new AddListener(addButton);
        RemoveListener removeListener = new RemoveListener();

        addButton.setActionCommand("Add Book");
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton.setActionCommand("Remove Book");
        removeButton.addActionListener(removeListener);

        bookName = new JTextField(15);
        bookName.addActionListener(addListener);
        bookName.getDocument().addDocumentListener(addListener);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(addButton);
        buttonPane.add(bookName);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));

        add(listScroller, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    public class AddListener implements ActionListener, DocumentListener {

        private JButton button;
        private boolean alreadyEnabled = false;

        public AddListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = bookName.getText();

            int index = list.getSelectedIndex();
            listModel.addElement(name);

            bookName.requestFocusInWindow();
            bookName.setText("");

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyText(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

            if (!handleEmptyText(e)) {
                if (!alreadyEnabled) {
                    button.setEnabled(true);
                }
            }
        }

        private boolean handleEmptyText(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }

    }

    public class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);

            if (listModel.getSize() == 0) {
                removeButton.setEnabled(false);
            } else {
                if (index == listModel.getSize()) {
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            removeButton.setEnabled(list.getSelectedIndex() != -1);
        }
    }

    private static void showGUI() throws
            UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        JFrame frame = new JFrame("BookList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent pane = new GUI();
        pane.setOpaque(true);
        frame.setContentPane(pane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) throws
            UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        showGUI();
    }
}

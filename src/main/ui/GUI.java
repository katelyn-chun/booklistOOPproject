package ui;

import model.Book;
import model.BookList;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
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
    private JTextField bookAuthor;
    private JCheckBox isRead;
    private JTextField rating;
    private JTextField startDate;
    private JTextField endDate;
    private JTextField link;
    private BookList bookList;

    public GUI() throws
            UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(new BorderLayout());
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        listModel = new DefaultListModel();
        list = new JList(listModel);
        listModel.addElement("<<Sample Book>>");
        bookList = new BookList("BookList");
        Book sample = new Book("Sample Book", "John Smith", false, "1111", "2222", 5, "www.sample.com");
        bookList.addBook(sample);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        ListSelectionListener bookListListener = new BookListSelectionListener();
        list.addListSelectionListener(bookListListener);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(500, 200));

        addButton = new JButton("Add Book");
        removeButton = new JButton("Remove Book");
        AddBookListener addListener = new AddBookListener(addButton);
        RemoveBookListener removeListener = new RemoveBookListener();

        addButton.setActionCommand("Add Book");
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton.setActionCommand("Remove Book");
        removeButton.addActionListener(removeListener);

        bookName = new JTextField(15);
        bookName.getDocument().addDocumentListener(addListener);

        bookAuthor = new JTextField(15);
        bookAuthor.getDocument().addDocumentListener(addListener);

        isRead = new JCheckBox("Read?");
        isRead.setSelected(false);

        startDate = new JTextField(10);
        startDate.getDocument().addDocumentListener(addListener);

        endDate = new JTextField(10);
        endDate.getDocument().addDocumentListener(addListener);

        rating = new JTextField(5);
        rating.getDocument().addDocumentListener(addListener);

        link = new JTextField(5);
        link.getDocument().addDocumentListener(addListener);

        JPanel textPane = new JPanel();
        textPane.setLayout(new BoxLayout(textPane, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel();
        label.setText("Book name: ");
        textPane.add(label);
        textPane.add(bookName);
        JLabel label2 = new JLabel();
        label2.setText("Author: ");
        textPane.add(label2);
        textPane.add(bookAuthor);
        textPane.add(isRead);
        JLabel label3 = new JLabel();
        label3.setText("Start date: ");
        textPane.add(label3);
        textPane.add(startDate);
        JLabel label4 = new JLabel();
        label4.setText("End date: ");
        textPane.add(label4);
        textPane.add(endDate);
        JLabel label5 = new JLabel();
        label5.setText("Rating out of 5: ");
        textPane.add(label5);
        textPane.add(rating);
        JLabel label6 = new JLabel();
        label6.setText("Link: ");
        textPane.add(label6);
        textPane.add(link);
        textPane.add(addButton);
        textPane.add(removeButton);
        textPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        add(listScroller, BorderLayout.CENTER);
        add(textPane, BorderLayout.WEST);
    }

    public class BookListSelectionListener implements ListSelectionListener {

        public void setOnAction() {
            int index = list.getSelectedIndex();
            JFrame frame = new JFrame("Book Details");
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            Book book = bookList.getBookList().get(index);

            JLabel bookLabel = new JLabel("Title: " + book.getTitle());
            JLabel bookLabel2 = new JLabel("Author: " + book.getAuthor());
            JLabel bookLabel3 = new JLabel("Read? " + book.getIsRead());
            JLabel bookLabel4 = new JLabel("Start date: " + book.getStartDate());
            JLabel bookLabel5 = new JLabel("End date: " + book.getEndDate());
            JLabel bookLabel6 = new JLabel("Rating out of 5: " + book.getRating());
            JLabel bookLabel7 = new JLabel("Link: " + book.getLink());

            panel.add(bookLabel);
            panel.add(bookLabel2);
            panel.add(bookLabel3);
            panel.add(bookLabel4);
            panel.add(bookLabel5);
            panel.add(bookLabel6);
            panel.add(bookLabel7);

            frame.add(panel);
            frame.setSize(400, 200);
            frame.setVisible(true);
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {

            if (e.getValueIsAdjusting()) {
                setOnAction();
            }
        }
    }

    public class AddBookListener implements ActionListener, DocumentListener {

        private JButton button;
        private boolean alreadyEnabled = false;

        public AddBookListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = bookName.getText();
            int numberRating = Integer.parseInt(rating.getText());

            Book b = new Book(name, bookAuthor.getText(), isRead.isSelected(), startDate.getText(),
                    endDate.getText(), numberRating, link.getText());
            bookList.addBook(b);

            int index = list.getSelectedIndex();
            listModel.addElement(name);

            bookName.requestFocusInWindow();
            bookName.setText("");
            bookAuthor.setText("");
            isRead.setSelected(false);
            startDate.setText("");
            endDate.setText("");
            rating.setText("");
            link.setText("");
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

    public class RemoveBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            bookList.removeBook(bookList.getBookList().get(index));
            listModel.remove(index);
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

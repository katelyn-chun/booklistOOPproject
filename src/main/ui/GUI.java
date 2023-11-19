package ui;

import model.Book;
import model.BookList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

// A GUI for a bookkeeping app
// Citation: Used ListDemo as reference from https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
// and https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon for resizing an image
public class GUI extends JPanel
        implements ListSelectionListener {

    private JList list;
    private DefaultListModel listModel;
    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;

    private JTextField bookName;
    private JTextField bookAuthor;
    private JCheckBox isRead;
    private JTextField rating;
    private JTextField startDate;
    private JTextField endDate;
    private JTextField link;
    private BookList bookList;

    private final AddBookListener addListener;
    private final RemoveBookListener removeListener;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_PATH = "./data/booklist.json";

    // MODIFIES: this
    // EFFECTS: sets up a list scroller containing the booklist and panel to add books
    public GUI() throws
            UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        super(new BorderLayout());
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        jsonWriter = new JsonWriter(JSON_PATH);
        jsonReader = new JsonReader(JSON_PATH);
        initializeListModelAndList();

        ListSelectionListener bookListListener = new BookListSelectionListener();
        list.addListSelectionListener(bookListListener);
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(500, 200));

        addListener = new AddBookListener();
        removeListener = new RemoveBookListener();

        createButtons();
        createTextFields();
        JPanel textPane = new JPanel();
        textPane.setLayout(new BoxLayout(textPane, BoxLayout.PAGE_AXIS));

        addLabelsAndTextFieldsToTextPane(textPane);
        add(listScroller, BorderLayout.CENTER);
        add(textPane, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: initializes DefaultListModel and JList, adds a sample book
    public void initializeListModelAndList() {
        listModel = new DefaultListModel();
        list = new JList(listModel);
        bookList = new BookList("BookList");

        listModel.addElement("Sample Book");
        Book sample = new Book("Sample Book", "John Smith", false, "1111", "2222", 5, "www.sample.com");
        bookList.addBook(sample);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
    }

    // MODIFIES: this
    // EFFECTS: initializes 'Add', 'Remove', 'Save', and 'Load' buttons
    // adds listeners to each button
    public void createButtons() {
        addButton = new JButton("Add Book");
        addButton.setActionCommand("Add Book");
        addButton.setEnabled(false);
        addButton.addActionListener(addListener);

        removeButton = new JButton("Remove Book");
        removeButton.setActionCommand("Remove Book");
        removeButton.addActionListener(removeListener);

        saveButton = new JButton("Save Booklist");
        saveButton.setActionCommand("Save Booklist");
        SaveListener saveListener = new SaveListener();
        saveButton.addActionListener(saveListener);

        loadButton = new JButton("Load Booklist");
        loadButton.setActionCommand("Load Booklist");
        LoadListener loadListener = new LoadListener();
        loadButton.addActionListener(loadListener);
    }

    // MODIFIES: this
    // EFFECTS: initializes all text fields and adds a listener to bookName
    public void createTextFields() {
        bookName = new JTextField(15);
        bookName.getDocument().addDocumentListener(addListener);
        bookAuthor = new JTextField(15);
        isRead = new JCheckBox("Read?");
        isRead.setSelected(false);
        startDate = new JTextField(10);
        endDate = new JTextField(10);
        rating = new JTextField("0", 5);
        link = new JTextField(5);
    }

    // EFFECTS: adds the labels and text fields to JPanel for creating a book
    public void addLabelsAndTextFieldsToTextPane(JPanel textPane) {
        JLabel label = new JLabel("Book name: ");
        textPane.add(label);
        textPane.add(bookName);
        JLabel label2 = new JLabel("Author: ");
        textPane.add(label2);
        textPane.add(bookAuthor);
        textPane.add(isRead);
        JLabel label3 = new JLabel("Start date: ");
        textPane.add(label3);
        textPane.add(startDate);
        JLabel label4 = new JLabel("End date: ");
        textPane.add(label4);
        textPane.add(endDate);
        JLabel label5 = new JLabel("Rating out of 5: ");
        textPane.add(label5);
        textPane.add(rating);
        JLabel label6 = new JLabel("Link: ");
        textPane.add(label6);
        textPane.add(link);
        textPane.add(addButton);
        textPane.add(removeButton);
        textPane.add(saveButton);
        textPane.add(loadButton);
    }

    // A listener for when the user selects a book
    public class BookListSelectionListener implements ListSelectionListener {

        public void setOnAction() {
            int index = list.getSelectedIndex();
            JFrame frame = new JFrame("Book Details");
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        // EFFECTS: Opens the book when selected
        @Override
        public void valueChanged(ListSelectionEvent e) {

            if (e.getValueIsAdjusting()) {
                setOnAction();
            }
        }
    }

    // A listener for when the user adds a book
    public class AddBookListener implements ActionListener, DocumentListener {

        private boolean alreadyEnabled = false;

        // EFFECTS: Calls addBookToBooklistAndDisplay() when button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            addBookToBooklistAndDisplay();
        }

        // EFFECTS: Enables the button when user enters a title
        // MODIFIES: this
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (!alreadyEnabled) {
                addButton.setEnabled(true);
            }
        }

        // EFFECTS: Calls handleEmptyText() when user removes something from document
        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyText(e);
        }

        // EFFECTS: Enables the button if document is not empty and not already enabled
        // MODIFIES: this
        @Override
        public void changedUpdate(DocumentEvent e) {

            if (!handleEmptyText(e)) {
                if (!alreadyEnabled) {
                    addButton.setEnabled(true);
                }
            }
        }

        // EFFECTS: Disables button if document length is <= 0 and returns true, otherwise returns false
        // MODIFIES: this
        private boolean handleEmptyText(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                addButton.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }

        // EFFECTS: Adds book attributes to Booklist, and displays the title in the listModel
        // MODIFIES: this
        public void addBookToBooklistAndDisplay() {
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
            rating.setText("0");
            link.setText("");
            list.ensureIndexIsVisible(index);
            displayAddedGraphic();
        }

        // EFFECTS: Displays the checkmark image after book is added
        public void displayAddedGraphic() {
            ImageIcon icon = new ImageIcon("data/checkmark.png");
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);
            label.setVerticalAlignment(SwingConstants.TOP);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setSize(100, 100);
            JLabel labelString = new JLabel("Added!");
            labelString.setVerticalAlignment(SwingConstants.BOTTOM);
            labelString.setHorizontalAlignment(SwingConstants.CENTER);
            JFrame frame = new JFrame();
            frame.add(label);
            frame.add(labelString);
            frame.setPreferredSize(new Dimension(90, 110));
            frame.setBackground(Color.LIGHT_GRAY);
            frame.setLocationRelativeTo(list);
            frame.pack();
            frame.setVisible(true);
        }
    }

    // A listener for when user removes a book
    public class RemoveBookListener implements ActionListener {

        // EFFECTS: Removes book from booklist and from listModel
        // MODIFIES: this
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            bookList.removeBook(bookList.getBookList().get(index));
            listModel.remove(index);
        }
    }

    // EFFECTS: Enables the removeButton if no changes are being made and if a book is selected
    // MODIFIES: this
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            removeButton.setEnabled(list.getSelectedIndex() != -1);
        }
    }

    // A listener for when the user wants to save the booklist
    public class SaveListener implements ActionListener {

        // EFFECTS: saves booklist to file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.openWriter();
                jsonWriter.write(bookList);
                jsonWriter.closeWriter();
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // A listener for when the user wants to load the previous booklist
    public class LoadListener implements ActionListener {

        // EFFECTS: Loads the booklist from file and displays the previous books
        // MODIFIES: this
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                bookList = jsonReader.read();
                listModel.removeAllElements();
                for (Book b : bookList.getBookList()) {
                    String name = b.getTitle();
                    listModel.addElement(name);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    // EFFECTS: Displays GUI
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

    // EFFECTS: Runs GUI
    public static void main(String[] args) throws
            UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        showGUI();
    }
}

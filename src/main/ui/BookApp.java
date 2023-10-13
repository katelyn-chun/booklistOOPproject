package ui;

import model.BookList;
import model.Book;

import java.util.Scanner;

// Bookkeeping application
// CITATION: Used TellerApp as reference from the TellerApp example.

public class BookApp {

    private Scanner input;
    private BookList bookList;

    // EFFECTS: runs the book app

    public BookApp() {
        runBookApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user's input

    public void runBookApp() {
        bookList = new BookList();
        boolean done = false;
        input = new Scanner(System.in).useDelimiter("\n");
        String command;

        while (!done) {
            showMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("x")) {
                done = true;
            } else {
                doCommands(command);
            }
        }
        input.close();
    }

    // EFFECTS: displays options to user

    private void showMenu() {
        System.out.println("\nChoose a command:");
        System.out.println("\ta -> add a book");
        System.out.println("\td -> delete a book");
        System.out.println("\tv -> view reading history");
        System.out.println("\tm -> mark a book as read/unread");
        System.out.println("\tr -> rate a book out of 5");
        System.out.println("\te -> edit the start/end date of a book");
        System.out.println("\tx -> exit");
    }

    // EFFECTS: processes user commands
    // MODIFIES: this

    private void doCommands(String command) {
        switch (command) {
            case "a":
                addBook();
                break;
            case "d":
                deleteBook();
                break;
            case "v":
                viewBooks();
                break;
            case "m":
                markAsRead();
                break;
            case "r":
                editRating();
                break;
            case "e":
                editDates();
                break;
            default:
                System.out.println("Invalid command.");
                break;
        }
    }

    // EFFECTS: adds a new book to bookList
    // MODIFIES: this

    private void addBook() {
        System.out.println("Enter the book title: ");
        String title = input.next();
        System.out.println("Enter the author's name: ");
        String author = input.next();
        System.out.println("Have you finished this book? (y/n)");
        String finished = input.next();
        finished = finished.toLowerCase();

        boolean isFinished = false;

        if (finished.equals("y")) {
            isFinished = true;
        }

        System.out.println("Enter the date you started the book: ");
        String date = input.next();

        if (checkLengthEqualsZero(title) || checkLengthEqualsZero(author) || checkLengthEqualsZero(date)) {
            System.out.println("Add failed. Length must be greater than 0.");
        } else {
            Book book = new Book(title, author, isFinished, date);
            bookList.addBook(book);
            System.out.println("Book added!");
        }
    }

    // EFFECTS: checks if the length of a string is 0

    private boolean checkLengthEqualsZero(String str) {
        if (str.length() == 0) {
            System.out.println("Error, length must be greater than 0.");
            return true;
        }
        return false;
    }

    // EFFECTS: removes an existing book from bookList
    // MODIFIES: this

    private void deleteBook() {
        System.out.println("Enter the book title: ");
        String name = input.next();
        name = name.toLowerCase();
        boolean found = false;

        for (int i = 0; i < bookList.getBookList().size(); i++) {
            Book b = bookList.getBookList().get(i);
            if (bookList.getBookList().get(i).getTitle().toLowerCase().equals(name)) {
                bookList.removeBook(b);
                found = true;
            }
        }
        if (found) {
            System.out.println("Remove successful.");
        } else {
            System.out.println("Remove failed; book not found.");
        }
    }

    // EFFECTS: prints information of each book in bookList

    private void viewBooks() {
        if (bookList.getBookList().isEmpty()) {
            System.out.println("No books to view.");
        } else {
            for (Book b : bookList.getBookList()) {
                System.out.println("Title: " + b.getTitle() +  "\nAuthor: " + b.getAuthor()
                        + "\nRead? " + b.getIsRead() + "\nRating: " + b.getRating() + "/5"
                        + "\nStart Date: " + b.getStartDate() + "\nEnd Date: " + b.getEndDate()
                        + "\n----------------------------");
            }
        }
    }

    // EFFECTS: takes in user input to mark a book to be read or unread
    // MODIFIES: this

    private void markAsRead() {
        System.out.println("Enter the book title to select: ");
        String name = input.next();
        name = name.toLowerCase();
        boolean found = false;
        for (Book b : bookList.getBookList()) {
            if (b.getTitle().equals(name)) {
                found = true;
                System.out.println("Do you want to mark it as read or unread? (r/u)");
                String command = input.next();
                if (command.equals("r")) {
                    setAsRead(name);
                } else if (command.equals("u")) {
                    setAsUnread(name);
                } else {
                    System.out.println("Invalid command; type 'r' or 'u'");
                }
            }
        }
        if (!found) {
            System.out.println("Error, book not found.");
        }
    }
    // EFFECTS: marks a book as read
    // MODIFIES: this

    private void setAsRead(String name) {
        boolean found = false;
        for (Book b : bookList.getBookList()) {
            if (b.getTitle().equals(name)) {
                b.editStatus(true);
                found = true;
            }
        }
        if (found) {
            System.out.println(name + " is now marked as read.");
        } else {
            System.out.println(name + " is not in your book list.");
        }
    }

    // EFFECTS: marks a book as unread
    // MODIFIES: this

    private void setAsUnread(String name) {
        boolean found = false;
        for (Book b : bookList.getBookList()) {
            if (b.getTitle().toLowerCase().equals(name)) {
                b.editStatus(false);
                found = true;
            }
        }
        if (found) {
            System.out.println(name + " is now marked as unread.");
        } else {
            System.out.println(name + " is not in your book list.");
        }
    }

    // EFFECTS: edits the book's rating
    // MODIFIES: this

    private void editRating() {
        System.out.println("Enter the book you would like to rate: ");
        String name = input.next();
        name = name.toLowerCase();
        System.out.println("Enter a rating out of 5: ");
        int rating = input.nextInt();
        boolean found = false;

        for (Book b : bookList.getBookList()) {
            if (b.getTitle().toLowerCase().equals(name)) {
                b.editRating(rating);
                found = true;
            }
        }
        if (found) {
            System.out.println("Rating successful.");
        } else {
            System.out.println("Rating failed; book not found.");
        }
    }

    // EFFECTS: edits the book's start or end date
    // MODIFIES: this

    private void editDates() {
        System.out.println("Enter the book you would like to edit: ");
        String name = input.next();
        name = name.toLowerCase();
        System.out.println("Would you like to edit the start date or end date? (s/e): ");
        String command = input.next();
        System.out.println("Enter the new date: ");
        String newDate = input.next();
        boolean found = false;

        for (Book b : bookList.getBookList()) {
            if (b.getTitle().toLowerCase().equals(name)) {
                if (command.equals("s")) {
                    b.editStartDate(newDate);
                } else if (command.equals("e")) {
                    b.editEndDate(newDate);
                }
                found = true;
            }
        }
        if (found) {
            System.out.println("Edit complete.");
        } else {
            System.out.println("Edit failed; book not found.");
        }
    }
}

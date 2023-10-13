package model;

import java.util.ArrayList;
import java.util.List;

// A book-list manager

public class BookList {
    private List<Book> bookList;

    // EFFECTS: constructs an empty list of books

    public BookList() {
        bookList = new ArrayList<>();
    }

    // EFFECTS: returns list of books

    public List<Book> getBookList() {
        return bookList;
    }

    // EFFECTS: adds a book to list of books if it has not been added
    //          returns true if add was successful, false if unsuccessful
    // MODIFIES: this

    public boolean addBook(Book book) {
        if (bookList.isEmpty()) {
            bookList.add(book);
        } else {
            for (Book b : bookList) {
                if (b == book) {
                    return false;
                }
            }
            bookList.add(book);
        }
        return true;
    }

    // EFFECTS: removes a book from list of books if it exists
    //          returns true if remove was successful, false if unsuccessful
    // MODIFIES: this

    public boolean removeBook(Book book) {
        for (Book b : bookList) {
            if (b == book) {
                bookList.remove(book);
                return true;
            }
        }
        return false;
    }
}

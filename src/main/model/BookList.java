package model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

// A book-list manager

public class BookList implements Writable {
    private List<Book> bookList;
    private String name;

    // EFFECTS: constructs an empty list of books
    public BookList(String name) {
        bookList = new ArrayList<>();
        this.name = name;
    }

    // EFFECTS: returns list of books
    public List<Book> getBookList() {
        EventLog.getInstance().logEvent(new Event("Viewed a book"));
        return bookList;
    }

    // EFFECTS: returns book-list name
    public String getName() {
        return name;
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
        EventLog.getInstance().logEvent(new Event("Added book: " + book.getTitle()));
        return true;
    }

    // EFFECTS: removes a book from list of books if it exists
    //          returns true if remove was successful, false if unsuccessful
    // MODIFIES: this
    public boolean removeBook(Book book) {
        for (Book b : bookList) {
            if (b == book) {
                bookList.remove(book);
                EventLog.getInstance().logEvent(new Event("Removed book: " + book.getTitle()));
                return true;
            }
        }
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("booklist", booklistToJson());
        return json;
    }

    // EFFECTS: returns books in the booklist as a JSON array
    private JSONArray booklistToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Book b : bookList) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }
}

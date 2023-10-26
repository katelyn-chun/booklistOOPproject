package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a book with a title, author, rating, and a status of read or unread
// CITATION: Used Account as reference from the TellerApp example.

public class Book implements Writable {
    private String title;
    private String author;
    private int rating;
    private boolean isRead;
    private String startDate;
    private String endDate;
    private String link;

    // REQUIRES: a title, author, and startDate with length greater than 0
    // EFFECTS: sets the book's title and author, and its read or unread status, start date, and empty end date
    //          sets the initial book rating to 0
    public Book(String title, String author, boolean read, String startDate, String endDate, int rating, String link) {
        this.title = title;
        this.author = author;
        this.isRead = read;
        this.rating = rating;
        this.startDate = startDate;
        this.endDate = endDate;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getRating() {
        return rating;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getLink() {
        return link;
    }

    // REQUIRES: an integer rating >= 0 and <= 5
    // MODIFIES: this
    // EFFECTS: updates the book's rating out of 5

    public void editRating(int newRating) {
        if (newRating >= 0 && newRating <= 5) {
            rating = newRating;
        }
    }

    // REQUIRES: a title with length > 0
    // MODIFIES: this
    // EFFECTS: updates the book's title

    public void editTitle(String newTitle) {
        if (newTitle.length() > 0) {
            title = newTitle;
        }
    }

    // REQUIRES: an author with length > 0
    // MODIFIES: this
    // EFFECTS: updates the book's author

    public void editAuthor(String newAuthor) {
        if (newAuthor.length() > 0) {
            author = newAuthor;
        }
    }

    // MODIFIES: this
    // EFFECTS: edits the book's status to either read or unread

    public void editStatus(boolean read) {
        isRead = read;
    }

    // REQUIRES: a start date with length > 0
    // EFFECTS: edits the start date of the book
    // MODIFIES: this

    public void editStartDate(String newDate) {
        startDate = newDate;
    }

    // REQUIRES: an end date with length > 0
    // EFFECTS: edits the end date of the book
    // MODIFIES: this

    public void editEndDate(String newDate) {
        endDate = newDate;
    }

    // REQUIRES: a link as a string with length > 0
    // EFFECTS: adds a link to a book
    // MODIFIES: this

    public void addLink(String linkString) {
        link = linkString;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("author", author);
        json.put("rating", rating);
        json.put("isRead", isRead);
        json.put("startDate", startDate);
        json.put("endDate", endDate);
        json.put("link", link);
        return json;
    }
}

package model;

// Represents a book with a title, author, rating, and a status of read or unread
public class Book {
    private String title;
    private String author;
    private int rating;
    private boolean isRead;
    private String startDate;
    private String endDate;

    // REQUIRES: a title and author with length greater than 0
    // EFFECTS: sets the book's title and author, and its read or unread status
    public Book(String title, String author, boolean read, String startDate) {
        this.title = title;
        this.author = author;
        this.isRead = read;
        this.rating = 0;
        this.startDate = startDate;
        this.endDate = "-- / -- / --";
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

    // EFFECTS: displays all information about the book

    public String showBook() {
        String status;
        if (isRead) {
            status = "Yes";
        } else {
            status = "No";
        }
        return "Title: " + title +  "\nAuthor: " + author
                + "\nRead? " + status + "\nRating: " + rating + "/5"
                + "\nStart Date: " + startDate + "\nEnd Date: " + endDate;
    }

    public void editStartDate(String newDate) {
        startDate = newDate;
    }

    public void editEndDate(String newDate) {
        endDate = newDate;
    }
}

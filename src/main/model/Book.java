package model;

// Represents a book with a title, author, rating, and a status of read or unread
public class Book {
    private String title;
    private String author;
    private int rating;
    private boolean isRead;

    // REQUIRES: a title and author with length greater than 0
    // EFFECTS: sets the book's title and author, and its read or unread status
    public Book(String title, String author, boolean read) {
        this.title = title;
        this.author = author;
        this.isRead = read;
        this.rating = 0;
    }

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }

    int getRating() {
        return rating;
    }

    boolean getIsRead() {
        return isRead;
    }

    // REQUIRES: an integer rating >= 0
    // MODIFIES: this
    // EFFECTS: updates the book's rating out of 5

    void editRating(int newRating) {
        if (newRating >= 0) {
            rating = newRating;
        }
    }

    // REQUIRES: a title with length > 0
    // MODIFIES: this
    // EFFECTS: updates the book's title

    void editTitle(String newTitle) {
        if (newTitle.length() > 0) {
            title = newTitle;
        }
    }

    // REQUIRES: an author with length > 0
    // MODIFIES: this
    // EFFECTS: updates the book's author

    void editAuthor(String newAuthor) {
        if (newAuthor.length() > 0) {
            author = newAuthor;
        }
    }

    void editStatus(boolean read) {
        isRead = read;
    }

    String showBook() {
        String status;
        if (isRead) {
            status = "Yes";
        } else {
            status = "No";
        }
        return "Title: " + title +  "\nAuthor: " + author
                + "\nRead? " + isRead + "\nRating: " + rating;
    }
}

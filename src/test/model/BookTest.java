package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests the Book class
class BookTest {
    private Book book1;
    private Book book2;
    private BookList bookList;

    @BeforeEach
    void doBefore() {
        book1 = new Book("A Brief History of Time", "S. Hawking", true);
        book2 = new Book("Atomic Habits", "J. Clear", false);
        bookList = new BookList();
    }

    @Test
    void testConstructor() {
        assertEquals("A Brief History of Time", book1.getTitle());
        assertEquals("S. Hawking", book1.getAuthor());
        assertTrue(book1.getIsRead());
        assertEquals(0, book1.getRating());
    }

    @Test
    void testEditRating() {
        book1.editRating(3);
        assertEquals(3, book1.getRating());
        book2.editRating(-1);
        assertEquals(0, book2.getRating());
    }

    @Test
    void testEditTitle() {
        book1.editTitle("A Brief History");
        assertEquals("A Brief History", book1.getTitle());
        book2.editTitle("");
        assertEquals("Atomic Habits", book2.getTitle());
    }

    @Test
    void testEditAuthor() {
        book1.editAuthor("Stephen Hawking");
        assertEquals("Stephen Hawking", book1.getAuthor());
        book2.editAuthor("");
        assertEquals("J. Clear", book2.getAuthor());
    }

    @Test
    void testEditStatus() {
        book2.editStatus(true);
        assertTrue(book2.getIsRead());
    }
}
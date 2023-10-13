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
        book1 = new Book("A Brief History of Time", "S. Hawking", true, "10/01/2023");
        book2 = new Book("Atomic Habits", "J. Clear", false, "06/30/2022");
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

    @Test
    void testEditDates() {
        assertEquals("10/01/2023", book1.getStartDate());
        book1.editStartDate("10/05/2023");
        assertEquals("10/05/2023", book1.getStartDate());
        assertEquals("-- / -- / --", book2.getEndDate());
        book2.editEndDate("07/07/2022");
        assertEquals("07/07/2022", book2.getEndDate());
    }

    @Test
    void testBookListConstructor() {
        assertTrue(bookList.getBookList().isEmpty());
        assertEquals(0, bookList.getBookList().size());
    }

    @Test
    void testAddBook() {
        bookList.addBook(book1);
        assertEquals(book1, bookList.getBookList().get(0));
        bookList.addBook(book2);
        assertEquals(book2, bookList.getBookList().get(1));
        assertEquals(2, bookList.getBookList().size());
    }

    @Test
    void testAddBookDuplicate() {
        bookList.addBook(book1);
        bookList.addBook(book1);
        assertEquals(1, bookList.getBookList().size());
    }

    @Test
    void testRemoveBook() {
        bookList.addBook(book1);
        assertEquals(book1, bookList.getBookList().get(0));
        bookList.addBook(book2);
        assertEquals(book2, bookList.getBookList().get(1));
        assertTrue(bookList.removeBook(book1));
        assertEquals(1, bookList.getBookList().size());
        assertEquals(book2, bookList.getBookList().get(0));
    }

    @Test
    void testRemoveBookFail() {
        bookList.addBook(book1);
        assertFalse(bookList.removeBook(book2));
    }
}
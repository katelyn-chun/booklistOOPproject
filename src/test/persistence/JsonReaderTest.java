package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    @Test
    void testReaderNoFile() {
        JsonReader reader = new JsonReader("./data/blank.json");
        try {
            BookList bookList = reader.read();
            fail("IOException should be thrown");
        } catch (IOException e) {
            // good
        }
    }
    @Test
    void testReaderEmptyBookList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBookList");
        try {
            BookList bookList = reader.read();
            assertEquals("My Booklist", bookList.getName());
        } catch (IOException e) {
            fail("Did not read file");
        }
    }
    @Test
    void testReaderGeneralBookList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBookList");
        try {
            BookList bookList = reader.read();
            assertEquals("My Booklist",bookList.getName());
            List<Book> books = bookList.getBookList();
            assertEquals(2, bookList.getBookList().size());
            assertEquals("A Brief History", books.get(0).getTitle());
            assertEquals(5, books.get(0).getRating());
            assertEquals("2/2/2009", books.get(1).getStartDate());
            assertTrue(books.get(0).getIsRead());
        } catch (IOException e) {
            fail("Did not read file");
        }

    }
}

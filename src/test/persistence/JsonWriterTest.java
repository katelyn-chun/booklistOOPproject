package persistence;

import model.Book;
import model.BookList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests the Json Writer
// Citation: Used JsonSerializationDemo as reference
public class JsonWriterTest {
    @Test
    void testWriterBadFile() {
        try {
            BookList bookList = new BookList("My booklist");
            JsonWriter writer = new JsonWriter("./data/invalidFile\0.json");
            writer.openWriter();
            fail("Exception was expected");
        } catch (IOException e) {
            // good
        }
    }
    @Test
    void testWriterEmptyWorkroom() {
        try {
            BookList bookList = new BookList("My Booklist");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBookList");
            writer.openWriter();
            writer.write(bookList);
            writer.closeWriter();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBookList");
            bookList = reader.read();
            assertEquals("My Booklist", bookList.getName());
            assertEquals(0, bookList.getBookList().size());
        } catch (IOException e) {
            fail("Exception should not be thrown");
        }
    }
    @Test
    void testWriterGeneralBookList() {
        try {
            BookList bookList = new BookList("My Booklist");
            bookList.addBook(new Book("A Brief History", "Hawking", true, "1/1/2022", "1/3/2022", 5, "---"));
            bookList.addBook(new Book("7 Silly Eaters", "Smith", true, "2/2/2009", "n/a", 4, "---"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBookList");
            writer.openWriter();
            writer.write(bookList);
            writer.closeWriter();
            JsonReader reader = new JsonReader("./data/testWriterGeneralBookList");
            bookList = reader.read();
            assertEquals("My Booklist", bookList.getName());
            List<Book> books = bookList.getBookList();
            assertEquals(2, books.size());
            assertEquals("A Brief History", books.get(0).getTitle());
            assertEquals(4, books.get(1).getRating());
            assertEquals("Smith", books.get(1).getAuthor());
            assertTrue(books.get(0).getIsRead());
        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
    }
}

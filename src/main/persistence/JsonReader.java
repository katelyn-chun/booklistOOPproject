package persistence;

import model.Book;
import model.BookList;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

// Represents a reader that reads book-list from JSON data stored in file
// Citation: Used JsonSerializationDemo as reference
public class JsonReader {
    private String source;

    // EFFECTS: constructs Json reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads booklist from file and returns it
    //          throws IOException if an error occurs reading data from file
    public BookList read() throws IOException {
        String data = readFile(source);
        JSONObject object = new JSONObject(data);
        return parseBookList(object);
    }

    // EFFECTS: reads source file as string and returns it
    //          throws IOException if an error occurs from reading file
    private String readFile(String source) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }
        return builder.toString();
    }

    // EFFECTS: parses book-list from JSON object and returns it
    private BookList parseBookList(JSONObject object) {
        String name = object.getString("name");
        BookList bookList = new BookList(name);
        addBooks(bookList, object);
        return bookList;
    }

    // MODIFIES: bookList
    // EFFECTS: parses books from JSON object and adds to booklist
    private void addBooks(BookList bookList, JSONObject object) {
        JSONArray array = object.getJSONArray("booklist");
        for (Object json : array) {
            JSONObject nextBook = (JSONObject) json;
            addBook(bookList, nextBook);
        }
    }

    // MODIFIES: bookList
    // EFFECTS: parses a book from JSON object and adds to booklist
    private void addBook(BookList bookList, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String author = jsonObject.getString("author");
        boolean isRead = jsonObject.getBoolean("isRead");
        String startDate = jsonObject.getString("startDate");
        String endDate = jsonObject.getString("endDate");
        int rating = jsonObject.getInt("rating");
        String link = jsonObject.getString("link");
        Book b = new Book(title, author, isRead, startDate, endDate, rating, link);
        bookList.addBook(b);
    }

}

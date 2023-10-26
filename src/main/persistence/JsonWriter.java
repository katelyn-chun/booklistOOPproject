package persistence;

import model.BookList;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.*;

// Represents a  writer that writes JSON representation of workroom to file

public class JsonWriter {
    private PrintWriter writer;
    private String target;
    private static final int TAB = 3;

    // EFFECTS: constructs JSON writer to write to target file
    public JsonWriter(String target) {
        this.target = target;
    }

    // MODIFIES: this
    // EFFECTS: opens writer
    //          throws FileNotFoundException if target file cannot be opened for writing
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(new File(target));
    }

    // MODIFIES: this
    // EFFECTS: writes a JSON book-list to file
    public void write(BookList bookList) {
        JSONObject json = bookList.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void closeWriter() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.println(json);
    }
}

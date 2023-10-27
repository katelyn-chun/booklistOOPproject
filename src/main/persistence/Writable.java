package persistence;

import org.json.JSONObject;

// Template for Book and BookList classes
// Citation: Used JsonSerializationDemo as reference
public interface Writable {

    // EFFECTS: returns this as a JSON object

    JSONObject toJson();
}

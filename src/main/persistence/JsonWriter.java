package persistence;

import model.*;
import org.json.JSONObject;
import java.io.BufferedReader;



import java.io.*;

// Represents a writer that writes JSON representation of the app to file
// NOTE: This class uses code taken from the JsonSerializationDemo provided in the course
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    // Effects : constructs a json writer with the destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Modifies: this
    // Effects: opens the writer, throws IOException if the destination file not found
    public void openWriter() throws IOException {
        writer = new PrintWriter(new File(destination));
    }

    // Modifies: this
    // Effects: writes the Json representation of region to the file
    public void write(Region r) {
        JSONObject myObject = r.toJson();
        saveToFile(myObject.toString(5));
    }

    // Modifies : this
    // Effects: writes string to file
    private void saveToFile(String toBePrinted) {
        writer.print(toBePrinted);
    }

    // Modifies: this
    // Effects : closes the writer
    public void closeWriter() {
        writer.close();
    }


}

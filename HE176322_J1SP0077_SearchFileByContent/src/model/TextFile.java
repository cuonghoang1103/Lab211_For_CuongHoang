package model;

import java.util.ArrayList;

/**
 * MODEL: one text file that was read - its name and its lines - a JavaBean (private
 * fields, public no-argument constructor, getters/setters).
 *
 * @author HE176322
 */
public class TextFile {

    // The file name.
    private String name;
    // The lines of the file, in file order.
    private ArrayList<String> lines;

    // JavaBean constructor: an empty text.
    public TextFile() {
        this.lines = new ArrayList<>();
    }

    // Creates a text file with its lines.
    public TextFile(String name, ArrayList<String> lines) {
        this.name = name;
        this.lines = lines;
    }

    // Returns the name.
    public String getName() {
        return name;
    }

    // Sets the name.
    public void setName(String name) {
        this.name = name;
    }

    // Returns the lines.
    public ArrayList<String> getLines() {
        return lines;
    }

    // Sets the lines.
    public void setLines(ArrayList<String> lines) {
        this.lines = lines;
    }
}

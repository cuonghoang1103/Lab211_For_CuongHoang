package model;

import java.util.ArrayList;

/**
 * MODEL: one text file that was read - its name and its lines - a JavaBean (private
 * fields, public no-argument constructor, getters/setters). The name is the path typed
 * (option 1) or the file's name inside the folder (option 2).
 *
 * @author HE176322
 */
public class TextFile {

    // The file name.
    private String name;

    // The lines of the file, in file order.
    private ArrayList<String> lineList;

    // JavaBean constructor: an empty text.
    public TextFile() {
        this.lineList = new ArrayList<>();
    }

    // Creates a text file with its lines.
    public TextFile(String name, ArrayList<String> lineList) {
        this.name = name;
        this.lineList = lineList;
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
    public ArrayList<String> getLineList() {
        return lineList;
    }

    // Sets the lines.
    public void setLineList(ArrayList<String> lineList) {
        this.lineList = lineList;
    }
}
